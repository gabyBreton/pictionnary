package projet.pictionnary.breton.client.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import projet.pictionnary.breton.client.ClientController;
import projet.pictionnary.breton.common.model.DrawingInfos;
import projet.pictionnary.breton.common.model.GameStatus;
import projet.pictionnary.breton.common.util.Observer;

/**
 * This class is used as an abstract generic class to derive from and create 
 * game windows.
 * 
 * @author Gabriel Breton - 43397
 */
public abstract class GameWindow extends Region {
    
    private ClientController clientController;    
    
    /* Visual elements */
    private final Label gameStatusTitleLbl;
    private final Label gameStatusLbl;
    private final Label historyLbl;
    private final TextArea propositionHist;
    private final HBox rootBox;
    private final GridPane infosPane;
    private final Button quitBtn;
    
    /**
     * Constructs a new game window. 
     */
    public GameWindow() {
        rootBox = new HBox();
        
        infosPane = new GridPane();
        infosPane.setStyle("-fx-background-color: #e6e6e6;");        
        infosPane.setVgap(10);
        
        gameStatusTitleLbl = new Label("Game status");
        gameStatusTitleLbl.setUnderline(true);        
        gameStatusLbl = new Label();
        
        historyLbl = new Label("Propositions history");
        propositionHist = new TextArea();
        propositionHist.setEditable(false);
        
        quitBtn = createsButtonQuit();
    }

    /**
     * Displays an alert to say that the game is win when it is.
     */
    public void displayWin() {
        Alert dialogWin = new Alert(Alert.AlertType.INFORMATION);
        dialogWin.setTitle("You win !");        
        dialogWin.setHeaderText("Congratulations, you win this game !");
        dialogWin.showAndWait();
    }

    /**
     * Sets the label for the game status.
     * 
     * @param gameStatus the game status.
     */
    public void setStatus(GameStatus gameStatus) {
        this.gameStatusLbl.setText(gameStatus.toString());
    }
    
    /**
     * Creates the button quit.
     * 
     * @return the button quit.
     */
    private Button createsButtonQuit() {
        Button quitBtn = new Button("Quit");
        quitBtn.setOnAction((event) -> {
            clientController.quitGame();
        });
        return quitBtn;
    }

    /**
     * Sets the controller of this window.
     * 
     * @param controller the controller of the window.
     */
    public void setController(ClientController controller) {
        clientController = controller;
    }

    /**
     * Adds a proposed word to the history area.
     * 
     * @param word the word to add.
     */
    public void addWordHistory(String word) {
        propositionHist.setText(propositionHist.getText() + "\n" + word);
    }

    /**
     * Hook method to pass an observer to the drawing tools component.
     * 
     * @param obs the observer.
     */
    public void addObserver(Observer obs) {
    }
    
    /**
     * Hook method to disable the drawing area.
     */
    public void disableDraw() {
    }
    
    /**
     * Hook method to disable the submit button.
     */
    public void disableSubmit() {
    }
    
    /**
     * Hook method to pass the drawing infos to the drawing pane.
     * 
     * @param drawingInfos the drawing infos.
     */
    public void draw(DrawingInfos drawingInfos) {
    }
    
    /**
     * Hook method used to clear the drawing pane.
     */
    public void clearPane() {
    }   
    
    /**
     * Gives the client controller.
     * 
     * @return the client controller.
     */
    public ClientController getClientController() {
        return clientController;
    }
    
    /**
     * Gives the game status title label.
     * 
     * @return the game status title label.
     */
    public Label getGameStatusTitleLbl() {
        return gameStatusTitleLbl;
    }

    /**
     * Gives the game status label.
     * 
     * @return the game status label.
     */
    public Label getGameStatusLbl() {
        return gameStatusLbl;
    }

    /**
     * Gives the history label.
     * 
     * @return the history label.
     */
    public Label getHistoryLbl() {
        return historyLbl;
    }

    /**
     * Gives the propositions history.
     * 
     * @return the propositions history.
     */
    public TextArea getPropositionHist() {
        return propositionHist;
    }

    /**
     * Gives the root box for the window's elements.
     * 
     * @return the root box.
     */
    public HBox getRootBox() {
        return rootBox;
    }

    /**
     * Gives the info pane.
     * 
     * @return the info pane.
     */
    public GridPane getInfosPane() {
        return infosPane;
    }

    /**
     * Gives the button to quit the window.
     * 
     * @return the button to quit.
     */
    public Button getQuitBtn() {
        return quitBtn;
    }
}