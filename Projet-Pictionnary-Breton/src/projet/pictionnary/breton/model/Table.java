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
    private final String word;
    private int playerCount;
    
    public Table(String name, int id, User drawer, String word) {
        this.name = name;
        this.id = id;
        open = true;
        this.drawer = drawer;
        partner = null;
        this.word = word;
        playerCount = 1;
    }
    
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isOpen() {
        return open;
    }

    public User getDrawer() {
        return drawer;
    }

    public User getPartner() {
        return partner;
    }

    public String getWord() {
        return word;
    }
    
    public void addPartner(User partner) {
        this.partner = partner;
        playerCount++;
    }
    
    public void removeDrawer() {
        drawer = null;
        playerCount--;
    }
    
    public void removePartner() {
        partner = null;
        playerCount--;
    }
    
    public int getPlayerCount() {
        return playerCount;
    }
}
