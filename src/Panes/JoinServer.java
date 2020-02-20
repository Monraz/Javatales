package Panes;

import Backend.Backend;
import Backend.Server;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class generates the Pane for the Create/Join Server screen
 * @author Christopher
 */
public class JoinServer{
    Backend backend;
    Server server;
    public SceneController sceneController;
    public Button btCreate = null;
    public TextField ipTextfield;
    public TextField portTextfield;
    public Button connectBt;

    public void setBackend(Backend backend) {
        this.backend = backend;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    boolean fieldCorrect(){
        if(ipTextfield.getText().isEmpty() || portTextfield.getText().isEmpty()){
            return false;
        }
        return true;
    }

    void setConnectVisible(){
        if(fieldCorrect()){
            connectBt.setDisable(false);
        }else{
            connectBt.setDisable(true);
        }
    }

    /**
     * Sets the sceneController to be same as the sceneController of the main method.
     * @param sceneController the sceneController to set equal to
     */
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    /**
     * Default constructor
     */
    public JoinServer() {

    }

    /**
     * Creates and returns a Pane with the create/join server screen
     * 
     * @return Pane Create/Join Server Screen
     */
    public Pane getPane(){
        //Create buttons
        btCreate = new Button();
        btCreate.setText("Create Server");
        btCreate.getStyleClass().add("greenButton");
        btCreate.setPrefWidth(250);

        Button btJoin = new Button();
        btJoin.setText("Join Server");
        btJoin.getStyleClass().add("greenButton");
        btJoin.setPrefWidth(250);

        ipTextfield = new TextField("Enter the IP Address");
        portTextfield = new TextField("Enter the port");

        connectBt = new Button();
        connectBt.setText("Connect");
        connectBt.getStyleClass().add("greenButton");
        connectBt.setPrefWidth(250);
        
        //Create GridPane
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(50);
        pane.add(btCreate, 0, 0);
        pane.add(btJoin, 0, 1);

        //Set button actions
        btCreate.setOnMouseClicked(e->{ //Switches to selectCharacter pane
            server.start(new Stage());
            backend.connectLocal(6000);
            backend.player.setHoster(true);
            backend.refreshMapScreen();
            sceneController.activate("Select Character");
        });

        connectBt.setOnMouseClicked(e->{
            backend.connectNet(ipTextfield.getText(),Integer.parseInt(portTextfield.getText()));
            backend.player.setHoster(false);
            backend.javaTales.mapScreen.setCharacterNoHost();
            backend.refreshMapScreen();
            sceneController.activate("Select Character");
        });
        
        btJoin.setOnMouseClicked(e->{ //Joins a server (NOT YET IMPLEMENTED)
            pane.add(ipTextfield,0,0);
            pane.add(portTextfield,0,1);
            pane.add(connectBt, 0,2);
            connectBt.setDisable(true);

        });

        ipTextfield.setOnKeyPressed(e->{
            setConnectVisible();
        });
        portTextfield.setOnKeyPressed(e->{
            setConnectVisible();
        });
        
        return pane;
    }
}