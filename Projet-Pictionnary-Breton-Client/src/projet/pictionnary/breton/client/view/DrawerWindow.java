package projet.pictionnary.breton.client.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import projet.pictionnary.breton.common.util.Observer;
import projet.pictionnary.breton.drawing.components.DrawingTools;

// TODO classe pareille à PARTNER, faire un truc générique

/**
 * This class is used as UI to represents the drawer side of the game.
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawerWindow extends GameWindow {
    
    private final DrawingTools drawingTools;
   
    /**
     * Constructs a new <code> DrawerWindow </code>.
     * h
     * @param toDraw the word to draw.
     */
    public DrawerWindow(String toDraw) {
        drawingTools = new DrawingTools();
        
        Label wordLbl = new Label();
        wordLbl.setText(toDraw);
        Label toDrawLbl = new Label("Word to draw");
        toDrawLbl.setUnderline(true);
        
        addElementsGridPane(super.getInfosPane(), 
                            super.getGameStatusTitleLbl(),
                            toDrawLbl, 
                            wordLbl, 
                            super.getHistoryLbl(), 
                            super.getQuitBtn());
        
        super.getRootBox().getChildren().addAll(drawingTools, 
                                                super.getInfosPane());
        
        getChildren().add(super.getRootBox());
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
        infosPane.add(super.getGameStatusLbl(), 0, 1);
        infosPane.add(toDrawLbl, 0, 5);
        infosPane.add(wordLbl, 0, 6);
        infosPane.add(historyLbl, 0, 10);
        infosPane.add(super.getPropositionHist(), 0, 11);
        infosPane.add(quitBtn, 0, 20);
    }
    
    /**
     * Pass an observer to the drawing tools component.
     * 
     * @param obs the observer.
     */
    @Override
    public void addObserver(Observer obs) {
        drawingTools.addObserver(obs);
    }
    
    /**
     * Disables the drawing area.
     */
    @Override
    public void disableDraw() {
        drawingTools.disableDraw();
    }
}