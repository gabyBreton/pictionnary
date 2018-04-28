package projet.pictionnary.breton.server.db;

import java.util.ArrayList;
import java.util.List;
import projet.pictionnary.breton.server.dto.PlayerDto;
import projet.pictionnary.breton.server.exception.PictionnaryDTOException;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;
import projet.pictionnary.breton.server.seldto.PlayerSel;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class PlayerDB {

    public static List<PlayerDto> getCollection(PlayerSel sel) 
                                                 throws PictionnaryDbException {
        List<PlayerDto> al = new ArrayList<>();
        try {
            String query = "Select pid, pname FROM Player ";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            String where = "";
            if (sel.getId() != 0) {
                where = where + " pid = ? ";
            }
            if (sel.getLogin() != null) {
                if (!where.equals("")) {
                    where = where + " AND ";
                }
                where = where + " pname = ? ";
            }
            
            if (where.length() != 0) {
                where = " where " + where + " order by pid";
                query = query + where;
                stmt = connexion.prepareStatement(query);
                int i = 1;
                if (sel.getId() != 0) {
                    stmt.setInt(i, sel.getId());
                    i++;

                }
                if (sel.getLogin() != null) {
                    stmt.setString(i, sel.getLogin());
                    i++;
                }

            } else {
                query = query + " Order by pname";
                stmt = connexion.prepareStatement(query);
            }
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                al.add(new PlayerDto(rs.getInt("pid"), rs.getString("pname")));
            }
        } catch (java.sql.SQLException pSQL) {
            throw new PictionnaryDbException("Instanciation de Player impossible:\nSQLException: " + pSQL.getMessage());
        }
        return al;
    }
    
    public static int insertDb(PlayerDto player) throws PictionnaryDbException {
        try {
            int num = SequenceDB.getNextNum(SequenceDB.PLAYER);
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement insert;
            insert =
                    connexion.prepareStatement("Insert into Player(pid, pname) values(?, ?)");
            insert.setInt(1, num);
            insert.setString(2, player.getLogin());
            insert.execute();
            return num;
        } catch (Exception ex) {
            throw new PictionnaryDbException("Player: ajout impossible\r" + ex.getMessage());
        }
    }
}
