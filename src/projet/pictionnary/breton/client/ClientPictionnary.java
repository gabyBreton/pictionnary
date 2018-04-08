package projet.pictionnary.breton.client;

import java.io.IOException;
import projet.pictionnary.breton.model.Message;
import projet.pictionnary.breton.model.MessageProfile;
import projet.pictionnary.breton.model.MessageTable;
import projet.pictionnary.breton.model.Type;
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
        updateName(name);
    //    createTable(); la création de table ne doit pas se faire ici. -> à tester lorsque j'aurai le paneau de sélection de table
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
                System.out.println("ClientPictionnary.handleMessageFromServer():\n case CREATE_TABLE : " + ((MessageTable) msg).getNameTable());
//            case MAIL_TO:
//                showMessage(message);
//                break;
//            case MEMBERS:
//                Members members = (Members) message.getContent();
//                updateMembers(members);
//                break;
            default:
                throw new IllegalArgumentException("Message type unknown " + type);
        }    }
    
    void setMySelf(User user) {
        this.mySelf = user;
    }
    /**
     * Quits the client and closes all aspects of the connection to the server.
     *
     * @throws IOException if an I/O error occurs when closing.
     */
    public void quit() throws IOException {
        closeConnection();
    }
    
    private void createTable() {
        try {
            sendToServer(new MessageTable(mySelf, User.ADMIN, null, "table_test"));
        } catch (IOException ioe) {
            System.out.println("IOException in ClientPictionnary.createTable()");
        }
    }
    
    private void updateName(String name) throws IOException {
        sendToServer(new MessageProfile(0, name));
    }
}
