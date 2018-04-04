package projet.pictionnary.breton.client.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Gabriel Breton - 43397
 */
public class ConnexionStageController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField pseudoTfd;

    @FXML
    private TextField serverIpTfd;

    @FXML
    private TextField portNumberTfd;

    @FXML
    private Button connexionBtn;

    @FXML
    void connect(ActionEvent event) {

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
