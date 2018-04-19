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
                Message messageName = new MessageProfile(memberId, author.getName());
                sendToClient(messageName, memberId);
                break;
            case CREATE_TABLE:
                System.out.println("\nServerPictionnary.handleMessageFromClient()\n case CREATE_TABLE from : " + author.getName());                
                
                // on crée la table
                Table table = new Table(((MessageCreateTable) message).getNameTable(), 
                                        getNextTableId(), author);
                
                // on la rajoute dans la liste de table
                tables.add(table);
                System.out.println("\nServerPictionnary.handleMessageFromClient()\n case CREATE_TABLE : tables.size() ==  " + tables.size());
                // on la renvoi au client
                Message messageCreateTable = new MessageCreateTable(User.ADMIN, author, 
                                                        table,  
                                                        table.getName());
                sendToClient(messageCreateTable, memberId);
                
                // on met à jour les données de tables
                String namePartner = (table.getPartner() == null) ? "" : table.getPartner().getName();
                String statusTable = (table.isOpen() == true) ? "Open" : "Closed";
                
                // TODO : afficher correctement le statut et nom du drawer
                dataTables.add(new DataTable(table.getName(), table.getId(), 
                                             statusTable,
                                             table.getDrawer().getName(), 
                                             namePartner));
                System.out.println("\nServerPictionnary.handleMessageFromClient()\n case CREATE_TABLE : dataTables.size() == " + dataTables.size());
                // on envoi toutes les données de tables à tout les clients
                Message messageGetAllTables = new MessageGetAllTables(User.ADMIN, 
                                                        User.EVERYBODY, dataTables);
                sendToAllClients(messageGetAllTables);
                break;
            case GET_ALL_TABLES:
                System.out.println("\nServerPictionnary.handleMessageFromClient()\n case GET_ALL_TABLES from : " + author.getName());
                Message messageGetTables = new MessageGetAllTables(User.ADMIN, author, dataTables);
                sendToClient(messageGetTables, author);
                break;
            default:
                throw new IllegalArgumentException("Message type unknown " + type);
        }
//        setChanged();
//        notifyObservers(message);    
    } 
    
    @Override
    protected void clientConnected(ConnectionToClient client) {
        super.clientConnected(client);
        int memberId = members.add(getNextClientId(), client.getName(), client.getInetAddress());
        client.setInfo(ID_MAPINFO, memberId);
        System.out.println("\nServerPictionnary.clientConnected() " + memberId);
        sendToClient(new MessageGetAllTables(User.ADMIN, null, dataTables), memberId);
//        setChanged();
//        notifyObservers();
    }
    
    void sendToClient(Message message, User recipient) {
        sendToClient(message, recipient.getId());
    }

    void sendToClient(Message message, int clientId) {
        System.out.println("\nServerPictionnary.sendToClient " + clientId);
                        
        for (Thread clientThreadList : getClientConnections()) {
            int idClientThread = (Integer) ((ConnectionToClient) clientThreadList).getInfo(ID_MAPINFO);
            
            if (clientId == idClientThread) {
                try {
                    ((ConnectionToClient) clientThreadList).sendToClient(message);
                } catch (IOException ioe) {
                    System.out.println("\nServerPictionnary: Connection failed");
                }
                break;
            }
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
