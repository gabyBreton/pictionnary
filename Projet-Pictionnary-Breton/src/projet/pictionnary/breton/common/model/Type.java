package projet.pictionnary.breton.common.model;

/**
 * The <code> Type </code> represents the type of a message send between a user
 * and the server.
 */
public enum Type {

    /**
     * Message used to inform the client that it is well connected.
     */
    CONNECTED, 
    
    /**
     * Message used to inform the client that it try to connect with an 
     * invalid login.
     */
    INVALID_LOGIN,
    
    /**
     * Message with the profile of a specific user.
     */
    PROFILE,
    
    /**
     * Message to share the data for all the tables.
     */
    GET_TABLES,
    
    /**
     * Message to share the word to draw.
     */
    GET_WORD,
    
    /**
     * Message to quit the game.
     */
    QUIT,
    
    /**
     * Message to create the table.
     */
    CREATE,
    
    /**
     * Message to join a table.
     */
    JOIN,
    
    /**
     * Message when a bad request is send to the server.
     */
    BAD_REQUEST,
    
    /**
     * When the drawer draws or clear the pane.
     */
    SEND_DRAW,
    
    /**
     * When the partner recept the draw.
     */
    RECEPT_DRAW,
    
    /**
     * To get the status of the game after a create, a join or a quit request.
     */
    GAME_STATUS,
    
    /**
     * When the partner submit a word.
     */
    SUBMIT;
}
