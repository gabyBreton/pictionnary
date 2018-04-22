package projet.pictionnary.breton.client.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projet.pictionnary.breton.client.ClientPictionnary;

/**
 * This class is used as FXML controller for the connexion stage.
 *
 * @author Gabriel Breton - 43397
 */
public class ConnexionStageController implements Initializable {

    private ClientPictionnary clientPictionnary;
    
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
        // TODO verify input before !        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TableSelectionStage.fxml"));
        
        try {
            // TODO : Afficher erreur proprement si mauvais port
            clientPictionnary = new ClientPictionnary(serverIpTfd.getText(), 
                                    Integer.parseInt(portNumberTfd.getText()), 
                                    pseudoTfd.getText());
            
            loader.load();
        } catch (IOException ioe) {
            Logger.getLogger(ConnexionStageController.class.getName())
                    .log(Level.SEVERE, null, ioe);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(ConnexionStageController.class.getName())
                    .log(Level.SEVERE, null, nfe);            
        }
        
        launchTableSelectionStage(loader);
    }
    
    /**
     * Launch the <code> Table </code> selection stage.
     * 
     * @param loader the loader for the table selection stage.
     */
    private void launchTableSelectionStage(FXMLLoader loader) {
        // pass the client instance to the new stage
        TableSelectionStageController selectionStage = loader.getController();
        selectionStage.setClient(clientPictionnary);

        Parent root = loader.getRoot();
        Stage tableSelectionStage = new Stage();
        tableSelectionStage.setScene(new Scene(root));
        tableSelectionStage.setTitle("Pictionnary - Table selection");
        tableSelectionStage.show();
        
        // close the connexion stage          
        Stage connexionStage = (Stage) connexionBtn.getScene().getWindow();
        connexionStage.close();
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
