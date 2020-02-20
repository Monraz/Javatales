package javaTales;

import Backend.Backend;
import Backend.Server;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.*;
import Panes.*;

import java.util.Base64;

/**
 * This class is the main class of the project. Its duty is to initialize the
 * application with the introduction window and to set the SceneController of
 * the project to jump between the different panes that constitute the project.
 *
 * @author Christopher
 */
public class JavaTales extends Application {


    public SelectChar selectChar = null;
    public SelectMap selectMap = null;
    public MapScreen mapScreen = null;
    public GameOver gameOver = null;
    public JoinServer joinServer = null;
    public Intro intro = null;

    Backend backend = null;
    Server server = null;
    public SceneController sceneController = null;

    /**
     * main class used to launch the program
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    //This backgroundImage is used to set the background of the window.
    /*BackgroundImage myBI = new BackgroundImage(new Image("images/background_Title.jpg"),
            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);*/
    @Override
    /**
     *
     * The start class is used to generate the Pane with the introduction
     * options to start or continue a gameplay
     *
     * @param primaryStage
     */
    public void start(Stage primaryStage) {


        backend = new Backend();
        server = new Server();
        backend.setJavaTales(this);
        Thread thread = new Thread(backend);
        thread.setDaemon(true);
        thread.start();
        //stackPane.setBackground(new Background((myBI)));

        Scene scene = new Scene(new StackPane(), 1500, 1000);
        scene.getStylesheets().add("style/mystyle.css");


        /*
         * Scenecontroller is used to switch between the different panes of the application*/
        sceneController = new SceneController(scene);


        /*Each pane of the application was stored as classes in the Package named "Panes"
         * Each of this classes has a variable member of type SceneController, this member allows the pane to switch between the
         * different panes.
         **/
        //The different classes that store the panes of the games are initialized
        selectChar = new SelectChar();
        selectMap = new SelectMap();
        mapScreen = new MapScreen();
        gameOver = new GameOver();
        joinServer = new JoinServer();
        intro = new Intro();

        joinServer.setServer(server);
        joinServer.setBackend(backend);
        mapScreen.setBackend(backend);

        //The panes of each class are added to the SceneController
        sceneController.addScreen("Select Character", selectChar.getPane());
        sceneController.addScreen("Select Map", selectMap.getPane());
        sceneController.addScreen("MapScreen", mapScreen.getPane());
        sceneController.addScreen("GameOver", gameOver.getPane());
        sceneController.addScreen("JoinServer", joinServer.getPane());
        sceneController.addScreen("Intro", intro.getPane());

        //The Scenecontroller from the main application is passed to all the pane classes,
        //By doing that each pane knows which panes the application has and can switch to them
        selectChar.setSceneController(sceneController);
        selectMap.setSceneController(sceneController);
        mapScreen.setSceneController(sceneController);
        gameOver.setSceneController(sceneController);
        joinServer.setSceneController(sceneController);
        intro.setSceneController(sceneController);







        primaryStage.setTitle("Java Tales");
        //The scene is added to the Stage
        primaryStage.setScene(scene);
        sceneController.activate("Intro");

        primaryStage.show();

    }
}
