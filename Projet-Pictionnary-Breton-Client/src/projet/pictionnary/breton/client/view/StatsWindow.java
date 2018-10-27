package projet.pictionnary.breton.client.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;

/**
 * Class used to display the client game statistics.
 * 
 * @author Gabriel Breton - 43397
 */
public class StatsWindow extends BorderPane {
    
    private final ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    private final PieChart pieChart;
    private final int indexDrawer = 0;
    private final int indexPartner = 1;
    
    public StatsWindow(int nbDrawer, int nbPartner) {
        data.add(indexDrawer, new PieChart.Data("Drawer", nbDrawer));
        data.add(indexPartner, new PieChart.Data("Partner", nbPartner));
        
        pieChart = new PieChart();
        pieChart.setData(data);
        pieChart.setTitle("Percentage of games as drawer or partner");
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setLabelsVisible(true);        

        setCenter(pieChart);
    }
    
    /*
    public void setDataDrawer(int value) {
        data.set(indexDrawer, new PieChart.Data("Drawer", value));
    }
    
    public void set
    */
}
