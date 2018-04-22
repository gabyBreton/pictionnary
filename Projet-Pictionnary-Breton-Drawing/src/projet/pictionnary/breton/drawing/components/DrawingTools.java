package projet.pictionnary.breton.drawing.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import projet.pictionnary.breton.util.Observer;

/*
 CREDITS ICONS:
 open folder Icons made by Freepik "http://www.freepik.com" from "https://www.flaticon.com/" is licensed by "http://creativecommons.org/licenses/by/3.0/" "Creative Commons BY 3.0" 
 save        Icons made by Smashicons "https://www.flaticon.com/authors/smashicons" from "https://www.flaticon.com/" is licensed by "http://creativecommons.org/licenses/by/3.0/" "Creative Commons BY 3.0"
 paint-brush Icons made by Baianat "https://www.flaticon.com/authors/baianat" from "https://www.flaticon.com/" is licensed by "http://creativecommons.org/licenses/by/3.0/" "Creative Commons BY 3.0"
 eraser      Icons made by Pixel Buddha "https://www.flaticon.com/authors/pixel-buddha" from "https://www.flaticon.com/" is licensed by "http://creativecommons.org/licenses/by/3.0/" "Creative Commons BY 3.0"
*/

/**
 * This class provides tools to draw such as brush, eraser, colorpicker, 
 * thickness spinner, saver and opener.
 * 
 * @author Gabriel Breton - 43397
 */
public class DrawingTools extends Region {
    
    private final DrawingPane drawingPane;
    private final VBox toolsBox;
    private final HBox rootBox;
    private Spinner<Integer> spinner;
    private ColorPicker colorPicker;
    
    /**
     * Constructs a <code> DrawingTools </code> component.
     * 
     */
    public DrawingTools() {
        drawingPane = new DrawingPane();
        toolsBox = new VBox();
        rootBox = new HBox();
        
        createsSpinner(drawingPane); 
        createsColorPicker(drawingPane);        

        Button brushBtn = createsBrushBtn(drawingPane);        
        Button eraserBtn = createsEraseBtn(drawingPane);        
        Button clearAllBtn = createsClearAllBtn(drawingPane);
        
        setRootBox(brushBtn, eraserBtn, clearAllBtn);
        
        rootBox.getChildren().addAll(toolsBox, drawingPane);
        getChildren().add(rootBox);
    }

    /**
     * Pass an observer to the drawing pane.
     * 
     * @param obs the observer to passs.
     */
    public void addObserver(Observer obs) {
        drawingPane.addObserver(obs);
    }
    
    /**
     * Set the root box and add its elements
     * 
     * @param brushBtn the brush button.
     * @param eraserBtn the eraser button.
     * @param saveBtn the save button.
     * @param openBtn the open button.
     * @param clearAllBtn the clear button.
     */
    private void setRootBox(Button brushBtn, Button eraserBtn,
                            Button clearAllBtn) {
        toolsBox.setSpacing(20);
        toolsBox.setStyle("-fx-background-color: #e6e6e6;");
        toolsBox.setMinHeight(800);
        toolsBox.setMaxWidth(70);
        toolsBox.setPadding(new Insets(5, 5, 0, 5));
        toolsBox.getChildren().addAll(colorPicker, spinner, brushBtn, eraserBtn, 
                                      clearAllBtn);
    }

    /**
     * Creates the button to clear all the canvas.
     * 
     * @param drawingPane the drawing pane.
     * @return the clear button.
     */
    private Button createsClearAllBtn(DrawingPane drawingPane) {
        Button clearAllBtn = new Button("Clear");
        clearAllBtn.setAlignment(Pos.BOTTOM_CENTER);
        clearAllBtn.setOnAction((event) -> {
            drawingPane.clearPane();
            drawingPane.getDrawingInfos().clearList();
        });
        
        return clearAllBtn;
    }

    /**
     * Creates the button to erase.
     * 
     * @param drawingPane the drawing pane.
     * @return the erase button.
     */
    private Button createsEraseBtn(DrawingPane drawingPane) {
        Image eraseImg = new Image(getClass().
                                   getResourceAsStream("eraser.png"));
        
        Button eraserBtn = new Button();
        eraserBtn.setTooltip(new Tooltip("Erase..."));
        
        eraserBtn.setGraphic(new ImageView(eraseImg));
        eraserBtn.setOnAction((event) -> {
            drawingPane.setErase(true);
        });
        
        return eraserBtn;
    }

    /**
     * Creates the button to draw.
     * 
     * @param drawingPane the drawing pane.
     * @return the brush button.
     */
    private Button createsBrushBtn(DrawingPane drawingPane) {
        Image brushImg = new Image(getClass().
                                   getResourceAsStream("paint-brush.png"));
        
        Button brushBtn = new Button();
        brushBtn.setTooltip(new Tooltip("Draw..."));
        
        brushBtn.setGraphic(new ImageView(brushImg));
        brushBtn.setOnAction((event) -> {
            drawingPane.setErase(false);
        });
        
        return brushBtn;
    }

    /**
     * Creates the color picker to change the draw color.
     * 
     * @param drawingPane the drawing pane.
     */
    private void createsColorPicker(DrawingPane drawingPane) {
        colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.setEditable(true);
        colorPicker.valueProperty().
                    addListener((observable, oldValue, newValue) -> {
            drawingPane.setColor(newValue);
        });
    }

    /**
     * Creates the thickness spinner.
     * 
     * @param drawingPane the drawing controller.
     */
    private void createsSpinner(DrawingPane drawingPane) {
        spinner = new Spinner(1, 200, 20);
        spinner.setEditable(true);
        spinner.valueProperty().
                addListener((observable, oldValue, newValue) -> {
            drawingPane.setThickness(newValue);
        });
    }
    
    /**
     * Disable the capacity to draw on the drawing pane.
     */
    public void disableDraw() {
        drawingPane.setDisableCanvas(true);
    }
}