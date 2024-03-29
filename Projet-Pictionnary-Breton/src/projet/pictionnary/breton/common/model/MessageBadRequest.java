package projet.pictionnary.breton.common.model;

import projet.pictionnary.breton.common.users.User;

/**
 * This class is used to send message about a bad request.
 * 
 * @author Gabriel Breton - 43397
 */
public class MessageBadRequest implements Message {

    private final User author;
    private final User recipient;
    private final String what;
    
    /**
     * Constructs a new <code> MessageBadRequest </code>.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message.
     * @param what a String to explain the bad request.
     */
    public MessageBadRequest(User author, User recipient, String what) {
        this.author = author;
        this.recipient = recipient;
        this.what = what;
    }
    
    @Override
    public Type getType() {
        return Type.BAD_REQUEST;
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
        return what;
    }        
}
