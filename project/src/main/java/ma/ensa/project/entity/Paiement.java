package ma.ensa.project.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
@Data
public class Paiement {
    private int id;
    private int commandeId;
    private int idUser;
    private Date date;

    public Paiement(){}
    public Paiement(int id, int commandeId, double montant, int idUser, Date datepaiement) {
        this.id = id;
        this.commandeId = commandeId;
        this.date = datepaiement;
        this.idUser= idUser;
    }



}
