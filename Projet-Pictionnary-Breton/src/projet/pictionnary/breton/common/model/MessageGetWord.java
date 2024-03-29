package projet.pictionnary.breton.common.model;

import projet.pictionnary.breton.common.users.User;

/**
 * This class is used to send or get the word to draw for a table.
 * 
 * @author Gabriel Breton - 43397
 */
public class MessageGetWord implements Message {

    private final User author;
    private final User recipient;
    private final String word;
    private final int avgProps;

    /**
     * Constructs a new <code> MessageGetWord </code>.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message.
     * @param word the word to draw.
     */
    public MessageGetWord(User author, User recipient, String word, int avgProps) {
        this.author = author;
        this.recipient = recipient;
        this.word = word;
        this.avgProps = avgProps;
    }
    
    @Override
    public Type getType() {
        return Type.GET_WORD;
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
        return word;
    }   

    public int getAvgProps() {
        return avgProps;
    }
}
