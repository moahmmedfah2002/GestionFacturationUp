package ma.ensa.project.service;

import ma.ensa.project.Connexion;

import ma.ensa.project.entity.Produit;
import ma.ensa.project.repo.ProduitRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ProduitService implements ProduitRepo {
  private Connexion connection;
  private Connection con;

    public ProduitService() throws SQLException, ClassNotFoundException {
      connection=new Connexion();
      con=connection.getCon();
    }

    @Override
    public boolean addProduit(Produit p,int UserId) throws SQLException {

        String sql="Insert into Produit(nom,prix,quantitéDisponible,idUser,tva)values(?,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,p.getNom());
        pstmt.setDouble(2,p.getPrix());
        pstmt.setInt(3,p.getQuantiteDisponible());
        pstmt.setFloat(5,p.getTva());
        pstmt.setInt(4,UserId);
        return pstmt.executeUpdate()!=0;

    }

    @Override
    public void updateProduit(Produit p) throws SQLException {
        String sql="Update Produit set nom=?,prix=?,quantitéDisponible=?, tva=? where id=?";
        PreparedStatement str = con.prepareStatement(sql);
        str.setString(1, p.getNom());
        str.setDouble(2,p.getPrix());
        str.setInt(3,p.getQuantiteDisponible());
        str.setFloat(4,p.getTva());
        str.setInt(5,p.getId());
        str.executeUpdate();


    }

    @Override
    public void deleteProduit(int id) throws SQLException {
        String sql="Delete from produit where id=?";

        PreparedStatement str=con.prepareStatement(sql);
        str.setInt(1,id);
        str.executeUpdate();
        

    }

    @Override
    public Produit getProduit(int id) throws SQLException {

        String sql="Select * from produit where id=?";
        PreparedStatement str=con.prepareStatement(sql);
        str.setInt(1,id);
        ResultSet rs=str.executeQuery();
        Produit p=new Produit();
        if (rs.next()) {
          int idpro=  rs.getInt("id");
          p.setId(idpro);
          String name=    rs.getString("nom");
          p.setNom(name);
          float prix=     rs.getFloat("prix");
          p.setPrix(prix);
          int quantity=    rs.getInt("quantitédisponible");
          p.setQuantiteDisponible(quantity);
          int idUser=rs.getInt("idUser");
          p.setUserId(idUser);
          float tva= rs.getFloat("tva");
          p.setTva(tva);



        }
        return  p;

    }


    @Override
    public Produit getProduitByName(String name) throws SQLException {
        String sql = "SELECT * FROM Produit WHERE nom = ?";
        PreparedStatement str = con.prepareStatement(sql);
        str.setString(1, name);
        ResultSet rs = str.executeQuery();
        Produit p = new Produit();
        if (rs.next()) {
            int idpro = rs.getInt("id");
            p.setId(idpro);
            p.setNom(rs.getString("nom"));
            p.setPrix(rs.getFloat("prix"));
            p.setQuantiteDisponible(rs.getInt("quantitéDisponible"));
            p.setUserId(rs.getInt("idUser"));
            p.setTva(rs.getFloat("tva"));
        }
        return p;
    }

    @Override
    public List<Produit> getAllProduits() throws SQLException {
        List<Produit> produits=new ArrayList<>();
        String sql="Select * from Produit";
        PreparedStatement str= con.prepareStatement(sql);
        ResultSet rs=str.executeQuery();

        while (rs.next()) {
            int idpro=  rs.getInt("id");
            String name=    rs.getString("nom");
            float prix=     rs.getFloat("prix");
            int quantity=    rs.getInt("quantitéDisponible");

            int idUser=rs.getInt("idUser");
            float tva= rs.getFloat("tva");
            Produit p=new Produit(idpro,name,prix,quantity,idUser,tva);
            produits.add(p);
        }
        return produits;
    }


    @Override
    public float CalculTTC(int id) throws SQLException{
        Produit pr=getProduit(id);
        float ht=pr.getPrix();
        float tva=pr.getTva();
        return ht * (1+tva/100);

    }
}

