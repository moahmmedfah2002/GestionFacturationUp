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

    private CommandeService commandeService;
    private PaimentService paimentService;



    public FactureService() throws SQLException, ClassNotFoundException {
        connection=new Connexion();
        con=connection.getCon();
    }
    @Override
    public void addFacture(Facture facture) throws SQLException {
        String sql="insert into facture(idCommande,idUser) values(?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setInt(1,facture.getIdCommande());
        pstmt.setInt(2,facture.getIdUser());

        pstmt.executeUpdate();
    }

    @Override
    public Facture getFacture(int id) throws SQLException {
        String sql="select * from facture where id=?";
        PreparedStatement str=con.prepareStatement(sql);
        str.setInt(1,id);
        ResultSet rs=str.executeQuery();
        Facture facture=new Facture();

        if(rs.next()){
           int idfac= rs.getInt("id");
           facture.setId(idfac);
           int idCommande = rs.getInt("idCommande");
           facture.setIdCommande(idCommande);
           int idUser=rs.getInt("idUser");
           facture.setIdUser(idUser);



        }
        return facture;


    }


//    public Commande getCommandeForFacture(int idFacture) throws SQLException {
//        String sql="select idCommande from FactureCommande where idFacture=?";
//        PreparedStatement str=con.prepareStatement(sql);
//        str.setInt(1,idFacture);
//        ResultSet rs=str.executeQuery();
//        int commandeids = 0;
//       if (rs.next()){
//             commandeids=rs.getInt("idCommande");
//
//        }
//
//
//            String s="select * from Commande where id=?";
//            PreparedStatement st=con.prepareStatement(s);
//            st.setInt(1,commandeids);
//            ResultSet rst=st.executeQuery();
//            Commande c=new Commande();
//            if (rst.next()) {
//                c.setId(rst.getInt("idCommande"));
//                c.setCommandeDate(rst.getDate("Date"));
//                c.setTotalAmount(rst.getFloat("TotalAmount"));
//            }
//
//            return c;
//
//
//    }

    public boolean deleteFacture(int id) throws SQLException {

        String sql="delete from facture where id=?";
        PreparedStatement str=con.prepareStatement(sql);
        str.setInt(1,id);
        return str.executeUpdate()>0;

    }

//    @Override
//    public boolean updateFacture(Facture facture) throws SQLException {
//        String sql="Update Facture set date=?,tax=?,status=?,prixaftertax=? where idFacture=?";
//        PreparedStatement str=con.prepareStatement(sql);
//        str.setDate(1,facture.getDate());
//        str.setDouble(2,facture.getTax());
//        str.setBoolean(3,facture.isStatut());
//        str.setDouble(4,facture.getMontant());
//        str.setInt(5,facture.getId());
//        return str.executeUpdate()>0;
//
//    }

    @Override
    public List<Facture> getFactures() throws SQLException, ClassNotFoundException {
        List<Facture> factures=new ArrayList<Facture>();
        String sql="select * from facture";
        PreparedStatement str=con.prepareStatement(sql);
        ResultSet rs=str.executeQuery();
        while(rs.next()){
            int idfac= rs.getInt("id");
            int idCommande=   rs.getInt("idCommande");

            int idUser=    rs.getInt("idUser");

            Facture facture=new Facture(idfac, idCommande,idUser);
            factures.add(facture);
        }

                return factures;

    }
}
