package projet.pictionnary.breton.server.seldto;

import java.sql.Timestamp;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class GameSel {
    
    private int id;
    private final int drawer;
    private final int partner;
    private final Timestamp startTime;
    private final Timestamp endTime;
    private final int stopPlayer;

    public GameSel(int id, int drawer, int partner, Timestamp startTime, 
                    Timestamp endTime, int stopPlayer) {
        this.id = id;
        this.drawer = drawer;
        this.partner = partner;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stopPlayer = stopPlayer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
