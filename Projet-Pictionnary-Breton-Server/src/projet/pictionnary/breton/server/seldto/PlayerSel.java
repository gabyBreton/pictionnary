package projet.pictionnary.breton.server.seldto;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class PlayerSel {
    
    private int id;
    private String login;
    
    public PlayerSel(int id, String login) {
        this.id = id;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
