package ma.ensa.project.entity;

import lombok.Data;

import java.util.List;

@Data
public class DetaileCommande {

    private int id;                    // Identifiant unique du détail de commande
    private int idcommande;         // Commande associée
    private int  IdProduit;           // Produit associé à ce détail
    private int quantite;// Quantité commandée

    // Constructeur
    public DetaileCommande() {

    }
    public DetaileCommande(int id, int idcommande, int Idproduit, int quantite, double prixUnitaire) {
        this.id = id;
        this.idcommande = idcommande;
        this.IdProduit = Idproduit;
        this.quantite = quantite;

    }



}
