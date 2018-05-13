package projet.pictionnary.breton.common.model;

import projet.pictionnary.breton.common.users.User;

/**
 * This class is used to transfera message when the client is well connected.
 * 
 * @author Gabriel Breton - 43397
 */
public class MessageConnected implements Message {

    private final User author;
    private final User recipient;
    private final boolean connected;

    /**
     * Constructs a new <code> MessageConnected </code>.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message.
     * @param connected true if the client is connected, or else false.
     */
    public MessageConnected(User author, User recipient, boolean connected) {
        this.author = author;
        this.recipient = recipient;
        this.connected = connected;
    }
    
    @Override
    public Type getType() {
        return Type.CONNECTED;
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
        return connected;
    }
}
