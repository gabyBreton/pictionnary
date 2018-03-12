package projet.pictionnary.breton.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import projet.pictionnary.breton.components.DrawingPane;
import projet.pictionnary.breton.components.DrawingTools;
import projet.pictionnary.breton.model.WordCheck;

/**
 * 
 * @author Gabriel Breton - 43397
 */
public class Drawer extends Region {
    
    private DrawingPane drawingPane;
    private DrawingTools drawingTools;
    
    public Drawer(WordCheck wordCheck) {
        HBox rootBox = new HBox();
        VBox wordBox = new VBox();
        drawingTools = new DrawingTools();
        
        Label wordLbl = new Label();
        wordLbl.setText(wordCheck.getToGuess());

        Label toDrawTitleLbl = new Label();
        toDrawTitleLbl.setText("Word to draw");
        toDrawTitleLbl.setUnderline(true);
        
        wordBox.getChildren().addAll(toDrawTitleLbl, wordLbl);
        wordBox.setStyle("-fx-background-color: #e6e6e6;");        
        
        rootBox.getChildren().addAll(drawingTools, wordBox);
        
        getChildren().add(rootBox);
    }
}
