package projet.pictionnary.breton.server.seldto;

/**
 *
 * @author G43397
 */
public class WordSel {
    private final int id;
    private final String txt;

    public WordSel(int id, String txt) {
        this.id = id;
        this.txt = txt;
    }

    public int getId() {
        return id;
    }

    public String getTxt() {
        return txt;
    }
}
