package ma.ensa.project.entity;
import java.sql.Date;
import java.util.List;

public class Commande {
    private int id;
    private Date commandeDate;
    private float totalAmount;
    private int client;
    private int idUser;
    private boolean status=false;
    private int idpaiement;

    private List<Integer> detailCommande;

    public Commande() {}
    public Commande(int id, Date commandeDate, float totalAmount, int client,int idUser,boolean status, List<Integer> detailCommande  ) {
        this.id = id;
        this.commandeDate = commandeDate;
        this.totalAmount = totalAmount;
        this.client = client;
        this.idUser = idUser;
        this.detailCommande = detailCommande;
        this.status =status ;
    }
    public Commande(Date commandeDate, float totalAmount, int client,int idUser,boolean status) {
        this.id = id;
        this.commandeDate = commandeDate;
        this.totalAmount = totalAmount;
        this.client = client;
        this.idUser = idUser;
        this.detailCommande = detailCommande;
        this.status =status ;
    }

    public int getIdpaiement() {
        return idpaiement;
    }

    public void setIdpaiement(int idpaiement) {
        this.idpaiement = idpaiement;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Date getCommandeDate() {
        return commandeDate;
    }

    public void setCommandeDate(Date commandeDate) {
        this.commandeDate = commandeDate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public List<Integer> getDetailCommande() {
        return detailCommande;
    }

    public void setDetailCommande(List<Integer> detailCommande) {
        this.detailCommande = detailCommande;
    }
}
