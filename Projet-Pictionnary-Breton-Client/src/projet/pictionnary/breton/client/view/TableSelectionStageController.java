package projet.pictionnary.breton.client.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import projet.pictionnary.breton.client.ClientPictionnary;
import projet.pictionnary.breton.drawing.DrawerSide;
import projet.pictionnary.breton.drawing.PartnerSide;
import projet.pictionnary.breton.model.DataTable;
import projet.pictionnary.breton.model.DrawEvent;
import projet.pictionnary.breton.model.Message;
import projet.pictionnary.breton.model.MessageReceptDraw;
import projet.pictionnary.breton.model.MessageSendDraw;
import projet.pictionnary.breton.model.Type;
import projet.pictionnary.breton.util.Observer;

/**
 * FXML Controller class
 *
 * @author Gabriel Breton - 43397
 */
public class TableSelectionStageController implements Initializable, Observer {

    // TODO : ne peux dessiner tant que l'autre n'est pas connecté.
    
    private ClientPictionnary clientPictionnary;
    private List<DataTable> dataTables;
    private DrawerSide drawerWindow;
    private PartnerSide partnerWindow;

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
        System.out.println("Controller : join");
        clientPictionnary.join(tableView.getSelectionModel().getSelectedItem().getId());
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
                dataTables = (List<DataTable>) message.getContent();
                refreshTableView();
                break;

            case CREATE:
                clientPictionnary.askWord();
                break;

            case JOIN:
                System.out.println("Controller : join : update");
                displayPartnerWindow();
                break;

            case GET_WORD:
                displayDrawerWindow();                
                break;
                
            case SEND_DRAW:
                MessageSendDraw msgSendDraw = (MessageSendDraw) message;
                DrawEvent eventSendDraw = (DrawEvent) msgSendDraw.getContent();
                
                if (eventSendDraw == DrawEvent.DRAW) {
                    clientPictionnary.draw(msgSendDraw.getDrawingInfos());
                } else {
                    clientPictionnary.clearPane();
                }
                break;
                
            case RECEPT_DRAW:
                System.out.println("Controller : Recept_Draw");
                MessageReceptDraw msgReceptDraw = (MessageReceptDraw) message;
                DrawEvent eventReceptDraw = (DrawEvent) msgReceptDraw.getContent();
                        
                if (eventReceptDraw == DrawEvent.DRAW) {
                    partnerWindow.draw(msgReceptDraw.getDrawingInfos());
                } else {
                    partnerWindow.clearPane();
                }
                break;
                
            default:
                System.out.println("TableSelectionStageController.update: default");
                break;
        }
    }

    private void displayPartnerWindow() {
        partnerWindow = new PartnerSide();
        Scene scenePartner = new Scene(partnerWindow, 1200, 800);
        
        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setScene(scenePartner);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest((WindowEvent event) -> {
                Platform.runLater(clientPictionnary::quitGame);
            });
            stage.show();
        });
    }
    
    private void displayDrawerWindow() {
        drawerWindow = new DrawerSide(clientPictionnary.getWord());
        drawerWindow.addObserver(this);
        
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
    
    private void refreshTableView() {
        System.out.println("TableSelectionStageController.refreshTableView()");
        tableView.getItems().clear();
        dataTables.forEach((dataTable) -> {
            tableView.getItems().add(dataTable);
        });
    }
}
