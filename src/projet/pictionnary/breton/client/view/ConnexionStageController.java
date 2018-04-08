package projet.pictionnary.breton.client.view;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

   
    @FXML
    private Button connexionBtn;
    
    public void connectToServer() {
        // VERIFY input before !
        try {
            clientPictionnary = new ClientPictionnary(serverIpTfd.getText(), Integer.parseInt(portNumberTfd.getText()), pseudoTfd.getText());
            
            Stage connexionStage = (Stage) connexionBtn.getScene().getWindow();
            connexionStage.close();
            
            Parent root = FXMLLoader.load(getClass().getResource("TableSelectionStage.fxml"));
            Stage tableSelectionStage = new Stage();
            
            tableSelectionStage.setScene(new Scene(root));
            tableSelectionStage.setTitle("Pictionnary");
            tableSelectionStage.show();            
        } catch (IOException ioe) {
           System.out.println("ConnexionStageController.connectToServer()\nConnection failed");
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
