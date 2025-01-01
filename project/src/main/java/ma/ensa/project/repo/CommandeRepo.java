package ma.ensa.project.repo;

import ma.ensa.project.entity.Commande;
import ma.ensa.project.entity.DetaileCommande;

import java.sql.SQLException;
import java.util.List;

public interface CommandeRepo {
    boolean addCommande(Commande commande,List<DetaileCommande> detaileCommandes) throws SQLException, ClassNotFoundException;

    Commande getCommande(int id) throws SQLException;
    List<Commande> getCommandes() throws SQLException;
    boolean updateCommande(Commande commande) throws SQLException;
    boolean deleteCommande(int id) throws SQLException, ClassNotFoundException;
    List<DetaileCommande> getDetaileCommandesByCommande(int id) throws SQLException;

}
