package projet.pictionnary.breton.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.RadioButton;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawingPane extends Region {
    
    // Add this eraser radiobutton to try the capacity to erase
    // the draw.
    //private RadioButton eraser;
    
    private Canvas canvas;
    private StackPane rootPane;
    private GraphicsContext graphicContxt;
    private boolean erase;
    
    public DrawingPane() {
        canvas = new Canvas(1000, 800);
        //eraser = new RadioButton();
        //eraser.setSelected(false);
        erase = false;
        //erase = eraser.isSelected();
        rootPane = new StackPane();
        
        
        setGraphiContxt();
        setMouseEvent();
        
        rootPane.getChildren().add(canvas);
        this.getChildren().addAll(rootPane);
        //this.getChildren().addAll(rootPane, eraser);
    }

    private void setGraphiContxt() {
        graphicContxt = canvas.getGraphicsContext2D();
        graphicContxt.setStroke(Color.BLACK);
        graphicContxt.setLineWidth(7);
    }

    private void setMouseEvent() {
        canvas.setOnMouseDragged((event) -> {
            //erase = eraser.isSelected();
            double size = graphicContxt.getLineWidth();
            double x = event.getX() - size;
            double y = event.getY() - size;

            if (erase) {
                graphicContxt.clearRect(x, y, size + 1, size + 1);
            } else {
                graphicContxt.setFill(Color.BLACK);
                graphicContxt.fillRect(x, y, size, size);
            }
        });
    }
    
    public void setDrawColor(Color color) {
        graphicContxt.setStroke(color);
    }    
    
    public void setThickness(double size) {
        graphicContxt.setLineWidth(size);
    } 
    
    public void setErase(boolean erase) {
        this.erase = erase;
    }
}
