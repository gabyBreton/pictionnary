package projet.pictionnary.breton.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class DrawingTools extends Region {
    
    private final VBox rootBox;
    private final Spinner<Integer> spinner;
    private final ColorPicker colorPicker;
    
    public DrawingTools(DrawingPaneControl controller) {
        rootBox = new VBox();
        
        spinner = new Spinner(1, 200, 20);
        spinner.setEditable(true);
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            controller.setThickness(newValue);
        });
        
        colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.setEditable(true);
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                controller.setColor(newValue);
        });
        
        Button noEraseBtn = new Button("Draw");
        noEraseBtn.setOnAction((event) -> {
            controller.setErase(false);
        });
        
        Button eraserBtn = new Button("Eraser");
        eraserBtn.setOnAction((event) -> {
           controller.setErase(true);
        });
        
        rootBox.setSpacing(20);
        rootBox.setStyle("-fx-background-color: #e6e6e6;");
        rootBox.setMinHeight(800);
        rootBox.setMaxWidth(100);
        rootBox.setPadding(new Insets(5, 5, 0, 5));
        rootBox.getChildren().addAll(colorPicker, spinner, noEraseBtn, eraserBtn);
        getChildren().add(rootBox);
    }
}
