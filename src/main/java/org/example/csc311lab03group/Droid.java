package org.example.csc311lab03group;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class Droid {
    @FXML
    Button robotStart;

    @FXML
    ImageView robot;


    public void autoRobot(ImageView robot){
        this.robot = robot;
        robotStart.onActionProperty(

        );




    }
}
