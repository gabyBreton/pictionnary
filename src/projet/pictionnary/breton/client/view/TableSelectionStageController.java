package projet.pictionnary.breton.client.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
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
        clientPictionnary.createTable();
    }
    
    void setClient(ClientPictionnary clientPictionnary) {
        this.clientPictionnary = clientPictionnary;
    } 
}
