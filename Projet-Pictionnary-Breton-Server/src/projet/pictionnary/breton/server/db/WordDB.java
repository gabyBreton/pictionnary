package projet.pictionnary.breton.server.db;

import java.util.ArrayList;
import java.util.List;
import projet.pictionnary.breton.server.dto.PlayerDto;
import projet.pictionnary.breton.server.dto.WordDto;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;
import projet.pictionnary.breton.server.seldto.PlayerSel;
import projet.pictionnary.breton.server.seldto.WordSel;

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
    
    public static List<WordDto> getCollection(WordSel sel) 
                                                 throws PictionnaryDbException {
        List<WordDto> al = new ArrayList<>();
        try {
            String query = "Select wid, wtxt FROM Word ";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            String where = "";
            if (sel.getId() != 0) {
                where = where + " wid = ? ";
            }
            if (sel.getTxt() != null) {
                if (!where.equals("")) {
                    where = where + " AND ";
                }
                where = where + " wtxt = ? ";
            }
            
            if (where.length() != 0) {
                where = " where " + where + " order by wid";
                query = query + where;
                stmt = connexion.prepareStatement(query);
                int i = 1;
                if (sel.getId() != 0) {
                    stmt.setInt(i, sel.getId());
                    i++;

                }
                if (sel.getTxt() != null) {
                    stmt.setString(i, sel.getTxt());
                    i++;
                }

            } else {
                query = query + " Order by wtxt";
                stmt = connexion.prepareStatement(query);
            }
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                al.add(new WordDto(rs.getInt("wid"), rs.getString("wtxt")));
            }
        } catch (java.sql.SQLException pSQL) {
            throw new PictionnaryDbException("Instanciation de Word impossible:\nSQLException: " + pSQL.getMessage());
        }
        return al;
    }
}
