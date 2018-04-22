package projet.pictionnary.breton.client;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 * This class is used as the main class to launch the Pictionnary client.
 * 
 * @author Gabriel Breton - 43397
 */
public class ClientMain extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientController clientController = new ClientController();
        clientController.loadConnexionStage();
    }
}
