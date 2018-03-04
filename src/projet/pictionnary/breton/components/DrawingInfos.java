package projet.pictionnary.breton.components;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class DrawingInfos implements Serializable {
    
    private final ArrayList<Point> listPositions;
    
    
    public DrawingInfos() {
        listPositions = new ArrayList<>();
    }
    
    public void add(Point p) {
        listPositions.add(new Point(p.getX(), p.getY(), p.getThickness(), 
                                      p.getColor(), p.isErase()));
    }
    
    public ArrayList<Point> getListPositions() {
//        ArrayList<Point> copyListPos = new ArrayList<>();
//        
//        for (Point p : listPositions) {
//            copyListPos.add(new Point(p.getX(), p.getY(), p.getThickness(), 
//                                      p.getColor(), p.isErase()));
//        }
//        return copyListPos;
        return listPositions;
    }
}
