package projet.pictionnary.breton.server.business;

import java.util.Collection;
import projet.pictionnary.breton.server.db.PlayerDB;
import projet.pictionnary.breton.server.dto.PlayerDto;
import projet.pictionnary.breton.server.exception.PictionnaryBusinessException;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;
import projet.pictionnary.breton.server.seldto.PlayerSel;

/**
 * This class is used to interact with the Player table in the database.
 * 
 * @author Gabriel Breton - 43397
 */
public class PlayerBusiness {

    /**
     * Gets a player specified by a login.
     * 
     * @param login the login of the player to get.
     * @return the player to get.
     * @throws PictionnaryDbException in case if an error occurs while 
     *         interacting with the database.
     */
    static PlayerDto getPlayerByLogin(String login) throws PictionnaryDbException {
        PlayerSel sel = new PlayerSel(0, login);
        Collection<PlayerDto> col = PlayerDB.getCollection(sel);
        if (col.size() == 1) {
            return col.iterator().next();
        } else {
            return null;
        }
    }

    /**
     * Adds a player in the database.
     * 
     * @param player the player to add.
     * @return the id of the player added.
     * @throws PictionnaryBusinessException if the player added is already 
     *         persistent.
     */
    static int add(PlayerDto player) throws PictionnaryBusinessException {
        try {
            if (!player.isPersistant()) {
                return PlayerDB.insertDb(player);
            } else {
                throw new PictionnaryBusinessException("Player: on ne peut "
                               + "rendre persistant un objet déjà persistant!");
            }
        } catch (PictionnaryDbException pDB) {
            throw new PictionnaryBusinessException(pDB.getMessage());
        }
    }
    
    static int getNextPlayerId() throws PictionnaryDbException {
        return PlayerDB.getNextPlayerId();
    }
}
