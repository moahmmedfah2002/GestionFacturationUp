package ma.ensa.project.controller;



import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Data;
import ma.ensa.project.ApplicationGestionFacturation;
import ma.ensa.project.entity.Commande;
import ma.ensa.project.entity.Produit;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.*;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Paiement {
    @Data
    public class PaiementModel extends RecursiveTreeObject<PaiementModel> {
        private Object id;
        private Object name;
        private Object p;
        private Object da;
        private Object UserName;


        private Button update;

        // Constructeur
        public PaiementModel(String id, Object Nompaye, Object pr, Object d, Object Us) {

            this.id=id;

            this.name =  Nompaye;
//            System.out.println(name.get());
            this.p = pr;
            this.da=d;

            this.UserName=Us;


            this.update=new Button("Update");
            this.update.setId(id);
            this.update.setStyle("-fx-background-color: #27ae60;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-padding: 5px 10px;");

            this.update.setOnAction(event -> {


                if(Update.etat) {

                    Update.etat = false;


                    da = new TextField(String.valueOf(da));










                    update.setText("ok");
                    paiementTable.refresh();


                }else {

                    update.setText("Update");
                    ma.ensa.project.entity.Paiement pay= new ma.ensa.project.entity.Paiement();
                    pay.setId(Integer.parseInt(id));
                    pay.setDate(Date.valueOf(((TextField)da).getText()));



                    try {
                        paiementDao.updatePaiement(pay);

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Update.etat = true;
                    Update update1 = new Update();
                    try {
                        update1.loadPaiement();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                   paiementTable.refresh();



                }

            });

        }
    }
    @FXML
    public VBox vbox= new VBox();

    @FXML
    public JFXTreeTableView<PaiementModel> paiementTable= new JFXTreeTableView<>();
    @FXML
    public JFXTreeTableColumn<PaiementModel, Object> nom= new JFXTreeTableColumn<>("nom");
    @FXML
    public JFXTreeTableColumn<PaiementModel, Object> prix= new JFXTreeTableColumn<>("prix");
    @FXML
    public JFXTreeTableColumn<PaiementModel, Object>  date=new JFXTreeTableColumn<>("date");

    @FXML
    public JFXTreeTableColumn<PaiementModel, Object> user=new JFXTreeTableColumn<>("username");


    @FXML
    public Button btnClose;
    public Button btnFull;
    private UserService userService;
    private static ObservableList<PaiementModel> paiementList = FXCollections.observableArrayList();
    @FXML
    private JFXTreeTableColumn<PaiementModel, Button> DeleteColumn=new JFXTreeTableColumn<>("delete");
    @FXML
    private JFXTreeTableColumn<PaiementModel, Button> updateColumn=new JFXTreeTableColumn<>("update");

    private static PaimentService paiementDao;


    public void addpaiement(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        addproduct add= new addproduct();

        add.initialize(vbox.getScene());

        this.paiementTable.getScene().getWindow().hide();



    }
    public void user(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        DashboardUser user = new DashboardUser();

        user.initialize(vbox.getScene());

        this.paiementTable.getScene().getWindow().hide();



    }
    public void produit(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        product produit = new product();

        produit.initialize(vbox.getScene());

        this.paiementTable.getScene().getWindow().hide();



    }

    public void facture(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        facture facture = new facture();

        facture.initialize(new Stage().getScene());

        this.paiementTable.getScene().getWindow().hide();



    }
    public void commande(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        commande commande = new commande();

        commande.initialize(vbox.getScene());

        this.paiementTable.getScene().getWindow().hide();



    }
    public void client(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Client client = new Client();

        client.initialize(vbox.getScene());

        this.paiementTable.getScene().getWindow().hide();



    }
    public void addpaiements(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Commandenonpay c = new Commandenonpay();

        c.initialize(vbox.getScene());

        this.paiementTable.getScene().getWindow().hide();



    }


    public Paiement() throws SQLException, ClassNotFoundException, IOException {

        paiementDao = new PaimentService();
       paiementList = FXCollections.observableArrayList();

        Update update = new Update();
        update.loadPaiement();

        paiementTable.getColumns().add(nom);
        paiementTable.getColumns().add(prix);
        paiementTable.getColumns().add(date);

        paiementTable.getColumns().add(user);

        paiementTable.getColumns().add(DeleteColumn);
        paiementTable.getColumns().add(updateColumn);
        update.loadPaiement();

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
    public class Update extends Thread{
        public void loadPaiement() throws SQLException {
            paiementList.clear();
            try {
                // Vider la liste existante




                // Convertir les Users en UserModels
                List<ma.ensa.project.entity.Paiement> paiements =paiementDao.getAllPaiement();
                Platform.runLater(() -> {
                    try {
                        paiementList.clear();


                        for (ma.ensa.project.entity.Paiement paiement : paiements) {

                            int id = paiement.getIdUser();
                            int idc=paiement.getCommandeId();
                            System.out.println(id);
                            CommandeService commandeservice=new CommandeService();
                            Commande commande1=commandeservice.getCommande(idc);
                            UserService userService=new UserService();
                            User user1=userService.getUser(id);
                            ClientService clientService=new ClientService();
                            ma.ensa.project.entity.Client client=clientService.getClient(commande1.getClient());


                            PaiementModel paiementModel = new PaiementModel(
                                    String.valueOf(paiement.getId()),
                                   client.getNom(),
                                    commande1.getTotalAmount(),
                                    paiement.getDate(),


                                    user1.getNomUtilisateur()


                            );


                            paiementList.add(paiementModel);


                        }

                        TreeItem<PaiementModel> root = new RecursiveTreeItem<>(paiementList, RecursiveTreeObject::getChildren);
//                        root.getChildren().forEach((e)->{ System.out.println(e.getValue().getName().get()); });
                        paiementTable.setRoot(root);
                        paiementTable.setShowRoot(false);
                        nom.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getName()));

                        prix.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getP()));
                        date.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getDa()));

                        user.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getUserName()));


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


                    loadPaiement();
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
    @FXML
    public void initialize(Scene scene) throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("paiement.fxml"));

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


        paiementTable.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        paiementTable.getStyleClass().addAll("table-view","table table-striped");



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