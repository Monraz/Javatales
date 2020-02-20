package Panes;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This class generates the Pane for the Map Selection screen
 */
public class SelectMap {
    public SceneController sceneController;
    
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
    public SelectMap() {

    }
    
    /**
     * Creates and returns a Pane with the map selection screen
     * 
     * @return Pane Select Map Screen
     */
    public Pane getPane() {
        //Create labels
        final Label lbHeader = new Label("Choose A Map");
        lbHeader.setFont(Font.font("Courier", FontWeight.BOLD, 30));
        final Label lbMap1 = new Label("Map 1");
        final Label lbMap2 = new Label("Map 2");
        final Label lbMap3 = new Label("Map 3");
        final Label lbMap4 = new Label("Map 4");
        final Label lbMap5 = new Label("Map 5");
        
        //Create map images
        // Placeholder maps until Map class is integrated
        ImageView imgMap1 = new ImageView("images/bg1.png");
        // ImageView imgMap2 = new ImageView("images/bg2.png");
        // ImageView imgMap3 = new ImageView("images/bg3.png");
        // ImageView imgMap4 = new ImageView("images/bg4.png");
        // ImageView imgMap5 = new ImageView("images/bg5.png"); 

        //Create gridPane
        GridPane mapView = new GridPane();
        mapView.setHgap(10);
        mapView.setVgap(10);
        mapView.setAlignment(Pos.CENTER);
        
        //Add map images and labels to gridPane
        mapView.add(lbMap1, 0, 0);
        mapView.add(imgMap1, 0, 1);
        GridPane.setHalignment(lbMap1, HPos.CENTER);
        // mapView.add(lbMap2, 1, 0);
        // mapView.add(imgMap2, 1, 1);
        // GridPane.setHalignment(lbMap2, HPos.CENTER);
        // mapView.add(lbMap3, 2, 0);
        // mapView.add(imgMap3, 2, 1);
        // GridPane.setHalignment(lbMap3, HPos.CENTER);
        // mapView.add(lbMap4, 0, 2);
        // mapView.add(imgMap4, 0, 3);
        // GridPane.setHalignment(lbMap4, HPos.CENTER);
        // mapView.add(lbMap5, 1, 2);
        // mapView.add(imgMap5, 1, 3);
        // GridPane.setHalignment(lbMap5, HPos.CENTER);

        //Create borderPane and add label and gridPane
        BorderPane pane = new BorderPane();
        pane.setTop(lbHeader);
        BorderPane.setAlignment(lbHeader, Pos.CENTER);
        BorderPane.setMargin(lbHeader, new Insets(50));
        pane.setCenter(mapView);

        //Set map image action
        imgMap1.setOnMouseClicked(e -> { //Selects the chosen map
            sceneController.activate("MapScreen"); //PLACEHOLDER
        });
        /* NOT YET IMPLEMENTED
        imgMap2.setOnMouseClicked(e -> {
            
        });
        imgMap3.setOnMouseClicked(e -> {
            
        });
        imgMap4.setOnMouseClicked(e -> {
            
        });
        imgMap5.setOnMouseClicked(e -> {
            
        });
        */
        return pane;
    }
}
