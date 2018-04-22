package projet.pictionnary.breton.drawer;

import javafx.scene.layout.Region;
import projet.pictionnary.breton.util.Observer;

/**
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawerSide extends Region {
    
    private final DrawingTools drawingTools;
    
    
    public DrawerSide(String toDraw) {
        drawingTools = new DrawingTools();
    }
    
    public void addObserver(Observer obs) {
        drawingTools.addObserver(obs);
    }
}