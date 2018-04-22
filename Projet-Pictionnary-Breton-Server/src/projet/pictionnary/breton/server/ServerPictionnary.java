package projet.pictionnary.breton.server;

import projet.pictionnary.breton.model.Table;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projet.pictionnary.breton.server.users.Members;
import projet.pictionnary.breton.model.*;
import projet.pictionnary.breton.server.users.User;
import projet.pictionnary.breton.util.Observer;

/**
 * This class is used to manage and control the Pictionnary server.
 * 
 * @author Gabriel Breton - 43397
 */
public class ServerPictionnary extends AbstractServer {

    // TODO : gerer clientDisconnected
    
    private static final int PORT = 12_345;
    static final String ID_MAPINFO = "ID";
    
    private final List<Observer> observers;    
    private final List<Table> tables;
    private final List<DataTable> dataTables;
    private int clientId;
    private int tableId;
    private final Members members;
    
    private static InetAddress getLocalAddress() {
        try {
            Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
            while (b.hasMoreElements()) {
                for (InterfaceAddress f : b.nextElement().getInterfaceAddresses()) {
                    if (f.getAddress().isSiteLocalAddress()) {
                        return f.getAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Logger.getLogger(ServerPictionnary.class.getName()).log(Level.SEVERE, "NetworkInterface error", e);
        }
        return null;
    }    

    /**
     * Constructs the server. Build a thread to listen connection request.
     *
     * @throws IOException if an I/O error occurs when creating the server
     * socket.
     */
    public ServerPictionnary() throws IOException {
        super(PORT);
        members = new Members();
        clientId = 0;
        tableId = 0;
        tables = new ArrayList<>();
        dataTables = new ArrayList<>();
        observers = new ArrayList<>();
        this.listen();
    }
        
    /**
     * Return the list of connected users.
     *
     * @return the list of connected users.
     */
    public Members getMembers() {
        return members;
    }
    
    /**
     * Return the server IP address.
     *
     * @return the server IP address.
     */
    public String getIP() {
        if (getLocalAddress() == null) {
            return "Unknown";
        }
        return getLocalAddress().getHostAddress();
    }

    /**
     * Return the number of connected users.
     *
     * @return the number of connected users.
     */
    public int getNbConnected() {
        return getNumberOfClients();
    }
    
    /**
     * Quits the server and closes all aspects of the connection to clients.
     *
     * @throws IOException in case of error while closing the connection.
     */
    public void quit() throws IOException {
        this.stopListening();
        this.close();
    }
    
    /**
     * Return the next client id.
     *
     * @return the next client id.
     */
    final synchronized int getNextClientId() {
        clientId++;
        return clientId;
    }
    
    final synchronized int getNextTableId() {
        tableId++;
        return tableId;
    }
    
    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        Message message = (Message) msg;
        Type type = message.getType();
        int memberId = (int) client.getInfo(ID_MAPINFO);
        User author = message.getAuthor();
        
        switch (type) {      
            case PROFILE:
                members.changeName(author.getName(), memberId);
                Message msgName = new MessageProfile(memberId, author.getName());
                sendToClient(msgName, memberId);
                sendToClient(new MessageGetTables(User.ADMIN, author, dataTables), memberId);
                notifyObservers(msg);
                break;
            
            case CREATE:
                if ((Role) message.getContent() == Role.NOT_IN_GAME) {
                    handleCreateTableRequest(message, memberId);
                } else {
                    Message msgBadRequestCreate = new MessageBadRequest(User.ADMIN, 
                                author, "Can't create table. Already in game.");
                    sendToClient(msgBadRequestCreate, memberId);
                }
                break;
    
            case GET_TABLES:
                Message msgGetTables = new MessageGetTables(User.ADMIN, author, 
                                                                dataTables);
                sendToClient(msgGetTables, author);
                break;
                
            case GET_WORD:
                Message msgGetWord = new MessageGetWord(User.ADMIN, author, 
                                                            getWord(memberId));
                sendToClient(msgGetWord, memberId);
                break;
            
            case QUIT:
                Table tableQuit = findTable(memberId);
                Role roleQuit = (Role) message.getContent();
                handleQuitRequest(tableQuit, roleQuit, memberId, author);
                sendMessagesAfterQuit(author, memberId);
                break;

            case JOIN:
                MessageJoin msgJoin = (MessageJoin) message;
                Table tableJoin = findTableFromId((Integer) msgJoin.getContent());
                Role roleJoin = msgJoin.getRole();       
                handleJoinRequest(tableJoin, author, memberId, roleJoin);
                break;
                
            case SEND_DRAW:
                MessageSendDraw msgSendDraw = (MessageSendDraw) message;
                Table tableDrawer = findTable(memberId);
                User partner = tableDrawer.getPartner();
                DrawEvent event = (DrawEvent) msgSendDraw.getContent();
                Message msgReceptDraw;
                
                if (event == DrawEvent.DRAW) {
                    msgReceptDraw = new MessageReceptDraw(User.ADMIN, partner, 
                                          event, msgSendDraw.getDrawingInfos());
                } else {
                    msgReceptDraw = new MessageReceptDraw(User.ADMIN, partner, 
                                                            event, null);
                }
                sendToClient(msgReceptDraw, partner.getId());
                break;
                
            default:
                throw new IllegalArgumentException("Message type unknown " + type);
        }
    } 
    
    /**
     * Handles and applys a create table request.
     * 
     * @param message the create table request.
     * @param memberId the id of the author of the request.
     */
    private void handleCreateTableRequest(Message message, int memberId) {
        Table table = new Table(((MessageCreate) message).getNameTable(),
                                    getNextTableId(), message.getAuthor(), "Stylo");
        
        tables.add(table);
        Message messageCreateTable = new MessageCreate(User.ADMIN, message.getAuthor(),
                                                   Role.DRAWER,table.getName());
        sendToClient(messageCreateTable, memberId);

        // update datatables.
        String namePartner = (table.getPartner() == null) ? "" : table.getPartner().getName();
        String statusTable = (table.isOpen() == true) ? "Open" : "Closed";        
        dataTables.add(new DataTable(table.getName(), table.getId(),
                statusTable,
                table.getDrawer().getName(),
                namePartner));

        Message messageGetAllTables = new MessageGetTables(User.ADMIN,
                User.EVERYBODY, dataTables);
        sendToAllClients(messageGetAllTables);
    }    
    
    /**
     * Handle and apply a quit request.
     * 
     * @param tableQuit the table to quit.
     * @param roleQuit the role of the player that want to quit.
     * @param memberId the id of the member that want to quit.
     * @param author the author of the request.
     * @throws IllegalArgumentException in case of an unknown role type.
     */
    private void handleQuitRequest(Table tableQuit, Role roleQuit, int memberId, 
                                    User author) throws IllegalArgumentException {
        
        if (tableQuit != null && tableQuit.getPlayerCount() == 1) {
            destroyTable(tableQuit);
        } else if (tableQuit != null && tableQuit.getPlayerCount() == 2) {
            switch (roleQuit) {
                case DRAWER:
                    tableQuit.removeDrawer();
                    updateDataTables(tableQuit.getId(), roleQuit, "", "Closed");
                    // TODO notifier autre joueur
                    break;
        
                case PARTNER:
                    tableQuit.removePartner();
                    updateDataTables(tableQuit.getId(), roleQuit, "", "Closed");
                    // TODO notifier autre joueur
                    break;
                
                case NOT_IN_GAME:
                    Message msgBadRequestQuit = new MessageBadRequest(User.ADMIN,
                            author, "Can't quit, not in game.");
                    sendToClient(msgBadRequestQuit, memberId);
                    break;
                
                default:
                    throw new IllegalArgumentException("Unknown role : " + roleQuit);
            }
        }
    }

    /**
     * Send several messages after a quit request, such as an update of the 
     * tables and a confirmation for the client that have emitted this request.
     * 
     * @param author the author of the message.
     * @param memberId the id of the author of the message.
     */
    private void sendMessagesAfterQuit(User author, int memberId) {
        Message msgGetAllTablesRefresh = new MessageGetTables(User.ADMIN,
                                                    User.EVERYBODY, dataTables);
        sendToAllClients(msgGetAllTablesRefresh);
        Message msgQuitGame = new MessageQuit(User.ADMIN, author, Role.NOT_IN_GAME);
        sendToClient(msgQuitGame, memberId);
    }    
    
    /**
     * Handle and apply a join request.
     * 
     * @param tableJoin the table to join.
     * @param author the author that want to join the table.
     * @param memberId the id of the user.
     * @param roleJoin the role of the user.
     */
    private void handleJoinRequest(Table tableJoin, User author, int memberId, 
                                    Role roleJoin) {
        if (tableJoin != null) {
            if (!tableJoin.isOpen()) {
                Message msgBadRequestClosed = new MessageBadRequest(User.ADMIN,
                                           author, "Can't join, table closed.");
                sendToClient(msgBadRequestClosed, memberId);
                
            } else if (roleJoin != Role.NOT_IN_GAME) {
                Message msgBadRequestInGame = new MessageBadRequest(User.ADMIN,
                                author, "Can't join, you are already in game.");
                sendToClient(msgBadRequestInGame, memberId);
                
            } else if (tableJoin.getPlayerCount() == 1) {
                tableJoin.addPartner(author);
                updateDataTables(tableJoin.getId(), Role.PARTNER, author.getName(), 
                                    "Closed");
                sendMessagesAfterJoin(author, tableJoin, memberId);
            }
        } else {
            Message msgBadRequestNoTable = new MessageBadRequest(User.ADMIN,
                                                    author, "Can't find table");
            sendToClient(msgBadRequestNoTable, memberId);
        }
    }

    /**
     * Send several messages after a join request, such as an update of the 
     * tables and a confirmation for the client that have emitted this request.
     * 
     * @param author the author of the request.
     * @param tableJoin the table to join.
     * @param memberId the id of the author of the request.
     */
    private void sendMessagesAfterJoin(User author, Table tableJoin, int memberId) {
        Message msgGetAllTablesRefresh = new MessageGetTables(User.ADMIN,
                User.EVERYBODY, dataTables);
        sendToAllClients(msgGetAllTablesRefresh);
        Message msgJoin = new MessageJoin(User.ADMIN, author, Role.PARTNER, tableJoin.getId());
        sendToClient(msgJoin, memberId);
    }
    
    /**
     * Finds a table based on its id.
     * 
     * @param tableId the id of the table.
     * @return the table corresponding to the id or null is the table is not 
     * find.
     */
    private Table findTableFromId(int tableId) {
        for (Table table : tables) {
            if (table.getId() == tableId) {
                return table;
            }
        }
        return null;
    }

    /**
     * Finds a table that contains the given client.
     * 
     * @param clientId the id of the client.
     * @return the table that contains the client or null if no table contains 
     * this client.
     */
    private Table findTable(int clientId) {
        for (Table table : tables) {
            if ((table.getDrawer() != null && table.getDrawer().getId() == clientId) 
                    || (table.getPartner() != null && table.getPartner().getId() == clientId)) {
                return table;
            }
        }
        return null;
    }    
    
    @Override
    protected void clientConnected(ConnectionToClient client) {
        super.clientConnected(client);
        int memberId = members.add(getNextClientId(), client.getName(), client.getInetAddress());
        client.setInfo(ID_MAPINFO, memberId);
        sendToClient(new MessageGetTables(User.ADMIN, null, dataTables), memberId);
    }
    
    /**
     * Sends a message to a client.
     * 
     * @param message the message to send.
     * @param recipient the recipient of the message.
     */
    void sendToClient(Message message, User recipient) {
        sendToClient(message, recipient.getId());
    }

    /**
     * Sends a message to a client, based on the client id.
     * 
     * @param message the message to send.
     * @param clientId the id of the recipient client.
     */
    void sendToClient(Message message, int clientId) {
        for (Thread clientThreadList : getClientConnections()) {
            int idClientThread = (Integer) ((ConnectionToClient) clientThreadList).getInfo(ID_MAPINFO);
            
            if (clientId == idClientThread) {
                try {
                    ((ConnectionToClient) clientThreadList).sendToClient(message);
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
                break;
            }
        }
    }
    
    /**
     * Destroys a table and update datatables.
     * 
     * @param table the table to destroy.
     */
    private void destroyTable(Table table) {
        System.out.println("destroyTable table");
        for(DataTable data : dataTables) {
            if (data.getId() == table.getId()) {
                dataTables.remove(data);
                break;
            }
        }
        tables.remove(table);
    }

    /**
     * Update the list of table informations when a role or the table status 
     * change.
     * 
     * @param tableId the id of the table that change.
     * @param role the role of that change.
     * @param name the name of the drawer or the partner.
     * @param status the status that change.
     */
    private void updateDataTables(int tableId, Role role, String name, 
                                    String status) {
        for(DataTable data : dataTables) {
            if (data.getId() == tableId) {
                if (role == Role.DRAWER) {
                    data.setDrawer(name);
                } else if (role == Role.PARTNER) {
                    data.setPartner(name);
                }
                data.setStatus(status);
                break;
            }
        }
    }
    
    /**
     * Gives the word to draw attributed to a table.
     * 
     * @param clientId the id of the client that needs the word to draw.
     * @return the word to draw.
     */
    private String getWord(int clientId) {
        String word = "";
        
        for (Table table : tables) {
            if (table.getDrawer().getId() == clientId) {
                word = table.getWord();
                break;
            }
        }
        return word;
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
