package Panes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * This class generates the Pane for the Character Selection screen
 *  @author paul
 */
public class SelectChar {
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
    public SelectChar() {

    }

    /**
     * Creates and returns a Pane with the character selection screen
     * 
     * @return Pane Select Character Screen
     */
    public Pane getPane() {
        //Starting x-positions
        int JackX = 380, MaggieX = -740, HansX = 1500;

        //Create characters
        Archer Jack = new Archer(JackX, 200, 0);
        Jack.setFitWidth(500.0);
        Jack.setFitHeight(500.0);
        
        Mage Maggie = new Mage(MaggieX, 200, 0);
        Maggie.setFitWidth(500.0);
        Maggie.setFitHeight(500.0);
        
        Barbarian Hans = new Barbarian(HansX, 200, 0);
        Hans.setFitWidth(500.0);
        Hans.setFitHeight(500.0);

        // SETS THE INSTANCE VARIABLES RELATED TO THE STARTING POSITION (SO YOU CAN MOD IN LAMBDA)
        Jack.setXPos(JackX);
        Maggie.setXPos(MaggieX);
        Hans.setXPos(HansX);
        
        //Create label
        Label lbSelectCharacter = new Label("Select Character");
        lbSelectCharacter.setLayoutX(100);
        lbSelectCharacter.setLayoutY(50);
        lbSelectCharacter.getStyleClass().add("title");
        
        //Create button
        Button btChooseCharacter = new Button("Choose Character");
        btChooseCharacter.setLayoutX(550);
        btChooseCharacter.setLayoutY(650);
        btChooseCharacter.setPrefWidth(200);
        btChooseCharacter.getStyleClass().add("blueButton");

        //Set button action
        btChooseCharacter.setOnMouseClicked(e -> { //Switches to selectMap pane
            sceneController.activate("Select Map");
        });

        //Create left and right arrows
        ImageView imgRightArrow = new ImageView();
        imgRightArrow.setImage(new Image("images/arrow_right.png"));
        imgRightArrow.setX(800);
        imgRightArrow.setY(600);
        imgRightArrow.setFitWidth(100.0);
        imgRightArrow.setFitHeight(100.0);
        
        ImageView imgLeftArrow = new ImageView();
        imgLeftArrow.setImage(new Image("images/arrow_left.png"));
        imgLeftArrow.setX(400);
        imgLeftArrow.setY(600);
        imgLeftArrow.setFitWidth(100.0);
        imgLeftArrow.setFitHeight(100.0);

        //Set arrow actions    
        imgRightArrow.setOnMouseClicked(e -> { //Scrolls to next character on right
            Jack.setXPos(Jack.getX() + 1120);
            Maggie.setXPos(Maggie.getX() + 1120);
            Hans.setXPos(Hans.getX() + 1120);
            slideRight(Jack, Jack.getXPos());
            slideRight(Maggie, Maggie.getXPos());
            slideRight(Hans, Hans.getXPos());
        });

        imgLeftArrow.setOnMouseClicked(e -> { //Scrolls to next character on left
            Jack.setXPos(Jack.getX() - 1120);
            Maggie.setXPos(Maggie.getX() - 1120);
            Hans.setXPos(Hans.getX() - 1120);
            slideLeft(Jack, Jack.getXPos());
            slideLeft(Maggie, Maggie.getXPos());
            slideLeft(Hans, Hans.getXPos());
        });

        //Create pane and add characters, button, arrows, and label
        Pane pane = new Pane();
        pane.getStyleClass().add("selectCharBackground");
        pane.getChildren().addAll(Jack, Maggie, Hans, btChooseCharacter, imgRightArrow, imgLeftArrow, lbSelectCharacter);
        
        return pane;
    }

    /**
     * Animates a character moving right to a specified position on the map
     * 
     * @param character character to move
     * @param xDestination where to move
     */
    public void slideRight(Character character, double xDestination) {
        //Create timeline
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.stop();
        timeline.getKeyFrames().clear();

        //Create keyframe
        KeyFrame kFLeft = new KeyFrame(Duration.millis(5), e -> {
            if (character.getX() == xDestination)
                timeline.stop();
            else
                character.setX(character.getX() + 10);
        });

        //Add keyframe to timeline
        timeline.getKeyFrames().add(kFLeft);

        //Wrap character to other side of map if at edge
        if (character.getX() >= 1500)
            character.setX(-740);
        else
            timeline.play();
    }

    /**
     * Animates a character moving left to a specified position on the map
     * 
     * @param character character to move
     * @param xDestination where to move
     */
    public void slideLeft(Character character, double xDestination) {
        //Create timeline
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.stop();
        timeline.getKeyFrames().clear();

        //Create keyframe
        KeyFrame kFLeft = new KeyFrame(Duration.millis(5), e -> {
            if (character.getX() == xDestination)
                timeline.stop();
            else
                character.setX(character.getX() - 10);
        });

        //Add keyframe to timeline
        timeline.getKeyFrames().add(kFLeft);
        
        //Wrap character to other side of map if at edge
        if (character.getX() <= -740)
            character.setX(1500);
        else
            timeline.play();
    }
}
