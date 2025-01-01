package ma.ensa.project.repo;

import ma.ensa.project.entity.Produit;

import java.sql.SQLException;
import java.util.List;

public interface ProduitRepo {
    boolean addProduit(Produit p, int UserId) throws SQLException;
    void updateProduit(Produit p) throws SQLException;
    void deleteProduit(int id) throws SQLException;
    Produit getProduit(int id) throws SQLException;
    public float CalculTTC(int id) throws SQLException;

    Produit getProduitByName(String name) throws SQLException;

    List<Produit> getAllProduits() throws SQLException;

}
