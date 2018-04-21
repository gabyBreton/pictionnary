package projet.pictionnary.breton.model;

import projet.pictionnary.breton.server.users.User;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class MessageBadRequest implements Message {

    private final User author;
    private final User recipient;
    private final String what;

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
