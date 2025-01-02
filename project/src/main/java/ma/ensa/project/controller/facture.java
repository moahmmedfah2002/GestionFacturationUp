package ma.ensa.project.controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.github.palexdev.mfxcore.controls.Label;
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
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Data;
import ma.ensa.project.ApplicationGestionFacturation;
import javafx.scene.layout.VBox;
import ma.ensa.project.entity.Facture;
import ma.ensa.project.entity.Produit;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.FactureService;
import ma.ensa.project.service.ProduitService;
import ma.ensa.project.service.UserService;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class facture {


    public void produit(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        ma.ensa.project.controller.commande.Update.etat=false;
        product produit = new product();

        produit.initialize(vbox.getScene());
        this.factureTable.getScene().getWindow().hide();



    }
    @Data
    public class FactureModel extends RecursiveTreeObject<FactureModel>{
        private Object id;
        private Object client;
        private Object commande;
        private Object UserName;

        private Button delete;
        private Button update;

        // Constructeur
        public FactureModel(String id, Object com, Object cli,Object us) {

            this.id=id;
            this.client = cli;
            this.commande = com;
            this.UserName=us;

            this.delete=new Button("Delete");
            this.delete.setId(id);
            this.update=new Button("Update");
            this.update.setId(id);
            this.update.setStyle("-fx-background-color: #27ae60;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-padding: 5px 10px;");
            this.delete.setStyle("-fx-background-color: #ae2727;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-padding: 5px 10px;");
            System.out.println("id: "+delete.getId());
            this.update.setOnAction(event -> {


                if(product.Update.etat) {

                    Update.etat = false;






                }else {



                }

            });
            this.delete.setOnAction(event -> {





                try {
                    factureDao.deleteFacture(Integer.parseInt(delete.getId()));
                    System.out.println(Integer.parseInt(delete.getId()));
                    Update update=new Update();
                    update.loadProduit();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    @FXML
    public VBox vbox= new VBox();

    @FXML
    public JFXTreeTableView<FactureModel> factureTable= new JFXTreeTableView<>();
    @FXML
    public JFXTreeTableColumn<FactureModel, Object> client= new JFXTreeTableColumn<>("client");
    @FXML
    public JFXTreeTableColumn<FactureModel, Object> commande= new JFXTreeTableColumn<>("commande");
    @FXML
    public JFXTreeTableColumn<FactureModel, Object>  id=new JFXTreeTableColumn<>("id");

    @FXML
    public JFXTreeTableColumn<FactureModel, Object> user=new JFXTreeTableColumn<>("username");

    @FXML
    public Button btnClose;
    public Button btnFull;
    private UserService userService;
    private static ObservableList<FactureModel> factureList = FXCollections.observableArrayList();
    @FXML
    private JFXTreeTableColumn<FactureModel, Button> DeleteColumn=new JFXTreeTableColumn<>("delete");
    @FXML
    private JFXTreeTableColumn<FactureModel, Button> PdfColumn=new JFXTreeTableColumn<>("update");

    private static FactureService factureDao;


    public void addproduct(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        addproduct add= new addproduct();

        add.initialize(vbox.getScene());

        this.factureTable.getScene().getWindow().hide();



    }
    public void user(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        product.Update.etat=false;
        DashboardUser user = new DashboardUser();

        user.initialize(vbox.getScene());

        this.factureTable.getScene().getWindow().hide();



    }

    public void facture(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        facture facture = new facture();

        facture.initialize(new Stage().getScene());

        this.factureTable.getScene().getWindow().hide();



    }
    public void commande(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {



        commande commande = new commande();

        commande.initialize(vbox.getScene());

        this.factureTable.getScene().getWindow().hide();



    }
    public void client(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        Client client = new Client();


        client.initialize(vbox.getScene());

        this.factureTable.getScene().getWindow().hide();



    }

    public void Paiement(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Paiement paiement = new Paiement();

        paiement.initialize(vbox.getScene());

        this.factureTable.getScene().getWindow().hide();



    }

    public facture() throws SQLException, ClassNotFoundException, IOException {

        factureDao = new FactureService();
        factureList = FXCollections.observableArrayList();
        ProduitService ProduitService=new ProduitService();
        Update update = new Update();
        update.loadProduit();

        factureTable.getColumns().add(id);
        factureTable.getColumns().add(user);
        factureTable.getColumns().add(client);
        factureTable.getColumns().add(commande);
        factureTable.getColumns().add(DeleteColumn);
        factureTable.getColumns().add(PdfColumn);



        update.start();

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
        public void loadProduit() throws SQLException {
            factureList.clear();
            try {
                // Vider la liste existante




                // Convertir les Users en UserModels
                List<Facture> factures = factureDao.getFactures();
                Platform.runLater(() -> {
                    try {
                        factureList.clear();


                        for (Facture facture : factures) {

                            int id = facture.getId();

                            UserService userService=new UserService();
                            User user1=userService.getUser(id);


                            FactureModel factureModel = new FactureModel(
                                    String.valueOf(facture.getId()),
                                    facture.getCommande(),
                                    facture.getClientId(),

                                    user1.getNomUtilisateur()



                            );


                            factureList.add(factureModel);


                        }

                        TreeItem<FactureModel> root = new RecursiveTreeItem<>(factureList, RecursiveTreeObject::getChildren);
//                        root.getChildren().forEach((e)->{ System.out.println(e.getValue().getName().get()); });
                        factureTable.setRoot(root);
                        factureTable.setShowRoot(false);
                        id.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getId()));
                        user.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getUserName()));
                        client.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getClient()));
                        commande.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getId()));


                        DeleteColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getDelete()));
                        PdfColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getUpdate()));





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


                    loadProduit();
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

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("facture.fxml"));

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


        factureTable.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        factureTable.getStyleClass().addAll("table-view","table table-striped");



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
