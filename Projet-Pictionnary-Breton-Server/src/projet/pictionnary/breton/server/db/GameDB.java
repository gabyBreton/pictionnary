package projet.pictionnary.breton.server.db;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import projet.pictionnary.breton.server.dto.GameDto;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;
import projet.pictionnary.breton.server.seldto.GameSel;

/**
 * Class used to interact with the Game table in the database.
 * 
 * @author Gabriel Breton - 43397
 */
public class GameDB {
    
    /**
     * Gets a collection of informations stored in the database.
     * 
     * @param sel the selection to gets the infos.
     * @return a list of informations.
     * @throws PictionnaryDbException if the instanciation of Game is impossible.
     */
    public static List<GameDto> getCollection(GameSel sel) 
                                                 throws PictionnaryDbException {
        List<GameDto> al = new ArrayList<>();
        try {
            String query = "Select gid, gdrawer, gpartner, gstarttime, gendtime, "
                           + "gstopplayer FROM Game ";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            String where = "";
            if (sel.getId() != 0) {
                where = where + " gid = ? ";
            }
            
            if (sel.getDrawer() != 0) {
                if (!where.equals("")) {
                    where = where + " AND ";
                }
                where = where + " gdrawer = ? ";
            }
            
            if (sel.getPartner() != 0) {
                if (!where.equals("")) {
                    where = where + " AND ";
                }
                where = where + " gpartner = ? ";
            }
            
            if (sel.getStartTime() != null) {
                if (!where.equals("")) {
                    where = where + " AND ";
                }                
                where = where + " gstarttime = ? ";
            }
            
            if (sel.getEndTime() != null) {
                if (!where.equals("")) {
                    where = where + " AND ";
                }
                where = where + " gendtime = ? ";
            }
            
            if (sel.getStopPlayer() != 0) {
                if (!where.equals("")) {
                    where = where + " AND ";
                }
                where = where + " gstopplayer ";
            }
            
            if (where.length() != 0) {
                where = " where " + where + " order by gid";
                query = query + where;
                stmt = connexion.prepareStatement(query);
                int i = 1;
                if (sel.getId() != 0) {
                    stmt.setInt(i, sel.getId());
                    i++;
                }
                if (sel.getDrawer() != 0) {
                    stmt.setInt(i, sel.getDrawer());
                    i++;
                }
                if (sel.getPartner() != 0) {
                    stmt.setInt(i, sel.getPartner());
                    i++;
                }
                
            } else {
                query = query + " Order by gdrawer";
                stmt = connexion.prepareStatement(query);
            }
            
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                al.add(new GameDto(rs.getInt("gid"), 
                                   rs.getInt("gdrawer"), 
                                   rs.getInt("gpartner"),
                                   rs.getTimestamp("gstarttime"),
                                   rs.getTimestamp("gendtime"),
                                   rs.getInt("gstopplayer")));
            }
        } catch (java.sql.SQLException pSQL) {
            throw new PictionnaryDbException("Instanciation de Game impossible:\nSQLException: " + pSQL.getMessage());
        }
        return al;
    }
    
    /**
     * Inserts a game in the database.
     * 
     * @param game the game to insert.
     * @return the id of the game added.
     * @throws PictionnaryDbException if it is not possible to add the game.
     */
    public static int insertDb(GameDto game) throws PictionnaryDbException {
        try {
            int num = SequenceDB.getNextNum(SequenceDB.GAME);
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement insert;
            insert = 
                    connexion.prepareStatement("Insert into Game(gid, gdrawer, "
                                               + "gpartner, gstarttime) "
                                               + "values(?, ?, ?, ?)");
            insert.setInt(1, num);
            insert.setInt(2, game.getDrawer());
            insert.setInt(3, game.getPartner());
            insert.setTimestamp(4, game.getStartTime());
            insert.execute();
            return num;
        } catch (Exception ex) {
            throw new PictionnaryDbException("Game: ajout impossible\r" + ex.getMessage());
        }
    }
    
    /**
     * Updates a game informations.
     * 
     * @param game the game to update.
     * @throws PictionnaryDbException if it is not possible to modify the game.
     */
    public static void updateDb(GameDto game) throws PictionnaryDbException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();

            java.sql.PreparedStatement update;
            update = connexion.prepareStatement("Update Game set "
                                                + " gdrawer = ?,"
                                                + " gpartner = ?,"
                                                + " gstarttime = ?,"
                                                + " gendtime = ?,"
                                                + " gstopplayer = ?"
                                                + " where gid= ?");
            update.setInt(1, game.getDrawer());
            update.setInt(2, game.getPartner());

            if (game.getStartTime() == null) {
                update.setNull(3, Types.TIMESTAMP);
            } else {
                update.setTimestamp(3, game.getStartTime());
            }

            if (game.getEndTime() == null) {
                update.setNull(4, Types.TIMESTAMP);
            } else {
                update.setTimestamp(4, game.getEndTime());
            }

            update.setInt(5, game.getStopPlayer());
            update.setInt(6, game.getId());
            int a = update.executeUpdate();
        } catch (Exception ex) {
            throw new PictionnaryDbException("Game, modification impossible:\n" + ex.getMessage());
        }
    }
}
