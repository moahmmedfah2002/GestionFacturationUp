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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Data;
import ma.ensa.project.ApplicationGestionFacturation;
import ma.ensa.project.entity.*;
import ma.ensa.project.entity.Client;
import ma.ensa.project.service.ClientService;
import ma.ensa.project.service.CommandeService;
import ma.ensa.project.service.FactureService;
import ma.ensa.project.service.UserService;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.List;

import static java.lang.Thread.sleep;

public class commande {

    @FXML
    public Button userBtn =new Button();


    @Data
    public class CommandeModel extends RecursiveTreeObject<CommandeModel> {
        private Object id;

        private Object commandeda;
        private Object total;
        private Object cl;
        private Object UserName;
        private Object statu;
        private String clientname="";
        private Button delete;
        private Button update;
        private Button detailscommande;
        private Button ajoutefacture;
        private Boolean s;
        private int num;

        // Constructeur
        public CommandeModel(String id, Object c, Object to, Object cli,Object us,int num,Object st) throws SQLException {

            this.id=id;

            this.commandeda =  c;
//            System.out.println(name.get());
            this.total = to;
            this.cl=cli;

            this.UserName=us;
            this.statu=st;
            List<Client> clients1=clientService.getClients();
            ChoiceBox<String> cl = new ChoiceBox<>();
            cl.setValue((String) cli);
            for(Client c1: clients1) {
                cl.getItems().add(c1.getNom());
            }


            this.delete=new Button("Delete");
            this.delete.setId(id);
            this.detailscommande=new Button("details ");
            this.detailscommande.setId(id);
            this.ajoutefacture=new Button("ajoutefacture");
            this.ajoutefacture.setId(id);
            this.ajoutefacture.setOnAction(event->{

                try {
                    facture=new Facture();
                     factureService=new FactureService();

                    int idfa= Integer.parseInt(ajoutefacture.getId());

                    User user2=userService.getSession();

                    facture.setIdCommande(idfa);
                    facture.setIdUser(user2.getId());
                    factureService.addFacture(facture);
                    System.out.println(idfa);
                    System.out.println(user2.getId());



                } catch (SQLException | ClassNotFoundException | IOException e) {
                    throw new RuntimeException(e);
                }


            });
            this.detailscommande.setStyle("-fx-background-color: #808080;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-padding: 5px 10px;");

            this.delete.setStyle("-fx-background-color: #ae2727;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-padding: 5px 10px;");
            this.update=new Button("Update");

            this.update.setId(id);
            this.update.setStyle("-fx-background-color: #27ae60;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-padding: 5px 10px;");
            System.out.println("id: "+delete.getId());
            ChoiceBox <Boolean> statu = new ChoiceBox<Boolean>();
            statu.getItems().add(true);
            statu.getItems().add(false);
            statu.setValue((Boolean) st);
            s=(Boolean) st;
            statu.setValue((Boolean) st);
            statu.setOnAction(Event_->{


                s = statu.getSelectionModel().getSelectedItem();

            });

            clientname=(String) cli;
            cl.setOnAction(e-> {

                        clientname = cl.getSelectionModel().getSelectedItem();

                    }
            );
            this.detailscommande.setOnAction(event->{

                try {
                    int idfac= Integer.parseInt(detailscommande.getId());


                    commandeTable.getScene().getWindow().hide();

                    FXMLLoader loader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("detail.fxml"));
                    Parent parent = loader.load(); // Load the FXML file
                    detail detail1 = loader.getController(); // Retrieve the controller instance
                    detail1.setIdcom(idfac); // Set the ID

                    Stage stage = new Stage();
                    stage.setScene(new Scene(parent));
                    stage.initStyle(StageStyle.UTILITY);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }











            });

