package projet.pictionnary.breton.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projet.pictionnary.breton.model.DataTable;
import projet.pictionnary.breton.model.Message;
import projet.pictionnary.breton.model.MessageProfile;
import projet.pictionnary.breton.model.MessageCreateTable;
import projet.pictionnary.breton.model.MessageGetAllTables;
import projet.pictionnary.breton.model.Type;
import projet.pictionnary.breton.model.Table;
import projet.pictionnary.breton.server.users.User;
import projet.pictionnary.breton.util.Observer;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class ClientPictionnary extends AbstractClient {

    private User mySelf;
    private List<DataTable> dataTables;
    private Table currentTable;
    private List<Observer> observers;
    
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
        observers = new ArrayList<>();
        dataTables = new ArrayList<>();
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
                System.out.println("\nClientPictionnary.handleMessageFromServer():\n case CREATE_TABLE : " + ((MessageCreateTable) msg).getNameTable());
                currentTable = (Table) message.getContent();
                // TODO : notifier ?
                break;
            case GET_ALL_TABLES:
                System.out.println("\nClientPictionnary.handleMessageFromServer():\n case GET_ALL_TABLES");
                setTables((List <DataTable>) message.getContent());
                notifyObservers(message);
                break;
            default:
                throw new IllegalArgumentException("\nMessage type unknown " + type);
        }    }
    
    void setMySelf(User user) {
        this.mySelf = user;
    }

    @Override
    protected void connectionException(Exception exception) {
        System.out.println(exception.getMessage());
    }
      
    void setTables(List <DataTable> dataTables) {
        this.dataTables = dataTables;
    }
    
    public List<DataTable> getTables() {
        return dataTables;
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
            System.out.println("\nIOException in ClientPictionnary.createTable()");
        }
    }
    
    private void updateName(String name) throws IOException {
        sendToServer(new MessageProfile(0, name));
    }

    public void getAllTables() throws IOException {
         sendToServer(new MessageGetAllTables(mySelf, User.ADMIN, dataTables));
    }
    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }
    
    @Override
    public void notifyObservers(Object arg) {
        System.out.println("\nClientPictionnary.notifyObservers");
        observers.forEach((obs) -> {
            obs.update(arg);
        });
    }
}
