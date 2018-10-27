package projet.pictionnary.breton.server.exception;

/**
 * Exception thrown by manipulating the dto.
 */
public class PictionnaryDTOException extends PictionnaryException {

    /**
     * Creates a new instance of <code>PictionnaryDTOException</code> without
     * detail message.
     */
    public PictionnaryDTOException() {
    }

    /**
     * Constructs an instance of <code>PictionnaryDTOException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PictionnaryDTOException(String msg) {
        super(msg);
    }
}
