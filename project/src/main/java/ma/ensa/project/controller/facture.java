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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Data;
import ma.ensa.project.ApplicationGestionFacturation;
import javafx.scene.layout.VBox;
import ma.ensa.project.entity.DetaileCommande;
import ma.ensa.project.entity.Facture;
import ma.ensa.project.entity.Produit;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        private Button commandeBtn= new Button("Commande");


        // Constructeur
        public FactureModel(String id, Object com, Object cli,Object us) {

            this.id=id;
            this.client = cli;
            this.commande = com;
            this.UserName=us;


            this.delete=new Button("Delete");
            this.delete.setId(id);
            this.update=new Button("PDF");
            this.update.setId(id);
            commandeBtn.setId(id);
            commandeBtn.setText("commande");
            this.update.setStyle("-fx-background-color: #27ae60;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-padding: 5px 10px;");
            this.delete.setStyle("-fx-background-color: #ae2727;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-padding: 5px 10px;");
            System.out.println("id: "+delete.getId());
            this.update.setOnAction(_ -> {
                try {



                DetaileCommandeService detaileCommandeService= null;

                    detaileCommandeService = new DetaileCommandeService();

                    CommandeService commandeService = new CommandeService();
                    ClientService clientService = new ClientService();
                    ma.ensa.project.entity.Client c = clientService.getClient(commandeService.getCommande(Integer.parseInt(String.valueOf(com))).getClient());


                    List<DetaileCommande> detaileCommandes= null ;
                    detaileCommandes = detaileCommandeService.getDetaileCommandebyidcommande(Integer.parseInt(String.valueOf(com)));
                    List<Produit>produits=new ArrayList<>();
                    for(DetaileCommande detaileCommande : detaileCommandes) {
                        ProduitService produitService = new ProduitService();
                        Produit produit=new Produit();
                        produit.setId(detaileCommande.getIdProduit());
                        produit=produitService.getProduit(produit.getId());
                        produits.add(produit);

                    }


                    try (PDDocument document = new PDDocument()) {
                        PDPage page = new PDPage();
                        document.addPage(page);

                        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {


//                 Add Invoice Header

                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                            contentStream.newLineAtOffset(220, 750); // X, Y coordinates
                            contentStream.showText("Facture");
                            contentStream.endText();



                            // Add Client Details
                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.HELVETICA, 12);
                            contentStream.newLineAtOffset(50, 640);
                            contentStream.showText("Client Name: " + cli);
                            contentStream.newLineAtOffset(0, -15);
                            contentStream.showText("Address: " + c.getAdresse());
                            contentStream.newLineAtOffset(0, -15);
                            contentStream.showText("Contact: " + c.getTelephone());

                            contentStream.endText();

                            // Add Invoice Details
                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.HELVETICA, 12);
                            contentStream.newLineAtOffset(400, 700);
                            contentStream.showText("Invoice #: " + id);
                            contentStream.newLineAtOffset(0, -15);
                            contentStream.showText("Date: " + LocalDate.now().toString());
                            contentStream.endText();

                            // Add Table Header
                            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                            contentStream.beginText();
                            contentStream.newLineAtOffset(50, 580);
                            contentStream.showText("Item");
                            contentStream.newLineAtOffset(200, 0);
                            contentStream.showText("Quantity");
                            contentStream.newLineAtOffset(100, 0);
                            contentStream.showText("Price");
                            contentStream.newLineAtOffset(100, 0);
                            contentStream.showText("Total");
                            contentStream.endText();

                            // Add Table Rows (Example Items)
                            int yPosition = 560; // Start position



                            List<String[]> items =new ArrayList<>();
                            float somme=0;
                            for(Produit p  :produits  ){

                                float quantite=detaileCommandes.stream().filter((e)->e.getIdProduit()==p.getId()).toList().getFirst().getIdProduit();
                                items.add(new String[]{"Product " + p.getNom(), String.valueOf(quantite), String.valueOf(p.getPrix()), String.valueOf(quantite*p.getPrix()*(p.getTva()/100))});
                                somme+=quantite*p.getPrix()*(p.getTva()/100);
                            }

                            contentStream.setFont(PDType1Font.HELVETICA, 12);
                            for (String[] item : items) {
                                contentStream.beginText();
                                contentStream.newLineAtOffset(50, yPosition);
                                contentStream.showText(item[0]);
                                contentStream.newLineAtOffset(200, 0);
                                contentStream.showText(item[1]);
                                contentStream.newLineAtOffset(100, 0);
                                contentStream.showText(item[2]);
                                contentStream.newLineAtOffset(100, 0);
                                contentStream.showText(item[3]);
                                contentStream.endText();
                                yPosition -= 20; // Move to the next row
                            }

                            // Add Total Amount
                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                            contentStream.newLineAtOffset(350, yPosition - 20);
                            contentStream.showText("Grand Total: " + somme);
                            contentStream.endText();
                        }

                        // Save the PDF to a file
                        document.save("C:\\Users\\pc\\Documents\\java2\\GestionFacturationCopie\\project\\src\\main\\resources\\ma\\ensa\\project\\facture.pdf");
                        System.out.println("pdf :: facture1.pdf !!!!!!!!!!!1");
                        System.out.println("Invoice PDF created successfully!");


                        MailService mailService = new MailService();
                        mailService.send(c.getEmail(), "Facture", "your facture is :", "C:\\Users\\pc\\Documents\\java2\\GestionFacturationCopie\\project\\src\\main\\resources\\ma\\ensa\\project\\facture.pdf");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            });
            this.delete.setOnAction(event -> {





                try {
                    Facture facture1 = new Facture();
                    facture1.setId(Integer.parseInt(delete.getId()));
                    factureDao.deleteFacture(facture1.getId());
                    System.out.println(Integer.parseInt(delete.getId()));
                    Update update=new Update();
                    update.loadFacture();
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
        factureDao=new FactureService();
        Update update = new Update();
        update.loadFacture();
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
        public void loadFacture() throws SQLException {
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
                            User user1=userService.getUser(userService.getSession().getId());

                            CommandeService commandeService=new CommandeService();
                            ClientService clientService=new ClientService();
                            System.out.println("nom :: "+clientService.getClient(facture.getIdCommande()).getNom());
                            FactureModel factureModel = new FactureModel(
                                    String.valueOf(facture.getId()),
                                    facture.getIdCommande(),
                                    clientService.getClient(commandeService.getCommande(facture.getIdCommande()).getClient()).getNom(),

                                    userService.getUser(user1.getId()).getNomUtilisateur()



                            );

                            System.out.println("ccc"+factureModel.getId());
                            factureList.add(factureModel);


                        }

                        TreeItem<FactureModel> root = new RecursiveTreeItem<>(factureList, RecursiveTreeObject::getChildren);
//                        root.getChildren().forEach((e)->{ System.out.println(e.getValue().getName().get()); });
                        factureTable.setRoot(root);
                        factureTable.setShowRoot(false);
                        id.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getId()));
                        user.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getUserName()));
                        client.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getClient()));
                        commande.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getCommandeBtn()));


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


                    loadFacture();
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
