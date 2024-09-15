package org.example.csc311lab03group;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

import java.io.IOException;

public class Droid {
public ImageView robot;
private  double [][] robotpath;
public  int curr =0;

public Droid(ImageView robot, double [][]robotpath){
    this.robot = robot;
    this.robotpath= robotpath;

}

public void robotMov(){
    if( curr < robotpath.length){
        double nextX = robotpath[curr][0];
        double nextY = robotpath[curr][1];

        robot.setLayoutX(nextX);
        robot.setLayoutY(nextY);
        curr++;
    }
}
}
