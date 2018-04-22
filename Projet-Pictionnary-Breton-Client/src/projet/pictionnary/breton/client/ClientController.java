package projet.pictionnary.breton.client;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import projet.pictionnary.breton.client.view.ConnexionStageController;
import projet.pictionnary.breton.client.view.TableSelectionStageController;
import projet.pictionnary.breton.drawing.DrawerSide;
import projet.pictionnary.breton.drawing.PartnerSide;
import projet.pictionnary.breton.model.DataTable;
import projet.pictionnary.breton.model.DrawEvent;
import projet.pictionnary.breton.model.Message;
import projet.pictionnary.breton.model.MessageReceptDraw;
import projet.pictionnary.breton.model.MessageSendDraw;
import projet.pictionnary.breton.model.Type;
import projet.pictionnary.breton.util.Observer;

/**
 * This class is used as the main client controller. It manages the interactions 
 * between <code> ClientPictionnary </code> and FXML views controllers.
 * 
 * @author Gabriel Breton - 43397
 */
public class ClientController implements Observer {

    private ClientPictionnary clientPictionnary;
    private ConnexionStageController connexionStageCtrl;
    private TableSelectionStageController tableSelectionCtrl;
    private List<DataTable> dataTables;
    private DrawerSide drawerWindow;
    private PartnerSide partnerWindow;
    
    /**
     * Loads the connexion stage.
     * 
     * @throws IOException if case of loading error.
     */
    public void loadConnexionStage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/ConnexionStage.fxml"));
        loader.load();
        connexionStageCtrl = loader.getController();
        connexionStageCtrl.setClientController(this);
        
        Parent root = loader.getRoot();        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Pictionnary - Server connexion");
        stage.show();
    }
    
    /**
     * Loads the table selection stage.
     * 
     * @throws java.io.IOException in of loading error.
     */
    public void loadTableSelectionStage() throws IOException {
        // TODO verify input before !           
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/TableSelectionStage.fxml"));

        // TODO : Afficher erreur proprement si mauvais port
        clientPictionnary = new ClientPictionnary(connexionStageCtrl.getServerIp(),
                           Integer.parseInt(connexionStageCtrl.getPortNumber()),
                           connexionStageCtrl.getPseudo());
        clientPictionnary.addObserver(this);

        loader.load();
        launchTableSelectionStage(loader);
    }
    
    
    /**
     * Launch the <code> Table </code> selection stage.
     * 
     * @param loader the loader for the table selection stage.
     */
    private void launchTableSelectionStage(FXMLLoader loader) {
        // pass the client instance to the new stage
        tableSelectionCtrl = loader.getController();

        Parent root = loader.getRoot();
        Stage tableSelectionStage = new Stage();
        tableSelectionStage.setScene(new Scene(root));
        tableSelectionStage.setTitle("Pictionnary - Table selection");
        tableSelectionStage.show();
        
        // close the connexion stage          
        Stage connexionStage = (Stage) connexionStageCtrl.getConnexionBtn()
                                                         .getScene()
                                                         .getWindow();
        connexionStage.close();
    }
    
    @Override
    public void update(Object arg) {
        Message message = (Message) arg;
        Type type = (Type) message.getType();

        switch (type) {
            case GET_TABLES:
                dataTables = (List<DataTable>) message.getContent();
                tableSelectionCtrl.refreshTableView(dataTables);
                break;

            case CREATE:
                clientPictionnary.askWord();
                break;

            case JOIN:
                displayPartnerWindow();
                break;

            case GET_WORD:
                displayDrawerWindow();                
                break;
                
            case SEND_DRAW:
                MessageSendDraw msgSendDraw = (MessageSendDraw) message;
                DrawEvent eventSendDraw = (DrawEvent) msgSendDraw.getContent();
                
                if (eventSendDraw == DrawEvent.DRAW) {
                    clientPictionnary.draw(msgSendDraw.getDrawingInfos());
                } else {
                    clientPictionnary.clearPane();
                }
                break;
                
            case RECEPT_DRAW:
                System.out.println("Controller : Recept_Draw");
                MessageReceptDraw msgReceptDraw = (MessageReceptDraw) message;
                DrawEvent eventReceptDraw = (DrawEvent) msgReceptDraw.getContent();
                        
                if (eventReceptDraw == DrawEvent.DRAW) {
                    partnerWindow.draw(msgReceptDraw.getDrawingInfos());
                } else {
                    partnerWindow.clearPane();
                }
                break;
                
            default:
                throw new IllegalArgumentException("\nMessage type unknown " + type);
        }
    }
    
    /**
     * Requests to the client to create a new table.
     * 
     * @param tableName the name of the table.
     */
    public void createTable(String tableName) {
        clientPictionnary.createTable(tableName);
    }
    
    /**
     * Requests to the client to join the table specified by the given id.
     * 
     * @param id the id of the table to join.
     */
    public void joinTable(int id) {
        clientPictionnary.join(id);
    }
    
    /**
     * Creates and shows the <code> PartnerSide </code> window.
     */
    private void displayPartnerWindow() {
        partnerWindow = new PartnerSide();
        Scene scenePartner = new Scene(partnerWindow, 1200, 800);
        
        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setScene(scenePartner);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest((WindowEvent event) -> {
                Platform.runLater(clientPictionnary::quitGame);
            });
            stage.show();
        });
    }
    
    /**
     * Creates and shows the <code> Drawer </code> window.
     */
    private void displayDrawerWindow() {
        drawerWindow = new DrawerSide(clientPictionnary.getWord());
        drawerWindow.addObserver(this);
        
        Scene sceneDrawer = new Scene(drawerWindow, 1200, 800);

        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setScene(sceneDrawer);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest((WindowEvent event) -> {
                Platform.runLater(clientPictionnary::quitGame);
            });
            stage.show();
        });
    }  
}
