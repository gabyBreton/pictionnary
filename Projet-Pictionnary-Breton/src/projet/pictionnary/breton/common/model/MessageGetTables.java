package projet.pictionnary.breton.common.model;

import java.util.List;
import projet.pictionnary.breton.common.users.User;

/**
 * This class is used to send or get all the data of the tables on the server.
 * 
 * @author Gabriel Breton - 43397
 */
public class MessageGetTables implements Message {

    private final User author;
    private final User recipient;
    private final List<DataTable> dataTables;

    /**
     * Construcs a new <code> MessageGetAllTables </code>.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message.
     * @param dataTables the list of all the data of the tables.
     */
    public MessageGetTables(User author, User recipient,
                            List<DataTable> dataTables) {
        this.author = author;
        this.recipient = recipient;
        this.dataTables = dataTables;
    }
    
    @Override
    public Type getType() {
        return Type.GET_TABLES;
    }

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    public User getRecipient() {
        return recipient;
    }

    @Override
    public Object getContent() {
        return dataTables;
    }
}
