package projet.pictionnary.breton.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import projet.pictionnary.breton.components.DrawingPane;
import projet.pictionnary.breton.components.DrawingTools;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class Guesser extends Region {
        
    private DrawingPane drawingPane;
    private DrawingTools drawingTools;
    
    public Guesser() {
        HBox rootBox = new HBox();
        VBox wordBox = new VBox();
        drawingTools = new DrawingTools();
        
        Label toGuessTitleLbl = new Label();
        toGuessTitleLbl.setText("Proposition");
        toGuessTitleLbl.setUnderline(true);
        
        TextField proposalTfd = new TextField();
        proposalTfd.setPromptText("Enter a word...");
        
        Button submitBtn = new Button();
        submitBtn.setText("Submit");
        
        rootBox.getChildren().addAll(drawingTools, wordBox);
        
        wordBox.getChildren().addAll(toGuessTitleLbl, proposalTfd, submitBtn);
        wordBox.setStyle("-fx-background-color: #e6e6e6;");  
        
        getChildren().add(rootBox);
    }
}
