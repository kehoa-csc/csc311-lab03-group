package org.example.csc311lab03group;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static org.example.csc311lab03group.HelloApplication.currKey;

public class Car {

    private ImageView carImg;

    private Timeline timeline;

    private boolean canMove() {
        //put here with the pixelReader if it can move.
        return true;
    }

    public Car(ImageView carImg, Timeline timeline) {
        this.carImg = carImg;
        System.out.println("made car");

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(currKey.getName().equals("Up") && carImg.getLayoutY()>=0) {
                    carImg.setLayoutY(carImg.getLayoutY()-1);
                    carImg.setRotate(-90);
                    carImg.setScaleX(1);
                } else if(currKey.getName().equals("Down") && carImg.getLayoutY()<=500) {
                    carImg.setLayoutY(carImg.getLayoutY()+1);
                    carImg.setRotate(90);
                    carImg.setScaleX(1);
                } else if(currKey.getName().equals("Left") && carImg.getLayoutX()>=0) {
                    carImg.setLayoutX(carImg.getLayoutX()-1);
                    carImg.setRotate(0);
                    carImg.setScaleX(-1);
                } else if(currKey.getName().equals("Right") && carImg.getLayoutX()<=700) {
                    carImg.setLayoutX(carImg.getLayoutX()+1);
                    carImg.setRotate(0);
                    carImg.setScaleX(1);
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        System.out.println("set timeline");
    }


}





