package projet.pictionnary.breton.client.view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import projet.pictionnary.breton.client.ClientPictionnary;

/**
 * FXML Controller class
 *
 * @author Gabriel Breton - 43397
 */
public class TableSelectionStageController implements Initializable {

    private ClientPictionnary clientPictionnary;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
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
    } 
}
