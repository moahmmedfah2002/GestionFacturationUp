package ma.ensa.project.entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.Data;

@Data
public class User {
    private int id;
    private String nomUtilisateur;
    private String motDePasse;
    private String role;
    public User() {
    }
    public User(int id, String nomUtilisateur, String motDePasse, String typeUser) {
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.role = typeUser;

    }


    public User(long l, String johnDoe, String mail, String admin, String actif) {
    }
}
