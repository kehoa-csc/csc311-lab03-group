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
        test();
    }

    // test class, no need to care
    void test(){
        Image finderImg = robot.getImage();
        Image mazeImg = robetMaze.getImage();
        int widthFinder = (int) finderImg.getWidth();
        int heightFinder = (int) finderImg.getHeight();
        int widthMaze = (int)mazeImg.getWidth() / widthFinder; // size of path should be able to allow finder passing
        int heightMaze =(int)mazeImg.getHeight() / heightFinder;
        colorText.setText(widthFinder + "," +heightFinder + " : "+mazeImg.getWidth()+ "," + mazeImg.getHeight());
    }

    // translate the maze image to be a 2D Array, 0 is the path, 1 is the wall
    void imageTo2DArray(Image finderImg, Image mazeImg){
        // get width and height of finder and maze image
        int widthFinder = (int) finderImg.getWidth();
        int heightFinder = (int) finderImg.getHeight();
        int widthMaze = (int)mazeImg.getWidth() / widthFinder; // size of path should be able to allow finder passing
        int heightMaze =(int)mazeImg.getHeight() / heightFinder;

        int[][] maze = new int[widthMaze][heightMaze];

        for (int i = 0; i < widthMaze; i++) {
            for (int j = 0; j < heightMaze; j++) {
                boolean isWall = false;
                for (int x = 0; x < widthFinder; x++) {
                    for (int y = 0; y < heightFinder; y++) {
                        int pixel = mazeImg.getPixelReader().getArgb(i * widthFinder + x, j * heightFinder + y);
                        if (pixel != 0xFFFFFFFF) {
                            isWall = true;
                            break;
                        }
                    }
                    if (isWall) break;
                }
                maze[i][j] = isWall ? 1 : 0;

            }

        }
    }


}