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
 *
 * @author Gabriel Breton - 43397
 */
public class ServerPictionnary extends AbstractServer {

    private static final int PORT = 12_345;
    static final String ID_MAPINFO = "ID";
    
    private List<Observer> observers;    
    private int clientId;
    private int tableId;
    private final Members members;
    private List<Table> tables;
    private List<DataTable> dataTables;
    
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
     * @throws IOException
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
                
                // on envoi les tables à ce moment là car le client est en attente de message
                sendToClient(new MessageGetAllTables(User.ADMIN, author, dataTables), memberId);
                break;
            
            case CREATE_TABLE:
                if ((Role) message.getContent() == Role.NOT_IN_GAME) {
                    createNewTable(message, author, memberId);
                } else {
                    Message msgBadRequestCreate = new MessageBadRequest(User.ADMIN, author, "Can't create table. Already in game.");
                    sendToClient(msgBadRequestCreate, memberId);
                }
                break;
    
            case GET_ALL_TABLES:
                Message msgGetTables = new MessageGetAllTables(User.ADMIN, author, dataTables);
                sendToClient(msgGetTables, author);
                break;
                
            case GET_WORD:
                Message msgGetWord = new MessageGetWord(User.ADMIN, author, 
                                                            getWord(memberId));
                sendToClient(msgGetWord, memberId);
                break;
            
            case QUIT_GAME:
                Table table = findTable(clientId);
                Role role = (Role) message.getContent();
                
                if (table != null && table.getPlayerCount() == 1) {
                    destroyTable(table);
                } else if (table != null && table.getPlayerCount() == 2) {
                    switch (role) {
                        case DRAWER:
                            table.removeDrawer();
                            updateDataTables(memberId, role);
                            // TODO notifier autre joueur
                            break;
                        case PARTNER:
                            table.removePartner();
                            updateDataTables(memberId, role);
                            // TODO notifier autre joueur
                            break;
                        case NOT_IN_GAME:
                            Message msgBadRequestQuit = new MessageBadRequest(User.ADMIN, 
                                                                              author, 
                                                                              "Can't quit, not in game.");
                            sendToClient(msgBadRequestQuit, memberId);
                            break;
                        default:
                            throw new IllegalArgumentException("Role type unknown " + role);
                    }
                }
                sendMessagesAfterQuit(author, memberId);
                break;

            default:
                throw new IllegalArgumentException("Message type unknown " + type);
        }
    } 

    private void sendMessagesAfterQuit(User author, int memberId) {
        Message msgGetAllTablesRefresh = new MessageGetAllTables(User.ADMIN,
                User.EVERYBODY, dataTables);
        sendToAllClients(msgGetAllTablesRefresh);
        Message messageQuitGame = new MessageQuitGame(User.ADMIN, author, Role.NOT_IN_GAME);
        sendToClient(messageQuitGame, memberId);
    }

    private void createNewTable(Message message, User author, int memberId) {
        Table table = new Table(((MessageCreateTable) message).getNameTable(),
                getNextTableId(), author, "Stylo");
        // TODO : aller chercher le mot dans une BD
        
        // on la rajoute dans la liste de table
        tables.add(table);
        // on la renvoi au client
        Message messageCreateTable = new MessageCreateTable(User.ADMIN, author,
                Role.DRAWER,
                table.getName());
        sendToClient(messageCreateTable, memberId);
        
        // on met à jour les données de tables
        String namePartner = (table.getPartner() == null) ? "" : table.getPartner().getName();
        String statusTable = (table.isOpen() == true) ? "Open" : "Closed";
        
        dataTables.add(new DataTable(table.getName(), table.getId(),
                statusTable,
                table.getDrawer().getName(),
                namePartner));
        // on envoi toutes les données de tables à tout les clients
        Message messageGetAllTables = new MessageGetAllTables(User.ADMIN,
                User.EVERYBODY, dataTables);
        sendToAllClients(messageGetAllTables);
    }
    
    @Override
    protected void clientConnected(ConnectionToClient client) {
        super.clientConnected(client);
        int memberId = members.add(getNextClientId(), client.getName(), client.getInetAddress());
        client.setInfo(ID_MAPINFO, memberId);
        sendToClient(new MessageGetAllTables(User.ADMIN, null, dataTables), memberId);
    }
    
    void sendToClient(Message message, User recipient) {
        sendToClient(message, recipient.getId());
    }

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
    
//    private boolean isAlone(int clientId) {
//        for (Table table : tables) {
//            if (table.getDrawer() != null
//                    && table.getDrawer().getId() == clientId) {
//                
//                return table.getPartner() == null;
//            } else if (table.getPartner() != null 
//                    && table.getPartner().getId() == clientId) {
//                
//                return table.getDrawer() == null;
//            }
//        }
//        return false;
//    }
    
    private Table findTable(int clientId) {
        for (Table table : tables) {
            if ((table.getDrawer() != null && table.getDrawer().getId() == clientId) 
                    || (table.getPartner() != null && table.getPartner().getId() == clientId)) {
                    return table;
            }
        }
        return null;
    }
    
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

    private void updateDataTables(int tableId, Role role) {
        for(DataTable data : dataTables) {
            if (data.getId() == tableId) {
                if (role == Role.DRAWER) {
                    data.setDrawer("");
                } else if (role == Role.PARTNER) {
                    data.setPartner("");
                }
                break;
            }
        }
    }
//    private void destroyTable(int clientId) {
//        for (Table table : tables) {
//            if ((table.getDrawer() != null && table.getDrawer().getId() == clientId) 
//                    || (table.getPartner() != null && table.getPartner().getId() == clientId)) {
//                
//                for(DataTable data : dataTables) {
//                    if (data.getId() == table.getId()) {
//                        dataTables.remove(data);
//                        break;
//                    }
//                }
//                tables.remove(table);
//                break;
//            }
//        }
//    }
    
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
