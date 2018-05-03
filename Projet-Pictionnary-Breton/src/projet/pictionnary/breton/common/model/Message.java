package projet.pictionnary.breton.common.model;

import java.io.Serializable;
import projet.pictionnary.breton.common.users.User;

/**
 * The <code> Message </code> represents a general message send to a user.
 */
public interface Message extends Serializable {

    /**
     * Return the message type.
     *
     * @return the message type.
     */
    Type getType();

    /**
     * Return the message author.
     *
     * @return the message author.
     */
    User getAuthor();

    /**
     * Return the message recipient.
     *
     * @return the message recipient.
     */
    User getRecipient();

    /**
     * Return the message content.
     *
     * @return the message content.
     */
    Object getContent();

}
