package projet.pictionnary.breton.server.dto;

/**
 * Class used a dto for the Player table.
 * 
 * @author Gabriel Breton - 43397
 */
public class PlayerDto extends EntityDto<Integer> {
    
    private String login;

    /**
     * Constructs a new PlayerDto.
     * 
     * @param id the id of the player.
     * @param login the login of the player.
     */
    public PlayerDto(int id, String login) {
        this.id = id;
        this.login = login;
    }
    
    /**
     * Constructs a new PlayerDto.
     * 
     * @param login the login of the player.
     */
    public PlayerDto(String login) {
        this.login = login;
    }
    
    /**
     * Gives the login of the player.
     * 
     * @return the login of the player.
     */
    public String getLogin() {
        return login;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
