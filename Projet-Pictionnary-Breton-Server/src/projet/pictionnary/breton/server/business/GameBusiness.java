package projet.pictionnary.breton.server.business;

import java.util.Collection;
import projet.pictionnary.breton.server.db.GameDB;
import projet.pictionnary.breton.server.dto.GameDto;
import projet.pictionnary.breton.server.exception.PictionnaryBusinessException;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;
import projet.pictionnary.breton.server.seldto.GameSel;

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
                throw new PictionnaryBusinessException("Game: on ne peut rendre "
                                      + "persistant un objet déjà persistant!");
            }
        } catch (PictionnaryDbException pDB) {
            throw new PictionnaryBusinessException(pDB.getMessage());
        }
    }  
    
    static void update(GameDto game) throws PictionnaryBusinessException {
        try {
            GameDB.updateDb(game);
        } catch (PictionnaryDbException pDb) {
            throw new PictionnaryBusinessException (pDb.getMessage());
        }
    }

    static GameDto getGameByDrawerId(int id) throws PictionnaryDbException {
        GameSel sel = new GameSel(0, id, 0, null, null, 0);
        Collection<GameDto> col = GameDB.getCollection(sel);
        if (col.size() == 1) {
            return col.iterator().next();
        } else {
            return null;
        }
    }
    
    static GameDto getGameByPartnerId(int id) throws PictionnaryDbException {
        GameSel sel = new GameSel(0, 0, id, null, null, 0);
        Collection<GameDto> col = GameDB.getCollection(sel);
        if (col.size() == 1) {
            return col.iterator().next();
        } else {
            return null;
        }
    }    
}
