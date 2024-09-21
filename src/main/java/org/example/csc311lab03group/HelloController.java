package org.example.csc311lab03group;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.image.PixelReader;
import javafx.util.Duration;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class HelloController {
    @FXML
    private ImageView robot;

    @FXML
    private Button carStart;

    @FXML
    private Button robotStart;

    @FXML
    private ImageView robetMaze;

    @FXML
    private ImageView carImg;

    @FXML
    private TabPane tabPn;

    public int curr = 0;
    private Car car;

    private double[][] robotPath = {
            {35, 215}, {35, 120}, {220, 120}, {220, 75}, {267, 75}, {267, 265}, {315, 265}, {315, 170}, {410, 170}, {410, 80}, {460, 80}
            , {460, 200}, {550, 200}
    };


    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            double nextX = robotPath[curr][0];
            double nextY = robotPath[curr][1];

            robot.setLayoutX(nextX);
            robot.setLayoutY(nextY);
            curr++;
        }
    }));

    @FXML
    public void robotMov() {
        timeline.setCycleCount(robotPath.length);
        timeline.play();
        robotStart.setDisable(true);
    }

    @FXML
    public void makeCar() {
        //car = new Car(carImg, timeline);
        carStart.setFocusTraversable(false);
        tabPn.setFocusTraversable(false);
        tabPn.setDisable(true);
    }
    public void carInitialized(PixelReader pr){
        this.car = new Car(carImg, timeline, pr);
    }
}

