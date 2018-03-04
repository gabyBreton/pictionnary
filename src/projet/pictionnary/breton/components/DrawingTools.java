package projet.pictionnary.breton.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Gabriel Breton - 43397
 */

// open folder <div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
// save <div>Icons made by <a href="https://www.flaticon.com/authors/smashicons" title="Smashicons">Smashicons</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
// paint-brush <div>Icons made by <a href="https://www.flaticon.com/authors/baianat" title="Baianat">Baianat</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
// eraser <div>Icons made by <a href="https://www.flaticon.com/authors/pixel-buddha" title="Pixel Buddha">Pixel Buddha</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
public class DrawingTools extends Region {
    
    private final VBox rootBox;
    private final Spinner<Integer> spinner;
    private final ColorPicker colorPicker;
    
    public DrawingTools(DrawingPaneControl controller) {
        rootBox = new VBox();

//=========== SPINNER        
        spinner = new Spinner(1, 200, 20);
        spinner.setEditable(true);
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            controller.setThickness(newValue);
        });
 
//========== COLORPICKER        
        colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.setEditable(true);
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                controller.setColor(newValue);
        });
        
 //=========== DRAW
        Image brushImg = new Image(getClass().getResourceAsStream("paint-brush.png"));
        Button brushBtn = new Button();
        brushBtn.setGraphic(new ImageView(brushImg));
        brushBtn.setOnAction((event) -> {
            controller.setErase(false);
        });
        
 //=========== ERASE        
        Image eraseImg = new Image(getClass().getResourceAsStream("eraser.png"));
        Button eraserBtn = new Button();
        eraserBtn.setGraphic(new ImageView(eraseImg));
        eraserBtn.setOnAction((event) -> {
           controller.setErase(true);
        });
        
 //============ CLEAR       
        Button clearAllBtn = new Button("Clear");
        //clearAllBtn.setStyle("fx-font: bold;");
        clearAllBtn.setAlignment(Pos.BOTTOM_CENTER);
        clearAllBtn.setOnAction((event) -> {
           controller.clearPane();
        });
        
 //============== SAVE
         Image saveImg = new Image(getClass().getResourceAsStream("save.png"));
        Button saveBtn = new Button();
        saveBtn.setGraphic(new ImageView(saveImg));
        saveBtn.setOnAction((event) -> {
            FileChooser fileChooser = createsFileChooser("Register draw", 
                                                            "ser");
            registerDraw(fileChooser, controller.getDrawingInfos());            
        });

//================ OPEN
        Image openImg = new Image(getClass().getResourceAsStream("folder.png"));
        Button openBtn = new Button();
        openBtn.setGraphic(new ImageView(openImg));
        openBtn.setOnAction((event) -> {
            FileChooser fileChooser = createsFileChooser("Open draw", "ser");
            controller.setDrawingInfos(recoverDraw(fileChooser));
        });
        
        rootBox.setSpacing(20);
        rootBox.setStyle("-fx-background-color: #e6e6e6;");
        rootBox.setMinHeight(800);
        rootBox.setMaxWidth(70);
        rootBox.setPadding(new Insets(5, 5, 0, 5));
        rootBox.getChildren().addAll(colorPicker, spinner, brushBtn, eraserBtn, saveBtn, openBtn, clearAllBtn);
        getChildren().add(rootBox);
    }
    
    private DrawingInfos recoverDraw(FileChooser fileChooser) {
        DrawingInfos drawingInfos = null;
        try {
            FileInputStream fileIn = new FileInputStream(
                    fileChooser.showOpenDialog(new Stage())
            );
            ObjectInputStream in = new ObjectInputStream(fileIn);
            drawingInfos = (DrawingInfos) in.readObject();
            in.close();
            fileIn.close();                
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
        
        return drawingInfos;
    }
    
    private FileChooser createsFileChooser(String title, String... extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        
        for (String extension : extensions) {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter(extension, "*." + extension)
            );            
        }
       
        return fileChooser;
    }
    
    private void registerDraw(FileChooser fileChooser, DrawingInfos drawingInfos) {
        try {
            FileOutputStream fileOut = new FileOutputStream(
                    fileChooser.showSaveDialog(new Stage()) + ".ser"
            );
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(drawingInfos);
            out.close();
            fileOut.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
