package projet.pictionnary.breton.components;

import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import projet.pictionnary.breton.DrawingInfos;

/**
 *
 * @author Gabriel Breton - 43397
 */
public final class DrawingPaneControl extends Region implements IDrawing {

    private DrawingPane drawingPane;
    private DrawingTools drawingTools;
    
    public DrawingPaneControl() {
        initialize();
    }
    
    @Override
    public void initialize() {
        drawingPane = new DrawingPane();
        drawingTools = new DrawingTools(this);
        HBox rootBox = new HBox();        
        rootBox.getChildren().addAll(drawingTools, drawingPane);
        getChildren().add(rootBox);
    }

    @Override
    public void clearPane() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DrawingInfos getDrawingInfos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDrawingInfos(DrawingInfos dInfos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
