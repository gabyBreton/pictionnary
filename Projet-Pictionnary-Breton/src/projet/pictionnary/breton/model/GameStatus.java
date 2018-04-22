package projet.pictionnary.breton.model;

/**
 * This class provides several game status.
 * 
 * @author Gabriel Breton - 43397
 */
public enum GameStatus {
    
    /**
     * When the drawer wait for a partner.
     */
    WAITING {
        @Override
        public String toString() {
            return "Waiting for partner";
        }
    },
    
    /**
     * When the game is going on.
     */
    IN_GAME {
        @Override
        public String toString() {
            return "In game";
        }
    },
    
    /**
     * When the game is over and winned.
     */
    WIN {
        @Override
        public String toString() {
            return "Win";
        }
    },
    
    /**
     * When the game is over but not win.
     */
    OVER {
        @Override
        public String toString() {
            return "Game over";
        }
    };
}
