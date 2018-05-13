package projet.pictionnary.breton.server.db;

import java.util.ArrayList;
import java.util.List;
import projet.pictionnary.breton.server.dto.WordDto;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;

/**
 * Class used to interact with the Word table in the database.
 * 
 * @author Gabriel Breton - 43397
 */
public class WordDB {

    /**
     * Gives the all the words in the Word table.
     * 
     * @return the list of words.
     * @throws PictionnaryDbException if it is not possible to instanciate the
     *         Word table.
     */
    public static List<WordDto> getAllWords() throws PictionnaryDbException {
        List<WordDto> words = new ArrayList<>();
        try {
            String query = "Select wtxt  FROM Word ";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
           
            stmt = connexion.prepareStatement(query);
            java.sql.ResultSet rs = stmt.executeQuery();
            //java.sql.ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                words.add(new WordDto(rs.getString("wtxt")));
            }
        } catch (java.sql.SQLException eSQL) {
            throw new PictionnaryDbException("Instanciation of Word impossible:\rSQLException: " + eSQL.getMessage());
        }

        return words;
    }
}
