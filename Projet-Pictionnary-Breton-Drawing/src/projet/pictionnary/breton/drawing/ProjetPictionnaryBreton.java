package projet.pictionnary.breton.drawing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projet.pictionnary.breton.drawing.components.DrawingTools;
import projet.pictionnary.breton.drawing.WordCheck;
import projet.pictionnary.breton.drawing.Drawer;
import projet.pictionnary.breton.drawing.Guesser;

/**
 * This class launches the application.
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
        primaryStage.setTitle("Pictionnary - Drawer");
        
       //DrawingTools root = new DrawingTools();
        WordCheck wordCheck = new WordCheck();
        
        Drawer rootDrawer = new Drawer(wordCheck);
        setAndDisplayDrawerStage(primaryStage, rootDrawer);
        
        Stage secondStage = new Stage();
        Guesser rootGuesser = new Guesser(wordCheck, rootDrawer);
        rootDrawer.getDrawingInfos().addObserver(rootGuesser);        
        setAndDisplayGuesserStage(secondStage, rootGuesser);
    }

    private void setAndDisplayGuesserStage(Stage secondStage, Guesser rootGuesser) {
        secondStage.setTitle("Pictionnary - Guesser");
        Scene sceneGuesser = new Scene(rootGuesser, 1200, 800);
        secondStage.setScene(sceneGuesser);
        secondStage.show();
    }

    private void setAndDisplayDrawerStage(Stage primaryStage, Drawer rootDrawer) {
        Scene sceneDrawer = new Scene(rootDrawer, 1200, 800);
        primaryStage.setScene(sceneDrawer);
        primaryStage.show();
    }
}
