package projet.pictionnary.breton.server.business;

import projet.pictionnary.breton.server.db.GameDB;
import projet.pictionnary.breton.server.dto.GameDto;
import projet.pictionnary.breton.server.exception.PictionnaryBusinessException;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class GameBusiness {
    static int add(GameDto game) throws PictionnaryBusinessException {
        try {
            if (!game.isPersistant()) {
                return GameDB.insertDb(game);
            } else {
                throw new PictionnaryBusinessException("Game: on ne peut rendre persistant un objet déjà persistant!");
            }
        } catch (PictionnaryDbException pDB) {
            throw new PictionnaryBusinessException(pDB.getMessage());
        }
    }    
}
