package projet.pictionnary.breton.server.db;

import projet.pictionnary.breton.server.dto.PropositionDto;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;

/**
 *
 * @author G43397
 */
public class PropositionDB {
    public static int insertDb(PropositionDto proposition) throws PictionnaryDbException {
        try {
            int num = SequenceDB.getNextNum(SequenceDB.PROPOSITION);
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement insert;
            insert = 
                    connexion.prepareStatement("Insert into Proposition(prid, prtxt, "
                                               + "prgame) "
                                               + "values(?, ?, ?)");
            insert.setInt(1, num);
            insert.setString(2, proposition.getTxt());
            insert.setInt(3, proposition.getGame());
            insert.execute();
            return num;
        } catch (Exception ex) {
            throw new PictionnaryDbException("Game: ajout impossible\r" + ex.getMessage());
        }
    }
}
