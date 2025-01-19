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
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Data;
import ma.ensa.project.ApplicationGestionFacturation;
import ma.ensa.project.entity.DetaileCommande;
import ma.ensa.project.entity.Produit;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.CommandeService;
import ma.ensa.project.service.DetaileCommandeService;
import ma.ensa.project.service.ProduitService;
import ma.ensa.project.service.UserService;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@Data
public class detail {
  private int idcom;



    @FXML
    public Button userbtn;
    public Button clientbtn;
    public Button produitbtn;
    public Button commandebtn;
    public Button paiementbtn;
    public Button facturnbtn;



    @Data
    public class DetailModel extends RecursiveTreeObject<DetailModel> {
        private Object id;
        private Object produi;
        private Object quantite;

        // Constructeur
        public DetailModel(String id, Object p, Object q) {

            this.id=id;

            this.produi =  p;
//            System.out.println(name.get());
            this.quantite = q;








    }}
    @FXML
    public VBox vbox= new VBox();

    @FXML

    public JFXTreeTableView<DetailModel> detailTable= new JFXTreeTableView<>();
    @FXML
    public JFXTreeTableColumn<DetailModel, Object> product= new JFXTreeTableColumn<>("produit");
    @FXML
    public JFXTreeTableColumn<DetailModel, Object> quan= new JFXTreeTableColumn<>("quantity");

    @FXML
    public Button btnClose;
    public Button btnFull;
    private UserService userService;
    private static ObservableList<DetailModel> detailList = FXCollections.observableArrayList();


    private static ProduitService Dao;
    private static DetaileCommandeService service;


    public void user(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        DashboardUser user = new DashboardUser();

        user.initialize(vbox.getScene());

        this.detailTable.getScene().getWindow().hide();



    }
    public void client(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        Client c= new Client();

        c.initialize(vbox.getScene());

        this.detailTable.getScene().getWindow().hide();



    }

    public void produit(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        product produit = new product();

        produit.initialize(vbox.getScene());

        this.detailTable.getScene().getWindow().hide();



    }
    public void commande(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        ma.ensa.project.controller.commande commande= new ma.ensa.project.controller.commande();

        commande.initialize(vbox.getScene());

        this.detailTable.getScene().getWindow().hide();




    }
    public void Paiement(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        Paiement paiement = new Paiement();

        paiement.initialize(vbox.getScene());

        this.detailTable.getScene().getWindow().hide();



    }
    public void facture(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        facture facture = new facture();

        facture.initialize(new Stage().getScene());

        this.detailTable.getScene().getWindow().hide();



    }


    public  detail(int id) throws SQLException, ClassNotFoundException, IOException {



    }
    public detail() throws SQLException, ClassNotFoundException {

        service= new DetaileCommandeService();
        detailList = FXCollections.observableArrayList();

        Update update = new Update();

        detailTable.getColumns().add(product);
        detailTable.getColumns().add(quan);
        update.loadDetail();





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

        public void loadDetail() throws SQLException {
            //clientList.clear();
            // public void loadClient() throws SQLException {
            detailList.clear();

            try {
                // Vider la liste existante




                // Convertir les Users en UserModels
                List<DetaileCommande> commandedetails = service.getDetaileCommandebyidcommande(idcom);
                System.out.println(idcom);
                Platform.runLater(() -> {
                    detailList.clear();

                    try {
                        //clientList.clear();


                        for (DetaileCommande d : commandedetails) {

                            int id = d.getIdProduit();

                            Dao=new ProduitService();
                            Produit nomproduit=Dao.getProduit(id);



                            DetailModel detailModel = new DetailModel(
                                    String.valueOf(d.getId()),
                                    nomproduit.getNom(),
                                    d.getQuantite()

                            );


                            detailList.add(detailModel);


                        }

                        TreeItem<DetailModel> root = new RecursiveTreeItem<>(detailList, RecursiveTreeObject::getChildren);
//                        root.getChildren().forEach((e)->{ System.out.println(e.getValue().getName().get()); });
                        detailTable.setRoot(root);
                        detailTable.setShowRoot(false);
                        product.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getProdui()));

                        quan.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getQuantite()));






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


                    loadDetail();
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

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("detail.fxml"));

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


        detailTable.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        detailTable.getStyleClass().addAll("table-view","table table-striped");



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
