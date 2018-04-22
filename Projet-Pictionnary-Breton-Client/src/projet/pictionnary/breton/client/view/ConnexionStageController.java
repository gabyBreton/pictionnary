package projet.pictionnary.breton.client.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import projet.pictionnary.breton.client.ClientController;

/**
 * This class is used as FXML controller for the connexion stage.
 *
 * @author Gabriel Breton - 43397
 */
public class ConnexionStageController implements Initializable {

    private ClientController clientController;
    
    @FXML
    private TextField pseudoTfd; // TODO : make unique on server

    @FXML
    private TextField serverIpTfd;

    @FXML
    private TextField portNumberTfd;

    @FXML
    private Button connexionBtn;

    /**
     * Action used to connect the client to the server.
     */
    @FXML
    public void connectToServer() {
        try {
            clientController.loadTableSelectionStage();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
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
     * Gives the value of the pseudo textfield.
     * 
     * @return the value of the pseudo textfield.
     */
    public String getPseudo() {
        return pseudoTfd.getText();
    }

    /**
     * Gives the value of the server ip textfield.
     * 
     * @return the value of the server ip textfield.
     */
    public String getServerIp() {
        return serverIpTfd.getText();
    }

    /**
     * Gives the value of the port number textfield.
     * 
     * @return the value of the port number textfield.
     */
    public String getPortNumber() {
        return portNumberTfd.getText();
    }
    
    /**
     * Gives the connexion button.
     * 
     * @return the connexion button.
     */
    public Button getConnexionBtn() {
        return connexionBtn;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverIpTfd.setText("localhost");
        portNumberTfd.setText("12345");
    }      
}
