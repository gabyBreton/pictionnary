package projet.pictionnary.breton.common.model;

import projet.pictionnary.breton.common.users.User;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class MessageConnected implements Message {

    private final User author;
    private final User recipient;
    private final boolean connected;

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
