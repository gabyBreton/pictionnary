package projet.pictionnary.breton.client.view;

import projet.pictionnary.breton.common.model.Role;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class GameWindowFactory {
    
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
