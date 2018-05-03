package projet.pictionnary.breton.common.model;

import java.io.Serializable;

/**
 * This class is used to share and transfer the essentials informations of a 
 * table.
 * 
 * @author Gabriel Breton - 43397
 */
public class DataTable implements Serializable {

    private final String name;
    private final int id;
    private String status;
    private String drawer;
    private String partner;
    private String gameStatus;

    /**
     * Constructs a new <code> DataTable </code>.
     * 
     * @param name the name of the table.
     * @param id the id of the table.
     * @param status the status of the table.
     * @param drawer the name of the drawer.
     * @param partner the name of the partner.
     * @param gameStatus the game status of the table.
     */
    public DataTable(String name, int id, String status, String drawer,
                     String partner, String gameStatus) {
        this.name = name;
        this.id = id;
        this.status = status;
        this.drawer = drawer;
        this.partner = partner;
        this.gameStatus = gameStatus;
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
     * Gives the status of the table.
     * 
     * @return the status of the table.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gives the name of the drawer.
     * 
     * @return the name of the drawer or an empty String if there is no drawer.
     */
    public String getDrawer() {
        return drawer;
    }

    /**
     * Gives the name of the partner.
     * 
     * @return the name of the partner or an empty String if there is no 
     * partner.
     */
    public String getPartner() {
        return partner;
    }

    // TODO : empecher un nom vide lors de la connexion du client.
    /**
     * Sets the name of the drawer.
     * 
     * @param drawer the name of the drawer.
     */
    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    /**
     * Sets the name of the partner.
     * 
     * @param partner the name of the partner.
     */
    public void setPartner(String partner) {
        this.partner = partner;
    }

    /**
     * Set the status of the table.
     * 
     * @param status the status as String, Open or Closed.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gives the game status of the table.
     * 
     * @return the game status of the table.
     */
    public String getGameStatus() {
        return gameStatus;
    }

    /**
     * Sets the game status of the table.
     * 
     * @param gameStatus the game status of the table.
     */
    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
}
