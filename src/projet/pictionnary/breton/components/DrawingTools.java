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
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class provides tools to draw such as brush, eraser, colorpicker, 
 * thickness spinner, saver and opener.
 * 
 * @author Gabriel Breton - 43397
 */

/*
 CREDITS ICONS:
 open folder Icons made by Freepik "http://www.freepik.com" from "https://www.flaticon.com/" is licensed by "http://creativecommons.org/licenses/by/3.0/" "Creative Commons BY 3.0" 
 save        Icons made by Smashicons "https://www.flaticon.com/authors/smashicons" from "https://www.flaticon.com/" is licensed by "http://creativecommons.org/licenses/by/3.0/" "Creative Commons BY 3.0"
 paint-brush Icons made by Baianat "https://www.flaticon.com/authors/baianat" from "https://www.flaticon.com/" is licensed by "http://creativecommons.org/licenses/by/3.0/" "Creative Commons BY 3.0"
 eraser      Icons made by Pixel Buddha "https://www.flaticon.com/authors/pixel-buddha" from "https://www.flaticon.com/" is licensed by "http://creativecommons.org/licenses/by/3.0/" "Creative Commons BY 3.0"
*/
public class DrawingTools extends Region {
    
    private final VBox toolsBox;
    private final HBox rootBox;
    private Spinner<Integer> spinner;
    private ColorPicker colorPicker;
    
    /**
     * Creates a new DrawingTools component.
     * 
     * @param drawingPane the drawing pane.
     */
    public DrawingTools(DrawingPane drawingPane) {
        toolsBox = new VBox();
        rootBox = new HBox();
        
        createsSpinner(drawingPane); 
        createsColorPicker(drawingPane);        

        Button brushBtn = createsBrushBtn(drawingPane);        
        Button eraserBtn = createsEraseBtn(drawingPane);        
        Button clearAllBtn = createsClearAllBtn(drawingPane);
        
        Button saveBtn = createsSaveBtn(drawingPane);
        Button openBtn = createsOpenBtn(drawingPane);
        
        setRootBox(brushBtn, eraserBtn, saveBtn, openBtn, clearAllBtn);
        
//        setRootBox(brushBtn, eraserBtn, clearAllBtn);
        rootBox.getChildren().addAll(toolsBox, drawingPane);
        getChildren().add(rootBox);
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
    private void setRootBox(Button brushBtn, Button eraserBtn, Button saveBtn, 
                            Button openBtn, Button clearAllBtn) {
        toolsBox.setSpacing(20);
        toolsBox.setStyle("-fx-background-color: #e6e6e6;");
        toolsBox.setMinHeight(800);
        toolsBox.setMaxWidth(70);
        toolsBox.setPadding(new Insets(5, 5, 0, 5));
//        toolsBox.getChildren().addAll(colorPicker, spinner, brushBtn, eraserBtn, 
//                                     clearAllBtn);
        toolsBox.getChildren().addAll(colorPicker, spinner, brushBtn, eraserBtn, 
                                     saveBtn, openBtn, clearAllBtn);

    }

    /**
     * Creates the button to open a saved draw.
     * 
     * @param drawingPane the drawing pane.
     * @return the open button.
     */
    private Button createsOpenBtn(DrawingPane drawingPane) {
        Image openImg = new Image(getClass().getResourceAsStream("folder.png"));
        
        Button openBtn = new Button();
        openBtn.setTooltip(new Tooltip("Open..."));
        openBtn.setGraphic(new ImageView(openImg));
        openBtn.setOnAction((event) -> {
            FileChooser fileChooser = createsFileChooser("Open draw", "ser");
            drawingPane.setDrawingInfos(recoverDraw(fileChooser));
        });
        
        return openBtn;
    }

    /**
     * Creates the button to save a draw.
     * 
     * @param drawingPane the drawing pane.
     * @return the save button.
     */    
     private Button createsSaveBtn(DrawingPane drawingPane) {
        Image saveImg = new Image(getClass().getResourceAsStream("save.png"));                
        
        Button saveBtn = new Button();
        saveBtn.setTooltip(new Tooltip("Save..."));
        saveBtn.setGraphic(new ImageView(saveImg));
        saveBtn.setOnAction((event) -> {
            FileChooser fileChooser = createsFileChooser("Register draw",
                    "ser");
            registerDraw(fileChooser, drawingPane.getDrawingInfos());
        });
        
        return saveBtn;
    }

    /**
     * Handle the action to recover a draw saved on the file system.
     * 
     * @param fileChooser the file chooser to select a draw.
     * @return the draw as an DrawingInfos instance.
     */
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

    /**
     * Creates a file chooser.
     * 
     * @param title the title of the file chooser.
     * @param extensions the extensions displayed by the file chooser.
     * 
     * @return the new file chooser.
     */
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

    /**
     * Handle the action to save a draw.
     * 
     * @param fileChooser the file chooser to select where to save the draw on 
     * the file system.
     * @param drawingInfos the draw as an instance of DrawingInfos.
     */
    private void registerDraw(FileChooser fileChooser, 
                              DrawingInfos drawingInfos) {
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
    
    
    /**
     * Creates the button to clear all the canvas.
     * 
     * @param controller the drawing controller.
     * @return the clear button.
     */
    private Button createsClearAllBtn(DrawingPane drawingPane) {
        Button clearAllBtn = new Button("Clear");
        clearAllBtn.setAlignment(Pos.BOTTOM_CENTER);
        clearAllBtn.setOnAction((event) -> {
            drawingPane.clearPane();
        });
        
        return clearAllBtn;
    }

    /**
     * Creates the button to erase.
     * 
     * @param controller the drawing controller.
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
     * @param controller the drawing controller.
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
     * @param controller the drawing controller.
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
}
