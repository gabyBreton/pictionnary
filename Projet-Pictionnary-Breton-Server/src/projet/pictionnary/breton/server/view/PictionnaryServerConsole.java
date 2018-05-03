package projet.pictionnary.breton.server.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import projet.pictionnary.breton.server.ServerPictionnary;
import projet.pictionnary.breton.server.exception.PictionnaryBusinessException;
import projet.pictionnary.breton.common.users.User;
import projet.pictionnary.breton.common.util.Observer;

/**
 * This class is used as a console to manage and get informations from the 
 * server running.
 * 
 * @author Gabriel Breton - 43397
 */
public class PictionnaryServerConsole implements Observer {

    public static void main(String[] args) {
        try {
            ServerPictionnary model = new ServerPictionnary();
            PictionnaryServerConsole console = new PictionnaryServerConsole(model);
            model.addObserver(console);
            System.out.println("Server started");
            System.out.println("");
        } catch (IOException ex) {
            Logger.getLogger(PictionnaryServerConsole.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (PictionnaryBusinessException pdb) {
            System.out.println(pdb.getMessage());
        }
    }
    
    private final ServerPictionnary model;

    /**
     * Constructs the console view. Subscribes to the instant messaging server.
     *
     * @param model instant messaging server.
     */
    public PictionnaryServerConsole(ServerPictionnary model) {
        this.model = model;
    }
    
    @Override
    public void update(Object arg) {
        updateUser();
        if (arg != null) {
           // TODO : afficher nouvelles informations lors de changements importants.
           // Message message = (Message) arg;
           //updateMessage(message);
        }

    }

    /**
     * Prints the list of the connected users.
     */
    private void updateUser() {
        System.out.println("");
        StringBuilder builder = new StringBuilder();
        builder.append("\n---- ---- Liste Users ---- ----\n");
        builder.append("Nombre d'utilisateurs connectes : ")
                .append(model.getNbConnected()).append("\n");
        builder.append("ID").append("\t");
        builder.append("IP").append("\t\t");
        builder.append("NAME").append("\n");
        for (User member : model.getMembers()) {
            builder.append(member.getId()).append("\t");
            builder.append(member.getAddress()).append("\t");
            builder.append(member.getName()).append("\n");
        }
        System.out.println(builder);
        System.out.println("");
    }
}
