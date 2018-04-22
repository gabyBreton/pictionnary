package projet.pictionnary.breton.model;

import projet.pictionnary.breton.server.users.User;

/**
 * This class is used to send message about a table creation.
 * 
 * @author Gabriel Breton - 43397
 */
public class MessageCreate implements Message {

    private final User author;
    private final User recipient;
    private final String nameTable;

    /**
     * Constructs a new <code> MessageCreateTable </code>.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message.
     * @param nameTable the name of the table.
     */
    public MessageCreate(User author, User recipient, String nameTable) {
        this.author = author;
        this.recipient = recipient;
        this.nameTable = nameTable;
    }
    
    @Override
    public Type getType() {
        return Type.CREATE;
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
        return nameTable;
    }    
}
