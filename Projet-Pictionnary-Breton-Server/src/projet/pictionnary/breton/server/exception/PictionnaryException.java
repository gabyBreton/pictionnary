package projet.pictionnary.breton.server.exception;

/**
 * Exception thrown by a method of a 'Facade'.
 */
public class PictionnaryException extends Exception {

    /**
     * Creates a new instance of <code>PictionnaryException</code> without 
     * detail message.
     */
    public PictionnaryException() {
    }


    /**
     * Constructs an instance of <code>PictionnaryException</code> with the 
     * specified detail message.
     * 
     * @param msg the detail message.
     */
    public PictionnaryException(String msg) {
        super(msg);
    }
}
