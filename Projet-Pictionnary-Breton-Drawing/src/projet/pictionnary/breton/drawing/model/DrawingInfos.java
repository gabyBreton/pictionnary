package projet.pictionnary.breton.drawing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import projet.pictionnary.breton.drawing.components.Point;
import projet.pictionnary.breton.model.DrawEventKind;
import projet.pictionnary.breton.util.Observable;
import projet.pictionnary.breton.util.Observer;

/**
 * This class is used to transfer a draw.
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawingInfos implements Serializable, Observable {
    
    private final transient List<Observer> listObs;
    private ArrayList<Point> listPositions;
    
    /**
     * Creates a new DrawingInfos.
     */
    public DrawingInfos() {
        listPositions = new ArrayList<>();
        listObs = new ArrayList<>();        
    }
    
    /**
     * Add a point of the draw to the list of points.
     * 
     * @param p the point to add. 
     */
    public void add(Point p) {
        listPositions.add(p);
        notifyObservers(DrawEventKind.DRAW);
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
     * Clear the list of points.
     */
    public void clearList() {
        listPositions = new ArrayList<>();
        notifyObservers(DrawEventKind.CLEARPANE);
    }

    
    /**
     * Add an observer in the list of observers.
     *
     * @param o the observer to add.
     */
    @Override
    public void addObserver(Observer o) {
        if (!listObs.contains(o)) {
            listObs.add(o);
        }
    }

    /**
     * Deletes an observer from the list of observers.
     *
     * @param o the observer to delete.
     */
    @Override
    public void deleteObserver(Observer o) {
        listObs.remove(o);
    }

    /**
     * Notifies of an update the observers of the list.
     */
    @Override
    public void notifyObservers(Object arg) {
        listObs.forEach((obs) -> {
            obs.update(arg);
        });
    }
}
