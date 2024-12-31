package ma.ensa.project.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.ensa.project.ApplicationGestionFacturation;

import java.io.IOException;

public class home {
    @FXML
    private StackPane contentPane;
    private Stage clientStage = null;
    private Stage productStage = null;
    private Stage factureStage = null;


    public void initialize(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setTitle("Gestion de stock");

        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public void showClient() {
        try {
            if (factureStage != null ) {
                factureStage.close();
            }
            if (productStage != null){
                productStage.close();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ma/ensa/project/client.fxml"));
            Parent root = loader.load();
            clientStage = new Stage();
            clientStage.setTitle("Interface Client");
            clientStage.setScene(new Scene(root));
            clientStage.setX(380);
            clientStage.setY(190);
            clientStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showfacture(ActionEvent actionEvent) {
        try {
            if (clientStage != null ) {
                clientStage.close();
            }
            if (productStage != null){
                productStage.close();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ma/ensa/project/tabfacture.fxml"));
            Parent root = loader.load();
            factureStage = new Stage();
            factureStage.setTitle("Interface facture");
            factureStage.setScene(new Scene(root));
            factureStage.setX(380);
            factureStage.setY(190);
            factureStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showproduct(ActionEvent actionEvent) {
        try {
            if (factureStage != null ) {
                factureStage.close();
            }
            if (productStage != null){
                clientStage.close();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ma/ensa/project/product.fxml"));
            Parent root = loader.load();
            productStage = new Stage();
            productStage.setTitle("Interface product");
            productStage.setScene(new Scene(root));
            productStage.setX(380);
            productStage.setY(190);
            productStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
