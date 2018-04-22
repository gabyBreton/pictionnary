package projet.pictionnary.breton.drawing;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import projet.pictionnary.breton.drawing.components.DrawingPane;
import projet.pictionnary.breton.model.DrawingInfos;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class PartnerSide extends Region {
        
    private final DrawingPane drawingPane;
    private Label status;
    private TextArea propositionHist;

    
    public PartnerSide() {
        HBox rootBox = new HBox();
        GridPane infosPane = new GridPane();
        drawingPane = new DrawingPane();
        drawingPane.setDisableCanvas(true);

        Label gameStatusLbl = new Label("Game status");
        gameStatusLbl.setUnderline(true);        
        status = new Label();
        
        Label toGuessLbl = createToGuessLbl();        
        TextField proposalTfd = new TextField();
        proposalTfd.setPromptText("Enter a word...");
        
        Button submitBtn = new Button();
        submitBtn.setText("Submit");
        
        Label historyLbl = new Label("Propositions history");
        propositionHist = new TextArea();
        propositionHist.setEditable(false);
        
        Button quitBtn = new Button("Quit");
        
        infosPane.add(gameStatusLbl, 0, 0);
        infosPane.add(status, 0, 1);
        infosPane.add(toGuessLbl, 0, 5);
        infosPane.add(proposalTfd, 0, 6);
        infosPane.add(submitBtn, 0, 7);
        infosPane.add(historyLbl, 0, 11);
        infosPane.add(propositionHist, 0, 12);
        infosPane.add(quitBtn, 0, 20);

        infosPane.setStyle("-fx-background-color: #e6e6e6;");          
        
        rootBox.getChildren().addAll(drawingPane, infosPane);
        
        getChildren().add(rootBox);
    }

    public void draw(DrawingInfos drawingInfos) {
        drawingPane.setDrawingInfos(drawingInfos);
    }
    
    public void clearPane() {
        drawingPane.clearPane();
    }    
    
    private Label createToGuessLbl() {
        Label toGuessTitleLbl = new Label();
        toGuessTitleLbl.setText("Proposition");
        toGuessTitleLbl.setUnderline(true);
        return toGuessTitleLbl;
    }
}
