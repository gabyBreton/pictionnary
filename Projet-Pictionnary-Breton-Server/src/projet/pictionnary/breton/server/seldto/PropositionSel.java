package projet.pictionnary.breton.server.seldto;

/**
 *
 * @author G43397
 */
public class PropositionSel {
    
    private final int id;
    private final String txt;
    private final int game;

    public PropositionSel(int id, String txt, int game) {
        this.id = id;
        this.txt = txt;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public String getTxt() {
        return txt;
    }

    public int getGame() {
        return game;
    }
}
