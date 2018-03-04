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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
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
    
    private final VBox rootBox;
    private Spinner<Integer> spinner;
    private ColorPicker colorPicker;
    
    public DrawingTools(DrawingPaneControl controller) {
        rootBox = new VBox();
        
        createsSpinner(controller); 
        createsColorPicker(controller);        

        Button brushBtn = createsBrushBtn(controller);        
        Button eraserBtn = createsEraseBtn(controller);        
        Button clearAllBtn = createsClearAllBtn(controller);
        Button saveBtn = createsSaveBtn(controller);
        Button openBtn = createsOpenBtn(controller);
        
        setRootBox(brushBtn, eraserBtn, saveBtn, openBtn, clearAllBtn);
        getChildren().add(rootBox);
    }

    private void setRootBox(Button brushBtn, Button eraserBtn, Button saveBtn, Button openBtn, Button clearAllBtn) {
        rootBox.setSpacing(20);
        rootBox.setStyle("-fx-background-color: #e6e6e6;");
        rootBox.setMinHeight(800);
        rootBox.setMaxWidth(70);
        rootBox.setPadding(new Insets(5, 5, 0, 5));
        rootBox.getChildren().addAll(colorPicker, spinner, brushBtn, eraserBtn, saveBtn, openBtn, clearAllBtn);
    }

    private Button createsOpenBtn(DrawingPaneControl controller) {
        Image openImg = new Image(getClass().getResourceAsStream("folder.png"));
        Button openBtn = new Button();
        openBtn.setTooltip(new Tooltip("Open..."));
        openBtn.setGraphic(new ImageView(openImg));
        openBtn.setOnAction((event) -> {
            FileChooser fileChooser = createsFileChooser("Open draw", "ser");
            controller.setDrawingInfos(recoverDraw(fileChooser));
        });
        return openBtn;
    }

    private Button createsSaveBtn(DrawingPaneControl controller) {
        Image saveImg = new Image(getClass().getResourceAsStream("save.png"));                
        Button saveBtn = new Button();
        saveBtn.setTooltip(new Tooltip("Save..."));
        saveBtn.setGraphic(new ImageView(saveImg));
        saveBtn.setOnAction((event) -> {
            FileChooser fileChooser = createsFileChooser("Register draw",
                    "ser");
            registerDraw(fileChooser, controller.getDrawingInfos());
        });
        return saveBtn;
    }

    private Button createsClearAllBtn(DrawingPaneControl controller) {
        Button clearAllBtn = new Button("Clear");
        clearAllBtn.setAlignment(Pos.BOTTOM_CENTER);
        clearAllBtn.setOnAction((event) -> {
            controller.clearPane();
        });
        return clearAllBtn;
    }

    private Button createsEraseBtn(DrawingPaneControl controller) {
        Image eraseImg = new Image(getClass().getResourceAsStream("eraser.png"));
        Button eraserBtn = new Button();
        eraserBtn.setTooltip(new Tooltip("Erase..."));
        
        eraserBtn.setGraphic(new ImageView(eraseImg));
        eraserBtn.setOnAction((event) -> {
            controller.setErase(true);
        });
        return eraserBtn;
    }

    private Button createsBrushBtn(DrawingPaneControl controller) {
        Image brushImg = new Image(getClass().getResourceAsStream("paint-brush.png"));
        Button brushBtn = new Button();
        brushBtn.setTooltip(new Tooltip("Draw..."));
        
        brushBtn.setGraphic(new ImageView(brushImg));
        brushBtn.setOnAction((event) -> {
            controller.setErase(false);
        });
        return brushBtn;
    }

    private void createsColorPicker(DrawingPaneControl controller) {
        colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.setEditable(true);
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            controller.setColor(newValue);
        });
    }

    private void createsSpinner(DrawingPaneControl controller) {
        spinner = new Spinner(1, 200, 20);
        spinner.setEditable(true);
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            controller.setThickness(newValue);
        });
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
