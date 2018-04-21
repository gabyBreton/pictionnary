package projet.pictionnary.breton.drawing;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import projet.pictionnary.breton.drawing.components.DrawingPane;
import projet.pictionnary.breton.model.DrawEventKind;
import projet.pictionnary.breton.util.Observer;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class PartnerSide extends Region implements Observer {
        
    private final DrawingPane drawingPane;
    
    public PartnerSide() {
        HBox rootBox = new HBox();
        VBox wordBox = new VBox();
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
    public void update(Object arg) {
        // TODO : revoir la méthode d'update via serveur plutot qu'en dedans à la fenetre de dessin
        DrawEventKind eventKind = (DrawEventKind) arg;
        
        if (eventKind == DrawEventKind.CLEARPANE){
            drawingPane.clearPane();
        } else {
//            drawingPane.setDrawingInfos(drawer.getDrawingInfos());        
        }
    }
}
