package projet.pictionnary.breton.server.business;

import java.util.Collection;
import projet.pictionnary.breton.server.db.PlayerDB;
import projet.pictionnary.breton.server.dto.PlayerDto;
import projet.pictionnary.breton.server.exception.PictionnaryBusinessException;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;
import projet.pictionnary.breton.server.seldto.PlayerSel;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class PlayerBusiness {

//    public static int getPlayerId(String pseudo) throws PictionnaryDbException {
//        return PlayerDB.getPlayerId(pseudo);
//        // Category.getCollection() version player 
//    }
    static PlayerDto getPlayerByLogin(String login) throws PictionnaryDbException {
        PlayerSel sel = new PlayerSel(0, login);
        Collection<PlayerDto> col = PlayerDB.getCollection(sel);
        if (col.size() == 1) {
            return col.iterator().next();
        } else {
            return null;
        }
    }

    static int add(PlayerDto player) throws PictionnaryBusinessException {
        try {
            if (!player.isPersistant()) {
                return PlayerDB.insertDb(player);
            } else {
                throw new PictionnaryBusinessException("Player: on ne peut rendre persistant un objet déjà persistant!");
            }
        } catch (PictionnaryDbException eDB) {
            throw new PictionnaryBusinessException(eDB.getMessage());
        }
    }
}
