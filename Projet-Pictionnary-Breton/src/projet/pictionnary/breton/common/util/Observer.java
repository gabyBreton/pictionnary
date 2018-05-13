package projet.pictionnary.breton.common.util;

/**
 * Interface to implement the Observer pattern.
 * 
 * @author Gabriel Breton - 43397
 */
public interface Observer {

    /**
     * Updates after a notification from an observable.
     * 
     * @param arg the information to use to update.
     */
    public void update(Object arg);
}
