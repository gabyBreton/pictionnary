package projet.pictionnary.breton.common.model;

import projet.pictionnary.breton.common.users.User;

/**
 * This class is used to transfer a message when an user try to connect with
 * an invalid login.
 * A login is not valid if it this already used by a connected user.
 * 
 * @author Gabriel Breton - 43397
 */
public class MessageInvalidLogin implements Message{

    private final User author;
    private final User recipient;
    private final String login;

    /**
     * Constructs a new <code> MessageInvalidLogin </code>.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message.
     * @param login the login of the user.
     */
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