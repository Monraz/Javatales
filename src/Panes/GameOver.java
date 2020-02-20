package Panes;

import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Window;

/**
 * This class generates the Pane for the Game Over screen
 * @author Christopher
 */
public class GameOver {
    public SceneController sceneController;
    
   /**
    * Sets the sceneController to be same as the sceneController of the main method.
    *
    * @param sceneController the sceneController to set equal to
    */
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
    
   /**
    * Default constructor
    */
    public GameOver() {

    }
    
   /**
    * Creates and returns a Pane with the game over screen
    *
    * @return Pane Game Over Screen
    */
    public Pane getPane() {
        //Create background image and title image
        BackgroundImage backgroundImage = new BackgroundImage(new Image("images/endbackground.jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        ImageView imgTitle = new ImageView(new Image("images/endtitle.png"));
        imgTitle.setPreserveRatio(true);
        imgTitle.setFitHeight(150);
        
        //Create stackPane
        StackPane stackPane = new StackPane();
        
        //Create buttons
        Button btNewGame = new Button("Play Again");
        Button btChangeCharacter = new Button("Change Player");
        Button btExit = new Button("Exit Game");

        //Set button actions
        btNewGame.setOnMouseClicked(e -> { //Switches to selectMap pane
            sceneController.activate("Select Map");
        });

        btChangeCharacter.setOnMouseClicked(e -> { //Switches to selectCharacter pane
            sceneController.activate("Select Character");
        });

        btExit.setOnMouseClicked(e -> { //Exits program
            Window stage = stackPane.getScene().getWindow();
            stage.hide();
        });

        //Create vbox
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(imgTitle, btNewGame, btChangeCharacter, btExit);
        vbox.setAlignment(Pos.CENTER);

        //Add vbox to stackPane and set background image
        stackPane.getChildren().add(vbox);
        stackPane.setBackground(new Background((backgroundImage)));
        
        return stackPane;
    }
}