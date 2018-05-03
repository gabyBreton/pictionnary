package projet.pictionnary.breton.common.model;

import projet.pictionnary.breton.common.users.User;

/**
 * This class is used to update <code> GameStatus </code> for the players of a 
 * game.
 * 
 * @author Gabriel Breton - 43397
 */
public class MessageGameStatus implements Message{
        
    private final User author;
    private final User recipient;
    private final GameStatus gameStatus;
    
    /**
     * Constructs a new <code> MessageGameStatus </code>.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message. 
     * @param gameStatus the status of the game.
     */
    public MessageGameStatus(User author, User recipient, 
                                GameStatus gameStatus) {
        this.author = author;
        this.recipient = recipient;
        this.gameStatus = gameStatus;
    }
    
    @Override
    public Type getType() {
        return Type.GAME_STATUS;
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
        return gameStatus;
    }    
}
