package ma.ensa.project.controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.ensa.project.ApplicationGestionFacturation;
import ma.ensa.project.entity.*;
import ma.ensa.project.entity.Client;
import ma.ensa.project.service.*;
import org.kordamp.bootstrapfx.BootstrapFX;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class AjouterCommande {

    @FXML
    private ComboBox<String> comboBoxChoix;
    @FXML
    private TextField clientField;  // Champ pour le client

    @FXML
    private DatePicker datePicker;  // Champ pour la date

    @FXML
    private ComboBox<String> statusField;  // ComboBox pour le statut du paiement

    @FXML
    private VBox productsContainer; // Conteneur pour les HBox des produits
    @FXML
    private Button addProductBtn;
    @FXML
    private Button addCommandeBtn;
    @FXML
    private ComboBox<String> produitComboBox1;

    private final ProduitService produitService = new ProduitService();
    private final ClientService clientService=new ClientService();
    private int productCount = 1;

    public AjouterCommande() throws SQLException, ClassNotFoundException {}

    @FXML
    public void initialize() {
        try {
            List<Produit> produits = produitService.getAllProduits();
            ObservableList<String> produitsNames = FXCollections.observableArrayList();
            for (Produit produit : produits) {
                produitsNames.add(produit.getNom());
            }
            produitComboBox1.setItems(produitsNames);

            List<Client> clients=clientService.getClients();
            ObservableList<String> clientsNames = FXCollections.observableArrayList();
            for (Client p : clients) {
                clientsNames.add(p.getNom());
            }
            comboBoxChoix.setItems(clientsNames);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        addProductBtn.setOnAction(event -> ajouterProduitField());
        addCommandeBtn.setOnAction(event -> {
            try {
                collecterProduits();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Méthode pour ajouter dynamiquement un produit et une quantité
    private void ajouterProduitField() {
        productCount++;
        HBox newProductBox = new HBox(15);
        newProductBox.setAlignment(Pos.CENTER);
        ComboBox<String> produitComboBox = new ComboBox<>();
        produitComboBox.setPromptText("Sélectionnez un produit");
        produitComboBox.setPrefWidth(200);
        try {
            List<Produit> produits = produitService.getAllProduits();
            ObservableList<String> produitsNames = FXCollections.observableArrayList();
            for (Produit produit : produits) {
                produitsNames.add(produit.getNom());
            }
            produitComboBox.setItems(produitsNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TextField quantiteField = new TextField();
        quantiteField.setPromptText("Quantité");
        quantiteField.setPrefWidth(100);
        newProductBox.getChildren().addAll(produitComboBox, quantiteField);
        productsContainer.getChildren().add(newProductBox);
    }




    private void collecterProduits() throws SQLException, ClassNotFoundException, ParseException, IOException {
        List<DetaileCommande> detaileCommandes = new ArrayList<>();

        // Parcours de chaque HBox (chaque produit ajouté)
        for (int i = 0; i < productsContainer.getChildren().size(); i++) {
            HBox productBox = (HBox) productsContainer.getChildren().get(i);
            ComboBox<String> produitComboBox = (ComboBox<String>) productBox.getChildren().get(0);
            TextField quantiteField = (TextField) productBox.getChildren().get(1);

            String produitNom = produitComboBox.getValue();
            String quantiteText = quantiteField.getText();

            if (produitNom != null && !produitNom.isEmpty() && quantiteText != null && !quantiteText.isEmpty()) {
                Produit produit = produitService.getProduitByName(produitNom);
                if (produit != null) {
                    // Créer un objet DetaileCommande et l'ajouter à la liste
                    DetaileCommande detaileCommande = new DetaileCommande();
                    detaileCommande.setIdProduit(produit.getId());
                    detaileCommande.setQuantite(Integer.parseInt(quantiteText));
                    detaileCommandes.add(detaileCommande);
                }
            }
        }
        CommandeService commandeService=new CommandeService();
        Commande commande=creerCommande();
        commandeService.addCommande(commande,detaileCommandes);
        for (DetaileCommande e : detaileCommandes){
            ProduitService p=new ProduitService();
            Produit pr=p.getProduit(e.getIdProduit());
            int quantite=pr.getQuantiteDisponible()-e.getQuantite();
            pr.setQuantiteDisponible(quantite);
            p.updateProduit(pr);
        }


    }




    public Commande creerCommande() throws SQLException, ClassNotFoundException, IOException {
        String client = comboBoxChoix.getValue();
        LocalDate localDate = datePicker.getValue();
        Date dateCommande = Date.valueOf(localDate);
        boolean statusPaiement = "Payé".equals(statusField.getValue());
        ClientService client1=new ClientService();
        Client clients=client1.getClient(client);
        int cl=clients.getId();
        UserService userService=new UserService();
        User user=userService.getSession();
        int id=user.getId();
        Commande commande = new Commande(dateCommande,0,cl,id,statusPaiement);

        return commande;
    }


    public void afficherAjout() {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("ajouter.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }


}
