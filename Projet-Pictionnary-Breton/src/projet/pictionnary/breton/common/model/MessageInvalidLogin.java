package projet.pictionnary.breton.common.model;

import projet.pictionnary.breton.common.users.User;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class MessageInvalidLogin implements Message{

    private final User author;
    private final User recipient;
    private final String login;

    public MessageInvalidLogin(User author, User recipient, String login) {
        this.author = author;
        this.recipient = recipient;
        this.login = login;
    }
    
    @Override
    public Type getType() {
        return Type.INVALID_LOGIN;
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
        return login;
    }
}


