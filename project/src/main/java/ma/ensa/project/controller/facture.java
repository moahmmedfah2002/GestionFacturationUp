package ma.ensa.project.controller;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class
facture {

    public void Close(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void initialize(Scene scene) {

    }
}
