package projet.pictionnary.breton.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawingPane extends Region {
    
    private final Canvas canvas;
    private final StackPane rootPane;
    private final GraphicsContext graphicContxt;
    private boolean erase;
    
    private final ObjectProperty<Color> color;
    private final ObjectProperty<Integer> thickness = new SimpleObjectProperty<>();
    
    public DrawingPane() {
        canvas = new Canvas(1000, 800);
        rootPane = new StackPane();
        erase = false;
        color = new SimpleObjectProperty<>();

        graphicContxt = canvas.getGraphicsContext2D();
        graphicContxt.setLineWidth(20);        

        setMouseEvent();
        addListeners();
        
        rootPane.getChildren().add(canvas);
        getChildren().addAll(rootPane);
    }

    private void addListeners() {
        color.addListener((observable, oldValue, newValue) -> {
            graphicContxt.setFill(newValue);
        });
        
        thickness.addListener((observable, oldValue, newValue) -> {
            graphicContxt.setLineWidth(newValue);
        });
    }
    
    private void setMouseEvent() {
        canvas.setOnMouseDragged((event) -> {
            double size = graphicContxt.getLineWidth();
            double x = event.getX() - size;
            double y = event.getY() - size;

            if (erase) {
                graphicContxt.clearRect(x, y, size + 1, size + 1);
            } else {
                graphicContxt.fillOval(x, y, size, size);
            }
        });
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }
    
    public Color getColor() {
        return color.get();
    }
    
    public void setColor(Color color) {
        this.color.set(color);
    }    

    public ObjectProperty<Integer> thicknessProperty() {
        return thickness;
    }
    
    public int getThickness() {
        return thickness.get();
    }
    
    public void setThickness(int thickness) {
        this.thickness.set(thickness);
    } 
    
    public void setErase(boolean erase) {
        this.erase = erase;
    }
}