            this.update.setOnAction(event -> {




                if(Update.etat) {

                    Update.etat = false;
                    commandeda = new DatePicker(LocalDate.parse(String.valueOf(commandeda)));

                    total = new TextField(String.valueOf(total));
                    update.setText("ok");
                    commandeTable.refresh();



                }else {






                    if(!clientname.isEmpty()){
                        System.out.println("item:"+ clientname);
                        try {
                            Client clien=clientService.getClient(clientname);
                            System.out.println(clien.getId());


                            update.setText("Update");
                            Commande commande= new Commande();
                            commande.setId(Integer.parseInt(id));
                            commande.setCommandeDate(Date.valueOf(((DatePicker) commandeda).getValue()));
                            commande.setTotalAmount(Float.parseFloat(((TextField)total).getText()));
                            commande.setClient(clien.getId());
                            System.out.println(s);
                            commande.setStatus(s);
                            commandeDao.updateCommande(commande);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        Update.etat = true;
                        Update update1 = new Update();
                        try {
                            update1.loadCommande();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        commandeTable.refresh();
                        try {
                            update1.loadCommande();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        msg.setText("");


                    }else {
                        msg.setStyle("-fx-text-fill: red;");
                        msg.setText("please select client");
                    }
                }


            });
            this.delete.setOnAction(event -> {





                try {
                    commandeDao.deleteCommande(Integer.parseInt(delete.getId()));
                    System.out.println(Integer.parseInt(delete.getId()));
                    Update update=new Update();
                    update.loadCommande();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }

    @FXML
    public VBox vbox= new VBox();
    @FXML
    public Label msg;
    @FXML
    public JFXTreeTableView<CommandeModel> commandeTable= new JFXTreeTableView<>();

    @FXML
    public JFXTreeTableColumn<CommandeModel, Object> commandedate= new JFXTreeTableColumn<>("commandedata");
    @FXML
    public JFXTreeTableColumn<CommandeModel, Object> totalamount= new JFXTreeTableColumn<>("prixtotal");
    @FXML
    public JFXTreeTableColumn<CommandeModel, Object>  client=new JFXTreeTableColumn<>("client");

    @FXML
    public JFXTreeTableColumn<CommandeModel, Object> user=new JFXTreeTableColumn<>("username");
    @FXML
    public JFXTreeTableColumn<CommandeModel, Object> status=new JFXTreeTableColumn<>("status");

    @FXML
    public Button btnClose;
    public detail d;

    public Button btnFull;
    private Facture facture;
    private  FactureService factureService;
    private UserService userService;
    private ClientService clientService;
    private static ObservableList<CommandeModel> commandeList = FXCollections.observableArrayList();
    private static ObservableList<String> cList = FXCollections.observableArrayList();
    @FXML
    private JFXTreeTableColumn<CommandeModel, Button> DeleteColumn=new JFXTreeTableColumn<>("delete");
    @FXML
    private JFXTreeTableColumn<CommandeModel, Button> updateColumn=new JFXTreeTableColumn<>("update");
    @FXML
    private JFXTreeTableColumn<CommandeModel, Button> detailsColumn=new JFXTreeTableColumn<>("details");
    @FXML
    private  JFXTreeTableColumn<CommandeModel, Button> genrateColumn=new JFXTreeTableColumn<>("generate");

    private static CommandeService commandeDao;
    public commande() throws SQLException, ClassNotFoundException, IOException {
        userService=new UserService();
        User user1=userService.getSession();
        List<Permission> permissions=userService.getUserPermissions(user1);
        for(Permission permission:permissions){
            System.out.println(permission.getNom());
        }


        clientService=new ClientService();
        userService=new UserService();
        commandeDao = new CommandeService();
        commandeList = FXCollections.observableArrayList();
        CommandeService commandeService=new CommandeService();
        Update update = new Update();
        update.loadCommande();

        client.prefWidthProperty().bind(commandeTable.widthProperty().multiply(1));
        commandedate.prefWidthProperty().bind(commandeTable.widthProperty().multiply(1));
        totalamount.prefWidthProperty().bind(commandeTable.widthProperty().multiply(1));
        user.prefWidthProperty().bind(commandeTable.widthProperty().multiply(0.6));
        commandeTable.getColumns().add(commandedate);
        commandeTable.getColumns().add(totalamount);
        commandeTable.getColumns().add(client);

        commandeTable.getColumns().add(user);
        commandeTable.getColumns().add(status);
        commandeTable.getColumns().add(DeleteColumn);
        commandeTable.getColumns().add(updateColumn);
        commandeTable.getColumns().add(detailsColumn);
        commandeTable.getColumns().add(genrateColumn);
        commandeTable.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

        update.loadCommande();









    }
    public class Update extends Thread{
        public void loadCommande() throws SQLException {
//            commandeList.clear();
            try {
                // Vider la liste existante



                // Convertir les Users en UserModels
                List<Commande> commandes = commandeDao.getCommandes();
                Platform.runLater(() -> {
                    try {
                        commandeList.clear();
                        int i=0;

                        for (Commande commande : commandes) {

                            int id1 = commande.getClient();
                            int id2=commande.getIdUser();
                            System.out.println(id1);

                            User user1=userService.getUser(id2);

                            Client client2 =clientService.getClient(id1);
                            System.out.println(client2.getNom());



                            CommandeModel commandeModel = new CommandeModel(
                                    String.valueOf(commande.getId()),
                                    commande.getCommandeDate(),
                                    commande.getTotalAmount(),
                                    client2.getNom(),
                                    user1.getNomUtilisateur(),
                                    i
                                    ,commande.isStatus()



                            );
                            i++;


                            commandeList.add(commandeModel);


                        }

                        TreeItem<CommandeModel> root = new RecursiveTreeItem<>(commandeList, RecursiveTreeObject::getChildren);
//                        root.getChildren().forEach((e)->{ System.out.println(e.getValue().getName().get()); });
                        commandeTable.setRoot(root);
                        commandeTable.setShowRoot(false);
                        commandedate.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getCommandeda()));

                        totalamount.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getTotal()));
                        status.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getStatu()));

                        user.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getUserName()));
                        client.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getCl()));
                        DeleteColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getDelete()));
                        updateColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getUpdate()));
                        detailsColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getDetailscommande()));
                        genrateColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getAjoutefacture()));




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



                    loadCommande();
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
        Update.etat=false;
        Stage stage = (Stage) btnClose.getScene().getWindow();
        ma.ensa.project.controller.Client.Update.etat=false;
        stage.hide();



    }
    public void user(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        DashboardUser user = new DashboardUser();

        user.initialize(vbox.getScene());

        this.commandeTable.getScene().getWindow().hide();



    }
    public void produit(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        product produit = new product();

        produit.initialize(vbox.getScene());

        this.commandeTable.getScene().getWindow().hide();



    }
    public void facture(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        facture facture = new facture();

        facture.initialize(new Stage().getScene());

        this.commandeTable.getScene().getWindow().hide();



    }
    public void client(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        ma.ensa.project.controller.Client client1= new ma.ensa.project.controller.Client();

        client1.initialize(vbox.getScene());

        this.commandeTable.getScene().getWindow().hide();



    }
    public void Paiement(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        Paiement paiement = new Paiement();

        paiement.initialize(vbox.getScene());

        this.commandeTable.getScene().getWindow().hide();



    }

    @FXML
    public void initialize(Scene scene) throws IOException, SQLException {

        double totalWidth = commandeTable.getWidth();
        int columnCount = commandeTable.getColumns().size();
        commandedate.prefWidthProperty().unbind();
        commandedate.setMinWidth(totalWidth / columnCount);
        commandeTable.refresh();
        for (TreeTableColumn<CommandeModel, ?> column : commandeTable.getColumns()) {
            column.prefWidthProperty().unbind();
            column.setPrefWidth(100); // Fixed width for testing
        }

        client.prefWidthProperty().bind(commandeTable.widthProperty().multiply(0.5));
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("commande.fxml"));

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


        commandeTable.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        commandeTable.getStyleClass().addAll("table-view","table table-striped");


    }
    @FXML
    private Button btnAjouter;
    @FXML
    public void ajouterCommande(ActionEvent actionEvent) throws Throwable {
        Scene currentScene = btnAjouter.getScene();

        AjouterCommande ajouterCommandeController = new AjouterCommande();
        ajouterCommandeController.afficherAjout();
        btnAjouter.getScene().getWindow().hide();

    }

}