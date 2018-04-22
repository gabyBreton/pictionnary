package projet.pictionnary.breton.drawing;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import projet.pictionnary.breton.drawing.components.DrawingTools;
import projet.pictionnary.breton.util.Observer;

/**
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawerSide extends Region {
    
    private final DrawingTools drawingTools;
    private Label status;
    private TextArea propositionHist;
    
    public DrawerSide(String toDraw) {
        HBox rootBox = new HBox();
        GridPane infosPane = new GridPane();
        drawingTools = new DrawingTools();

        Label gameStatusLbl = new Label("Game status");
        gameStatusLbl.setUnderline(true);        
        status = new Label();
        
        Label wordLbl = new Label();
        wordLbl.setText(toDraw);
        Label toDrawLbl = new Label("Word to draw");
        toDrawLbl.setUnderline(true);
          
        Label historyLbl = new Label("Propositions history");
        propositionHist = new TextArea();
        propositionHist.setEditable(false);
        
        Button quitBtn = new Button("Quit");
        
        infosPane.add(gameStatusLbl, 0, 0);
        infosPane.add(status, 0, 1);
        infosPane.add(toDrawLbl, 0, 5);
        infosPane.add(wordLbl, 0, 6);
        infosPane.add(historyLbl, 0, 10);
        infosPane.add(propositionHist, 0, 11);
        infosPane.add(quitBtn, 0, 20);
        
//        wordBox.getChildren().addAll(gameStatusLbl, status, toDrawLbl, 
//                                        wordLbl, historyLbl, propositionHist);
        infosPane.setStyle("-fx-background-color: #e6e6e6;");        
        infosPane.setHgap(200);
        
        rootBox.getChildren().addAll(drawingTools, infosPane);
        
        getChildren().add(rootBox);
    }
    
    public void addObserver(Observer obs) {
        drawingTools.addObserver(obs);
    }

    public void setStatus(Label status) {
        this.status = status;
    }
    
    public void addWordHistory(String word) {
        propositionHist.setText(propositionHist.getText() + "\n" + word);
    }
}