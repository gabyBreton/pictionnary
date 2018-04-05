package projet.pictionnary.breton.client;

import java.io.IOException;
import projet.pictionnary.breton.model.MessageProfile;
import projet.pictionnary.breton.server.users.User;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class ClientPictionnary extends AbstractClient {

    private User mySelf;
    
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
        openConnection();
    }
    
    @Override
    protected void handleMessageFromServer(Object msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Quits the client and closes all aspects of the connection to the server.
     *
     * @throws IOException if an I/O error occurs when closing.
     */
    public void quit() throws IOException {
        closeConnection();
    }
    
    private void updateName(String name) throws IOException {
        sendToServer(new MessageProfile(0, name));
    }
}
