package projet.pictionnary.breton.server.exception;

/**
 * Exception thrown by the data base accessors.
 */
public class PictionnaryDbException extends PictionnaryException {

    /**
     * Creates a new instance of <code>PictionnaryDbException</code> without 
     * detail message.
     */
    public PictionnaryDbException() {
    }


    /**
     * Constructs an instance of <code>PictionnaryDbException</code> with the 
     * specified detail message.
     * 
     * @param msg the detail message.
     */
    public PictionnaryDbException(String msg) {
        super(msg);
    }
}
