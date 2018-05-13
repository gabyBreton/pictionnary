package projet.pictionnary.breton.client.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import projet.pictionnary.breton.client.ClientController;
import projet.pictionnary.breton.common.model.DataTable;

/**
 * This class is used as the FXML controller to manage the selection of the 
 * tables, to join or create one.
 *
 * @author Gabriel Breton - 43397
 */
public class TableSelectionStageController implements Initializable {

    private List<DataTable> dataTables;
    private ClientController clientController;

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
    
    @FXML
    private Label nameLbl;

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

    /**
     * Displays a dialog box to set the name of the table.
     */
    @FXML
    public void createTable() {
        displayTableNameDialog();
    }

    /**
     * Requests to the server to get the game statistics for the client.
     */
    @FXML
    public void getStats() {
        clientController.getStats();
    }
    
    /**
     * Creates and shows the dialog box for the name of the table to create.
     */
    private void displayTableNameDialog() {
        TextInputDialog tableNameDialog = new TextInputDialog();
        tableNameDialog.setTitle("Create table");
        tableNameDialog.setHeaderText("Table name");
        tableNameDialog.setContentText("Enter the name of the table");
        tableNameDialog.initModality(Modality.APPLICATION_MODAL);

        Optional<String> result = tableNameDialog.showAndWait();
        result.ifPresent(tableName -> clientController.createTable(tableName));
    }

    /**
     * Used to request to the client to join a table.
     */
    @FXML
    public void join() {
        clientController.joinTable(tableView.getSelectionModel()
                                            .getSelectedItem()
                                            .getId());
    }

    /**
     * Sets the main client controller.
     * 
     * @param clientController the client controller.
     */
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }       
    
    /**
     * Refresh the table view that contains all the informations for the tables
     * selection.
     * 
     * @param dataTables the data of the tables.
     */
    public void refreshTableView(List<DataTable> dataTables) {
        this.dataTables = dataTables;
        
        tableView.getItems().clear();
        
        for (DataTable data : dataTables) {
            tableView.getItems().add(data);
        }
    }
    
    /**
     * Sets the client name that is displayed on the table selection stage.
     * 
     * @param name the name to set.
     */
    public void setClientName(String name) {
        nameLbl.setText(name);
    }
}

