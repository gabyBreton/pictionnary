package projet.pictionnary.breton.server.business;

import java.util.Collection;
import java.util.List;
import projet.pictionnary.breton.server.db.GameDB;
import projet.pictionnary.breton.server.db.WordDB;
import projet.pictionnary.breton.server.dto.GameDto;
import projet.pictionnary.breton.server.dto.WordDto;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;
import projet.pictionnary.breton.server.seldto.GameSel;
import projet.pictionnary.breton.server.seldto.WordSel;

/**
 * This class is used to interact with the Word table in the database.
 * 
 * @author Gabriel Breton - 43397
 */
public class WordBusiness {
    
    /**
     * Gives all the words stored in the database.
     * 
     * @return the words stored in the database.
     * @throws PictionnaryDbException if an error occurs while interacting with
     *         the database.
     */
    public static List<WordDto> getAllWords() throws PictionnaryDbException {
        return WordDB.getAllWords();
    }
    
    static WordDto getWord(String word) throws PictionnaryDbException {
        WordSel sel = new WordSel(0, word);
        Collection<WordDto> col = WordDB.getCollection(sel);
        if (col.size() == 1) {
            return col.iterator().next();
        } else {
            return null;
        }
    }
}
