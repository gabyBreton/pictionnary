package projet.pictionnary.breton.client;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
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
        Parent root = FXMLLoader.load(getClass().getResource("view/ConnexionStage.fxml"));
        
        Scene scene = new Scene(root);
       
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pictionnary - Server connexion");
        primaryStage.show();
    }
}
