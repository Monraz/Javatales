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
public class Intro {
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
    public Intro() {

    }

    /**
     * Creates and returns a Pane with the game over screen
     *
     * @return Pane Game Over Screen
     */
    public Pane getPane() {
        StackPane stackPane = new StackPane();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        ImageView title = new ImageView(new Image("images/title.png"));
        title.setPreserveRatio(true);
        title.setFitHeight(150);

        Button btNewGame = new Button("New Game");
        Button btLoad = new Button("Load (Will connect to database)");
        Button btHelp = new Button("Help");
        Button btExit = new Button("Exit");

        vbox.getChildren().addAll(title, btNewGame, btLoad, btHelp, btExit);

        vbox.setAlignment(Pos.CENTER);

        btLoad.setOnAction(e -> {

            //The SceneController switch the Pane to the Pane of the class SelectChar
            sceneController.activate("Select Character");
        });

        btNewGame.setOnAction(e -> {

            //The SceneController switch the Pane to the Pane of the class SelectChar
            sceneController.activate("JoinServer");
        });

        stackPane.getStyleClass().add("introPage");

        stackPane.getStyleClass().add("introPage");
        stackPane.getChildren().add(vbox);

        return stackPane;
    }
}