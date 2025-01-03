package ma.ensa.project.controller;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.ensa.project.ApplicationGestionFacturation;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.ClientService;
import ma.ensa.project.service.UserService;


import java.io.IOException;
import java.sql.SQLException;

public class addclient {
    public Button btnClose;
    public Button btnFull;
    @FXML
    Label label;
    @FXML
    TextField Nom;
    @FXML
    TextField Address;
    @FXML
    TextField Email;
    @FXML
    TextField Telephone;
    UserService us;
    ClientService cs;
    @FXML
    public VBox vbox= new VBox();



    @FXML
    public JFXTreeTableView<Client.ClientModel> addclientTable= new JFXTreeTableView<>();



    public addclient() throws SQLException, ClassNotFoundException, IOException {
        us=new UserService();
        cs=new ClientService();

    }

    @FXML
    public void save() throws IOException, SQLException, ClassNotFoundException {

        User user=us.getSession();

        String name=Nom.getText();
        String Add=Address.getText();
        String email=Email.getText();
        String phone= Telephone.getText();
        ma.ensa.project.entity.Client client=new ma.ensa.project.entity.Client();
        client.setNom(name);
        client.setAdresse(Add);
        client.setEmail(email);
        client.setTelephone(phone);



        cs.addClient(client,user.getId());
        label.setText("client added succesfully");
        Nom.setText("");
        Address.setText("");
        Email.setText("");
        Telephone.setText("");




    }

    @FXML
    public void cancel(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        // Réinitialisation des champs
        Nom.setText("");
        Address.setText("");
        Email.setText("");
        Telephone.setText("");

        // Charger la vue Produit
        ma.ensa.project.controller.Client client = new ma.ensa.project.controller.Client();
        client.initialize(vbox.getScene());

        // Cacher la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.hide();
    }

    public void user(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        DashboardUser user = new DashboardUser();

        user.initialize(vbox.getScene());

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.hide();



    }
    public void produit(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        product produit = new product();

        produit.initialize(vbox.getScene());

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.hide();



    }
    public void Paiement(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Paiement paiement = new Paiement();

        paiement.initialize(vbox.getScene());

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.hide();



    }
    public void facture(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        facture facture = new facture();

        facture.initialize(new Stage().getScene());

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.hide();



    }
    public void commande(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        ma.ensa.project.controller.commande commande= new ma.ensa.project.controller.commande();

        commande.initialize(vbox.getScene());

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.hide();




    }
    public void full(Event mouseEvent) {
        Stage stage = (Stage) btnFull.getScene().getWindow();
        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
        }else {
            if (!stage.isFullScreen() ) {
                stage.setFullScreen(true);

            }}
    }
    @FXML
    public void close(ActionEvent mouseEvent) throws Throwable {

        Stage stage = (Stage) btnClose.getScene().getWindow();
        Client.Update.etat=false;
        stage.hide();



    }

    @FXML
    public void initialize(Scene scene) throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("addclient.fxml"));

        scene=new Scene(fxmlLoader.load());
        Stage primaryStage =new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Gestion de stock");
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);



        primaryStage.show();}
}