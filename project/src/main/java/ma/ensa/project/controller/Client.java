


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

    @FXML
    public Button userbtn;
    public Button clientbtn;
    public Button produitbtn;
    public Button commandebtn;
    public Button paiementbtn;
    public Button facturnbtn;



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
            this.update.setStyle("-fx-background-color: #27ae60;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-padding: 5px 10px;");
            this.delete.setStyle("-fx-background-color: #ae2727;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-padding: 5px 10px;");

            this.delete.setOnAction(event -> {





                try {
                    System.out.println(Integer.parseInt(delete.getId()));
                    clientDao.deleteClient(Integer.parseInt(delete.getId()));
                    Update update=new Update();
                    update.loadClient();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            Update.etat = true;
            this.update.setOnAction(event -> {


                if(Update.etat) {

                    Update.etat = false;
                    name = new TextField(String.valueOf(name));

                    Adress = new TextField(String.valueOf(Adress));

                    Email = new TextField(String.valueOf(Email));

                    Telephone = new TextField(String.valueOf(Telephone));






                    update.setText("ok");

                    clientTable.refresh();


                }else {
                    Update.etat = true;

                    update.setText("Update");
                    ma.ensa.project.entity.Client client= new ma.ensa.project.entity.Client();
                    client.setId(Integer.parseInt(id));
                    client.setNom(((TextField)name).getText());
                    client.setAdresse(String.valueOf(String.valueOf(((TextField)Adress).getText())));
                    client.setEmail(String.valueOf(((TextField)Email).getText()));
                    client.setTelephone(String.valueOf(((TextField)Telephone).getText()));

                    try {
                        clientDao.updateClient(client);

                        Update update1 = new Update();
                        update1.loadClient();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                   clientTable.refresh();
                    Update update1 = new Update();
                    try {
                        update1.loadClient();
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

    public void addclient(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        addclient addclient= new addclient();

        addclient.initialize(vbox.getScene());

        this.clientTable.getScene().getWindow().hide();



    }
    public void user(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        DashboardUser user = new DashboardUser();

        user.initialize(vbox.getScene());

        this.clientTable.getScene().getWindow().hide();



    }
    public void produit(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        product produit = new product();

        produit.initialize(vbox.getScene());

        this.clientTable.getScene().getWindow().hide();



    }
    public void commande(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        ma.ensa.project.controller.commande commande= new ma.ensa.project.controller.commande();

        commande.initialize(vbox.getScene());

        this.clientTable.getScene().getWindow().hide();




    }
    public void Paiement(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        Paiement paiement = new Paiement();

        paiement.initialize(vbox.getScene());

        this.clientTable.getScene().getWindow().hide();



    }
    public void facture(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        facture facture = new facture();

        facture.initialize(new Stage().getScene());

        this.clientTable.getScene().getWindow().hide();



    }


    public Client() throws SQLException, ClassNotFoundException, IOException {

        clientDao = new ClientService();
        clientList = FXCollections.observableArrayList();
        UserService userService=new UserService();
        Update update = new Update();
        update.loadClient();

        clientTable.getColumns().add(nom);
        clientTable.getColumns().add(address);
        clientTable.getColumns().add(email);
        clientTable.getColumns().add(telephone);
        clientTable.getColumns().add(user);
        clientTable.getColumns().add(DeleteColumn);
        clientTable.getColumns().add(updateColumn);
        update.loadClient();

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
        public static Object mutex = new Object();

        public void loadClient() throws SQLException {

            // public void loadClient() throws SQLException {
            clientList.clear();

            try {
                // Vider la liste existante




                // Convertir les Users en UserModels
                List<ma.ensa.project.entity.Client> clients = clientDao.getClients();
                Platform.runLater(() -> {
                    clientList.clear();

                    try {
                        //clientList.clear();


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


                    loadClient();
                    sleep(30000);



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