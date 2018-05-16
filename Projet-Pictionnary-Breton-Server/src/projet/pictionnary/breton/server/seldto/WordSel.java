package projet.pictionnary.breton.server.seldto;

/**
 *
 * @author G43397
 */
public class WordSel {
    private int id;
    private String txt;

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
