package projet.pictionnary.breton.server.db;

import java.util.ArrayList;
import java.util.List;
import projet.pictionnary.breton.server.dto.PlayerDto;
import projet.pictionnary.breton.server.dto.PropositionDto;
import projet.pictionnary.breton.server.dto.WordDto;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;
import projet.pictionnary.breton.server.seldto.PlayerSel;
import projet.pictionnary.breton.server.seldto.PropositionSel;

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
    
    public static List<PropositionDto> getCollection(PropositionSel sel) 
                                                 throws PictionnaryDbException {
        List<PropositionDto> al = new ArrayList<>();
        try {
            String query = "Select prid, prtxt, prgame FROM Proposition ";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            String where = "";
            if (sel.getId() != 0) {
                where = where + " prid = ? ";
            }
            if (sel.getTxt() != null) {
                if (!where.equals("")) {
                    where = where + " AND ";
                }
                where = where + " prtxt = ? ";
            }
            
            if (sel.getGame() != 0) {
                if (!where.equals("")) {
                    where = where + " AND ";
                }
                where = where + " prgame = ? ";  
            }
            
            if (where.length() != 0) {
                where = " where " + where + " order by prid";
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
                
                if (sel.getGame() != 0) {
                    stmt.setInt(i, sel.getGame());
                    i++;
                }
                
            } else {
                query = query + " Order by prtxt";
                stmt = connexion.prepareStatement(query);
            }
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                al.add(new PropositionDto(rs.getInt("prid"), rs.getString("prtxt"), rs.getInt("prgame")));
            }
        } catch (java.sql.SQLException pSQL) {
            throw new PictionnaryDbException("Instanciation de Player impossible:\nSQLException: " + pSQL.getMessage());
        }
        return al;
    }
}
