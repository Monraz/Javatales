package Panes;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.util.HashMap;

/**
 * Each pane is stored as a class in the panes package. Each class has a
 * sceneController object that allows the application to switch panes.
 * 
 * @author Christopher
 */
public class SceneController {
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    /**
     * Default constructor
     * @param main 
     */
    public SceneController(Scene main) {
        this.main = main;
    }

    /**
     * 
     * @param name
     * @param pane 
     */
    public void addScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    /**
     * 
     * @param name 
     */
    public void removeScreen(String name) {
        screenMap.remove(name);
    }

    /**
     * 
     * @param name 
     */
    public void activate(String name) {
        main.setRoot(screenMap.get(name));
    }

    public Pane getPane(String name) {
        return  screenMap.get(name);
    }

    public void setScreenMap(HashMap<String, Pane> screenMap) {
        this.screenMap = screenMap;
    }

    public void removeAll(){
        screenMap.clear();
    }
}
