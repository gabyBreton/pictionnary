package projet.pictionnary.breton.model;

/**
 * The <code> Type </code> represents the type of a message send between a user
 * and the server.
 */
public enum Type {

    /**
     * Message with the profile of a specific user.
     */
    PROFILE,
    
    /**
     * Message to share the data for all the tables.
     */
    GET_ALL_TABLES,
    
    /**
     * Message to share the word to draw.
     */
    GET_WORD,
    
    /**
     * Message to quit the game.
     */
    QUIT_GAME,
    
    /**
     * Message to create the table.
     */
    CREATE_TABLE,
    
    /**
     * Message when a bad request is send to the server.
     */
    BAD_REQUEST;
}
