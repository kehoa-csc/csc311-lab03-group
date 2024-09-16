package org.example.csc311lab03group;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;

public class MazeController {
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
        //test1();
    }

    // test class, no need to care
    void test1(){
        Image maze = robetMaze.getImage();
        int[][] mazeMap = imageTo2DArray(maze);

       System.out.println(Arrays.deepToString(mazeMap));
    }

    void test2(){
        Image maze = robetMaze.getImage();
        int[][] mazeMap = imageTo2DArray(maze);

        int x = (int)robot.getX();
        int y = (int)robot.getY();


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

    // find the path in maze
    boolean findPath(int[][] maze, int x,int y,int targetX, int targetY, boolean[][] visited) {
        // define as up , down, right,left
        final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        if(x == targetX && x == targetY){
            setFinder(x, y);
            return  true;
        }

        visited[x][y] = true; // mark as passing

        for(int[] direction:DIRECTIONS){
            int newX = x+direction[0];
            int newY = y+direction[1];

            // check new coordinate is valid
            if(isVaild(maze,newX,newY,visited)){
                if(findPath(maze,newX,newY,targetX,targetY,visited)){
                    return  true;
                }
            }
        }


        return false;

    }

    // set the robot going
    void setFinder(int x, int y) {
        try {
            robot.setX(x);
            robot.setY(y);
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    boolean isVaild(int[][]maze, int x, int y, boolean[][]visited){
        int n = maze.length;
        // check it is in range , it is in the path(0 is path in maze), and it is visited
        boolean result = (x >= 0 && y >= 0 &&x < n && y < n)&& (maze[x][y] == 0) && (visited[x][y]);
        return  result;
    }



}