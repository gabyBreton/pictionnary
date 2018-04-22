package projet.pictionnary.breton.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import projet.pictionnary.breton.model.DataTable;
import projet.pictionnary.breton.model.DrawEvent;
import projet.pictionnary.breton.model.DrawingInfos;
import projet.pictionnary.breton.model.GameStatus;
import projet.pictionnary.breton.model.Message;
import projet.pictionnary.breton.model.MessageProfile;
import projet.pictionnary.breton.model.MessageCreate;
import projet.pictionnary.breton.model.MessageGameStatus;
import projet.pictionnary.breton.model.MessageSendDraw;
import projet.pictionnary.breton.model.MessageGetWord;
import projet.pictionnary.breton.model.MessageJoin;
import projet.pictionnary.breton.model.MessageQuit;
import projet.pictionnary.breton.model.MessageSubmit;
import projet.pictionnary.breton.model.Type;
import projet.pictionnary.breton.server.users.User;
import projet.pictionnary.breton.util.Observer;

/**
 * This class is used to create and manage a client for the Pictionnary.
 * 
 * @author Gabriel Breton - 43397
 */
public class ClientPictionnary extends AbstractClient {

    private User mySelf;
    private List<DataTable> dataTables;
    private final List<Observer> observers;
    private String word; // TODO : retirer le mot lors de la destruction de la table.
    private GameStatus gameStatus;
    
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
                
            case CREATE:
                setMySelf(message.getRecipient());                
                notifyObservers(message);
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
                setMySelf(message.getRecipient());                
                break;
                
            case BAD_REQUEST:
                System.out.println((String) message.getContent());
                break;
                
            case JOIN:
                setMySelf(message.getRecipient());                
                MessageJoin msgJoin = (MessageJoin) message;
                notifyObservers(msgJoin);
                break;
            
            case SEND_DRAW: 
            case RECEPT_DRAW:
            case SUBMIT:
                notifyObservers(message);
                break;
            
            case GAME_STATUS:
                gameStatus = (GameStatus) message.getContent();
                notifyObservers(message);
                break;
                
            
            default:
                throw new IllegalArgumentException("\nMessage type unknown " 
                                                    + type);
        }    }
    
    /**
     * Sets the <code> User </code> for the client.
     * 
     * @param user 
     */
    void setMySelf(User user) {
        this.mySelf = user;
    }

    @Override
    protected void connectionException(Exception exception) {
        System.out.println(exception.getMessage());
    }
      
    /**
     * Sets the <code> DataTables </code> list.
     * 
     * @param dataTables the data to set.
     */
    void setTables(List <DataTable> dataTables) {
        this.dataTables = dataTables;
    }
    
    /**
     * Gives the list of <code> DataTables </code>.
     * 
     * @return the list of data.
     */
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
    
    /**
     * Send a request to the server to create a <code> Table </code>.
     * 
     * @param tableName the name of the table to create.
     */
    public void createTable(String tableName) {
        try {
            sendToServer(new MessageCreate(mySelf, User.ADMIN, tableName));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());            
        }
    }
    
    /**
     * Updates and send the name of the client to the server.
     * 
     * @param name the name of the client.
     * @throws IOException if an error occurs when sending the name to the 
     * server.
     */
    private void updateName(String name) throws IOException {
        sendToServer(new MessageProfile(0, name));
    }

    /**
     * Asks the word to draw to the server.
     */
    public void askWord() {
        try {
            sendToServer(new MessageGetWord(mySelf, User.ADMIN, ""));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } 
    }
    
    /**
     * Gives the word to draw.
     * 
     * @return the word to draw.
     */
    public String getWord() {
        return word;
    }
    
    /**
     * Asks to the server to quit the game.
     */
    public void quitGame() {
        try {
            sendToServer(new MessageQuit(mySelf, User.ADMIN, mySelf.getRole()));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());            
        }
    }
    
    /**
     * Sends a <code> Message </code> to the server to join the 
     * <code> Table </code> specified by the id given in parameters.
     * 
     * @param tableId the id of the table to join.
     */
    public void join(int tableId) {
        try {
            sendToServer(new MessageJoin(mySelf, User.ADMIN, tableId));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());            
        }
    }
    
    /**
     * Sends a <code> Message </code> to the server to pass the draw to the 
     * partner.
     * 
     * @param drawingInfos the drawing infos of the draw.
     */
    public void draw(DrawingInfos drawingInfos) {
        try {
            sendToServer(new MessageSendDraw(mySelf, User.ADMIN, DrawEvent.DRAW, 
                                                drawingInfos));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());            
        }        
    }

    /**
     * Sends a <code> Message </code> to the server to clear the pane of the 
     * partner.
     */
    public void clearPane() {
        try {
            sendToServer(new MessageSendDraw(mySelf, User.ADMIN, 
                                                DrawEvent.CLEARPANE, null));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());            
        }
    }

    /**
     * Asks to the server the status of the current game.
     */
    public void askGameStatus() {
        try {
            sendToServer(new MessageGameStatus(mySelf, User.ADMIN, null));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
    
    public void submit(String propostion) {
        try {
            sendToServer(new MessageSubmit(mySelf, User.ADMIN, propostion, 
                                            gameStatus));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
    
    /**
     * Gives the value of gameStatus.
     * 
     * @return the value of gameStatus.
     */
    public GameStatus getGameStatus() {
        return gameStatus;
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
