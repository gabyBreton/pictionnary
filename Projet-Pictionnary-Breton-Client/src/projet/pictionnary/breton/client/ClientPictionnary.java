package projet.pictionnary.breton.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projet.pictionnary.breton.common.model.DataTable;
import projet.pictionnary.breton.common.model.DrawEvent;
import projet.pictionnary.breton.common.model.DrawingInfos;
import projet.pictionnary.breton.common.model.GameStatus;
import projet.pictionnary.breton.common.model.Message;
import projet.pictionnary.breton.common.model.MessageProfile;
import projet.pictionnary.breton.common.model.MessageCreate;
import projet.pictionnary.breton.common.model.MessageGameStatus;
import projet.pictionnary.breton.common.model.MessageSendDraw;
import projet.pictionnary.breton.common.model.MessageGetWord;
import projet.pictionnary.breton.common.model.MessageJoin;
import projet.pictionnary.breton.common.model.MessageQuit;
import projet.pictionnary.breton.common.model.MessageStats;
import projet.pictionnary.breton.common.model.MessageSubmit;
import projet.pictionnary.breton.common.model.Type;
import projet.pictionnary.breton.common.users.User;
import projet.pictionnary.breton.common.util.Observer;

/**
 * This class is used to create and manage a client for the Pictionnary.
 * 
 * @author Gabriel Breton - 43397
 */
public class ClientPictionnary extends AbstractClient {

    private User mySelf;
    private final String name;
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
        this.name = name;
    }
    
    @Override
    protected void handleMessageFromServer(Object msg) {
        Message message = (Message) msg;
        Type type = message.getType();
        switch (type) {
            case CONNECTED:
                try {
                    setMySelf(message.getRecipient());  
                    System.out.println("CONNECTED TO SERVER -> id : " + mySelf.getId());
                    updateName(name);
                } catch (IOException ex) {
                    Logger.getLogger(ClientPictionnary.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                
            case PROFILE:
                setMySelf(message.getAuthor());
                System.out.println("PROFILE VALIDATED -> id : " + mySelf.getId());                
                System.out.println("                  -> login : " + mySelf.getName());
                System.out.println("                  -> role : " +  mySelf.getRole());
                notifyObservers(msg);
                break;
                
            case INVALID_LOGIN:
                notifyObservers(msg);
                break;
            
            case STATS:
                System.out.println("STATISTICS RECEIVED");
                notifyObservers(msg);
                break;
                
            case CREATE:
                setMySelf(message.getRecipient());                
                notifyObservers(message);
                break;
                
            case GET_TABLES:
                setTables((List <DataTable>) message.getContent());
                System.out.println("TABLES RECEIVED");
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
        }    
    }
    
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
    
    /**
     * Sets the status of the game.
     * 
     * @param gameStatus the game status to set.
     */
    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
    
    /**
     * Gives the value of gameStatus.
     * 
     * @return the value of gameStatus.
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * Sends to the server the word that has been submited in a game.
     * 
     * @param propostion the word proposed.
     */
    public void submit(String propostion) {
        try {
            sendToServer(new MessageSubmit(mySelf, User.ADMIN, propostion, 
                                            gameStatus));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * Sends to the server a request to get the client statistics.
     */
    public void getStats() {
        try {
            sendToServer(new MessageStats(mySelf, User.ADMIN, null));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
    
    /**
     * Gives the name of the client.
     * 
     * @return the name of the client.
     */
    public String getName() {
        return mySelf.getName();
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
