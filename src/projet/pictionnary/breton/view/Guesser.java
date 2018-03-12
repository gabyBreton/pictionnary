package projet.pictionnary.breton.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import projet.pictionnary.breton.components.DrawingPane;
import projet.pictionnary.breton.model.EventKind;
import projet.pictionnary.breton.model.WordCheck;
import projet.pictionnary.breton.util.Observer;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class Guesser extends Region implements Observer {
        
    private DrawingPane drawingPane;
    private Drawer drawer;
    
    public Guesser(WordCheck wordCheck, Drawer drawer) {
        HBox rootBox = new HBox();
        VBox wordBox = new VBox();
        this.drawer = drawer;
        drawingPane = new DrawingPane();
        drawingPane.setDisableCanvas(true);
        
        Label toGuessTitleLbl = createToGuessTitleLbl();
        
        TextField proposalTfd = new TextField();
        proposalTfd.setPromptText("Enter a word...");
        
        Button submitBtn = new Button();
        submitBtn.setText("Submit");
        
        rootBox.getChildren().addAll(drawingPane, wordBox);
        
        wordBox.getChildren().addAll(toGuessTitleLbl, proposalTfd, submitBtn);
        wordBox.setStyle("-fx-background-color: #e6e6e6;");  
        
        getChildren().add(rootBox);
    }

    private Label createToGuessTitleLbl() {
        Label toGuessTitleLbl = new Label();
        toGuessTitleLbl.setText("Proposition");
        toGuessTitleLbl.setUnderline(true);
        return toGuessTitleLbl;
    }

    @Override
    public void update(EventKind eventKind) {
        if (eventKind == EventKind.CLEARPANE){
            drawingPane.clearPane();
        } else {
            drawingPane.setDrawingInfos(drawer.getDrawingInfos());        
        }
    }
}