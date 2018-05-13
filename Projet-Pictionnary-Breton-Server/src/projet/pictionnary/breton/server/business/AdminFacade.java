package projet.pictionnary.breton.server.business;

import java.util.List;
import projet.pictionnary.breton.server.db.DBManager;
import projet.pictionnary.breton.server.dto.GameDto;
import projet.pictionnary.breton.server.dto.PlayerDto;
import projet.pictionnary.breton.server.dto.WordDto;
import projet.pictionnary.breton.server.exception.PictionnaryBusinessException;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;

/**
 * This class is used as a facade for the admin of the server, to interact with
 * the database.
 * 
 * @author Gabriel Breton - 43397
 */
public class AdminFacade {
   
    /**
     * Gives the words stored in the database.
     * 
     * @return the words stored in the database.
     * @throws PictionnaryBusinessException if the list of words is not available.
     */
    public static List<WordDto> getAllWords() throws PictionnaryBusinessException {
        try {
            DBManager.startTransaction();
            List<WordDto> words = WordBusiness.getAllWords();
            DBManager.validateTransaction();
            return words;
        } catch (PictionnaryDbException pdb) {
            String msg = pdb.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (PictionnaryDbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PictionnaryBusinessException("Words list unavailable \n" + msg);
            }
        }
    }
    
    /**
     * Finds a player in the database with the login specified in parameters.
     * 
     * @param login the login of the player to find.
     * @return a player dto corresponding to the player.
     * @throws PictionnaryBusinessException if the player is not find in the 
     *         database.
     */
    public static PlayerDto getPlayerByLogin(String login) throws PictionnaryBusinessException {
        try {
            DBManager.startTransaction();
            PlayerDto playerDto = PlayerBusiness.getPlayerByLogin(login);
            DBManager.validateTransaction();
            return playerDto;
        } catch (PictionnaryDbException pdb) {
            String msg = pdb.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (PictionnaryDbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PictionnaryBusinessException("Player id unavailable \n" + msg);
            }
        }
    }
    
    /**
     * Adds a player in the database.
     * 
     * @param player the player to add.
     * @return the id of the added player.
     * @throws PictionnaryBusinessException if it is not possible to add the 
     *         player.
     */
    public static int addPlayer(PlayerDto player) throws PictionnaryBusinessException {
        try {
            DBManager.startTransaction();
            int i = PlayerBusiness.add(player);
            DBManager.validateTransaction();
            return i;
        } catch (PictionnaryDbException pDB) {
            String msg = pDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (PictionnaryDbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PictionnaryBusinessException("Not possible to add the player \n" + msg);
            }
        }
    }
    
    /**
     * Adds game informations in the database.
     * 
     * @param game the informations to add.
     * @return the id of the added game.
     * @throws PictionnaryBusinessException if it is not possible to add the 
     *         game.
     */
    public static int addGame(GameDto game) throws PictionnaryBusinessException {
        try {
            DBManager.startTransaction();
            int i = GameBusiness.add(game);
            DBManager.validateTransaction();
            return i;
        } catch (PictionnaryDbException pDB) {
            String msg = pDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (PictionnaryDbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PictionnaryBusinessException("Not possible to add the game \n" + msg);
            }
        }
    }
    
    /**
     * Updates the informations of a game stored in the database.
     * 
     * @param game the game informations.
     * @throws PictionnaryBusinessException if it is not possible to update the 
     *         informations.
     */
    public static void updateGame(GameDto game) throws PictionnaryBusinessException {
        try {
            DBManager.startTransaction();
            GameBusiness.update(game);
            DBManager.validateTransaction();
        } catch (PictionnaryDbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (PictionnaryDbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PictionnaryBusinessException("Mise à jour de Game impossible! \n" + msg);
            }
        }
    }
    
    /**
     * Gives the game informations corresponding to the id of a drawer.
     * 
     * @param id the id of the drawer.
     * @return the game information corresponding to the id of a drawer.
     * @throws PictionnaryBusinessException if the game is not available.
     */
    public static GameDto getGameByDrawerId(int id) throws PictionnaryBusinessException {
        try {
            DBManager.startTransaction();
            GameDto gameDto = GameBusiness.getGameByDrawerId(id);
            DBManager.validateTransaction();
            return gameDto;
        } catch (PictionnaryDbException pdb) {
            String msg = pdb.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (PictionnaryDbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PictionnaryBusinessException("Game unavailable \n" + msg);
            }
        }
    }
    
    /**
     * Gives the game informations corresponding to the id of a partner.
     * 
     * @param id the id of the partner.
     * @return the game information corresponding to the id of a partner.
     * @throws PictionnaryBusinessException if the game is not available.
     */
    public static GameDto getGameByPartnerId(int id) throws PictionnaryBusinessException {
        try {
            DBManager.startTransaction();
            GameDto gameDto = GameBusiness.getGameByPartnerId(id);
            DBManager.validateTransaction();
            return gameDto;
        } catch (PictionnaryDbException pdb) {
            String msg = pdb.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (PictionnaryDbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PictionnaryBusinessException("Game unavailable \n" + msg);
            }
        }
    }
}
