package org.example.csc311lab03group;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static org.example.csc311lab03group.HelloApplication.currKey;

public class Car {

    private ImageView carImg;

    private Timeline timeline;
    private PixelReader pr;

    public Car(ImageView carImg, Timeline timeline, PixelReader pr) {
        this.carImg = carImg;
        this.pr = pr;
        System.out.println("made car");
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double nexY = carImg.getLayoutY();
                double nexX = carImg.getLayoutX();
                //System.out.println(nexX + " " + nexY);
                if(currKey.getName().equals("Up") && nexY > 0 && canMove(nexX,nexY-1)) {
                    carImg.setLayoutY(nexY-1);
                    carImg.setRotate(-90);
                    carImg.setScaleX(1);
                } else if(currKey.getName().equals("Down") && nexY < 350 && canMove(nexX,nexY+1)) {
                    carImg.setLayoutY(nexY+1);
                    carImg.setRotate(90);
                    carImg.setScaleX(1);
                } else if(currKey.getName().equals("Left") && nexX > 0 && canMove(nexX-1,nexY)) {
                    carImg.setLayoutX(nexX-1);
                    carImg.setRotate(0);
                    carImg.setScaleX(-1);
                } else if(currKey.getName().equals("Right") && nexX < 470 && canMove(nexX+1,nexY)) {
                    carImg.setLayoutX(nexX+1);
                    carImg.setRotate(0);
                    carImg.setScaleX(1);
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        System.out.println("set timeline");
    }

    private boolean canMove (double x, double y) {
        //put here with the pixelReader if it can move.
        Color c = pr.getColor((int)x,(int) y);

        return c.equals(Color.WHITE);
    }
}