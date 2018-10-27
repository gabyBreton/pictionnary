package projet.pictionnary.breton.client.view;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import projet.pictionnary.breton.common.util.Observer;
import projet.pictionnary.breton.drawing.components.DrawingTools;

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
    public DrawerWindow(String toDraw, int avgProps) {
        drawingTools = new DrawingTools();
        
        Label wordLbl = new Label();
        wordLbl.setText(toDraw);
        Label toDrawLbl = new Label("Word to draw");
        toDrawLbl.setUnderline(true);
        Label avgPropsTitleLbl = new Label();
        avgPropsTitleLbl.setText("Average number of propositions: ");
        Label avgPropsLbl = new Label();
        avgPropsLbl.setText(Integer.toString(avgProps));
                
        
        addElementsGridPane(super.getInfosPane(), toDrawLbl, wordLbl, 
                            avgPropsTitleLbl, avgPropsLbl);
        
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
    private void addElementsGridPane(GridPane infosPane, Label toDrawLbl, 
                                     Label wordLbl, Label avgPropsTitleLbl, 
                                     Label avgPropsLbl) {
        infosPane.add(super.getGameStatusTitleLbl(), 0, 0);
        infosPane.add(super.getGameStatusLbl(), 0, 1);
        infosPane.add(toDrawLbl, 0, 5);
        infosPane.add(wordLbl, 0, 6);
        infosPane.add(avgPropsTitleLbl, 0, 8);
        infosPane.add(avgPropsLbl, 0, 9);
        infosPane.add(super.getHistoryLbl(), 0, 11);
        infosPane.add(super.getPropositionHist(), 0, 12);
        infosPane.add(super.getHistoryBtn(), 0, 18);
        infosPane.add(super.getQuitBtn(), 0, 20);
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