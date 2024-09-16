package org.example.csc311lab03group;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;

public class HelloController {
    @FXML
    private ImageView robot;
    @FXML
    private ImageView robetMaze;
    @FXML
    private Text colorText;

    @FXML
    void MoveRobot(ActionEvent event) {
        robot.setX(robot.getX()+5);
        int x = (int)robot.getX();
        int y = (int)robot.getY();

        PixelReader pixelReader = robetMaze.getImage().getPixelReader();
        Color color = pixelReader.getColor(x,y);
        colorText.setText(x+","+y+":" +color.toString());
    }

    @FXML
    void getColor(ActionEvent event) {
        /*
        robot.setY(robot.getY()+5);
        int x = (int)robot.getX();
        int y = (int)robot.getY();

        PixelReader pixelReader = robetMaze.getImage().getPixelReader();
        Color color = pixelReader.getColor(x,y);
        colorText.setText(x+","+y+":" +color.toString());

         */
        test1();
    }

    // test class, no need to care
    void test1(){
        Image maze = robetMaze.getImage();
        int[][] mazeMap = imageTo2DArray(maze);

       System.out.println(Arrays.deepToString(mazeMap));
    }

    // translate the maze image to be a 2D Array, 0 is the path, 1 is the wall
    int[][] imageTo2DArray(Image mazeImg) {
        // get width and height of maze image
        int widthMaze = (int) mazeImg.getWidth();
        int heightMaze = (int) mazeImg.getHeight();

        int[][] maze = new int[widthMaze][heightMaze];

        for (int i = 0; i < widthMaze; i++) {
            for (int j = 0; j < heightMaze; j++) {

                Color pixel =mazeImg.getPixelReader().getColor(i,j);

                if (pixel.equals(Color.WHITE)) { // white is path is 0, other is wall is 1
                    //System.out.println(0+" " + pixel);
                    maze[i][j] = 0;
                } else {
                    //System.out.println(1+" " + pixel);
                    maze[i][j] = 1;
                }

            }
        }
        return maze;
    }


}