package projet.pictionnary.breton.client.view;

import java.net.URL;
import java.util.List;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import projet.pictionnary.breton.client.ClientPictionnary;
import projet.pictionnary.breton.model.Message;
import projet.pictionnary.breton.model.Table;
import projet.pictionnary.breton.model.Type;
import projet.pictionnary.breton.util.Observer;

/**
 * FXML Controller class
 *
 * @author Gabriel Breton - 43397
 */
public class TableSelectionStageController implements Initializable, Observer {

    private ClientPictionnary clientPictionnary;
    private ObservableList<Table> listTables = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Table> tableView;
    
    @FXML
    private TableColumn<Table, String> nameCol;
    
    @FXML
    private TableColumn<Table, String> statusCol;

    @FXML
    private TableColumn<Table, String> drawerCol;
    
    @FXML
    private TableColumn<Table, String> partnerCol;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameCol = new TableColumn<>();
        statusCol = new TableColumn<>();
        drawerCol = new TableColumn<>();
        partnerCol = new TableColumn<>();
        
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("open"));
        drawerCol.setCellValueFactory(new PropertyValueFactory<>("drawer"));
        partnerCol.setCellValueFactory(new PropertyValueFactory<>("partner"));
        
        tableView.getItems().setAll(listTables);
    }    
    
    @FXML
    public void createTable(ActionEvent event) {
        TextInputDialog createTableDialog = new TextInputDialog();
        createTableDialog.setTitle("Create table");
        createTableDialog.setHeaderText("Table name");
        createTableDialog.setContentText("Enter the name of the table");
        createTableDialog.initModality(Modality.APPLICATION_MODAL);
        
        Optional<String> result = createTableDialog.showAndWait();
        result.ifPresent(tableName -> clientPictionnary.createTable(tableName));
    }
    
    void setClient(ClientPictionnary clientPictionnary) {
        this.clientPictionnary = clientPictionnary;
        this.clientPictionnary.addObserver(this);
        // TODO : this.clientPictionnary.addObserver(this);
    } 

    @Override
    public void update(Object arg) {
        Message message = (Message) arg;
        Type type = (Type) message.getType();
        
        switch (type) {
            case GET_ALL_TABLES:
                System.out.println("TableSelectionStageController.update: GET_ALL_TABLES");                
                listTables = FXCollections.observableArrayList((List<Table>) message.getContent());
                break;
            default:
                System.out.println("TableSelectionStageController.update: default");
                break;
        }
    }
}
