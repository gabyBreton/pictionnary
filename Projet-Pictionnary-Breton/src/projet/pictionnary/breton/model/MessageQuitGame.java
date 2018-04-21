package projet.pictionnary.breton.model;

import projet.pictionnary.breton.server.users.User;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class MessageQuitGame implements Message {
    
    private final User author;
    private final User recipient;
    private final Role role;

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
