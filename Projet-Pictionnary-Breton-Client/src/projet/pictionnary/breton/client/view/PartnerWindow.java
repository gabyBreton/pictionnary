package projet.pictionnary.breton.client.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import projet.pictionnary.breton.drawing.components.DrawingPane;
import projet.pictionnary.breton.common.model.DrawingInfos;

/**
 * This class is used as UI to represents the partner side of the game.
 * 
 * @author Gabriel Breton - 43397
 */
public class PartnerWindow extends GameWindow {
        
    private final DrawingPane drawingPane;
    private final TextField proposalTfd;
    private Button submitBtn;
    
    /**
     * Constructs a new <code> PartnerWindow </code>.
     */    
    public PartnerWindow() {
        drawingPane = new DrawingPane();
        drawingPane.setDisableCanvas(true);
        
        Label toGuessLbl = createToGuessLbl();        
        proposalTfd = new TextField();
        proposalTfd.setPromptText("Enter a word...");
        
        createsButtonSubmit();
        
        addElementsGridPane(super.getInfosPane(), 
                            toGuessLbl, 
                            proposalTfd, 
                            submitBtn);
        
        super.getRootBox().getChildren().addAll(drawingPane, 
                                                super.getInfosPane());
        
        getChildren().add(super.getRootBox());
    }

    /**
     * Creates the button to submit a word.
     */
    private void createsButtonSubmit() {
        submitBtn = new Button("Submit");
        submitBtn.setOnAction((event) -> {
            super.getClientController().submit(proposalTfd.getText());
        });
    }

    /**
     * Disables the submit button.
     */
    @Override
    public void disableSubmit() {
        submitBtn.setDisable(true);
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
    private void addElementsGridPane(GridPane infosPane, Label toGuessLbl, 
                                     TextField proposalTfd, Button submitBtn) {
        infosPane.add(super.getGameStatusTitleLbl(), 0, 0);
        infosPane.add(super.getGameStatusLbl(), 0, 1);
        infosPane.add(toGuessLbl, 0, 5);
        infosPane.add(proposalTfd, 0, 6);
        infosPane.add(submitBtn, 0, 7);
        infosPane.add(super.getHistoryLbl(), 0, 11);
        infosPane.add(super.getPropositionHist(), 0, 12);
        infosPane.add(super.getQuitBtn(), 0, 20);
    }

    /**
     * Pass the drawing infos to the drawing pane.
     * 
     * @param drawingInfos the drawing infos.
     */
    @Override
    public void draw(DrawingInfos drawingInfos) {
        drawingPane.setDrawingInfos(drawingInfos);
    }
    
    /**
     * Clear the drawing pane.
     */
    @Override
    public void clearPane() {
        drawingPane.clearPane();
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
