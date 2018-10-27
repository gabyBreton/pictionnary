package projet.pictionnary.breton.server.seldto;

/**
 * Class used to select player informations to get in the database.
 * 
 * @author Gabriel Breton - 43397
 */
public class PlayerSel {
    
    private int id;
    private String login;
    
    /**
     * Constructs a new PlayerSel.
     * 
     * @param id the id of the player.
     * @param login the login of the player.
     */
    public PlayerSel(int id, String login) {
        this.id = id;
        this.login = login;
    }

    /**
     * Gives the id of the player.
     * 
     * @return the id of the player.
     */
    public int getId() {
        return id;
    }

    /**
     * Gives the login of the player.
     * 
     * @return the login of the player.
     */
    public String getLogin() {
        return login;
    }
}
