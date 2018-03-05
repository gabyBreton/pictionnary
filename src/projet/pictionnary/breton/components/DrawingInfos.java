package projet.pictionnary.breton.components;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is used as a DTO to transfer a draw.
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawingInfos implements Serializable {
    
    private final ArrayList<Point> listPositions;
    
    /**
     * Creates a new DrawingInfos.
     */
    public DrawingInfos() {
        listPositions = new ArrayList<>();
    }
    
    /**
     * Add a point of the draw to the list.
     * @param p 
     */
    public void add(Point p) {
        listPositions.add(new Point(p.getX(), p.getY(), p.getThickness(), 
                                      p.getColor(), p.isErase()));
    }
    
    /**
     * Gives the list of point.
     * @return 
     */
    public ArrayList<Point> getListPositions() {
        return listPositions;
    }
}
