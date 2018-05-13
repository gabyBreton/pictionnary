package projet.pictionnary.breton.server.db;

import java.sql.*;
import projet.pictionnary.breton.server.exception.PictionnaryDbException;

/**
 * Offers tools for connexion et transaction managing.
 */
public class DBManager {

    private static Connection connection;

    /**
     * Retourne la connexion établie ou à défaut, l'établit
     * @return la connexion établie.
     */
    public static Connection getConnection() throws PictionnaryDbException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pictionnary", "g433971", "aaaa");
            } catch (SQLException ex) {
                throw new PictionnaryDbException("Connexion impossible: " + ex.getMessage());
            }
        }
        return connection;
    }

    /**
     * Starts a transaction.
     */
    public static void startTransaction() throws PictionnaryDbException {
        try {
           getConnection().setAutoCommit(false);
        } catch (SQLException ex) {
            throw new PictionnaryDbException("Impossible de démarrer une transaction: "+ex.getMessage());
        }
    }

    /**
     * Débute une transaction en spécifiant son niveau d'isolation.
     * Attention, ceci n'est pas implémenté sous Access,
     * (cette notion sera abordée ultérieurement dans le cours de bd).
     */
    public static void startTransaction(int isolationLevel) throws PictionnaryDbException {
        try {
            getConnection().setAutoCommit(false);

            int isol = 0;
            switch (isolationLevel) {
                case 0:
                    isol = java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
                    break;
                case 1:
                    isol = java.sql.Connection.TRANSACTION_READ_COMMITTED;
                    break;
                case 2:
                    isol = java.sql.Connection.TRANSACTION_REPEATABLE_READ;
                    break;
                case 3:
                    isol = java.sql.Connection.TRANSACTION_SERIALIZABLE;
                    break;
                default:
                    throw new PictionnaryDbException("Degré d'isolation inexistant!");
            }
            getConnection().setTransactionIsolation(isol);
        } catch (SQLException ex) {
            throw new PictionnaryDbException("Impossible de démarrer une transaction: "+ex.getMessage());
        }
    }

    /**
     * Valide la transaction courante.
     */
    public static void validateTransaction() throws PictionnaryDbException {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new PictionnaryDbException("Impossible de valider la transaction: "+ex.getMessage());
        }
    }

    /**
     * Annule la transaction courante.
     */
    public static void cancelTransaction() throws PictionnaryDbException {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new PictionnaryDbException("Impossible d'annuler la transaction: "+ex.getMessage());
        }
    }
}
