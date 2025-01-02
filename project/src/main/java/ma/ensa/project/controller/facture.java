package ma.ensa.project.controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.ensa.project.ApplicationGestionFacturation;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;

public class facture {


    public static Client.Update Update;

    public static class FactureModel extends RecursiveTreeObject<FactureModel> {




    }



    @FXML
    private VBox vbox;


    @FXML
    public Button btnFull;
    @FXML
    public Button userBtn;
    @FXML
    public Label msg;
    @FXML
    public JFXTreeTableView<facture.FactureModel> commandeTable=new JFXTreeTableView<>();
    @FXML
    public JFXTreeTableColumn<facture.FactureModel,String> client=new JFXTreeTableColumn<>("Client");
    @FXML
    public JFXTreeTableColumn<facture.FactureModel,Button> commande=new JFXTreeTableColumn<>("Commande");
    @FXML
    public JFXTreeTableColumn<facture.FactureModel,String> user=new JFXTreeTableColumn<>("User");
    @FXML
    public JFXTreeTableColumn<facture.FactureModel,Button> DeleteColumn=new JFXTreeTableColumn<>("Delete");
    @FXML
    public JFXTreeTableColumn<facture.FactureModel,Button> pdfColumn=new JFXTreeTableColumn<>("PDF");
    public Scene scene;

    public Button btnClose;

    public void Close(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }



    public void initialize(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("facture.fxml"));
        scene=new Scene(fxmlLoader.load());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Gestion de stock");
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);

        primaryStage.show();


    }







    public void facture(ActionEvent actionEvent) {

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
        Client.Update.etat = false;
        stage.hide();



    }
    public void client(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        ma.ensa.project.controller.facture.Update.etat = false;
        ma.ensa.project.controller.Client client1 = new ma.ensa.project.controller.Client();

        client1.initialize(vbox.getScene());

        this.commandeTable.getScene().getWindow().hide();


    }

        public void user(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Update.etat=false;
        DashboardUser user = new DashboardUser();

        user.initialize(vbox.getScene());

        this.commandeTable.getScene().getWindow().hide();



    }
    public void produit(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Update.etat=false;
        product produit = new product();

        produit.initialize(vbox.getScene());

        this.commandeTable.getScene().getWindow().hide();



    }

    public void commande(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Update.etat=false;
        ma.ensa.project.controller.commande commande1= new ma.ensa.project.controller.commande();

        commande1.initialize(vbox.getScene());

        this.commandeTable.getScene().getWindow().hide();




    }
    public void Paiement(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Update.etat=false;
        Paiement paiement = new Paiement();

        paiement.initialize(vbox.getScene());

        this.commandeTable.getScene().getWindow().hide();



    }



}
