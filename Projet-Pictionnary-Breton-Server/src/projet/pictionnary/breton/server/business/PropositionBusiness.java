package projet.pictionnary.breton.server.business;

import projet.pictionnary.breton.server.db.PropositionDB;
import projet.pictionnary.breton.server.dto.PropositionDto;
import projet.pictionnary.breton.server.exception.PictionnaryBusinessException;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;

/**
 *
 * @author G43397
 */
public class PropositionBusiness {
    
    static int add(PropositionDto proposition) throws PictionnaryBusinessException {
        try {
            if (!proposition.isPersistant()) {
                return PropositionDB.insertDb(proposition);
            } else {
                throw new PictionnaryBusinessException("Game: on ne peut rendre "
                                      + "persistant un objet déjà persistant!");
            }
        } catch (PictionnaryDbException pDB) {
            throw new PictionnaryBusinessException(pDB.getMessage());
        }
    }  
}
