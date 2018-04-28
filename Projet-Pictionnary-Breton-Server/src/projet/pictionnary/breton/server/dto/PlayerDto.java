package projet.pictionnary.breton.server.dto;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class PlayerDto extends EntityDto<Integer> {
    
    private String login;

    public PlayerDto(int id, String login) {
        this.id = id;
        this.login = login;
    }
    
    public PlayerDto(String login) {
        this.login = login;
    }
    
    public String getLogin() {
        return login;
    }

    @Override
    public Integer getId() {
        return id;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
}
