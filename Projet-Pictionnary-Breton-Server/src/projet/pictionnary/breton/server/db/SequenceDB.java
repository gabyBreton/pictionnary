package projet.pictionnary.breton.server.db;

import projet.pictionnary.breton.server.exception.PictionnaryDbException;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class SequenceDB {

    static final String GAME = "Game";
    static final String PLAYER = "Player";
    static final String WORD = "Word";

    static synchronized int getNextNum(String sequence) throws PictionnaryDbException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            String query = "Update SEQUENCES set sValue = sValue+1 where sid='" + sequence + "'";
            java.sql.PreparedStatement update = connexion.prepareStatement(query);
            update.execute();
            java.sql.Statement stmt = connexion.createStatement();
            query = "Select sValue FROM Sequences where sid='" + sequence + "'";
            java.sql.ResultSet rs = stmt.executeQuery(query);
            int nvId;
            if (rs.next()) {
                nvId = rs.getInt("sValue");
                return nvId;
            } else {
                throw new PictionnaryDbException("Nouveau n° de séquence inaccessible!");
            }
        } catch (java.sql.SQLException pSQL) {
            throw new PictionnaryDbException("Nouveau n° de séquence inaccessible!\n" + pSQL.getMessage());
        }
    }
}
