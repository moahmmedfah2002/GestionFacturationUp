package ma.ensa.project.entity;

public class Produit {
    private int id;
    private String nom;
    private float prix;
    private int quantiteDisponible;

    private int UserId;
    private float tva;



    public Produit() {
    }
    public Produit(int id, String nom, float prix, int quantiteDisponible,int UserId, float tva) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantiteDisponible = quantiteDisponible;
        this.UserId=UserId;
        this.tva=tva;
    }

    public boolean verifierDisponibilite(int quantiteDemandee) {
        return quantiteDemandee <= quantiteDisponible;
    }

    public void reduireStock(int quantiteVendue) {
        if (quantiteVendue <= quantiteDisponible) {
            quantiteDisponible -= quantiteVendue;
        } else {
            throw new IllegalArgumentException("Quantité demandée supérieure au stock disponible !");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public void setQuantiteDisponible(int quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }

    public float getTva() {
        return tva;
    }

    public void setTva(float tva) {
        this.tva = tva;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        this.UserId = userId;
    }
}
