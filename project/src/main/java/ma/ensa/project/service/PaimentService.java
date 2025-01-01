package ma.ensa.project.service;

import ma.ensa.project.Connexion;
import ma.ensa.project.entity.Paiement;
import ma.ensa.project.entity.User;
import ma.ensa.project.repo.PaiementRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaimentService implements PaiementRepo {
    private Connexion connexion;
    private Connection con;

    public PaimentService() throws SQLException, ClassNotFoundException {
        connexion=new Connexion();
        con=connexion.getCon();
    }
    @Override
    public Paiement addPaiement(Paiement paiement, User user) throws SQLException {

        PreparedStatement ps = con.prepareCall("INSERT INTO paiement(date,idCommande,idUser) values(?,?,?)");

        ps.setDate(1, paiement.getDate());
        ps.setInt(2, paiement.getCommandeId());

        ps.setInt(3, user.getId());
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            // Retrieve generated keys
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1); // Retrieve the generated key
                    paiement.setId(generatedId);   // Set the ID to the Paiement object
                }
            }
        } else {
            throw new SQLException("Failed to insert Paiement. No rows affected.");
        }


    return paiement;

    }


    @Override
    public boolean updatePaiement(Paiement paiement) throws SQLException {
        PreparedStatement ps=con.prepareCall("UPDATE Paiement set date=? where id=?");

        ps.setDate(1,paiement.getDate());
        ps.setInt(2,paiement.getId());
        return ps.executeUpdate()!=0;

    }

    @Override
    public Paiement getPaiement(int id) throws SQLException {
        PreparedStatement ps=con.prepareCall("SELECT * FROM Paiement where id=?");
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
        Paiement paiement=new Paiement();

        if(rs.next()){
            paiement.setId(rs.getInt("id"));
            paiement.setCommandeId(rs.getInt("idCommande"));
            paiement.setDate(rs.getDate("date"));
            paiement.setIdUser(rs.getInt("idUser"));

        }
        return paiement;
    }

    @Override
    public List<Paiement> getAllPaiement() throws SQLException {
        PreparedStatement ps=con.prepareCall("SELECT * FROM Paiement ");

        ResultSet rs=ps.executeQuery();
        List<Paiement> paiements=new ArrayList<Paiement>();

        while (rs.next()){
            Paiement paiement=new Paiement();
            paiement.setId(rs.getInt("id"));
            paiement.setCommandeId(rs.getInt("idCommande"));
            paiement.setDate(rs.getDate("date"));
            paiement.setIdUser(rs.getInt("idUser"));
            paiements.add(paiement);
        }
        return paiements;
    }
}
