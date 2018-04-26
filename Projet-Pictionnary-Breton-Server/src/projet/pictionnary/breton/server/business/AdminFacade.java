package projet.pictionnary.breton.server.business;

import java.util.List;
import projet.pictionnary.breton.server.db.DBManager;
import projet.pictionnary.breton.server.dto.WordDto;
import projet.pictionnary.breton.server.exception.PictionnaryBusinessException;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class AdminFacade {
    
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
}
