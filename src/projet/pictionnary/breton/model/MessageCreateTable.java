package projet.pictionnary.breton.model;

import projet.pictionnary.breton.server.Table;
import projet.pictionnary.breton.server.users.User;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class MessageCreateTable implements Message {

    private final User author;
    private final User recipient;
    private final Table table;
    private final String nameTable;

    public MessageCreateTable(User author, User recipient, Table table, 
                              String nameTable) {
        this.author = author;
        this.recipient = recipient;
        this.table = table;
        this.nameTable = nameTable;
    }
    
    @Override
    public Type getType() {
        return Type.CREATE_TABLE;
    }

    public String getNameTable() {
        return nameTable;
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
        return table;
    }    
}
