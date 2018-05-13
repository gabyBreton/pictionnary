package projet.pictionnary.breton.client.view;

import projet.pictionnary.breton.common.model.Role;

/**
 * This class is used as a factory to create differents kind of game windows, 
 * such as the drawer and the partner window.
 * 
 * @author Gabriel Breton - 43397
 */
public class GameWindowFactory {
    
    /**
     * Creates and gives a game window. The kind of the game window depends
     * on the role specified in parameters.
     * 
     * @param role the role of the user that need the window.
     * @return the new game window.
     */
    public static GameWindow createGameWindow(Role role) {
        GameWindow gameWindow;
        
        switch (role) {
            case PARTNER:
                gameWindow = new PartnerWindow();
                break;
                
            case NOT_IN_GAME:
                throw new IllegalArgumentException("Can't create window if "
                                                    + "not in game.");
                
            default:
                throw new IllegalArgumentException("Unrecognized role: " 
                                                    + role.toString());
        }
        
        return gameWindow;
    }
    
    /**
     * Creates and gives a game window. The kind of the game window depends
     * on the role specified in parameters.
     * 
     * @param role the role of the user that need the window.
     * @param wordToDraw the word that the drawer need to draw.
     * @return the new game window.
     */
    public static  GameWindow createGameWindow(Role role, String wordToDraw) {
        GameWindow gameWindow;
        
        switch (role) {
            case DRAWER:
                gameWindow = new DrawerWindow(wordToDraw);
                break;
                
            default:
                gameWindow = createGameWindow(role);
                break;
        }
        
        return gameWindow;
    }
}
