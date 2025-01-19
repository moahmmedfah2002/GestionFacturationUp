package ma.ensa.project.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.ensa.project.ApplicationGestionFacturation;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class Login {
    @FXML
    public Label msg;

    public MFXButton login;
    private VBox root;
    @FXML
    public VBox vbox;
    @FXML
    public TextField nom;
    @FXML
    public PasswordField password;
    

    @FXML
    public Button btnFull;
    public Button btnClose;
    public Scene scene ;


    public void initialize(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("login.fxml"));
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



    public void full(MouseEvent mouseEvent) {
        Stage stage = (Stage) vbox.getScene().getWindow();
        if (stage.isFullScreen() && mouseEvent.getClickCount() == 1) {
            stage.setFullScreen(false);
        }else {
            if (!stage.isFullScreen() && mouseEvent.getClickCount() == 1) {
                stage.setFullScreen(true);

            }}
    }
    public void close(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) {
            Stage stage = (Stage) vbox.getScene().getWindow();
            stage.hide();
        }
    }


    public void log(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        User user=new User();
        user.setNomUtilisateur(this.nom.getText());
        user.setMotDePasse(this.password.getText());
        UserService userService=new UserService();
        if(userService.login(user)) {
            commande dashboardUser = new commande();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("commande.fxml"));
            scene = new Scene(fxmlLoader.load());
            dashboardUser.initialize(vbox.getScene());
            Stage stage = (Stage) vbox.getScene().getWindow();
            stage.hide();
        }else {
            msg.setText("Username or password incorrect");
            System.out.println("bad login");
        }


    }
}