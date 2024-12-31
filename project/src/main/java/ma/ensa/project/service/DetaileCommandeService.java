package ma.ensa.project.service;

import ma.ensa.project.Connexion;
import ma.ensa.project.entity.DetaileCommande;
import ma.ensa.project.entity.Produit;
import ma.ensa.project.repo.DetaileCommandeRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetaileCommandeService implements DetaileCommandeRepo {
    private Connexion connexion;
    private Connection con;

    public DetaileCommandeService() throws SQLException, ClassNotFoundException {
        connexion=new Connexion();
        con=connexion.getCon();
    }
    @Override
    public boolean addDetaileCommande(DetaileCommande detailecommande) throws SQLException {
        PreparedStatement ps=con.prepareCall( "INSERT INTO DetailCommande(quantite,idCommande)");
        ps.setInt(1, detailecommande.getQuantite());
        ps.setInt(2,detailecommande.getIdcommande());
        return ps.executeUpdate()!=0;

    }

    @Override
    public float SommeAvecTva(DetaileCommande detaileCommande) throws SQLException, ClassNotFoundException {
         ProduitService produitService=new ProduitService();
         int quantite=detaileCommande.getQuantite();
         float produit=produitService.CalculTTC(detaileCommande.getIdProduit());
         float q=produit*quantite;
         return q;
    }

    @Override
    public boolean updateDetaileCommande(DetaileCommande detaileCommande) throws SQLException {
        PreparedStatement ps=con.prepareCall("update  DetailCommande values set quantite=?where id=?");
        ps.setInt(1, detaileCommande.getQuantite());
        ps.setInt(2,detaileCommande.getId());
        return ps.executeUpdate()!=0;
    }

    @Override
    public boolean deleteDetaileCommande(int id) throws SQLException {
        PreparedStatement ps=con.prepareCall("DELETE DetaileCommande where id=?");
        ps.setInt(2,id);
        return ps.executeUpdate()!=0;
    }

    @Override
    public DetaileCommande getDetaileCommande(int id) throws SQLException {
        PreparedStatement ps=con.prepareCall("SELECT * FROM DetailCommande where id=?");
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
        DetaileCommande detailecommande=new DetaileCommande();
        if(rs.next()) {
            detailecommande.setIdcommande(rs.getInt("idcommande"));
            detailecommande.setQuantite(rs.getInt("quantite"));
            detailecommande.setId(rs.getInt("id"));

        }
        return detailecommande;

    }

    @Override
    public List<DetaileCommande> getDetaileCommandes() throws SQLException {
        PreparedStatement ps=con.prepareCall("SELECT * FROM DetailCommande ");
        ResultSet rs=ps.executeQuery();
        List<DetaileCommande> detailecommandes=new ArrayList<DetaileCommande>();
        while (rs.next()){
            DetaileCommande detailecommande=new DetaileCommande();
            detailecommande.setIdcommande(rs.getInt("idcommande"));
            detailecommande.setQuantite(rs.getInt("quantite"));
            detailecommande.setId(rs.getInt("id"));
            detailecommandes.add(detailecommande);
        }
        return detailecommandes;
    }
}
