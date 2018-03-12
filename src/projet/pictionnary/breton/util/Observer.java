package projet.pictionnary.breton.util;

import projet.pictionnary.breton.model.EventKind;

/**
 * Interface to implement the observer/observable pattern.
 * 
 * @author Gabriel Breton - 43397
 */
public interface Observer {
    
    /**
     * Updates an observer.
     */
    public void update(EventKind eventKind);      
}
