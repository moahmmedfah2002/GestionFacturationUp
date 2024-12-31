package ma.ensa.project.service;


import ma.ensa.project.Connexion;
import ma.ensa.project.entity.Commande;
import ma.ensa.project.entity.Facture;
import ma.ensa.project.entity.Paiement;
import ma.ensa.project.repo.FactureRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactureService implements FactureRepo {
    private Connexion connection;
    private Connection con;
    private int idCommande;
    private int idPaiement;
    private CommandeService commandeService;
    private PaimentService paimentService;

    public FactureService(int idCommande, int idPaiement) {
        this.idCommande = idCommande;
        this.idPaiement = idPaiement;
    }

    public FactureService() throws SQLException, ClassNotFoundException {
        connection=new Connexion();
        con=connection.getCon();
    }
    @Override
    public void addFacture(Facture facture) throws SQLException {
        String sql="Insert into Facture(date,tax,status,montant,idCommande,idPaiement) values(?,?,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setDate(1,facture.getDate());
        pstmt.setDouble(2,facture.getTax());
        pstmt.setBoolean(3,facture.isStatut());
        pstmt.setDouble(4,facture.getMontant());
        pstmt.setInt(5,facture.getClientId());
        pstmt.setInt(6,facture.getPaiements().getId());
        pstmt.executeUpdate();
    }

    @Override
    public Facture getFacture(int id) throws SQLException {
        String sql="select * from Facture where idFacture=?";
        PreparedStatement str=con.prepareStatement(sql);
        str.setInt(1,id);
        ResultSet rs=str.executeQuery();
        Facture facture=new Facture();

        if(rs.next()){
           int idfac= rs.getInt("idFacture");
           facture.setId(idfac);
           Date date= rs.getDate("date");
           facture.setDate(date);
           int tax=   rs.getInt("tax");
           facture.setTax(tax);
           boolean status=   rs.getBoolean("status");
           facture.setStatut(status);
           float prixaftertax=  rs.getFloat("prixaftertax");
           facture.setMontant(prixaftertax);
           int idpaiement=rs.getInt("idPaiement");
           Commande commandes=getCommandeForFacture(id);
           facture.setCommande(commandes);
           Paiement paiement= paimentService.getPaiement(idpaiement);
           facture.setPaiements(paiement);


        }
        return facture;


    }

    @Override
    public Commande getCommandeForFacture(int idFacture) throws SQLException {
        String sql="select idCommande from FactureCommande where idFacture=?";
        PreparedStatement str=con.prepareStatement(sql);
        str.setInt(1,idFacture);
        ResultSet rs=str.executeQuery();
        int commandeids = 0;
       if (rs.next()){
             commandeids=rs.getInt("idCommande");

        }


            String s="select * from Commande where id=?";
            PreparedStatement st=con.prepareStatement(s);
            st.setInt(1,commandeids);
            ResultSet rst=st.executeQuery();
            Commande c=new Commande();
            if (rst.next()) {
                c.setId(rst.getInt("idCommande"));
                c.setCommandeDate(rst.getDate("Date"));
                c.setTotalAmount(rst.getFloat("TotalAmount"));
            }

            return c;


    }

    @Override
    public boolean deleteFacture(int id) throws SQLException {

        String sql="delete from Facture where idFacture=?";
        PreparedStatement str=con.prepareStatement(sql);
        str.setInt(1,id);
        return str.executeUpdate()>0;

    }

    @Override
    public boolean updateFacture(Facture facture) throws SQLException {
        String sql="Update Facture set date=?,tax=?,status=?,prixaftertax=? where idFacture=?";
        PreparedStatement str=con.prepareStatement(sql);
        str.setDate(1,facture.getDate());
        str.setDouble(2,facture.getTax());
        str.setBoolean(3,facture.isStatut());
        str.setDouble(4,facture.getMontant());
        str.setInt(5,facture.getId());
        return str.executeUpdate()>0;

    }

    @Override
    public List<Facture> getFactures() throws SQLException, ClassNotFoundException {
        List<Facture> factures=new ArrayList<Facture>();
        String sql="select * from Facture";
        PreparedStatement str=con.prepareStatement(sql);
        ResultSet rs=str.executeQuery();
        while(rs.next()){
            int idfac= rs.getInt("idFacture");
            int tax=   rs.getInt("tax");
            boolean status=   rs.getBoolean("status");
            float montant=rs.getFloat("montant");
            int idcommande=    rs.getInt("idCommande");
            Date date=    rs.getDate("Date");
            int idpaiement=    rs.getInt("idPaiement");
            PaimentService paimentService=new PaimentService();

            int clientId=    rs.getInt("clientId");
            Commande commande=  commandeService.getCommande(idcommande);
            Paiement paiement= paimentService.getPaiement(idpaiement);
            Facture facture=new Facture( idfac,  clientId,  montant,  status,  date,  tax,  paiement );
            factures.add(facture);
        }

                return factures;

    }
}
