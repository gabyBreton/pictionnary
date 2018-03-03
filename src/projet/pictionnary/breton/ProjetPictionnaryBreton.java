package projet.pictionnary.breton;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projet.pictionnary.breton.components.DrawingPaneControl;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class ProjetPictionnaryBreton extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pictionnary");
        
        DrawingPaneControl root = new DrawingPaneControl();
        Scene scene = new Scene(root, 1000, 800);
 
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
