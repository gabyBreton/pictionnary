package projet.pictionnary.breton.server.db;

import projet.pictionnary.breton.server.dto.GameDto;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class GameDB {

    public static int insertDb(GameDto game) throws PictionnaryDbException {
        try {
            int num = SequenceDB.getNextNum(SequenceDB.GAME);
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement insert;
            insert =
                    connexion.prepareStatement("Insert into Game(gid, gdrawer, "
                                        + "gpartner, gstarttime) values(?, ?, ?, ?)");
            insert.setInt(1, num);
            insert.setInt(2, game.getDrawer());
            insert.setInt(3, game.getPartner());
            insert.setTimestamp(4, game.getStartTime());
            insert.execute();
            return num;
        } catch (Exception ex) {
            throw new PictionnaryDbException("Game: ajout impossible\r" + ex.getMessage());
        }
    }
    
}
