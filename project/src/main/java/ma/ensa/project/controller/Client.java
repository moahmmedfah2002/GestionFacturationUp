


package ma.ensa.project.controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.StageStyle;
import lombok.Data;
import ma.ensa.project.ApplicationGestionFacturation;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.ClientService;
import ma.ensa.project.service.UserService;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class Client{



    @Data
    public class ClientModel extends RecursiveTreeObject<ClientModel> {
        private Object id;
        private Object name;
        private Object Adress;
        private Object Email;
        private Object Telephone;
        private Object UserName;
        private Button delete;
        private Button update;

        // Constructeur
        public ClientModel(String id, Object NomClient, Object Aress, Object E, Object Te , Object Us) {

            this.id=id;

            this.name =  NomClient;
//            System.out.println(name.get());
            this.Adress = Aress;
            System.out.println(Adress);
            this.Telephone = Te;
            this.Email = E;
            this.UserName=Us;
            this.delete=new Button("Delete");
            this.update=new Button("Update");

            this.delete.setId(id);
            this.update.setId(id);
            this.delete.setOnAction(event -> {





                try {
                    System.out.println(Integer.parseInt(delete.getId()));
                    clientDao.deleteClient(Integer.parseInt(delete.getId()));
                    Update update=new Update();
                    update.loadUsers();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            this.update.setOnAction(event -> {


                if(Update.etat) {

                    Update.etat = false;
                    name = new TextField(String.valueOf(name));

                    Adress = new TextField(String.valueOf(Adress));

                    Email = new TextField(String.valueOf(Email));

                    Telephone = new TextField(String.valueOf(Telephone));




                    nom.setCellValueFactory(cellData -> new SimpleObjectProperty<>(name));

                    address.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Adress));
                    email.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Email));
                    telephone.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Telephone));
                    update.setText("ok");
                    updateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(update));

                    TreeItem<ClientModel> root = new RecursiveTreeItem<>(clientList, RecursiveTreeObject::getChildren);
//                        root.getChildren().forEach((e)->{ System.out.println(e.getValue().getName().get()); });
                    clientTable.setRoot(root);
                    Update update1 = new Update();


                }else {

                    update.setText("Update");
                    ma.ensa.project.entity.Client client= new ma.ensa.project.entity.Client();
                    client.setId(Integer.parseInt(id));
                    client.setNom(String.valueOf(((TextField)name).getText()));
                    client.setAdresse(String.valueOf(String.valueOf(((TextField)Adress).getText())));
                    client.setEmail(String.valueOf(((TextField)Email).getText()));
                    client.setTelephone(String.valueOf(((TextField)Telephone).getText()));

                    try {
                        clientDao.updateClient(client);
                        Update.etat = true;
                        Update update1 = new Update();
                        update1.start();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    updateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(update));

                    TreeItem<ClientModel> root = new RecursiveTreeItem<>(clientList, RecursiveTreeObject::getChildren);
//                        root.getChildren().forEach((e)->{ System.out.println(e.getValue().getName().get()); });
                    clientTable.setRoot(root);
                    Update update1 = new Update();
                    try {
                        update1.loadUsers();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }

            });
        }







    }
    @FXML
    public VBox vbox= new VBox();

    @FXML
    public JFXTreeTableView<ClientModel> clientTable= new JFXTreeTableView<>();
    @FXML
    public JFXTreeTableColumn<ClientModel, Object> nom= new JFXTreeTableColumn<>("nom");
    @FXML
    public JFXTreeTableColumn<ClientModel, Object> address= new JFXTreeTableColumn<>("Address");
    @FXML
    public JFXTreeTableColumn<ClientModel, Object> email=new JFXTreeTableColumn<>("Email");
    @FXML
    public JFXTreeTableColumn<ClientModel, Object> telephone=new JFXTreeTableColumn<>("Telephone");
    @FXML
    public JFXTreeTableColumn<ClientModel, Object> user=new JFXTreeTableColumn<>("username");
    @FXML
    public Button btnClose;
    public Button btnFull;
    private UserService userService;
    private static ObservableList<ClientModel> clientList = FXCollections.observableArrayList();
    @FXML
    private JFXTreeTableColumn<ClientModel, Button> DeleteColumn=new JFXTreeTableColumn<>("delete");
    @FXML
    private JFXTreeTableColumn<ClientModel, Button> updateColumn=new JFXTreeTableColumn<>("update");

    private static ClientService clientDao;


    public void user(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Update.etat=false;
        DashboardUser user = new DashboardUser();

        user.initialize(vbox.getScene());

        this.clientTable.getScene().getWindow().hide();



    }
    public void produit(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Update.etat=false;
        product produit = new product();

        produit.initialize(vbox.getScene());

        this.clientTable.getScene().getWindow().hide();



    }
    public void facture(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Update.etat=false;
        facture facture = new facture();

        facture.initialize(vbox.getScene());

        this.clientTable.getScene().getWindow().hide();



    }

    public Client() throws SQLException, ClassNotFoundException, IOException {

        clientDao = new ClientService();
        clientList = FXCollections.observableArrayList();
        UserService userService=new UserService();
        Update update = new Update();

        clientTable.getColumns().add(nom);
        clientTable.getColumns().add(address);
        clientTable.getColumns().add(email);
        clientTable.getColumns().add(telephone);
        clientTable.getColumns().add(user);
        clientTable.getColumns().add(DeleteColumn);
        clientTable.getColumns().add(updateColumn);
        update.start();

            }












    private void handleDelete(ma.ensa.project.entity.Client client) {
        // Utiliser Platform.runLater pour éviter les conflits d'animation
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer l'utilisateur ?");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");

            // Styling de l'alerte de confirmation
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-border-color: #FF4757;" +
                            "-fx-border-width: 2px;" +
                            "-fx-border-radius: 5px;"
            );

            // Styling des boutons
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setStyle(
                    "-fx-background-color: #FF4757;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 5px;"
            );

            Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancelButton.setStyle(
                    "-fx-background-color: #E8ECEF;" +
                            "-fx-text-fill: #2D3436;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 5px;"
            );

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        clientDao.deleteClient(client.getId());
                        new Client();


                        showSuccessMessage("Utilisateur supprimé avec succès");
                    } catch (Exception e) {
                        showErrorMessage("Erreur lors de la suppression : " + e.getMessage());
                    }
                }
            });
        });
    }
    private void showSuccessMessage(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText(message);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("success-alert");
            dialogPane.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-border-color: #2ECC71;" +
                            "-fx-border-width: 2px;" +
                            "-fx-border-radius: 5px;"
            );

            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setStyle(
                    "-fx-background-color: #2ECC71;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 5px;"
            );

            alert.showAndWait();
        });
    }

    private void showErrorMessage(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText(message);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("error-alert");
            dialogPane.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-border-color: #E74C3C;" +
                            "-fx-border-width: 2px;" +
                            "-fx-border-radius: 5px;"
            );

            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setStyle(
                    "-fx-background-color: #E74C3C;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 5px;"
            );

            alert.showAndWait();
        });
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
        Update.etat=false;
        stage.hide();



    }



    public class Update extends Thread{
        public void loadUsers() throws SQLException {
            clientList.clear();
            try {
                // Vider la liste existante




                // Convertir les Users en UserModels
                List<ma.ensa.project.entity.Client> clients = clientDao.getClients();
                Platform.runLater(() -> {
                    try {
                        clientList.clear();


                        for (ma.ensa.project.entity.Client client : clients) {

                            int id = client.getUserId();
                            UserService userService=new UserService();
                            User user1=userService.getUser(id);


                            ClientModel clientModel = new ClientModel(
                                    String.valueOf(client.getId()),
                                    client.getNom(),
                                    client.getAdresse(),
                                    client.getEmail(),
                                    client.getTelephone(),
                                    user1.getNomUtilisateur()

                            );


                            clientList.add(clientModel);


                        }

                        TreeItem<ClientModel> root = new RecursiveTreeItem<>(clientList, RecursiveTreeObject::getChildren);
//                        root.getChildren().forEach((e)->{ System.out.println(e.getValue().getName().get()); });
                        clientTable.setRoot(root);
                        clientTable.setShowRoot(false);
                        nom.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getName()));

                        address.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getAdress()));
                        email.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getEmail()));
                        telephone.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getTelephone()));
                        user.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getUserName()));
                        DeleteColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getDelete()));
                        updateColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getUpdate()));





                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        public static boolean etat=true;
        @Override
        public void run(){

            while (etat){
                System.out.println("checked");

                try {


                    loadUsers();
                    sleep(3000);



                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        public static void cancel(){


            interrupted();
        }

    }
    public void commande(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Update.etat=false;
        ma.ensa.project.controller.commande commande1= new ma.ensa.project.controller.commande();

        commande1.initialize(vbox.getScene());

        this.clientTable.getScene().getWindow().hide();




    }
    @FXML
    public void initialize(Scene scene) throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("client.fxml"));

        scene=new Scene(fxmlLoader.load());
        Stage primaryStage =new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Gestion de stock");
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);



        primaryStage.show();


        clientTable.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
       clientTable.getStyleClass().addAll("table-view","table table-striped");



        // Configuration des colonnes



//        nom.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getNomClient()));
//        address.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getAdress()));
//        email.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getEmail()));
//        telephone.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getTelephone()));
//        user.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getUserName()));
//        clientTable.getColumns().add(nom);








        // Chargement des données




































    }
















}