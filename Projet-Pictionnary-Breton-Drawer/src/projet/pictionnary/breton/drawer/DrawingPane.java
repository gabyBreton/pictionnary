package projet.pictionnary.breton.drawer;

import projet.pictionnary.breton.model.LinePosition;
import projet.pictionnary.breton.model.Point;
import projet.pictionnary.breton.model.DrawingInfos;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import projet.pictionnary.breton.util.Observer;

/**
 * This class is used as a canvas where to draw.
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawingPane extends Region implements IDrawing {
    
    private Canvas canvas;
    private StackPane rootPane;
    private GraphicsContext graphicContxt;
    private boolean erase;
    
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

    public void addObserver(Observer obs) {
        drawingInfos.addObserver(obs);
    }
    
    @Override
    public final void initialize() {
        canvas = new Canvas(1000, 800);
        rootPane = new StackPane();    
        drawingInfos = new DrawingInfos();

        erase = false;        
        thickness = new SimpleObjectProperty<>();
        color = new SimpleObjectProperty<>();        
        
        createsGraphicsContext();
        
        setMouseEvent();
        addListeners();        
        rootPane.getChildren().add(canvas);
        getChildren().addAll(rootPane);        
    }    

    /**
     * Creates and set the graphic context.
     */
    private void createsGraphicsContext() {
        graphicContxt = canvas.getGraphicsContext2D();
        graphicContxt.setLineWidth(20);
        graphicContxt.setLineCap(StrokeLineCap.ROUND);
        graphicContxt.setLineJoin(StrokeLineJoin.ROUND);
    }
    
    /**
     * Add the listeners to color and thickness properties.
     */
    private void addListeners() {
        color.addListener((observable, oldValue, newValue) -> {
            graphicContxt.setStroke(newValue);
        });
        
        thickness.addListener((observable, oldValue, newValue) -> {
            graphicContxt.setLineWidth(newValue);
        });
    }
    
    /**
     * Set the mouse event.
     */
    private void setMouseEvent() {
        setOnMouseReleased();
        setOnMouseDragged();
        setOnMousePressed();
    }

    /**
     * Handle the mouse event when the mouse is pressed.
     */
    private void setOnMousePressed() {
        canvas.setOnMousePressed((event) -> {
                double thickness = graphicContxt.getLineWidth();
                double x = event.getX() - (thickness / 2);
                double y = event.getY() - (thickness / 2);

                drawingInfos.add(new Point(x, y, 
                                           (int) graphicContxt.getLineWidth(),
                                           (Color) graphicContxt.getStroke(),
                                           erase, LinePosition.BEGIN));
                if (erase) {
                    graphicContxt.clearRect(x, y, thickness, thickness);
                } else {
                    graphicContxt.beginPath();
                    graphicContxt.moveTo(x, y);
                    graphicContxt.stroke();
                }
        });
    }

    /**
     * Handle the mouse event when the mouse is dragged.
     */
    private void setOnMouseDragged() {
        canvas.setOnMouseDragged((event) -> {
                double thickness = graphicContxt.getLineWidth();
                double x = event.getX() - (thickness / 2);
                double y = event.getY() - (thickness / 2);

                drawingInfos.add(new Point(x, y, 
                                           (int) graphicContxt.getLineWidth(),
                                           (Color) graphicContxt.getStroke(),
                                           erase, LinePosition.MIDDLE));
                if (erase) {
                    graphicContxt.clearRect(x, y, thickness, thickness);
                } else {
                    graphicContxt.lineTo(x, y);
                    graphicContxt.stroke();
                }
        });
    }

    /**
     * Handle the mouse event when the is released.
     */
    private void setOnMouseReleased() {
        canvas.setOnMouseReleased((event) -> {
                double thickness = graphicContxt.getLineWidth();
                double x = event.getX() - (thickness / 2);
                double y = event.getY() - (thickness / 2);
                drawingInfos.add((new Point(x, y, 
                                            (int) graphicContxt.getLineWidth(),
                                            (Color) graphicContxt.getStroke(),
                                            erase, LinePosition.END)));            
        });
    }

    @Override
    public void clearPane() {
        graphicContxt.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
       // drawingInfos.clearList();
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
    
    /**
     * Draw based on the drawing infos given in parameters.
     */
    private void drawSavedDrawingInfos() {
        for (Point p : drawingInfos.getListPositions()) {
            double x = p.getX();
            double y = p.getY();
            double thickness = p.getThickness();

            if (p.isErase()) {
                graphicContxt.clearRect(x, y, thickness, thickness);
            } else {
                graphicContxt.setStroke(p.getColor());
                graphicContxt.setLineWidth(thickness);    
                switch (p.getlinePos()) {
                    case MIDDLE:
                        graphicContxt.lineTo(x, y);
                        graphicContxt.stroke();
                        break;
                    case BEGIN:
                        graphicContxt.beginPath();
                        graphicContxt.moveTo(x, y);
                        graphicContxt.stroke();
                        break;
                    default:
                        graphicContxt.closePath();
                        break;
                }
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
        drawSavedDrawingInfos();
    }
    
    public void setDisableCanvas(boolean disable) {
        canvas.setDisable(disable);
    }
}
