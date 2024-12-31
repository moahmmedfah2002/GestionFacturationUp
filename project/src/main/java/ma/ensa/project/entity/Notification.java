package ma.ensa.project.entity;

public class Notification {
    private Long id;
    private String type; // Email ou SMS
    private String contenu;
    private String destinataire;

    // Constructeurs
    public Notification() {}

    public Notification(Long id, String type, String contenu, String destinataire) {
        this.id = id;
        this.type = type;
        this.contenu = contenu;
        this.destinataire = destinataire;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public void envoyer() {
        System.out.println("Notification envoyée à " + destinataire + " via " + type + ": " + contenu);
    }
}
