package projet.pictionnary.breton.common.model;

import java.util.List;
import projet.pictionnary.breton.common.users.User;

/**
 * Class used to transfer client game statistics.
 * 
 * @author Gabriel Breton - 43397
 */
public class MessageStats implements Message {

    private final User author;
    private final User recipient;
    private final List<String> stats;
    
    /**
     * Constructs a new MessageStats.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message.
     * @param stats the statistics.
     */
    public MessageStats(User author, User recipient, List<String> stats) {
        this.author = author;
        this.recipient = recipient;
        this.stats = stats;
    }
    
    @Override
    public Type getType() {
        return Type.STATS;
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
        return stats;
    }
}
