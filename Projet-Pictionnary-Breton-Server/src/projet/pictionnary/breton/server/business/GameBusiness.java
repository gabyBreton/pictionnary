package projet.pictionnary.breton.server.business;

import java.util.Collection;
import java.util.List;
import projet.pictionnary.breton.server.db.GameDB;
import projet.pictionnary.breton.server.dto.GameDto;
import projet.pictionnary.breton.server.exception.PictionnaryBusinessException;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;
import projet.pictionnary.breton.server.seldto.GameSel;

/**
 * This class is used to interact with the Game table in the database.
 * 
 * @author Gabriel Breton - 43397
 */
public class GameBusiness {
    
    /**
     * Adds a game in the database.
     * 
     * @param game the game to add in the database.
     * @return the id of the game added. 
     * @throws PictionnaryBusinessException if the game added can not be added.
     */
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
    
    /**
     * Updates game information.
     * 
     * @param game the game information to update.
     * @throws PictionnaryBusinessException if the game is not available.
     */
    static void update(GameDto game) throws PictionnaryBusinessException {
        try {
            GameDB.updateDb(game);
        } catch (PictionnaryDbException pDb) {
            throw new PictionnaryBusinessException (pDb.getMessage());
        }
    }

    /**
     * Gets a game by its corresponding id.
     * 
     * @param id the id of the game to get.
     * @return the game.
     * @throws PictionnaryDbException if an errors occurs when interacting with 
     *         the database.
     */
    static GameDto getGameById(int id) throws PictionnaryDbException {
        GameSel sel = new GameSel(id, 0, 0, null, null, 0);
        Collection<GameDto> col = GameDB.getCollection(sel);
        if (col.size() == 1) {
            return col.iterator().next();
        } else {
            return null;
        }
    }

    /**
     * Gets a list of game informations for a drawer id.
     * 
     * @param id the id of the drawer.
     * @return the game informations or null if there is none.
     * @throws PictionnaryDbException if an errors occurs when interacting with 
     *         the database.
     */    
    static List<GameDto> getGameInfosDrawer(int id) throws PictionnaryDbException {
        GameSel sel = new GameSel(0, id, 0, null, null, 0);
        List<GameDto> list = GameDB.getCollection(sel);
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }    

    /**
     * Gets a list of game informations for a partner id.
     * 
     * @param id the id of the partner.
     * @return the game informations or null if there is none.
     * @throws PictionnaryDbException if an errors occurs when interacting with 
     *         the database.
     */ 
    static List<GameDto> getGameInfosPartner(int id) throws PictionnaryDbException {
        GameSel sel = new GameSel(0, 0, id, null, null, 0);
        List<GameDto> list = GameDB.getCollection(sel);
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }    
}
