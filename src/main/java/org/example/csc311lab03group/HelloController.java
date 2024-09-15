package org.example.csc311lab03group;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

public class HelloController {
    @FXML
    private ImageView robot;

    @FXML
    private Button robotStart;

    @FXML
    private ImageView robetMaze;

    private Droid droid;

    public  int curr =0;

    private double[][] robotPath ={
            {0,0}, {10,20}
    };




    public HelloController( ImageView robot, double [][]robotpath){
        this.robot = robot;
        this.robotPath= robotpath;

    }
    @FXML
    public void robotMov() {
        if (curr < robotPath.length) {
            double nextX = robotPath[curr][0];
            double nextY = robotPath[curr][1];

            robot.setLayoutX(nextX);
            robot.setLayoutY(nextY);
            curr++;
        }
    }



    @FXML
    public void initalize(){
       droid = new Droid(robot,robotPath);

        robotStart.setOnAction(e-> System.out.println("hi"));
    }

}