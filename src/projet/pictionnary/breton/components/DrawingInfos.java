package projet.pictionnary.breton.components;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is used to transfer a draw.
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
     * Add a point of the draw to the list of points.
     * 
     * @param p the point to add. 
     */
    public void add(Point p) {
        listPositions.add(p);
    }
    
    /**
     * Gives the list of points.
     * 
     * @return the list of points.
     */
    public ArrayList<Point> getListPositions() {
        return listPositions;
    }
    
    /**
     * Set the attributes modifiable.
     * 
     * @param modifiable the new value of modifiable.
     */
    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }
    
    /**
     * Gives the value of modifiable.
     * 
     * @return the value of modifiable.
     */
    public boolean isModifiable() {
        return modifiable;
    }
    
    /**
     * Clear the list of points.
     */
    public void clearList() {
        listPositions.clear();
    }
}
