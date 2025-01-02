package ma.ensa.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ma.ensa.project.ApplicationGestionFacturation;
import ma.ensa.project.Connexion;
import ma.ensa.project.entity.Paiement;
import ma.ensa.project.entity.Permission;
import ma.ensa.project.entity.User;
import ma.ensa.project.repo.UserRepo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserService implements UserRepo {
    private Connexion connection;
    private Connection con;

    public UserService() throws SQLException, ClassNotFoundException {
        connection=new Connexion();
        con=connection.getCon();
    }
    public String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
    public boolean login(User user) throws SQLException, IOException {
        PreparedStatement ps=con.prepareCall("SELECT password from user where username=?");
        ps.setString(1,user.getNomUtilisateur());
        ResultSet rs=ps.executeQuery();
        boolean log=false;
        if(rs.next()){
            log= BCrypt.checkpw(user.getMotDePasse(), rs.getString("password"));
        }
        if(log){

            try {
                PreparedStatement ps1=con.prepareCall("SELECT id,role from user where username=?");
                ps1.setString(1,user.getNomUtilisateur());
                ResultSet rs1=ps1.executeQuery();

                if(rs1.next()){
                    user.setId(rs1.getInt(1));
                    user.setRole(rs1.getString(2));
                }
                // Créer l'ObjectMapper
                ObjectMapper mapper = new ObjectMapper();
                String resourceUrl = Objects.requireNonNull(ApplicationGestionFacturation.class.getResource("login/login.json")).toExternalForm();

                System.out.println(resourceUrl.substring(6).replace("/","//"));
                // Chemin du fichier
                File jsonFile = new File(resourceUrl.substring(6).replace("/","//"));

                // Créer le nouveau JSON
                ObjectNode newJson = mapper.createObjectNode();
                newJson.put("id", user.getId());
                newJson.put("role", user.getRole());

                // Lire le fichier JSON existant
                ObjectNode existingJson;
                if (jsonFile.exists()) {
                    existingJson = (ObjectNode) mapper.readTree(jsonFile);
                } else {
                    existingJson = mapper.createObjectNode();
                }

                // Vérifier si les valeurs sont différentes
                if (!((ObjectNode) newJson).equals(existingJson)) {
                    // Mettre à jour les valeurs
                    existingJson.put("id", user.getId());
                    existingJson.put("role", user.getRole());

                    // Écrire dans le fichier
                    mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, existingJson);
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        return log;

    }
    public  User getSession() throws IOException {
        User user=new User();
        ObjectMapper mapper = new ObjectMapper();
        String resourceUrl = Objects.requireNonNull(ApplicationGestionFacturation.class.getResource("login/login.json")).toExternalForm();
        File jsonFile = new File(resourceUrl.substring(6).replace("/","//"));
        System.out.println(resourceUrl.substring(6).replace("/","//"));

        System.out.println(jsonFile.exists());

        ObjectNode newJson=mapper.readValue(jsonFile, ObjectNode.class);
        user.setId(newJson.get("id").asInt());
        user.setRole(newJson.get("role").asText());
        return user;

    }

    @Override
    public boolean addUser(User user, List<String> permissions) throws SQLException {
        String p1=user.getMotDePasse();
        BCrypt bCrypt=new BCrypt();
        p1=hashPassword(p1);
        System.out.println(p1);
        PreparedStatement ps =con.prepareCall("INSERT INTO user(username,password,role) VALUES(?,?,?)") ;
        ps.setString(1,user.getNomUtilisateur());
        ps.setString(2,p1);
        ps.setString(3,user.getRole());
        boolean result=ps.executeUpdate()>0;
        ResultSet id=ps.getGeneratedKeys();
        if(id.next()) {
            int i = id.getInt(1);

        if(result){
        for(String p:permissions){

            PreparedStatement ps1 =con.prepareCall("INSERT INTO permissions(permission,idUser) VALUES(?,?)") ;

            ps1.setString(1,p);
            ps1.setInt(2,i);
            ps1.executeUpdate();
        }

        }}
        return result;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {

        PreparedStatement ps =con.prepareCall("delete from permissions where idUser=?") ;
        ps.setInt(1,id);
        ps.executeUpdate();


        PreparedStatement ps1 =con.prepareCall("delete from user where id=?") ;
        ps1.setInt(1,id);
        return ps1.executeUpdate()>0;



    }

    @Override
    public boolean updateUser(User user,List<Permission> permissions) throws SQLException {
        PreparedStatement ps =con.prepareCall("update  user VALUES  set username=?,password=?,role=? where id=?  ") ;
        ps.setInt(4,user.getId());
        ps.setString(1,user.getNomUtilisateur());
        ps.setString(2,user.getMotDePasse());
        ps.setString(3,user.getRole());
        boolean result=ps.executeUpdate()>0;
        if(result){
            for(Permission p:permissions){
                PreparedStatement ps1 =con.prepareCall("SELECT * from permissions WHERE idUser=?") ;
                ps1.setInt(1,user.getId());
                ResultSet rs = ps1.executeQuery();

                List<Permission> permissions1=new ArrayList<>();
                while(rs.next()){
                    Permission permission=new Permission();
                    permission.setId(rs.getInt("id"));
                    permission.setNom(rs.getString("permission"));
                    permission.setIdUser(rs.getInt("idUser"));
                    permissions1.add(permission);
                }
                for(Permission p2:permissions){
                    if(!permissions1.contains(p2)){

                        PreparedStatement ps3 =con.prepareCall("INSERT INTO permissions(permission,idUser) VALUES(?,?)") ;

                        ps3.setString(1,p.getNom());
                        ps3.setInt(2,user.getId());
                        ps1.executeUpdate();
                    }
                }
                for(Permission p3:permissions1){
                    if(!permissions.contains(p3)) {
                        PreparedStatement ps4 = con.prepareCall("DELETE FROM permissions WHERE id=?");
                        ps4.setInt(1, p3.getId());
                        ps4.executeUpdate();
                    }
                }
                ps.setInt(1,user.getId());
                ps1.executeQuery();
            }
        }
        return result;
    }

    @Override
    public User getUser(int id) throws SQLException {
        PreparedStatement ps=this.con.prepareCall("SELECT * from user where id=?") ;
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
        User user=new User();
        if(rs.next()){
            user.setId(rs.getInt("id"));
            user.setNomUtilisateur(rs.getString("username"));
            user.setMotDePasse(rs.getString("password"));
            user.setRole(rs.getString("role"));

        }
        return user;


    }




    public List<Permission> getUserPermissions(User user) throws SQLException {
        PreparedStatement ps=this.con.prepareCall("SELECT * from permissions where idUser=?") ;
        ps.setInt(1,user.getId());
        ResultSet rs=ps.executeQuery();

        List<Permission> permissions=new ArrayList<>();
        while(rs.next()){
            Permission permission=new Permission();
            permission.setId(rs.getInt("id"));
            permission.setNom(rs.getString("permission"));
            permission.setIdUser(rs.getInt("idUser"));
            permissions.add(permission);
        }
        return permissions;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        PreparedStatement ps=this.con.prepareCall("SELECT * from user");
        ResultSet rs= ps.executeQuery();
        List<User> users=new ArrayList<User>();
        while (rs.next()){
            User user=new User();
            user.setId(rs.getInt("id"));
            user.setNomUtilisateur(rs.getString("username"));
            user.setMotDePasse(rs.getString("password"));
            user.setRole(rs.getString("role"));
            users.add(user);


        }

        return users;
    }
}
