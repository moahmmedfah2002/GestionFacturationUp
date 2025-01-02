package ma.ensa.project.entity;

import lombok.Data;
import lombok.Getter;

import java.sql.Date;
import java.util.List;
@Data
public class Facture {
    // Getters et Setters

    private int id;

    private int idCommande;  // Id du client

    private int idUser;





    public Facture() {

    }

    public Facture(int idfac, int idCommand, int iduser) {
        id=idfac;
        idCommande=idCommand;
        idUser=iduser;
    }


    // Constructeur







}
