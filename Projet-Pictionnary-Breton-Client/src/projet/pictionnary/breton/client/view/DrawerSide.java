package projet.pictionnary.breton.client.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import projet.pictionnary.breton.client.ClientController;
import projet.pictionnary.breton.drawing.components.DrawingTools;
import projet.pictionnary.breton.model.GameStatus;
import projet.pictionnary.breton.util.Observer;

/**
 * This class is used as UI to represents the drawer side of the game.
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawerSide extends Region {
    
    private final DrawingTools drawingTools;
    private ClientController clientController;
    private final Label gameStatusLbl;
    private final TextArea propositionHist;
   
    /**
     * Constructs a new <code> DrawerSide </code>.
     * 
     * @param toDraw the word to draw.
     */
    public DrawerSide(String toDraw) {
        HBox rootBox = new HBox();
        GridPane infosPane = new GridPane();
        drawingTools = new DrawingTools();

        Label gameStatusTitleLbl = new Label("Game status");
        gameStatusTitleLbl.setUnderline(true);        
        gameStatusLbl = new Label();
        
        Label wordLbl = new Label();
        wordLbl.setText(toDraw);
        Label toDrawLbl = new Label("Word to draw");
        toDrawLbl.setUnderline(true);
          
        Label historyLbl = new Label("Propositions history");
        propositionHist = new TextArea();
        propositionHist.setEditable(false);
        
        Button quitBtn = createsButtonQuit();
        
        addElementsGridPane(infosPane, gameStatusTitleLbl, toDrawLbl, wordLbl, 
                            historyLbl, quitBtn);

        infosPane.setStyle("-fx-background-color: #e6e6e6;");        
        infosPane.setHgap(200);
        
        rootBox.getChildren().addAll(drawingTools, infosPane);
        
        getChildren().add(rootBox);
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
     * Add side elements to the grid pane.
     * 
     * @param infosPane the grid pane that contains the elements.
     * @param gameStatusTitleLbl the info label for the game status.
     * @param toDrawLbl the info label for the word to draw.
     * @param wordLbl the word label.
     * @param historyLbl the info label for the history area.
     * @param quitBtn the quit button.
     */
    private void addElementsGridPane(GridPane infosPane, Label gameStatusTitleLbl, 
                                        Label toDrawLbl, Label wordLbl, 
                                        Label historyLbl, Button quitBtn) {
        infosPane.add(gameStatusTitleLbl, 0, 0);
        infosPane.add(gameStatusLbl, 0, 1);
        infosPane.add(toDrawLbl, 0, 5);
        infosPane.add(wordLbl, 0, 6);
        infosPane.add(historyLbl, 0, 10);
        infosPane.add(propositionHist, 0, 11);
        infosPane.add(quitBtn, 0, 20);
    }
    
    /**
     * Pass an observer to the drawing tools component.
     * 
     * @param obs the observer.
     */
    public void addObserver(Observer obs) {
        drawingTools.addObserver(obs);
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
     * Disables the drawing area.
     */
    public void disableDraw() {
        drawingTools.disableDraw();
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
}