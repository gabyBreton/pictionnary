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
 *
 * @author Gabriel Breton - 43397
 */
public abstract class GameWindow extends Region {
    
    private ClientController clientController;    
    
    /* Visual elements */
    private Label gameStatusTitleLbl;
    private Label gameStatusLbl;
    private Label historyLbl;
    private TextArea propositionHist;
    private HBox rootBox;
    private GridPane infosPane;
    private Button quitBtn;
    
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
    
    public ClientController getClientController() {
        return clientController;
    }

    public Label getGameStatusTitleLbl() {
        return gameStatusTitleLbl;
    }

    public Label getGameStatusLbl() {
        return gameStatusLbl;
    }

    public Label getHistoryLbl() {
        return historyLbl;
    }

    public TextArea getPropositionHist() {
        return propositionHist;
    }

    public HBox getRootBox() {
        return rootBox;
    }

    public GridPane getInfosPane() {
        return infosPane;
    }

    public Button getQuitBtn() {
        return quitBtn;
    }
}