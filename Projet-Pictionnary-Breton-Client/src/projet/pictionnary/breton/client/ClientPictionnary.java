package projet.pictionnary.breton.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import projet.pictionnary.breton.model.DataTable;
import projet.pictionnary.breton.model.Message;
import projet.pictionnary.breton.model.MessageProfile;
import projet.pictionnary.breton.model.MessageCreate;
import projet.pictionnary.breton.model.MessageGetWord;
import projet.pictionnary.breton.model.MessageJoin;
import projet.pictionnary.breton.model.MessageQuit;
import projet.pictionnary.breton.model.Role;
import projet.pictionnary.breton.model.Type;
import projet.pictionnary.breton.server.users.User;
import projet.pictionnary.breton.util.Observer;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class ClientPictionnary extends AbstractClient {

    private User mySelf;
    private List<DataTable> dataTables;
    private List<Observer> observers;
    private String word; // TODO : retirer le mot lors de la destruction de la table.
    private Role role; // TODO : changer le role lorsqu'on quitte/rentre dans une table.
    
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
        role = Role.NOT_IN_GAME;
        System.out.println("Role : " + role.toString());
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
                
            case CREATE:
                role = (Role) message.getContent();
                notifyObservers(message);
                System.out.println("Role : " + role.toString());
                break;
                
            case GET_TABLES:
                setTables((List <DataTable>) message.getContent());
                notifyObservers(message);
                break;
                
            case GET_WORD:
                word = (String) message.getContent();
                notifyObservers(message);
                break;
                
            case QUIT:
                role = (Role) message.getContent();
                System.out.println("Role : " + role.toString());        
                break;
                
            case BAD_REQUEST:
                System.out.println((String) message.getContent());
                break;
                
            case JOIN:
                role = (Role) message.getContent();
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
            sendToServer(new MessageCreate(mySelf, User.ADMIN, role, tableName));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());            
        }
    }
    
    private void updateName(String name) throws IOException {
        sendToServer(new MessageProfile(0, name));
    }

    public void askWord() {
        try {
            sendToServer(new MessageGetWord(mySelf, User.ADMIN, ""));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } 
    }
    
    public String getWord() {
        return word;
    }
    
    public void quitGame() {
        try {
            sendToServer(new MessageQuit(mySelf, User.ADMIN, role));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());            
        }
    }
    
    /**
     * Sends a message to the server to join the table specified by the id given
     * in parameters.
     * 
     * @param tableId the id of the table to join.
     */
    public void join(int tableId) {
        try {
            sendToServer(new MessageJoin(mySelf, User.ADMIN, role, tableId));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());            
        }
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
        observers.forEach((obs) -> {
            obs.update(arg);
        });
    }
}
