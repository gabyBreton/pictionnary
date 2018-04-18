package projet.pictionnary.breton.client;

import java.io.IOException;
import java.util.List;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projet.pictionnary.breton.model.Message;
import projet.pictionnary.breton.model.MessageProfile;
import projet.pictionnary.breton.model.MessageCreateTable;
import projet.pictionnary.breton.model.MessageGetAllTables;
import projet.pictionnary.breton.model.Type;
import projet.pictionnary.breton.model.Table;
import projet.pictionnary.breton.server.users.User;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class ClientPictionnary extends AbstractClient {

    private User mySelf;
    private ObservableList<Table> listTables;
    private Table currentTable;
    
    /**
     * Constructs the client. Opens the connection with the server. Sends the
     * user name inside a <code> MessageProfile </code> to the server. Builds an
     * empty list of users.
     *
     * @param host the server's host name.
     * @param port the port number.
     * @param name the name of the user.
     * @throws IOException if an I/O error occurs when opening.
     */
    public ClientPictionnary(String host, int port, String name) throws IOException {
        super(host, port);
        listTables = new SimpleListProperty<>();
        openConnection();
        updateName(name);
    }
    
    @Override
    protected void handleMessageFromServer(Object msg) {
        Message message = (Message) msg;
        Type type = message.getType();
        switch (type) {
            case PROFILE:
                setMySelf(message.getAuthor());
                break;
            case CREATE_TABLE:
                System.out.println("ClientPictionnary.handleMessageFromServer():\n case CREATE_TABLE : " + ((MessageCreateTable) msg).getNameTable());
                currentTable = (Table) message.getContent();
                break;
            case GET_ALL_TABLES:
                System.out.println("ClientPictionnary.handleMessageFromServer():\n case GET_TABLES");
                setTables((List <Table>) message.getContent());
                break;
            default:
                throw new IllegalArgumentException("Message type unknown " + type);
        }    }
    
    void setMySelf(User user) {
        this.mySelf = user;
    }
    
    void setTables(List <Table> tables) {
        listTables = FXCollections.observableArrayList(tables);
    }
    
    public ObservableList<Table> getTables() {
        return listTables;
    }

    /**
     * Quits the client and closes all aspects of the connection to the server.
     *
     * @throws IOException if an I/O error occurs when closing.
     */
    public void quit() throws IOException {
        closeConnection();
    }
    
    public void createTable(String tableName) {
        try {
            sendToServer(new MessageCreateTable(mySelf, User.ADMIN, null, tableName));
        } catch (IOException ioe) {
            System.out.println("IOException in ClientPictionnary.createTable()");
        }
    }
    
    private void updateName(String name) throws IOException {
        sendToServer(new MessageProfile(0, name));
    }
}
