package projet.pictionnary.breton.server;

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

/**
 *
 * @author Gabriel Breton - 43397
 */
public class ServerPictionnary extends AbstractServer {

    private static final int PORT = 12_345;
    static final String ID_MAPINFO = "ID";

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
        
    private int clientId;
    private int tableId;
    private final Members members;
    private List<Table> tables;
    

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
                System.out.println("ServerPictionnary.handleMessageFromClient()\n case CREATE_TABLE from : " + author.getName());                
                Table table = new Table(((MessageCreateTable) message).getNameTable(), 
                                        getNextTableId(), author);
                
                Message messageCreateTable = new MessageCreateTable(User.ADMIN, author, 
                                                        table,  
                                                        table.getName());
                sendToClient(messageCreateTable, memberId);
                break;
            case GET_TABLES:
                Message messageGetTables = new MessageGetTables(User.ADMIN, author, tables);
                sendToClient(messageGetTables, author);
                break;
            default:
                throw new IllegalArgumentException("Message type unknown " + type);
        }
        setChanged();
        notifyObservers(message);    
    } 
    
    @Override
    protected void clientConnected(ConnectionToClient client) {
        super.clientConnected(client);
        int memberId = members.add(getNextClientId(), client.getName(), client.getInetAddress());
        client.setInfo(ID_MAPINFO, memberId);
        sendToClient(new MessageGetTables(User.ADMIN, null, tables), memberId);
        setChanged();
        notifyObservers();
    }
    
    void sendToClient(Message message, User recipient) {
        sendToClient(message, recipient.getId());
    }

    void sendToClient(Message message, int clientId) {
        User user = members.getUser(clientId);    
                        
        for (Thread clientThreadList1 : getClientConnections()) {
            String adressClient = ((ConnectionToClient) clientThreadList1)
                                        .getInetAddress()
                                        .getHostAddress();
            
            if (user.getAddress().equals(adressClient)) {
                try {
                    ((ConnectionToClient) clientThreadList1).sendToClient(message);
                } catch (IOException ioe) {
                    System.out.println("ServerPictionnary: Connection failed");
                }
                break;
            }
        }
    }
}
