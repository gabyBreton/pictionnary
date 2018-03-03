package projet.pictionnary.breton.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Gabriel Breton - 43397
 */
// paint-brush <div>Icons made by <a href="https://www.flaticon.com/authors/baianat" title="Baianat">Baianat</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
// eraser <div>Icons made by <a href="https://www.flaticon.com/authors/pixel-buddha" title="Pixel Buddha">Pixel Buddha</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
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

        Image imageBrush = new Image(getClass().getResourceAsStream("paint-brush.png"));
        Button brushBtn = new Button();
        brushBtn.setGraphic(new ImageView(imageBrush));
        brushBtn.setOnAction((event) -> {
            controller.setErase(false);
        });
        
        Image imageErase = new Image(getClass().getResourceAsStream("eraser.png"));
        Button eraserBtn = new Button();
        eraserBtn.setGraphic(new ImageView(imageErase));
        eraserBtn.setOnAction((event) -> {
           controller.setErase(true);
        });
        
        Button clearAllBtn = new Button("Clear all");
        clearAllBtn.setOnAction((event) -> {
           controller.clearPane();
        });
        
        rootBox.setSpacing(20);
        rootBox.setStyle("-fx-background-color: #e6e6e6;");
        rootBox.setMinHeight(800);
        rootBox.setMaxWidth(100);
        rootBox.setPadding(new Insets(5, 5, 0, 5));
        rootBox.getChildren().addAll(colorPicker, spinner, brushBtn, eraserBtn, clearAllBtn);
        getChildren().add(rootBox);
    }
}
