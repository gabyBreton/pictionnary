package projet.pictionnary.breton.common.model;

import projet.pictionnary.breton.common.users.User;

/**
 * This class is used to transfer a join request.
 * 
 * @author Gabriel Breton - 43397
 */
public class MessageJoin implements Message {
    
    private final User author;
    private final User recipient;
    private final int tableId;
    
    /**
     * Constructs a new <code> MessageJoin </code>.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message. 
     * @param tableId the id of the table to join.
     */
    public MessageJoin(User author, User recipient, int tableId) {
        this.author = author;
        this.recipient = recipient;
        this.tableId = tableId;
    }
    
    @Override
    public Type getType() {
        return Type.JOIN;
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
        return tableId;
    }     
}
