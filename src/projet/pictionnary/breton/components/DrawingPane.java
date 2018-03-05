package projet.pictionnary.breton.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * This class is used as the canvas where to draw.
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawingPane extends Region implements IDrawing {
    
    private Canvas canvas;
    private StackPane rootPane;
    private GraphicsContext graphicContxt;
    private boolean erase;
    // private boolean modifiable;
    
    private ObjectProperty<Color> color;
    private ObjectProperty<Integer> thickness;
    private DrawingInfos drawingInfos;
    
    /**
     * Creates and initialize the attributes to draw.
     * 
     */
    public DrawingPane() {
        initialize();
    }

    @Override
    public final void initialize() {
        canvas = new Canvas(1000, 800);
        rootPane = new StackPane();    
        drawingInfos = new DrawingInfos();
        erase = false;        
        // modifiable = true;
        thickness = new SimpleObjectProperty<>();
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

    @Override
    public void clearPane() {
        graphicContxt.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    @Override
    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    @Override
    public ObjectProperty<Integer> thicknessProperty() {
        return thickness;
    }    

    @Override
    public Color getColor() {
        return color.get();
    }
    
    @Override
    public int getThickness() {
        return thickness.get();
    }

    @Override
    public void setColor(Color color) {
        this.color.set(color);
    }
    
    @Override
    public void setThickness(int thickness) {
        this.thickness.set(thickness);
    } 
    
    @Override
    public void setErase(boolean erase) {
        this.erase = erase;
    }
    
    public void drawSavedDrawingInfos(DrawingInfos drawingInfos) {
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

    @Override
    public DrawingInfos getDrawingInfos() {
        return drawingInfos;
    }

    @Override
    public void setDrawingInfos(DrawingInfos drawingInfos) {
        this.drawingInfos = drawingInfos;
        drawSavedDrawingInfos(drawingInfos);
    }
}
