package projet.pictionnary.breton.model;

import java.io.Serializable;
import projet.pictionnary.breton.server.users.User;

/**
 * This class is used to represent a game and its users of Pictionnary.
 * 
 * @author Gabriel Breton - 43397
 */
public class Table implements Serializable {
    
    private final String name;
    private final int id;
    private User drawer;
    private User partner;   
    private final String word;
    private int playerCount;
    
    /**
     * Constructs a new <code> Table </code>. A table is used by one or two 
     * players and contains the word to draw.
     * 
     * @param name the name of the table.
     * @param id the id the table.
     * @param drawer the drawer of the game.
     * @param word the word to draw.
     */
    public Table(String name, int id, User drawer, String word) {
        this.name = name;
        this.id = id;
        this.drawer = drawer;
        partner = null;
        this.word = word;
        playerCount = 1;
    }
    
    /**
     * Gives the name of the table.
     * 
     * @return the name of the table.
     */
    public String getName() {
        return name;
    }

    /**
     * Gives the id of the table.
     * 
     * @return the id of the table.
     */
    public int getId() {
        return id;
    }

    /**
     * Says if the table is open or not.
     * 
     * @return true if the table is open, or else false.
     */
    public boolean isOpen() {
        return playerCount == 1;
    }

    /**
     * Gives the drawer of the table.
     * 
     * @return the drawer of the table.
     */
    public User getDrawer() {
        return drawer;
    }

    /**
     * Gives the partner of the table.
     * 
     * @return the partner of the table.
     */
    public User getPartner() {
        return partner;
    }

    /**
     * Gives the word to draw.
     * 
     * @return the word to draw.
     */
    public String getWord() {
        return word;
    }
    
    /**
     * Adds a partner to the table.
     * 
     * @param partner the partner to add.
     */
    public void addPartner(User partner) {
        this.partner = partner;
        playerCount++;
    }
    
    /**
     * Removes the drawer of the table.
     */
    public void removeDrawer() {
        drawer = null;
        playerCount--;
    }
    
    /**
     * Removes the partner of the table.
     */
    public void removePartner() {
        partner = null;
        playerCount--;
    }
    
    /**
     * Gives the number of players in the table.
     * 
     * @return the number of players in the table.
     */
    public int getPlayerCount() {
        return playerCount;
    }
}
