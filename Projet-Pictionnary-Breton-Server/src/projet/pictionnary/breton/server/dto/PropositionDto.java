package projet.pictionnary.breton.server.dto;


/**
 *
 * @author G43397
 */
public class PropositionDto extends EntityDto<Integer> {

    private String txt;
    private int game;

    public PropositionDto(String txt, int game) {
        this.txt = txt;
        this.game = game;
    }

    public PropositionDto(int id, String txt, int game) {
        this(txt, game);
        this.id = id;
    }
    
    public String getTxt() {
        return txt;
    }

    public int getGame() {
        return game;
    }
}
