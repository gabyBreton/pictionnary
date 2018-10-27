package projet.pictionnary.breton.server.db;

import java.util.ArrayList;
import java.util.List;
import projet.pictionnary.breton.server.dto.PlayerDto;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;
import projet.pictionnary.breton.server.seldto.PlayerSel;

/**
 * Class used to interact with the Player table in the database.
 * 
 * @author Gabriel Breton - 43397
 */
public class PlayerDB {

    /**
     * Gets a collection of informations stored in the database.
     * 
     * @param sel the selection for the informations to get.
     * @return a list of player.
     * @throws PictionnaryDbException if it is not possible to instanciate 
     *         Player table.
     */
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
    
    /**
     * Inserts a player in the database.
     * 
     * @param player the player to add.
     * @return the id of the added player.
     * @throws PictionnaryDbException if it is not possible to add the player.
     */
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
    
    /**
     * Gets the id of the next player, to add it.
     * 
     * @return the id of the next player to add.
     * @throws PictionnaryDbException in case if an error occurs when 
     *         interacting with the Sequence table.
     */
    public static int getNextPlayerId() throws PictionnaryDbException {
        return SequenceDB.getNextNum(SequenceDB.PLAYER);
    }
}
