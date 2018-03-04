package projet.pictionnary.breton.components;

import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 *
 * @author Gabriel Breton - 43397
 */
public final class DrawingPaneControl extends Region implements IDrawing {

    private DrawingPane drawingPane;
    private DrawingTools drawingTools;
    private DrawingInfos drawingInfos;
    
    public DrawingPaneControl() {
        initialize();
    }
    
    @Override
    public void initialize() {
        drawingInfos = new DrawingInfos();
        drawingPane = new DrawingPane(drawingInfos);
        drawingTools = new DrawingTools(this);
        
        HBox rootBox = new HBox();        
        rootBox.getChildren().addAll(drawingTools, drawingPane);
        getChildren().add(rootBox);
    }

    @Override
    public void clearPane() {
        drawingPane.clearPane();
    }

    @Override
    public DrawingInfos getDrawingInfos() {
        return drawingInfos;
    }

    @Override
    public void setDrawingInfos(DrawingInfos drawingInfos) {
        this.drawingInfos = drawingInfos;
        drawingPane.drawSaved(this.drawingInfos);
    }

    @Override
    public ObjectProperty<Color> colorProperty() {
        return drawingPane.colorProperty();
    }

    @Override
    public void setColor(Color color) {
        drawingPane.setColor(color);
    }

    @Override
    public Color getColor() {
        return drawingPane.getColor();
    }

    @Override
    public ObjectProperty<Integer> thicknessProperty() {
        return drawingPane.thicknessProperty();
    }

    @Override
    public void setThickness(int thickness) {
        drawingPane.setThickness(thickness);
    }

    @Override
    public int getThickness() {
        return drawingPane.getThickness();
    }
    
   @Override
   public void setErase(boolean erase) {
       drawingPane.setErase(erase);
   }
}
