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
    private final ObjectProperty<Integer> thickness;
    
    public DrawingPane(DrawingInfos drawingInfos) {
        canvas = new Canvas(1000, 800);
        rootPane = new StackPane();
        
        erase = false;
        thickness = new SimpleObjectProperty<>();
        color = new SimpleObjectProperty<>();

        graphicContxt = canvas.getGraphicsContext2D();
        graphicContxt.setLineWidth(20);        

        setMouseEvent(drawingInfos);
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
    
    private void setMouseEvent(DrawingInfos drawingInfos) {
        canvas.setOnMouseDragged((event) -> {
            double thickness = graphicContxt.getLineWidth();
            double x = event.getX() - thickness;
            double y = event.getY() - thickness;

            drawingInfos.add(new Point(x, y, (int) graphicContxt.getLineWidth(), 
                                             (Color) graphicContxt.getFill(), 
                                             erase));
            if (erase) {
                graphicContxt.clearRect(x, y, thickness + 1, thickness + 1);
            } else {
                graphicContxt.fillOval(x, y, thickness, thickness);
            }
        });
    }

    public void clearPane() {
        graphicContxt.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public ObjectProperty<Integer> thicknessProperty() {
        return thickness;
    }    

    public Color getColor() {
        return color.get();
    }
    
    public int getThickness() {
        return thickness.get();
    }

    public void setColor(Color color) {
        this.color.set(color);
    }
    
    public void setThickness(int thickness) {
        this.thickness.set(thickness);
    } 
    
    public void setErase(boolean erase) {
        this.erase = erase;
    }
    
    public void drawSaved(DrawingInfos drawingInfos) {
        for (Point p : drawingInfos.getListPositions()) {
            double x = p.getX();
            double y = p.getY();
            double thickness = p.getThickness();
            
            if (p.isErase()) {
                graphicContxt.clearRect(x, y, thickness + 1, thickness + 1);
            } else {
                graphicContxt.setFill(p.getColor());
                graphicContxt.fillOval(x, y, thickness, thickness);
            }
        }
    }
}
