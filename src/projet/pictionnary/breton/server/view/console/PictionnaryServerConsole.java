package projet.pictionnary.breton.server.view.console;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import projet.pictionnary.breton.server.model.PictionnaryServer;
import projet.pictionnary.breton.server.users.User;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class PictionnaryServerConsole implements Observer {

    public static void main(String[] args) {
        try {
            PictionnaryServer model = new PictionnaryServer();
            PictionnaryServerConsole console = new PictionnaryServerConsole(model);
            model.addObserver(console);
            System.out.println("Server started");
            System.out.println("");
        } catch (IOException ex) {
            Logger.getLogger(PictionnaryServerConsole.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }
    
    private final PictionnaryServer model;

    /**
     * Constructs the console view. Subscribes to the instant messaging server.
     *
     * @param model instant messaging server.
     */
    public PictionnaryServerConsole(PictionnaryServer model) {
        this.model = model;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        updateUser();
        if (arg != null) {
           // Message message = (Message) arg;
            //updateMessage(message);
        }

    }

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
