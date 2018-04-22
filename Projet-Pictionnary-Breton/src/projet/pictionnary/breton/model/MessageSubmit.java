package projet.pictionnary.breton.model;

import projet.pictionnary.breton.server.users.User;

/**
 * This class is used to submit a word and get the response of the server.
 * 
 * @author Gabriel Breton - 43397
 */
public class MessageSubmit implements Message {
    
    private final User author;
    private final User recipient;
    private final String proposition;
    private final GameStatus gameStatus;
    
    /**
     * Constructs a new <code> MessageSubmit </code>.
     * When the word is submitted the <code> GameStatus </code> is IN_GAME
     * and if the word is correct, gameStatus becomes WIN, if not it stays 
     * IN_GAME.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message. 
     * @param gameStatus the status of the game.
    */
    public MessageSubmit(User author, User recipient, String proposition, 
                            GameStatus gameStatus) {
        this.author = author;
        this.recipient = recipient;
        this.proposition = proposition;
        this.gameStatus = gameStatus;
    }
    
    @Override
    public Type getType() {
        return Type.SUBMIT;
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
        return proposition;
    }    
    
    /**
     * Gives the game status for the game from where the word was submited.
     * 
     * @return the game status of the game.
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
