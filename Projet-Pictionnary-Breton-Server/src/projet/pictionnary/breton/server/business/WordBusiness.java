package projet.pictionnary.breton.server.business;

import java.util.List;
import projet.pictionnary.breton.server.db.WordDB;
import projet.pictionnary.breton.server.dto.WordDto;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class WordBusiness {
    
    
    public static List<WordDto> getAllWords() throws PictionnaryDbException {
        return WordDB.getAllWords();
    }
}
