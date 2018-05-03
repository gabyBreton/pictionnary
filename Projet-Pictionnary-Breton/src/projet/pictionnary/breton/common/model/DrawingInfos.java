package projet.pictionnary.breton.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import projet.pictionnary.breton.common.users.User;
import projet.pictionnary.breton.common.util.Observable;
import projet.pictionnary.breton.common.util.Observer;

/**
 * This class is used to transfer a draw.
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawingInfos implements Serializable, Observable {
    
    private final transient List<Observer> listObs;
    private ArrayList<Point> listPositions;
    
    /**
     * Constructs a new <code> DrawingInfos </code>.
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
        notifyObservers(new MessageSendDraw(null, User.ADMIN, DrawEvent.DRAW, 
                                            this));
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
        notifyObservers(new MessageSendDraw(null, User.ADMIN, 
                                            DrawEvent.CLEARPANE, null));
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
