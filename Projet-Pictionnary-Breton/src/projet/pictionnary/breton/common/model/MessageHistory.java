package projet.pictionnary.breton.common.model;

import java.util.List;
import projet.pictionnary.breton.common.users.User;

/**
 *
 * @author G43397
 */
public class MessageHistory implements Message {

    private User author;
    private User recipient;
    private List<String> history;

    public MessageHistory(User author, User recipient, List<String> history) {
        this.author = author;
        this.recipient = recipient;
        this.history = history;
    }

    @Override
    public Type getType() {
        return Type.HISTORY;
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
        return history;
    }
}
