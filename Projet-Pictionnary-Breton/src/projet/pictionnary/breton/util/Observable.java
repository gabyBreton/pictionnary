package projet.pictionnary.breton.util;

import java.util.List;
import projet.pictionnary.breton.model.EventKind;

/**
 * Interface to implement the observer/observable pattern.
 *
 * @author Gabriel Breton - 43397
 */
public interface Observable {

    /**
     * Add an observer in the list of observers.
     *
     * @param o the observer to add.
     */
    public void addObserver(Observer o);

    /**
     * Deletes an observer from the list of observers.
     *
     * @param o the observer to delete.
     */
    public void deleteObserver(Observer o);
    
    /**
     * 
     * @param arg
     */
    public void notifyObservers(Object arg);
}
