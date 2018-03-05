package projet.pictionnary.breton.components;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is used as a DTO to transfer a draw.
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawingInfos implements Serializable {
    
    private boolean modifiable;
    private final ArrayList<Point> listPositions;
    
    /**
     * Creates a new DrawingInfos.
     */
    public DrawingInfos() {
        listPositions = new ArrayList<>();
        modifiable = true;
    }
    
    /**
     * Add a point of the draw to the list.
     * @param p 
     */
    public void add(Point p) {
        listPositions.add(p);
    }
    
    /**
     * Gives the list of point.
     * @return 
     */
    public ArrayList<Point> getListPositions() {
        return listPositions;
    }
    
    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }
    
    public boolean isModifiable() {
        return modifiable;
    }
    
    public void clearList() {
        listPositions.clear();
    }
}
