package Panes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Backend.Backend;
import javafx.scene.PointLight;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

/**
 * This class generates the Pane for the Map screen
 *
 * @author paul
 */
public class MapScreen {



    Backend backend = null;
    int totalTiles = 36;
    Scanner mapInput;
    String filename;
    int xArcher = 0, yArcher = 0;
    int xMin = 0, yMin = 0;// xMax = 600, yMax = 600;
    double yMax = Screen.getPrimary().getBounds().getHeight();
    double xMax = yMax;
    double screenLength = Screen.getPrimary().getBounds().getWidth();
    int length;
    int width;
    char characterID;
    int currentTurn;
    boolean isCharacterSelected;
    HashMap<String,Player> players;
    Barbarian barb;
    Archer arch;
    Mage mage;
    Barbarian barbE;
    Archer archE;
    Mage mageE;
    ArrayList<Tile> gameMap;
    public HashMap<String, Character> charactersMap;
    ImageView mapImage;
    Pane mapPane;
    String selectedCharacter;
    ArrayList<String> message;


    double tileDistance = xMax / 6;

    public SceneController sceneController;

    public void setBackend(Backend backend) {
        this.backend = backend;
    }

    /**
     * Sets the sceneController to be same as the sceneController of the main
     * method.
     *
     * @param sceneController the sceneController to set equal to
     */
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    /**
     * Default constructor
     */
    public MapScreen() {
        try {
            filename = "src/Maps/map1.txt";
            ArrayList<Tile> map = new ArrayList<>();
            mapInput = new Scanner(new File(filename));
            length = mapInput.nextInt();
            width = mapInput.nextInt();
            totalTiles = length * width;

        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
        currentTurn = 0;

        gameMap = new ArrayList<>();


        try {
            fillMap(gameMap);
            System.out.println("Method was called");
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }

        System.out.println("Array size: " + gameMap.size());

        for (int j = 0; j < 36; j++) {
            System.out.println("Tile " + j + " startX: " + gameMap.get(j).getStartX() + " startY: " + gameMap.get(j).getStartY());

        }

        // CREATE MAP IMAGE
        mapImage = new ImageView("images/MainMap(SQ).png");
        mapImage.setFitWidth(xMax);
        mapImage.setFitHeight(yMax);
        charactersMap = new HashMap<>();
        setCharacterHost();
        mapPane = new Pane();


    }

    //Methods
    private void fillMap(ArrayList<Tile> map) throws FileNotFoundException { //Fills the array of tiles with tiles from a map data file

        int num = 0;
        int defBonus, elevation;
        boolean filled;

        System.out.println("Length: " + length + " Width: " + width);
        System.out.println("Method Started");
        for (int l = 0; l < length; l++) {

            for (int w = 0; w < length; w++) {

                switch (mapInput.nextInt()) {
                    case 1:
                        filename = "src/Tiles/plains.txt";
                        break;
                    case 2:
                        filename = "src/Tiles/hill.txt";
                        break;
                    case 3:
                        filename = "src/Tiles/mountain.txt";
                        break;
                    case 4:
                        filename = "src/Tiles/water.txt";
                        break;
                    case 5:
                        filename = "src/Tiles/town.txt";
                }
                Scanner tileInput = new Scanner(new File(filename));
                defBonus = tileInput.nextInt();
                elevation = tileInput.nextInt();
                filled = tileInput.nextBoolean();
                map.add(new Tile(defBonus, elevation, filled, tileDistance * w, tileDistance * l, tileDistance));
            }
            System.out.println("Column Iteration");
        }
        System.out.println("Method Finished");
    }






    public void setCharacterHost(){

        charactersMap.clear();

        barb = new Barbarian(gameMap.get(18).getStartX(), gameMap.get(18).getStartY(), 18);
        gameMap.get(18).setEntity(barb);
        gameMap.get(18).setFilled(true);

        arch = new Archer(gameMap.get(24).getStartX(), gameMap.get(24).getStartY(), 24);
        gameMap.get(24).setEntity(arch);
        gameMap.get(24).setFilled(true);

        mage = new Mage(gameMap.get(30).getStartX(), gameMap.get(30).getStartY(), 30);
        gameMap.get(30).setEntity(mage);
        gameMap.get(30).setFilled(true);


        barbE = new Barbarian(gameMap.get(5).getStartX(), gameMap.get(5).getStartY(), 5);
        gameMap.get(5).setEntity(barb);
        gameMap.get(5).setFilled(true);

        archE = new Archer(gameMap.get(11).getStartX(), gameMap.get(11).getStartY(), 11);
        gameMap.get(11).setEntity(arch);
        gameMap.get(11).setFilled(true);

        mageE = new Mage(gameMap.get(17).getStartX(), gameMap.get(17).getStartY(), 17);
        gameMap.get(17).setEntity(mage);
        gameMap.get(17).setFilled(true);


        barbE.setFitWidth(tileDistance);
        barbE.setFitHeight(tileDistance);

        archE.setFitWidth(tileDistance);
        archE.setFitHeight(tileDistance);

        mageE.setFitHeight(tileDistance);
        mageE.setFitWidth(tileDistance);

        barb.setFitWidth(tileDistance);
        barb.setFitHeight(tileDistance);

        arch.setFitWidth(tileDistance);
        arch.setFitHeight(tileDistance);

        mage.setFitHeight(tileDistance);
        mage.setFitWidth(tileDistance);

        charactersMap.put("arch",arch);
        charactersMap.put("barb",barb);
        charactersMap.put("mage",mage);
        charactersMap.put("archE",archE);
        charactersMap.put("barbE",barbE);
        charactersMap.put("mageE",mageE);


    }
    public void setCharacterNoHost(){
        charactersMap.clear();

            // CHARACTER CREATION SECTION
            System.out.println(backend.player.isHoster());
            barbE = new Barbarian(gameMap.get(18).getStartX(), gameMap.get(18).getStartY(), 18);
            gameMap.get(18).setEntity(barb);
            gameMap.get(18).setFilled(true);

            archE = new Archer(gameMap.get(24).getStartX(), gameMap.get(24).getStartY(), 24);
            gameMap.get(24).setEntity(arch);
            gameMap.get(24).setFilled(true);

            mageE = new Mage(gameMap.get(30).getStartX(), gameMap.get(30).getStartY(), 30);
            gameMap.get(30).setEntity(mage);
            gameMap.get(30).setFilled(true);
            barb = new Barbarian(gameMap.get(5).getStartX(), gameMap.get(5).getStartY(), 5);
            gameMap.get(5).setEntity(barb);
            gameMap.get(5).setFilled(true);

            arch = new Archer(gameMap.get(11).getStartX(), gameMap.get(11).getStartY(), 11);
            gameMap.get(11).setEntity(arch);
            gameMap.get(11).setFilled(true);

            mage = new Mage(gameMap.get(17).getStartX(), gameMap.get(17).getStartY(), 17);
            gameMap.get(17).setEntity(mage);
            gameMap.get(17).setFilled(true);


        barbE.setFitWidth(tileDistance);
        barbE.setFitHeight(tileDistance);

        archE.setFitWidth(tileDistance);
        archE.setFitHeight(tileDistance);

        mageE.setFitHeight(tileDistance);
        mageE.setFitWidth(tileDistance);

        barb.setFitWidth(tileDistance);
        barb.setFitHeight(tileDistance);

        arch.setFitWidth(tileDistance);
        arch.setFitHeight(tileDistance);

        mage.setFitHeight(tileDistance);
        mage.setFitWidth(tileDistance);

        charactersMap.put("arch",arch);
        charactersMap.put("barb",barb);
        charactersMap.put("mage",mage);
        charactersMap.put("archE",archE);
        charactersMap.put("barbE",barbE);
        charactersMap.put("mageE",mageE);


    }

    public void addEnemy(){
        mapPane.getChildren().addAll(charactersMap.get("archE"),charactersMap.get("barbE"),charactersMap.get("mageE"));
        System.out.println("LETS BATTLE");
    }


    /*public void addPlayer(Player player, String name){
        players.put(name, player);

        for(Character player1 :player.getCharacters()){
            player1.set
        }
    }*/

    public void waitWalk(){

    }

    public void move(String selectedCharacter, String direction){
        System.out.println("MOVE!");

        if(direction.equals("up")){
            charactersMap.get(selectedCharacter).walkUp(charactersMap.get(selectedCharacter).getY() - tileDistance, yMin - tileDistance, gameMap);
            charactersMap.get(selectedCharacter).setYPos(charactersMap.get(selectedCharacter).getY());
        }else if(direction.equals("down")){
            charactersMap.get(selectedCharacter).walkDown(charactersMap.get(selectedCharacter).getY() + tileDistance, yMax, gameMap);
            charactersMap.get(selectedCharacter).setYPos(charactersMap.get(selectedCharacter).getY());
        }else if(direction.equals("right")){
            charactersMap.get(selectedCharacter).walkRight(charactersMap.get(selectedCharacter).getX() + tileDistance, xMax, gameMap);
            charactersMap.get(selectedCharacter).setXPos(charactersMap.get(selectedCharacter).getX());
        }else if(direction.equals("left")){
            charactersMap.get(selectedCharacter).walkLeft(charactersMap.get(selectedCharacter).getX() - tileDistance, xMin - tileDistance, gameMap);
            charactersMap.get(selectedCharacter).setXPos(charactersMap.get(selectedCharacter).getX());
        }
    }
    /**
     * Creates and returns a Pane with the map screen
     *
     * @return Pane Map Screen
     */
    public Pane getPane() {
        mapPane.getChildren().clear();

        // CHARACTER SELECTION SECTION
        ImageView selectedCharImage = new ImageView();

        selectedCharImage.setFitHeight(tileDistance * 2);
        selectedCharImage.setFitWidth(tileDistance * 2);
        selectedCharImage.setX(xMax);
        selectedCharImage.setY(yMax - tileDistance * 2);

        charactersMap.get("barb").setOnMouseClicked(e -> {
            characterID = 'B';
            selectedCharImage.setImage(charactersMap.get("barb").getIdleImage());
            charactersMap.get("barb").isSelectedCharacter(true);
            selectedCharacter = "barb";
            isCharacterSelected = true;
        });

        charactersMap.get("arch").setOnMouseClicked(e -> {
            characterID = 'A';
            selectedCharImage.setImage(charactersMap.get("arch").getIdleImage());
            charactersMap.get("arch").isSelectedCharacter(true);
            selectedCharacter = "arch";
            isCharacterSelected = true;
        });

        charactersMap.get("mage").setOnMouseClicked(e -> {
            characterID = 'M';
            selectedCharImage.setImage(mage.getIdleImage());
            charactersMap.get("mage").isSelectedCharacter(true);
            selectedCharacter = "mage";
            isCharacterSelected = true;

        });

        //Button btCharacterSelect = new Button("Character Select");
       /* btCharacterSelect.setLayoutX(xMax + 200);
        btCharacterSelect.setLayoutY(50);
        btCharacterSelect.getStyleClass().add("blueButton");*/

       /* btCharacterSelect.setOnMouseClicked(e -> { // WILL HAVE TO RESET BOOLEAN VALUES TO FALSE WITH EACH TURN
            switch (characterID) {
                case 'B':
                    barb.isSelectedCharacter(true);
                    isCharacterSelected = true;
                    System.out.println("Barb Selected as character");
                    break;
                case 'A':
                    arch.isSelectedCharacter(true);
                    isCharacterSelected = true;
                    System.out.println("Archer Selected as character");
                    break;
                case 'M':
                    mage.isSelectedCharacter(true);
                    isCharacterSelected = true;
                    System.out.println("Mage Selected as character");
                    break;
            }

        });*/

        // MOVE BUTTON
        Button btMove = new Button("Move");
        btMove.setLayoutX(xMax + 100);
        btMove.setLayoutY(100);
        btMove.getStyleClass().add("blueButton");

        // ATTACK BUTTON
        Button btAttack = new Button("Attack");
        btAttack.setLayoutX(xMax + 200);
        btAttack.setLayoutY(100);
        btAttack.getStyleClass().add("blueButton");
        
        //Create labels
        Label lbHitPoints = new Label("HP: ");
        lbHitPoints.setLayoutX(150);
        lbHitPoints.setLayoutY(680);
        lbHitPoints.getStyleClass().add("stats");

        Label lbMagicPoints = new Label("MP: ");
        lbMagicPoints.setLayoutX(300);
        lbMagicPoints.setLayoutY(680);
        lbMagicPoints.getStyleClass().add("stats");
        
        Label lbActionPoints = new Label("AP: ");
        lbActionPoints.setLayoutX(450);
        lbActionPoints.setLayoutY(680);
        lbActionPoints.getStyleClass().add("stats");
        
        Label lbActionPointsCurrent = new Label(Integer.toString(backend.player.getActionPoints()));
        lbActionPoints.setLayoutX(600);
        lbActionPoints.setLayoutY(680);
        lbActionPoints.getStyleClass().add("stats");

        btAttack.setOnMouseClicked(e -> {
            if (isCharacterSelected && backend.player.getActionPoints()>=2) {
                switch (characterID) {
                    case 'A':
                        barb.attack(arch, tileDistance, 2);
                        System.out.println("Barb attacks archer for " + barb.getAttackPoints()
                                + " points of damage. Archer HP at " + arch.getHitPoints());
                        break;
                    case 'M':
                        barb.attack(mage, tileDistance, 2);
                        System.out.println("Barb attacks mage for " + barb.getAttackPoints()
                                + " points of damage. Mage HP at " + mage.getHitPoints());
                        break;
                }
            }
            
            //  }
            lbActionPointsCurrent.setText(Integer.toString(backend.player.getActionPoints()));
        });

        
        
        //Create buttons
        Button btMoveDown = new Button("Move Down");
        btMoveDown.setLayoutX(975);
        btMoveDown.setLayoutY(500.25);
        btMoveDown.getStyleClass().add("blueButton");

        Button btMoveUp = new Button("Move Up");
        btMoveUp.setLayoutX(975);
        btMoveUp.setLayoutY(166.75);
        btMoveUp.getStyleClass().add("blueButton");

        Button btMoveRight = new Button("Move Right");
        btMoveRight.setLayoutX(1075);
        btMoveRight.setLayoutY(333.5);
        btMoveRight.getStyleClass().add("blueButton");

        Button btMoveLeft = new Button("Move Left");
        btMoveLeft.setLayoutX(875);
        btMoveLeft.setLayoutY(333.5);
        btMoveLeft.getStyleClass().add("blueButton");

        Button btEndGame = new Button("End Game");
        btEndGame.setLayoutX(900);
        btEndGame.setLayoutY(500);
        btEndGame.getStyleClass().add("blueButton");

        Button tileIndex = new Button("tileIndex");
        tileIndex.setLayoutX(1000);
        tileIndex.setLayoutY(650);
        tileIndex.getStyleClass().add("blueButton");

        //Set button actions
        btMoveDown.setOnMouseClicked(e -> { //Character moves down one tile

            if (isCharacterSelected && backend.player.getActionPoints()>=1) {
                charactersMap.get(selectedCharacter).walkDown(charactersMap.get(selectedCharacter).getY() + tileDistance, yMax, gameMap);
                charactersMap.get(selectedCharacter).setYPos(charactersMap.get(selectedCharacter).getY());
                backend.player.reduceActionPoints(1);
                message = new ArrayList<>();


                message.add("move");
                if(backend.player.isHoster()){

                    message.add("0");
                }else{
                    message.add("1");
                }
                //The next element is the characterID used to know to whom the movement is going to be applied.
                message.add(selectedCharacter+"");
                //The next element represents the direction of the movement
                message.add("down");
                backend.send(message);
            }
            lbActionPointsCurrent.setText(Integer.toString(backend.player.getActionPoints()));
        });

        btMoveUp.setOnMouseClicked(e -> { //Character moves up one tile
            if (isCharacterSelected && backend.player.getActionPoints()>=1) {
                charactersMap.get(selectedCharacter).walkUp(charactersMap.get(selectedCharacter).getY() - tileDistance, yMin - tileDistance, gameMap);
                charactersMap.get(selectedCharacter).setYPos(charactersMap.get(selectedCharacter).getY());
                backend.player.reduceActionPoints(1);
                message = new ArrayList<>();
                message.add("move");
                if(backend.player.isHoster()){

                    message.add("0");
                }else{
                    message.add("1");
                }
                //The next element is the characterID used to know to whom the movement is going to be applied.
                message.add(selectedCharacter+"");
                //The next element represents the direction of the movement
                message.add("up");
                backend.send(message);
            }
            lbActionPointsCurrent.setText(Integer.toString(backend.player.getActionPoints()));
        });

        btMoveRight.setOnMouseClicked(e -> { //Character moves right  one tile

            if (isCharacterSelected && backend.player.getActionPoints()>=1) {
                charactersMap.get(selectedCharacter).walkRight(charactersMap.get(selectedCharacter).getX() + tileDistance, xMax, gameMap);
                charactersMap.get(selectedCharacter).setXPos(charactersMap.get(selectedCharacter).getX());
                backend.player.reduceActionPoints(1);
                message = new ArrayList<>();
                message.add("move");
                if(backend.player.isHoster()){

                    message.add("0");
                }else{
                    message.add("1");
                }
                //The next element is the characterID used to know to whom the movement is going to be applied.
                message.add(selectedCharacter+"");
                //The next element represents the direction of the movement
                message.add("right");
                backend.send(message);
            }
            lbActionPointsCurrent.setText(Integer.toString(backend.player.getActionPoints()));
        });

        btMoveLeft.setOnMouseClicked(e -> { //Character moves left one tile
            if (isCharacterSelected && backend.player.getActionPoints()>=1) {
                charactersMap.get(selectedCharacter).walkLeft(charactersMap.get(selectedCharacter).getX() - tileDistance, xMin - tileDistance, gameMap);
                charactersMap.get(selectedCharacter).setXPos(charactersMap.get(selectedCharacter).getX());
                backend.player.reduceActionPoints(1);
                message = new ArrayList<>();
                message.add("move");
                if(backend.player.isHoster()){

                    message.add("0");
                }else{
                    message.add("1");
                }
                //The next element is the characterID used to know to whom the movement is going to be applied.
                message.add(selectedCharacter+"");
                //The next element represents the direction of the movement
                message.add("left");
                backend.send(message);
            }
            lbActionPointsCurrent.setText(Integer.toString(backend.player.getActionPoints()));
        });

        btEndGame.setOnMouseClicked(e -> { //Switches to gameOver pane
            sceneController.activate("GameOver");
        });

        tileIndex.setOnMouseClicked(e -> {
            System.out.println("Current Tile Index: " + barb.getTileIndex());

        });

        //Create map pane and add buttons, labels, characters, and background

        mapPane.getChildren().addAll(mapImage, charactersMap.get("arch"),charactersMap.get("barb"),charactersMap.get("mage"));

        GridPane gridPane = new GridPane();
        gridPane.setVgap(20);
        gridPane.add(btMoveUp, 1, 0);
        gridPane.add(btMoveRight, 2, 1);
        gridPane.add(btMoveLeft, 0, 1);
        gridPane.add(btMoveDown, 1, 2);
        gridPane.add(btEndGame, 1, 3);
        gridPane.add(tileIndex, 1, 4);
        //gridPane.add(btCharacterSelect, 1, 6);
        gridPane.add(btMove, 0, 7);
        gridPane.add(btAttack, 2, 7);
        gridPane.add(lbActionPoints, 0, 8);
        gridPane.add(lbActionPointsCurrent, 1, 8);
        gridPane.add(selectedCharImage, 1, 9, 2, 1);

        BorderPane bPane = new BorderPane();
        bPane.setLeft(mapPane);
        bPane.setRight(gridPane);

        mapPane.getStyleClass().add("mapBackground");

        return bPane;
    }
}
