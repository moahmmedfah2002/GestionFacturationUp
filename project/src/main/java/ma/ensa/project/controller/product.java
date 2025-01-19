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
import ma.ensa.project.entity.Produit;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.ClientService;
import ma.ensa.project.service.ProduitService;
import ma.ensa.project.service.UserService;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class product {
    @Data
    public class ProduitModel extends RecursiveTreeObject<ProduitModel> {
        private Object id;
        private Object name;
        private Object p;
        private Object quanti;
        private Object UserName;
        private Object tv;
        private Button delete;
        private Button update;

        // Constructeur
        public ProduitModel(String id, Object NomProduit, Object pr, Object quan,Object t, Object Us) {

            this.id=id;

            this.name =  NomProduit;
//            System.out.println(name.get());
            this.p = pr;
            this.quanti=quan;

            this.UserName=Us;
            this.tv=t;
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


                if(Update.etat) {

                    Update.etat = false;
                    name = new TextField(String.valueOf(name));

                    p = new TextField(String.valueOf(p));

                    quanti = new TextField(String.valueOf(quanti));

                    tv = new TextField(String.valueOf(tv));





                    update.setText("ok");


                  produitTable.refresh();

                }else {

                    update.setText("Update");
                    Produit produit= new Produit();
                    produit.setId(Integer.parseInt(id));
                    produit.setNom(String.valueOf(((TextField)name).getText()));
                    produit.setPrix(Float.parseFloat(((TextField)p).getText()));
                    produit.setQuantiteDisponible(Integer.parseInt(((TextField)quanti).getText()));
                    produit.setTva(Float.parseFloat((((TextField)tv).getText())));

                    try {
                        produitDao.updateProduit(produit);

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Update.etat = true;
                    Update update1 = new Update();
                    try {
                        update1.loadProduit();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                   produitTable.refresh();


                }

            });
            this.delete.setOnAction(event -> {





                try {
                    produitDao.deleteProduit(Integer.parseInt(delete.getId()));
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
    public JFXTreeTableView<ProduitModel> produitTable= new JFXTreeTableView<>();
    @FXML
    public JFXTreeTableColumn<ProduitModel, Object> nom= new JFXTreeTableColumn<>("nom");
    @FXML
    public JFXTreeTableColumn<ProduitModel, Object> prix= new JFXTreeTableColumn<>("prix");
    @FXML
    public JFXTreeTableColumn<ProduitModel, Object>  quantitedispo=new JFXTreeTableColumn<>("quantitedispo");

    @FXML
    public JFXTreeTableColumn<ProduitModel, Object> user=new JFXTreeTableColumn<>("username");
    @FXML
    public JFXTreeTableColumn<ProduitModel, Object> tva=new JFXTreeTableColumn<>("tva");

    @FXML
    public Button btnClose;
    public Button btnFull;
    private UserService userService;
    private static ObservableList<ProduitModel> produitList = FXCollections.observableArrayList();
    @FXML
    private JFXTreeTableColumn<ProduitModel, Button> DeleteColumn=new JFXTreeTableColumn<>("delete");
    @FXML
    private JFXTreeTableColumn<ProduitModel, Button> updateColumn=new JFXTreeTableColumn<>("update");

    private static ProduitService produitDao;


    public void addproduct(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        addproduct add= new addproduct();

        add.initialize(vbox.getScene());

        this.produitTable.getScene().getWindow().hide();



    }
    public void user(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Update.etat=false;
        DashboardUser user = new DashboardUser();

        user.initialize(vbox.getScene());

        this.produitTable.getScene().getWindow().hide();



    }

    public void facture(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        facture facture = new facture();

        facture.initialize(new Stage().getScene());

        this.produitTable.getScene().getWindow().hide();



    }
    public void commande(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {



        commande commande = new commande();

        commande.initialize(vbox.getScene());

        this.produitTable.getScene().getWindow().hide();



    }
    public void client(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        Client client = new Client();


        client.initialize(vbox.getScene());

        this.produitTable.getScene().getWindow().hide();



    }

    public void Paiement(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        Paiement paiement = new Paiement();

        paiement.initialize(vbox.getScene());

        this.produitTable.getScene().getWindow().hide();



    }

    public product() throws SQLException, ClassNotFoundException, IOException {

        produitDao = new ProduitService();
        produitList = FXCollections.observableArrayList();
        ProduitService ProduitService=new ProduitService();
        Update update = new Update();
        update.loadProduit();

        produitTable.getColumns().add(nom);
        produitTable.getColumns().add(prix);
        produitTable.getColumns().add(quantitedispo);

        produitTable.getColumns().add(user);
        produitTable.getColumns().add(tva);
        produitTable.getColumns().add(DeleteColumn);
        produitTable.getColumns().add(updateColumn);
        update.loadProduit();

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
            produitList.clear();
            try {
                // Vider la liste existante




                // Convertir les Users en UserModels
                List<Produit> produits = produitDao.getAllProduits();
                Platform.runLater(() -> {
                    try {
                        produitList.clear();


                        for (Produit produit : produits) {

                            int id = produit.getUserId();
                            System.out.println(id);
                            UserService userService=new UserService();
                            User user1=userService.getUser(id);


                            ProduitModel produitModel = new ProduitModel(
                                    String.valueOf(produit.getId()),
                                    produit.getNom(),
                                    produit.getPrix(),
                                   produit.getQuantiteDisponible(),

                                    produit.getTva(),
                                    user1.getNomUtilisateur()


                                    );


                            produitList.add(produitModel);


                        }

                        TreeItem<ProduitModel> root = new RecursiveTreeItem<>(produitList, RecursiveTreeObject::getChildren);
//                        root.getChildren().forEach((e)->{ System.out.println(e.getValue().getName().get()); });
                        produitTable.setRoot(root);
                        produitTable.setShowRoot(false);
                        nom.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getName()));

                        prix.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getP()));
                        quantitedispo.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getQuanti()));

                        user.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getUserName()));
                        tva.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getTv()));
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

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("product.fxml"));

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


        produitTable.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
       produitTable.getStyleClass().addAll("table-view","table table-striped");



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
