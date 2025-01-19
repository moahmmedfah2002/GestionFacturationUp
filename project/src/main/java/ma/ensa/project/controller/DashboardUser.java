package ma.ensa.project.controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import ma.ensa.project.ApplicationGestionFacturation;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.UserService;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class DashboardUser {


    public Button userBtn;

    public class UserModel extends RecursiveTreeObject<UserModel> {
        private SimpleIntegerProperty id;
        private SimpleStringProperty nomUtilisateur;
        private SimpleStringProperty motDePasse;
        private SimpleStringProperty role;

        // Constructeur
        public UserModel(int id, String nomUtilisateur, String motDePasse, String role) {
            this.id = new SimpleIntegerProperty(id);
            this.nomUtilisateur = new SimpleStringProperty(nomUtilisateur);
            this.motDePasse = new SimpleStringProperty(motDePasse);
            this.role = new SimpleStringProperty(role);
        }

        // Getters pour les valeurs
        public int getId() {
            return id.get();
        }

        public String getNomUtilisateur() {
            return nomUtilisateur.get();
        }

        public String getMotDePasse() {
            return motDePasse.get();
        }

        public String getRole() {
            return role.get();
        }

        // Getters pour les propriétés (nécessaires pour JFXTreeTableView)
        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public SimpleStringProperty nomUtilisateurProperty() {
            return nomUtilisateur;
        }

        public SimpleStringProperty motDePasseProperty() {
            return motDePasse;
        }

        public SimpleStringProperty roleProperty() {
            return role;
        }
    }
    @FXML
    public VBox vbox= new VBox();

    @FXML
    public JFXTreeTableView<UserModel> userTable= new JFXTreeTableView<>();
    @FXML
    public JFXTreeTableColumn<UserModel, Integer> idColumn= new JFXTreeTableColumn<>("id");
    @FXML
    public JFXTreeTableColumn<UserModel, String> nameColumn= new JFXTreeTableColumn<>("username");
    @FXML
    public JFXTreeTableColumn<UserModel, String> passwordColumn=new JFXTreeTableColumn<>("password");
    @FXML
    public JFXTreeTableColumn<UserModel, String> roleColumn=new JFXTreeTableColumn<>("role");
    @FXML
    public Button btnClose;
    public Button btnFull;
    private static ObservableList<UserModel> userList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<User, Void> actionsColumn=new TableColumn<>("delete");

    private static UserService userDao;

    static {
        try {
            userDao = new UserService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public DashboardUser() throws SQLException, ClassNotFoundException, IOException {
        Update update=new Update();

        userDao = new UserService();
        userList = FXCollections.observableArrayList();
        userTable.getColumns().add(idColumn);
        userTable.getColumns().add(nameColumn);
        userTable.getColumns().add(passwordColumn);
        userTable.getColumns().add(roleColumn);


        update.loadUsers();

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
        private void loadUsers() throws SQLException {

            try {
                // Vider la liste existante
                userList.clear();


                // Convertir les Users en UserModels
                List<User> users = userDao.getAllUsers();
                Platform.runLater(() -> {
                    try {
                        userList.clear();

                        for (User user : users) {
                            UserModel userModel = new UserModel(
                                    user.getId(),
                                    user.getNomUtilisateur(),
                                    user.getMotDePasse(),
                                    user.getRole()
                            );
                            userList.add(userModel);
                        }

                        TreeItem<UserModel> root = new RecursiveTreeItem<>(userList, RecursiveTreeObject::getChildren);
                        userTable.setRoot(root);
                        userTable.setShowRoot(false);
                        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getId()));
                        nameColumn.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getNomUtilisateur()));
                        passwordColumn.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getMotDePasse()));
                        roleColumn.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getRole()));

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

                try {

                    System.out.println("check");
                    loadUsers();
                    sleep(20000);

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
    public void facture(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        product facture = new product();

        facture.initialize(vbox.getScene());

        this.userTable.getScene().getWindow().hide();



    }
    public void client(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        Client client = new Client();

        client.initialize(vbox.getScene());

        this.userTable.getScene().getWindow().hide();



    }
    public void produit(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        product produit = new product();

        produit.initialize(vbox.getScene());

        this.userTable.getScene().getWindow().hide();



    }
    public void adduser(ActionEvent actionEvent)throws SQLException, IOException, ClassNotFoundException{
        AddUser addUser=new AddUser();
        addUser.start(vbox.getScene());
        this.userTable.getScene().getWindow().hide();
    }
    public void commande(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        commande commande = new commande();

        commande.initialize(vbox.getScene());

        this.userTable.getScene().getWindow().hide();



    }
    public void Paiement(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        Paiement paiement = new Paiement();

        paiement.initialize(vbox.getScene());

        this.userTable.getScene().getWindow().hide();



    }


    @FXML
    public void initialize(Scene scene) throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("dashboarduser.fxml"));

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


        userTable.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        userTable.getStyleClass().addAll("table-view","table table-striped");


        // Configuration des colonnes











        // Chargement des données




































    }
















}