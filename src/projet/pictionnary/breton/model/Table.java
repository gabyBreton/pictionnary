package projet.pictionnary.breton.model;

import java.io.Serializable;
import projet.pictionnary.breton.server.users.User;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class Table implements Serializable {
    
    private String name;
    private int id;
    private boolean open;
    private User drawer;
    private User partner;    
    
    public Table(String name, int id, User drawer) {
        this.name = name;
        this.id = id;
        open = true;
        this.drawer = drawer;
    }
    
    public String getName() {
        return name;
    }
}
