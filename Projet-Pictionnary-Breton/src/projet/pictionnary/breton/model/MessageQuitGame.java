package projet.pictionnary.breton.model;

import projet.pictionnary.breton.server.users.User;

/**
 * This class is used to send message for manage the quit action.
 * @author Gabriel Breton - 43397
 */
public class MessageQuitGame implements Message {
    
    private final User author;
    private final User recipient;
    private final Role role;

    /**
     * Creates a new MessageQuitGame.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message.
     * @param role the role of the user.
     */
    public MessageQuitGame(User author, User recipient, Role role) {
        this.author = author;
        this.recipient = recipient;
        this.role = role;
    }
    
    @Override
    public Type getType() {
        return Type.QUIT_GAME;
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
        return role;
    }   
}
