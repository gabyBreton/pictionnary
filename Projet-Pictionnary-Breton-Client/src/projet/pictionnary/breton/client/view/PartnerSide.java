package projet.pictionnary.breton.client.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import projet.pictionnary.breton.client.ClientController;
import projet.pictionnary.breton.drawing.components.DrawingPane;
import projet.pictionnary.breton.model.DrawingInfos;
import projet.pictionnary.breton.model.GameStatus;

/**
 * This class is used as UI to represents the partner side of the game.
 * 
 * @author Gabriel Breton - 43397
 */
public class PartnerSide extends Region {
        
    private ClientController clientController;    
    private final DrawingPane drawingPane;
    private final Label gameStatusLbl;
    private final TextArea propositionHist;
    private final TextField proposalTfd;
    private Button submitBtn;
    
    /**
     * Constructs a new <code> PartnerSide </code>.
     */    
    public PartnerSide() {
        HBox rootBox = new HBox();

        GridPane infosPane = new GridPane();
        infosPane.setStyle("-fx-background-color: #e6e6e6;");        
        infosPane.setVgap(10);
        
        drawingPane = new DrawingPane();
        drawingPane.setDisableCanvas(true);

        Label gameStatusTitleLbl = new Label("Game status");
        gameStatusTitleLbl.setUnderline(true);        
        gameStatusLbl = new Label();
        
        Label toGuessLbl = createToGuessLbl();        
        proposalTfd = new TextField();
        proposalTfd.setPromptText("Enter a word...");
        
        createsButtonSubmit();
        
        Label historyLbl = new Label("Propositions history");
        propositionHist = new TextArea();
        propositionHist.setEditable(false);
        
        Button quitBtn = createsButtonQuit();
        
        addElementsGridPane(infosPane, gameStatusTitleLbl, toGuessLbl, proposalTfd, 
                            submitBtn, historyLbl, quitBtn);
        
        rootBox.getChildren().addAll(drawingPane, infosPane);
        
        getChildren().add(rootBox);
    }

    /**
     * Creates the button to submit a word.
     */
    private void createsButtonSubmit() {
        submitBtn = new Button("Submit");
        submitBtn.setOnAction((event) -> {
            clientController.submit(proposalTfd.getText());
        });
    }

    /**
     * Disables the submit button.
     */
    public void disableSubmit() {
        submitBtn.setDisable(true);
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
     * Add side elements to the grid pane.
     * 
     * @param infosPane the grid pane that contains the elements.
     * @param gameStatusTitleLbl the info label for the game status.
     * @param toGuessLbl the info label word the submit area.
     * @param proposalTfd the textfield where enter the proposition.
     * @param submitBtn the submit button.
     * @param historyLbl the info label for the history area.
     * @param quitBtn the quit button.
     */    
    private void addElementsGridPane(GridPane infosPane, Label gameStatusTitleLbl, 
                                        Label toGuessLbl, TextField proposalTfd, 
                                        Button submitBtn, Label historyLbl, 
                                        Button quitBtn) {
        infosPane.add(gameStatusTitleLbl, 0, 0);
        infosPane.add(gameStatusLbl, 0, 1);
        infosPane.add(toGuessLbl, 0, 5);
        infosPane.add(proposalTfd, 0, 6);
        infosPane.add(submitBtn, 0, 7);
        infosPane.add(historyLbl, 0, 11);
        infosPane.add(propositionHist, 0, 12);
        infosPane.add(quitBtn, 0, 20);
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
     * Pass the drawing infos to the drawing pane.
     * 
     * @param drawingInfos the drawing infos.
     */
    public void draw(DrawingInfos drawingInfos) {
        drawingPane.setDrawingInfos(drawingInfos);
    }
    
    /**
     * Clear the drawing pane.
     */
    public void clearPane() {
        drawingPane.clearPane();
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
     * Creates the info label for the propositon of the word.
     * 
     * @return the label created.
     */
    private Label createToGuessLbl() {
        Label toGuessTitleLbl = new Label();
        toGuessTitleLbl.setText("Proposition");
        toGuessTitleLbl.setUnderline(true);
        return toGuessTitleLbl;
    }
}
