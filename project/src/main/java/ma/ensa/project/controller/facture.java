package ma.ensa.project.controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.ensa.project.ApplicationGestionFacturation;

import java.io.IOException;

public class facture {


    public static class FactureModel extends RecursiveTreeObject<FactureModel> {



    }





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

    public void user(ActionEvent actionEvent) {

    }

    public void client(ActionEvent actionEvent) {
    }

    public void produit(ActionEvent actionEvent) {
    }

    public void facture(ActionEvent actionEvent) {
    }

    public void full(ActionEvent actionEvent) {
    }

    public void close(ActionEvent actionEvent) {
    }
}
