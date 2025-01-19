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
import ma.ensa.project.entity.Paiement;
import ma.ensa.project.service.ClientService;
import ma.ensa.project.service.CommandeService;
import ma.ensa.project.service.PaimentService;
import ma.ensa.project.service.UserService;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.EventListener;
import java.util.List;

import static java.lang.Thread.sleep;

public class Commandenonpay {


    @Data
    public class CommandeModel extends RecursiveTreeObject<CommandeModel> {
        private Object id;

        private Object commandeda;
        private Object total;
        private Object cl;
        private Object UserName;
        private Object datepay;


        private Button validate;


        // Constructeur
        public CommandeModel(String id, Object c, Object to, Object cli,Object us) throws SQLException {

            this.id=id;

            this.commandeda =  c;
//            System.out.println(name.get());
            this.total = to;
            this.cl=cli;


            this.UserName=us;




            this.validate=new Button("VALIDATE");
            this.validate.setId(id);

            this.validate.setStyle("-fx-background-color: #27ae60;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-padding: 5px 10px;");





            this.validate.setOnAction(event -> {

                        int idv= Integer.parseInt(validate.getId());
                        if(Update.etat) {


                            Update.etat = false;
                            datepay = new DatePicker(LocalDate.parse(String.valueOf(commandeda)));
                            validate.setText("ok");











                            commandedate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(commandeda));

                            totalamount.setCellValueFactory(cellData -> new SimpleObjectProperty<>(total));
                            client.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cl));
                            user.setCellValueFactory(cellData -> new SimpleObjectProperty<>(UserName));
                            datepaiement.setCellValueFactory(cellData -> new SimpleObjectProperty<>(datepay));

                            payColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(validate));




                            TreeItem<CommandeModel> root = new RecursiveTreeItem<>(commandeList, RecursiveTreeObject::getChildren);
//                        root.getChildren().forEach((e)->{ System.out.println(e.getValue().getName().get()); });
                            commandeTable.setRoot(root);
                            Update update1 = new Update();


                        }else {







                                try {
                                    PaimentService p=new PaimentService();


                                    User user=userService.getSession();
                                    validate.setText("Validate");

                                    Paiement pay=new Paiement();
                                    pay.setDate(Date.valueOf(((DatePicker)datepay).getValue()));
                                    pay.setCommandeId(idv);
                                    System.out.println(user.getId());



                                 Paiement k=   p.addPaiement(pay,user);





                                System.out.println(k.getId());


                                    commandeDao.updateCommandepaiement(idv ,k.getId());

                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }

                            Update.etat = true;
                                Update update1 = new Update();
                            try {
                                update1.loadCommande();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            payColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(validate));

                                TreeItem<CommandeModel> root = new RecursiveTreeItem<>(commandeList, RecursiveTreeObject::getChildren);
//                        root.getChildren().forEach((e)->{ System.out.println(e.getValue().getName().get()); });
                                commandeTable.setRoot(root);
                                try {
                                    update1.loadCommande();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }




                        }




                    }
                );









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
public   JFXTreeTableColumn<CommandeModel, Object>  datepaiement=new JFXTreeTableColumn<>("datepaiement");
    @FXML
    public JFXTreeTableColumn<CommandeModel, Object> user=new JFXTreeTableColumn<>("username");


    @FXML
    public Button btnClose;
    public Button btnFull;
    private UserService userService;
    private ClientService clientService;
    private PaimentService paimentService;
    private static ObservableList<CommandeModel> commandeList = FXCollections.observableArrayList();

    @FXML
    private JFXTreeTableColumn<CommandeModel, Button> payColumn=new JFXTreeTableColumn<>("pay");


    private static CommandeService commandeDao;
    public Commandenonpay() throws SQLException, ClassNotFoundException, IOException {
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


        commandeTable.getColumns().add(commandedate);
        commandeTable.getColumns().add(totalamount);
        commandeTable.getColumns().add(client);

        commandeTable.getColumns().add(user);
        commandeTable.getColumns().add(datepaiement);


        commandeTable.getColumns().add(payColumn);


        update.loadCommande();

    }
    public class Update extends Thread{
        public void loadCommande() throws SQLException {
            commandeList.clear();
            try {
                // Vider la liste existante




                // Convertir les Users en UserModels
                List<Commande> commandes = commandeDao.getCommandebystatus(false);
                Platform.runLater(() -> {
                    try {
                        commandeList.clear();


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
                                    user1.getNomUtilisateur()





                            );


                            commandeList.add(commandeModel);


                        }

                        TreeItem<CommandeModel> root = new RecursiveTreeItem<>(commandeList, RecursiveTreeObject::getChildren);
//                        root.getChildren().forEach((e)->{ System.out.println(e.getValue().getName().get()); });
                        commandeTable.setRoot(root);
                        commandeTable.setShowRoot(false);
                        commandedate.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getCommandeda()));

                        totalamount.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getTotal()));


                        user.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getUserName()));
                        client.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(cellData.getValue().getValue().getCl()));
                        datepaiement.setCellValueFactory(cellData ->  new SimpleObjectProperty<>(null));
                        payColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getValidate()));





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


        //facture.initialize(vbox.getScene());

        facture.initialize(new Stage().getScene());


        this.commandeTable.getScene().getWindow().hide();



    }
    public void client(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        ma.ensa.project.controller.Client client1= new ma.ensa.project.controller.Client();

        client1.initialize(vbox.getScene());

        this.commandeTable.getScene().getWindow().hide();



    }
    public void Paiement(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        ma.ensa.project.controller.Paiement paiement= new ma.ensa.project.controller.Paiement();

        paiement.initialize(vbox.getScene());

        this.commandeTable.getScene().getWindow().hide();
    }
    public void commande(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        commande c= new commande();

        c.initialize(vbox.getScene());

        this.commandeTable.getScene().getWindow().hide();
    }

    @FXML
    public void initialize(Scene scene) throws IOException, SQLException {
        client.prefWidthProperty().bind(commandeTable.widthProperty().multiply(0.5));
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationGestionFacturation.class.getResource("commandenonpay.fxml"));

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

}