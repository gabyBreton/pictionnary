package projet.pictionnary.breton.common.model;

import projet.pictionnary.breton.common.users.User;

/**
 * This class is used to send message for manage the quit action.
 * 
 * @author Gabriel Breton - 43397
 */
public class MessageQuit implements Message {
    
    private final User author;
    private final User recipient;
    private final Role role;

    /**
     * Constructs a new <code> MessageQuit </code>.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message.
     * @param role the role of the user that quit the game.
     */
    public MessageQuit(User author, User recipient, Role role) {
        this.author = author;
        this.recipient = recipient;
        this.role = role;
    }
    
    @Override
    public Type getType() {
        return Type.QUIT;
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
