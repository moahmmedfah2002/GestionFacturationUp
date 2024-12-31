package ma.ensa.project.entity;

import lombok.Data;
import lombok.Getter;

import java.sql.Date;
import java.util.List;
@Data
public class Facture {
    // Getters et Setters

    private int id;

    private int clientId;  // Id du client

    private double montant;

    private boolean statut;

    private Date date;

    private double tax;

    private Paiement paiements;
    private Commande commande;



    public Facture() {

    }




    // Constructeur
    public Facture(int id, int clientId, double montant, boolean statut, Date date, double tax, Paiement paiements) {
        this.id = id;
        this.clientId = clientId;
        this.montant = montant;
        this.statut = statut;
        this.date = date;
        this.tax = tax;
        this.paiements = paiements;
    }







}
