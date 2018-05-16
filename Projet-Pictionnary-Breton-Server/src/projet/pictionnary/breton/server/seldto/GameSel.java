package projet.pictionnary.breton.server.seldto;

import java.sql.Timestamp;

/**
 * Class used to select game informations to get in the database.
 * 
 * @author Gabriel Breton - 43397
 */
public class GameSel {
    
    private final int id;
    private final int drawer;
    private final int partner;
    private final Timestamp startTime;
    private final Timestamp endTime;
    private final int stopPlayer;
    private final int word;
    private final String table;

    /**
     * Constructs a new GameSel.
     * 
     * @param id the id of the game.
     * @param drawer the id of the drawer.
     * @param partner the id of the partner.
     * @param startTime the start time of the game. 
     * @param endTime the end time of the game.
     * @param stopPlayer the id of the player that stopped the game.
     */
    public GameSel(int id, int drawer, int partner, Timestamp startTime, 
                    Timestamp endTime, int stopPlayer, int word, String table) {
        this.id = id;
        this.drawer = drawer;
        this.partner = partner;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stopPlayer = stopPlayer;
        this.word = word;
        this.table = table;
    }

    /**
     * Gives the id of the game to select.
     * 
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gives the drawer id.
     * 
     * @return the id of the drawer.
     */
    public int getDrawer() {
        return drawer;
    }

    /**
     * Gives the partner id.
     * 
     * @return the id of the partner.
     */
    public int getPartner() {
        return partner;
    }

    /**
     * Gives the start time of the game.
     * 
     * @return the start time of the game.
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * Gives the end time of the game.
     * 
     * @return the end time of the game.
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * Gives the id of the player that stopped the game.
     * 
     * @return the id of player that stopped the game.
     */
    public int getStopPlayer() {
        return stopPlayer;
    }

    public int getWord() {
        return word;
    }

    public String getTable() {
        return table;
    }
}
