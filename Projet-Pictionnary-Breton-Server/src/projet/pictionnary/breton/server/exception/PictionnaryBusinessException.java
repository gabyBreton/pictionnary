package projet.pictionnary.breton.server.exception;

/**
 * Exception thrown by manipulationg dto.
 */
public class PictionnaryBusinessException extends PictionnaryException {

    /**
     * Creates a new instance of <code>PictionnaryBusinessException</code> without
     * detail message.
     */
    public PictionnaryBusinessException() {
    }

    /**
     * Constructs an instance of <code>PictionnaryBusinessException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PictionnaryBusinessException(String msg) {
        super(msg);
    }
}
