package projet.pictionnary.breton.client.view;

import java.util.List;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

/**
 *
 * @author G43397
 */
public class HistoryWindow extends HBox {
    
    private TextArea historyArea;
    private List<String> history;
    
    public HistoryWindow(List<String> history) {
        this.history = history;
        historyArea = new TextArea();
        historyArea.setEditable(false);

        Label title = new Label();
        title.setText("Full history");
        
        fillHistoryArea();
        getChildren().addAll(title, historyArea);
    }
    
    private void fillHistoryArea(){
        for (String str : history) {
            historyArea.setText(historyArea.getText() + "\n" + str);
        }
    }
}
