package org.example.csc311lab03group;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class MainController {

    @FXML
    private AnchorPane CarMaze;

    @FXML
    private AnchorPane RobotMaze;

    @FXML
    private TabPane tabPane;

    private RobotMazeController robotMazeController;
    private CarMazeController carMazeController;

    @FXML
    public void initialize() {
        try {
            //Load FXML for car view
            FXMLLoader carLoader = new FXMLLoader(getClass().getResource("car-view.fxml"));
            AnchorPane carPane = carLoader.load();
            carMazeController = carLoader.getController();  // Get CarMazeController instance
            CarMaze.getChildren().setAll(carPane);

            // Load FXML for robot view
            FXMLLoader robotLoader = new FXMLLoader(getClass().getResource("robot-view.fxml"));
            AnchorPane robotPane = robotLoader.load();
            robotMazeController = robotLoader.getController();  // Get RobotMazeController instance
            RobotMaze.getChildren().setAll(robotPane);

        } catch (IOException e) {
            e.printStackTrace();
        }

        tabPane.setFocusTraversable(false);

        tabPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN ||
                    event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
                // Disable the default behavior of the arrow keys
                event.consume();
                // Pass the event to the current maze controller for processing
                handleKeyPress(event);
            }
        });



    }
    public void handleKeyPress(KeyEvent event) {
        if (tabPane.getSelectionModel().getSelectedIndex() == 0) {
            robotMazeController.handleKeyPress(event);
        } else {
            carMazeController.handleKeyPress(event);
        }
    }

}