package projet.pictionnary.breton.drawing;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import projet.pictionnary.breton.drawing.components.DrawingTools;
import projet.pictionnary.breton.drawing.model.DrawingInfos;

/**
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawerSide extends Region {
    
    private DrawingTools drawingTools;
    
    public DrawerSide(String toDraw) {
        HBox rootBox = new HBox();
        VBox wordBox = new VBox();
        drawingTools = new DrawingTools();
        
        Label wordLbl = new Label();
        wordLbl.setText(toDraw);

        Label toDrawTitleLbl = new Label();
        toDrawTitleLbl.setText("Word to draw");
        toDrawTitleLbl.setUnderline(true);
        
        wordBox.getChildren().addAll(toDrawTitleLbl, wordLbl);
        wordBox.setStyle("-fx-background-color: #e6e6e6;");        
        
        rootBox.getChildren().addAll(drawingTools, wordBox);
        
        getChildren().add(rootBox);
    }
    
    public DrawingInfos getDrawingInfos() {
        return drawingTools.getDrawingInfos();
    }
}
