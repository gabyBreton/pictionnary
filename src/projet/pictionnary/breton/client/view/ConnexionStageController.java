package projet.pictionnary.breton.client.view;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import projet.pictionnary.breton.client.ClientPictionnary;

/**
 * FXML Controller class
 *
 * @author Gabriel Breton - 43397
 */
public class ConnexionStageController implements Initializable {

    private ClientPictionnary clientPictionnary;
    
    @FXML
    private TextField pseudoTfd;

    @FXML
    private TextField serverIpTfd;

    @FXML
    private TextField portNumberTfd;

   
    public void connectToServer() {
        // VERIFY input before !
        try {
            clientPictionnary = new ClientPictionnary(serverIpTfd.getText(), Integer.parseInt(portNumberTfd.getText()), pseudoTfd.getText());
        } catch (IOException ioe) {
            System.out.println("Connection failed");
        } catch (NumberFormatException nfe) {
            System.out.println("Enter an integer for the port number");
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
