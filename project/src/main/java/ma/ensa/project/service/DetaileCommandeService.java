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
        PreparedStatement ps=con.prepareCall( "INSERT INTO DetailCommande(quantite,idproduit,idCommande) values (?,?,?)");
        ps.setInt(1, detailecommande.getQuantite());
        ps.setInt(2, detailecommande.getIdProduit());
        ps.setInt(3,detailecommande.getIdcommande());
        return ps.executeUpdate()!=0;

    }


    @Override
    public float SommeAvecTva(List<DetaileCommande> detaileCommande) throws SQLException, ClassNotFoundException {
        float pr = 0;
        ProduitService produitService = new ProduitService();
        for (DetaileCommande e : detaileCommande) {
            Produit p = produitService.getProduit(e.getIdProduit());
            if (p != null) {
                float tva = p.getTva();
                float prix = p.getPrix();
                float somme = (prix * e.getQuantite()) * (1 + tva / 100);
                pr += somme;
            }
        }
        return pr;
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
        PreparedStatement ps=con.prepareCall("DELETE FROM detailcommande where id=?");
        ps.setInt(1,id);
        return ps.executeUpdate()!=0;
    }

    @Override
    public DetaileCommande getDetaileCommande(int id) throws SQLException {
        PreparedStatement ps=con.prepareCall("SELECT * FROM detailcommande where id=?");
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
    public List<DetaileCommande> getDetaileCommandebyidcommande(int id) throws SQLException {
        List<DetaileCommande> Detail=new ArrayList<>();
        PreparedStatement ps=con.prepareCall("SELECT * FROM detailcommande where idCommande=?");
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();

        while(rs.next()) {
            DetaileCommande detailecommande=new DetaileCommande();
            detailecommande.setIdcommande(rs.getInt("idCommande"));
            detailecommande.setQuantite(rs.getInt("quantite"));
            detailecommande.setIdProduit(rs.getInt("idProduit"));
            detailecommande.setId(rs.getInt("id"));
            detailecommande.setIdProduit(rs.getInt("idProduit"));
            Detail.add(detailecommande);

        }
        return Detail;

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
