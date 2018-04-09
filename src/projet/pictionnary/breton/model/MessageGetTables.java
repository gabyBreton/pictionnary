package projet.pictionnary.breton.model;

import java.util.List;
import projet.pictionnary.breton.server.Table;
import projet.pictionnary.breton.server.users.User;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class MessageGetTables implements Message {

    private final User author;
    private final User recipient;
    private final List<Table> tables;

    public MessageGetTables(User author, User recipient, List<Table> tables) {
        this.author = author;
        this.recipient = recipient;
        this.tables = tables;
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
        return tables;
    }
}
