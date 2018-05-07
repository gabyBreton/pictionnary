package projet.pictionnary.breton.server.dto;

import java.sql.Timestamp;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class GameDto extends EntityDto<Integer> {
    
    private final int drawer;
    private final int partner;
    private final Timestamp startTime;
    private Timestamp endTime;
    private int stopPlayer;

    public GameDto(int drawer, int partner, Timestamp startTime, 
                    Timestamp endTime, int stopPlayer) {
        this.drawer = drawer;
        this.partner = partner;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stopPlayer = stopPlayer;
    }
    
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    
    public void setStopPlayer(int stopPlayer) {
        this.stopPlayer = stopPlayer;
    }

    public int getDrawer() {
        return drawer;
    }

    public int getPartner() {
        return partner;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public int getStopPlayer() {
        return stopPlayer;
    }
}
