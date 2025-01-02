
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
import ma.ensa.project.entity.Produit;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.ClientService;
import ma.ensa.project.service.ProduitService;
import ma.ensa.project.service.UserService;


import java.io.IOException;
import java.sql.SQLException;

public class addproduct {
    public Button btnClose;
    public Button btnFull;
    @FXML
    Label label;
    @FXML
    TextField Nom;
    @FXML
    TextField Prix;
    @FXML
    TextField Qd;
    @FXML
    TextField Tva;
    UserService us;
    ProduitService Ps;


    public addproduct() throws SQLException, ClassNotFoundException, IOException {
        us=new UserService();
        Ps=new ProduitService();

    }
    @FXML
    public VBox vbox= new VBox();

    @FXML
    public JFXTreeTableView<product.ProduitModel> addproduitTable= new JFXTreeTableView<>();

    @FXML
    public void save() throws IOException, SQLException, ClassNotFoundException {

        User user=us.getSession();

        String name=Nom.getText();
        float prix= Float.parseFloat(Prix.getText());
        int QD= Integer.parseInt(Qd.getText());
        float tva= Float.parseFloat(Tva.getText());
        Produit produit =new Produit();
        produit.setNom(name);
        produit.setPrix(prix);
        produit.setQuantiteDisponible(QD);
        produit.setTva(tva);



        Ps.addProduit(produit,user.getId());
        label.setText("product added succesfully");
        Nom.setText("");
        Prix.setText("");
        Qd.setText("");
        Tva.setText("");




    }

    @FXML
    public void cancel(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        // Réinitialisation des champs
        Nom.setText("");
        Prix.setText("");
        Qd.setText("");
        Tva.setText("");

        // Charger la vue Produit
        ma.ensa.project.controller.product produit = new ma.ensa.project.controller.product();
        produit.initialize(vbox.getScene());

        // Cacher la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.hide();
    }

    public void commande(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        commande commande = new commande();

        commande.initialize(vbox.getScene());

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.hide();



    }
    public void produit(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        product produit = new product();

        produit.initialize(vbox.getScene());

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.hide();



    }
    public void facture(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        facture facture = new facture();


        facture.initialize(new Stage().getScene());

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.hide();



    }
    public void user(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        DashboardUser user = new DashboardUser();

        user.initialize(vbox.getScene());

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.hide();



    }
    public void client(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        Client client = new Client();

        client.initialize(vbox.getScene());

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.hide();



    }
    public  void paiement(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Paiement paiement=new Paiement();
        paiement.initialize(vbox.getScene());
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

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("addproduct.fxml"));

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

