package projet.pictionnary.breton.client.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import projet.pictionnary.breton.client.ClientPictionnary;
import projet.pictionnary.breton.drawing.DrawerSide;
import projet.pictionnary.breton.model.DataTable;
import projet.pictionnary.breton.model.Message;
import projet.pictionnary.breton.model.MessageJoin;
import projet.pictionnary.breton.model.Role;
import projet.pictionnary.breton.model.Type;
import projet.pictionnary.breton.server.users.User;
import projet.pictionnary.breton.util.Observer;

/**
 * FXML Controller class
 *
 * @author Gabriel Breton - 43397
 */
public class TableSelectionStageController implements Initializable, Observer {

    private ClientPictionnary clientPictionnary;
    private List<DataTable> dataTables;

    @FXML
    private TableView<DataTable> tableView;

    @FXML
    private TableColumn<DataTable, String> nameCol;

    @FXML
    private TableColumn<DataTable, Integer> idCol;

    @FXML
    private TableColumn<DataTable, String> statusCol;

    @FXML
    private TableColumn<DataTable, String> drawerCol;

    @FXML
    private TableColumn<DataTable, String> partnerCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataTables = new ArrayList<>();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        drawerCol.setCellValueFactory(new PropertyValueFactory<>("drawer"));
        partnerCol.setCellValueFactory(new PropertyValueFactory<>("partner"));
    }

    @FXML
    public void createTable(ActionEvent event) {
        displayTableNameDialog();
        // TODO créer la fenetre seulement qu'on a reçu la réponse du serveur.
        //displayDrawerWindow();
    }

    private void displayDrawerWindow() {
        DrawerSide drawerWindow = new DrawerSide(clientPictionnary.getWord());
        Scene sceneDrawer = new Scene(drawerWindow, 1200, 800);

        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setScene(sceneDrawer);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest((WindowEvent event) -> {
                Platform.runLater(clientPictionnary::quitGame);
            });
            stage.show();
        });
    }

    private void displayTableNameDialog() {
        TextInputDialog tableNameDialog = new TextInputDialog();
        tableNameDialog.setTitle("Create table");
        tableNameDialog.setHeaderText("Table name");
        tableNameDialog.setContentText("Enter the name of the table");
        tableNameDialog.initModality(Modality.APPLICATION_MODAL);

        Optional<String> result = tableNameDialog.showAndWait();
        result.ifPresent(tableName -> clientPictionnary.createTable(tableName));
    }

    @FXML
    public void join() {
        System.out.println("TableSelectionStageController.join() : "
                + tableView.getSelectionModel().getSelectedItem().getName());

        clientPictionnary.join(tableView.getSelectionModel().getSelectedItem().getId());
//        
//        
//        if ("Closed".equals(tableView.getSelectionModel().getSelectedItem()
//                                                         .getStatus())) {
//            displayAlertTableClosed();
//        } // alert else if client déjà dans cette partie ou dans une partie tout court
//        
//        // else il rejoint la table en tant que partner
    }

    private void displayAlertTableClosed() {
        Alert alertTableClosed = new Alert(Alert.AlertType.WARNING);
        alertTableClosed.setTitle("Table closed");
        alertTableClosed.setHeaderText("This table is closed, you can't join it.");
        alertTableClosed.showAndWait();
    }

    void setClient(ClientPictionnary clientPictionnary) {
        this.clientPictionnary = clientPictionnary;
        this.clientPictionnary.addObserver(this);
    }

    @Override
    public void update(Object arg) {
        Message message = (Message) arg;
        Type type = (Type) message.getType();

        switch (type) {
            case GET_TABLES:
                System.out.println("TableSelectionStageController.update: GET_ALL_TABLES");
                dataTables = (List<DataTable>) message.getContent();
                refreshTableView();
                break;

            case CREATE:
                clientPictionnary.askWord();
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException intE) {
//                    System.out.println(intE.getMessage());
//                }

                break;

            case JOIN:
                // TODO
                break;

            case GET_WORD:
                displayDrawerWindow();                
                break;
                
            default:
                System.out.println("TableSelectionStageController.update: default");
                break;
        }
    }

    private void refreshTableView() {
        System.out.println("TableSelectionStageController.refreshTableView()");
        tableView.getItems().clear();
        dataTables.forEach((dataTable) -> {
            tableView.getItems().add(dataTable);
        });
    }
}
