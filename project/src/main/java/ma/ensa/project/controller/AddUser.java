package ma.ensa.project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.ensa.project.ApplicationGestionFacturation;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddUser {

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label label;
    @FXML private Button btnFull;
    @FXML private Button btnClose;

    @FXML private CheckBox createUserCheck;
    @FXML private CheckBox readUserCheck;
    @FXML private CheckBox updateUserCheck;
    @FXML private CheckBox deleteUserCheck;

    @FXML private CheckBox createClientCheck;
    @FXML private CheckBox readClientCheck;
    @FXML private CheckBox updateClientCheck;
    @FXML private CheckBox deleteClientCheck;

    @FXML private CheckBox createCommandeCheck;
    @FXML private CheckBox readCommandeCheck;
    @FXML private CheckBox updateCommandeCheck;
    @FXML private CheckBox deleteCommandeCheck;

    @FXML private CheckBox createProduitCheck;
    @FXML private CheckBox readProduitCheck;
    @FXML private CheckBox updateProduitCheck;
    @FXML private CheckBox deleteProduitCheck;

    @FXML private CheckBox createFactureCheck;
    @FXML private CheckBox readFactureCheck;
    @FXML private CheckBox updateFactureCheck;
    @FXML private CheckBox deleteFactureCheck;

    @FXML private CheckBox createPaiementCheck;
    @FXML private CheckBox readPaiementCheck;
    @FXML private CheckBox updatePaiementCheck;
    @FXML private CheckBox deletePaiementCheck;

    @FXML private CheckBox mailCheck;
    @FXML
    public VBox vbox= new VBox();

    private ObservableList<String> list = FXCollections.observableArrayList("Admin", "User");
    private UserService userService;



    public void initialize(){
        ObservableList<String>list= FXCollections.observableArrayList("Admin","User");
        roleComboBox.setItems(list);
        if (roleComboBox==null){
            System.out.println("is null");

        }
    }


    private void updatePermissionsBasedOnRole(String role) {
        if (role == null) return;
        boolean isAdmin = "Admin".equals(role);
        setAllPermissions(isAdmin);
    }

    private void setAllPermissions(boolean enabled) {
        createUserCheck.setSelected(enabled);
        readUserCheck.setSelected(enabled);
        updateUserCheck.setSelected(enabled);
        deleteUserCheck.setSelected(enabled);

        createClientCheck.setSelected(enabled);
        readClientCheck.setSelected(enabled);
        updateClientCheck.setSelected(enabled);
        deleteClientCheck.setSelected(enabled);

        createCommandeCheck.setSelected(enabled);
        readCommandeCheck.setSelected(enabled);
        updateCommandeCheck.setSelected(enabled);
        deleteCommandeCheck.setSelected(enabled);

        createProduitCheck.setSelected(enabled);
        readProduitCheck.setSelected(enabled);
        updateProduitCheck.setSelected(enabled);
        deleteProduitCheck.setSelected(enabled);

        createFactureCheck.setSelected(enabled);
        readFactureCheck.setSelected(enabled);
        updateFactureCheck.setSelected(enabled);
        deleteFactureCheck.setSelected(enabled);

        createPaiementCheck.setSelected(enabled);
        readPaiementCheck.setSelected(enabled);
        updatePaiementCheck.setSelected(enabled);
        deletePaiementCheck.setSelected(enabled);

        mailCheck.setSelected(enabled);
    }

    @FXML
    public void save(ActionEvent event) {

        try {
            userService=new UserService();
            if (username.getText().isEmpty() || password.getText().isEmpty() ||
                    roleComboBox.getValue() == null) {
                label.setText("Veuillez remplir tous les champs");
                label.setTextFill(Color.RED);
                return;
            }

            List<String> permissions = getSelectedPermissions();
            ma.ensa.project.entity.User newUser=new ma.ensa.project.entity.User();
            newUser.setRole(roleComboBox.getValue());
            newUser.setMotDePasse(password.getText());
            newUser.setNomUtilisateur(username.getText());
            userService.addUser(newUser,permissions);
            label.setText("Utilisateur créé avec succès");
            label.setTextFill(Color.GREEN);
            System.out.println("Nouvel utilisateur créé : " + newUser); // Pour debug
            clearFields();

        } catch (Exception e) {
            label.setText("Erreur lors de la création: " + e.getMessage());
            label.setTextFill(Color.RED);
        }
    }

    private void clearFields() {
        username.clear();
        password.clear();
        roleComboBox.getSelectionModel().clearSelection();
        setAllPermissions(false);
    }

    private List<String> getSelectedPermissions() {
        List<String> permissions = new ArrayList<>();

        if (createUserCheck.isSelected()) permissions.add("CREATE_USER");
        if (readUserCheck.isSelected()) permissions.add("READ_USER");
        if (updateUserCheck.isSelected()) permissions.add("UPDATE_USER");
        if (deleteUserCheck.isSelected()) permissions.add("DELETE_USER");

        if (createClientCheck.isSelected()) permissions.add("CREATE_CLIENT");
        if (readClientCheck.isSelected()) permissions.add("READ_CLIENT");
        if (updateClientCheck.isSelected()) permissions.add("UPDATE_CLIENT");
        if (deleteClientCheck.isSelected()) permissions.add("DELETE_CLIENT");

        if (createCommandeCheck.isSelected()) permissions.add("CREATE_COMMANDE");
        if (readCommandeCheck.isSelected()) permissions.add("READ_COMMANDE");
        if (updateCommandeCheck.isSelected()) permissions.add("UPDATE_COMMANDE");
        if (deleteCommandeCheck.isSelected()) permissions.add("DELETE_COMMANDE");

        if (createProduitCheck.isSelected()) permissions.add("CREATE_PRODUIT");
        if (readProduitCheck.isSelected()) permissions.add("READ_PRODUIT");
        if (updateProduitCheck.isSelected()) permissions.add("UPDATE_PRODUIT");
        if (deleteProduitCheck.isSelected()) permissions.add("DELETE_PRODUIT");

        if (createFactureCheck.isSelected()) permissions.add("CREATE_FACTURE");
        if (readFactureCheck.isSelected()) permissions.add("READ_FACTURE");
        if (updateFactureCheck.isSelected()) permissions.add("UPDATE_FACTURE");
        if (deleteFactureCheck.isSelected()) permissions.add("DELETE_FACTURE");

        if (createPaiementCheck.isSelected()) permissions.add("CREATE_PAIEMENT");
        if (readPaiementCheck.isSelected()) permissions.add("READ_PAIEMENT");
        if (updatePaiementCheck.isSelected()) permissions.add("UPDATE_PAIEMENT");
        if (deletePaiementCheck.isSelected()) permissions.add("DELETE_PAIEMENT");

        if (mailCheck.isSelected()) permissions.add("MAIL");

        return permissions;
    }

    @FXML
    public void full(ActionEvent event) {
        Stage stage = (Stage) btnFull.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML
    public void close(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public static class User {
        private String username;
        private String password;
        private String role;
        private List<String> permissions;

        public User(String username, String password, String role, List<String> permissions) {
            this.username = username;
            this.password = password;
            this.role = role;
            this.permissions = permissions;

        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", role='" + role + '\'' +
                    ", permissions=" + permissions +
                    '}';
        }

        // Getters et Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public List<String> getPermissions() {
            return permissions;
        }

        public void setPermissions(List<String> permissions) {
            this.permissions = permissions;
        }
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


    @FXML
    public void start(Scene scene) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("AddUser.fxml"));
        scene = new Scene(fxmlLoader.load());
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Gestion des Utilisateurs");
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}
