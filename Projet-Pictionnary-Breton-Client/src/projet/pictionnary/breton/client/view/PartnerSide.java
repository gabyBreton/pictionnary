package projet.pictionnary.breton.client.view;

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
 *
 * @author Gabriel Breton - 43397
 */
public class PartnerSide extends Region {
        
    private ClientController clientController;    
    private final DrawingPane drawingPane;
    private Label gameStatusLbl;
    private TextArea propositionHist;
    private TextField proposalTfd;

    
    public PartnerSide() {
        HBox rootBox = new HBox();
        GridPane infosPane = new GridPane();
        drawingPane = new DrawingPane();
        drawingPane.setDisableCanvas(true);

        Label gameStatusTitleLbl = new Label("Game status");
        gameStatusTitleLbl.setUnderline(true);        
        gameStatusLbl = new Label();
        
        Label toGuessLbl = createToGuessLbl();        
        proposalTfd = new TextField();
        proposalTfd.setPromptText("Enter a word...");
        
        Button submitBtn = createsButtonSubmit();
        
        Label historyLbl = new Label("Propositions history");
        propositionHist = new TextArea();
        propositionHist.setEditable(false);
        
        Button quitBtn = createsButtonQuit();
        
        addElementsGridPane(infosPane, gameStatusTitleLbl, toGuessLbl, proposalTfd, 
                            submitBtn, historyLbl, quitBtn);

        infosPane.setStyle("-fx-background-color: #e6e6e6;");          
        
        rootBox.getChildren().addAll(drawingPane, infosPane);
        
        getChildren().add(rootBox);
    }

    private Button createsButtonSubmit() {
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction((event) -> {
            clientController.submit(proposalTfd.getText());
        });
        return submitBtn;
    }

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

    private Button createsButtonQuit() {
        Button quitBtn = new Button("Quit");
        quitBtn.setOnAction((event) -> {
            clientController.quitGame();
        });
        return quitBtn;
    }

    public void draw(DrawingInfos drawingInfos) {
        drawingPane.setDrawingInfos(drawingInfos);
    }
    
    public void clearPane() {
        drawingPane.clearPane();
    }    

    public void setStatus(GameStatus gameStatus) {
        this.gameStatusLbl.setText(gameStatus.toString());
    }
    
    public void setController(ClientController controller) {
        clientController = controller;
    }
    
    public void addWordHistory(String word) {
        propositionHist.setText(propositionHist.getText() + "\n" + word);
    }    

    private Label createToGuessLbl() {
        Label toGuessTitleLbl = new Label();
        toGuessTitleLbl.setText("Proposition");
        toGuessTitleLbl.setUnderline(true);
        return toGuessTitleLbl;
    }
}
