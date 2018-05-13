package projet.pictionnary.breton.server.dto;

import java.sql.Timestamp;

/**
 * Class used a dto for the Game table.
 * 
 * @author Gabriel Breton - 43397
 */
public class GameDto extends EntityDto<Integer> {
    
    private final int drawer;
    private final int partner;
    private final Timestamp startTime;
    private Timestamp endTime;
    private int stopPlayer;

    /**
     * Constructs a new GameDto.
     * 
     * @param drawer the id of the drawer.
     * @param partner the id of the partner.
     * @param startTime the start time of the game.
     * @param endTime the end time of the game. Can be null.
     * @param stopPlayer the id of the player that have stop the game. 
     *                   Can be null.
     */
    public GameDto(int drawer, int partner, Timestamp startTime, 
                   Timestamp endTime, int stopPlayer) {
        this.drawer = drawer;
        this.partner = partner;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stopPlayer = stopPlayer;
    }
    
    /**
     * Constructs a new GameDto.
     * 
     * @param id the id of the game to create.
     * @param drawer the id of the drawer.
     * @param partner the id of the partner.
     * @param startTime the start time of the game.
     * @param endTime the end time of the game. Can be null.
     * @param stopPlayer the id of the player that have stop the game. 
     *                   Can be null.
     */
    public GameDto (int id, int drawer, int partner, Timestamp startTime, 
                   Timestamp endTime, int stopPlayer) {
        this(drawer, partner, startTime, endTime, stopPlayer);
        this.id = id;
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
     * Gives the end time of the game. Can be null.
     * 
     * @return the end time of the game.
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * Gives the id of the player that have stopped the game. Can be null.
     * 
     * @return the id of the player.
     */
    public int getStopPlayer() {
        return stopPlayer;
    }
}
