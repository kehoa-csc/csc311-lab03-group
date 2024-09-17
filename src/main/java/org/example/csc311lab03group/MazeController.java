package org.example.csc311lab03group;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.Queue;

public class MazeController {
    @FXML
    private ImageView playerView;

    @FXML
    private ImageView MazeView;
    @FXML
    private Text colorText;

    @FXML
    private Pane pane;

    private int startX = 10;
    private int startY = 260;
    private int targetX = 100;
    private int targetY = 0;
    private int MOVE_STEP = 5;

    @FXML
    public void initialize(){
        String path = "/org/example/csc311lab03group/";
        Image playerImage = new Image(getClass().getResource(path+"robot.png").toExternalForm());
        playerView.setImage(playerImage);

        Image mazeImage = new Image(getClass().getResource(path+"maze.png").toExternalForm());
        MazeView.setImage(mazeImage);

        playerView.setX(startX);
        playerView.setY(startY);




    }

    @FXML
    void MoveRobot(ActionEvent event) {
        int[][] maze = imageTo2DArray(MazeView.getImage());
        findPath(maze, startX, startY,targetX,targetY);

    }


    public void handleKeyPress(KeyEvent event) {

        int X = (int)playerView.getX();
        int Y = (int)playerView.getY();

        switch (event.getCode()) {
            case UP:
                Y -= MOVE_STEP;
                break;
            case DOWN:
                Y += MOVE_STEP;
                break;
            case LEFT:
                X -= MOVE_STEP;
                break;
            case RIGHT:
                X += MOVE_STEP;
                break;
            default:
                break;
        }
        int[][] maze = imageTo2DArray(MazeView.getImage());
        // update the robot position
        if(canMove(maze,X,Y)) {
            setPlayer(X, Y);
        }
    }

    // check for keyboard moving
    boolean canMove(int[][]maze, int x, int y){
        int width = maze[0].length;
        int height = maze.length;
        // check it is in range , it is in the path(0 is path in maze)
        boolean result = (x >= 0 && y >= 0 && x < height && y < width)&& (maze[x][y] == 0);
        System.out.println(x +" " +y );
        System.out.println("isValid " + result);
        return  result;
    }


    // translate the maze image to be a 2D Array, 0 is the path, 1 is the wall
    int[][] imageTo2DArray(Image mazeImg) {
        // get width and height of maze image
        int widthMaze = (int) mazeImg.getWidth();
        int heightMaze = (int) mazeImg.getHeight();
        //System.out.println(widthMaze + " " + heightMaze);

        int[][] maze = new int[widthMaze][heightMaze];

        for (int i = 0; i < widthMaze; i++) {
            for (int j = 0; j < heightMaze; j++) {

                Color pixel =mazeImg.getPixelReader().getColor(i,j);

                if (pixel.equals(Color.WHITE)) { // white is path is 0, other is wall is 1
                    maze[i][j] = 0;
                } else {
                    maze[i][j] = 1;
                }

            }
        }
        return maze;
    }
    class Point{ // save x and y coordinate
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // find the path in maze , use BFS(Breadth-First Search)
    boolean findPath(int[][] maze, int startX,int startY,int targetX, int targetY) {
        // define as up , down, right,left
        final int[][] DIRECTIONS = {{-25, 0}, {25, 0}, {0, -25}, {0, 25}};

        boolean[][] visited = setVisited(maze);
        Queue<Point> queue = new LinkedList<>();

        queue.add(new Point(startX, startY)); // add start location point
        visited[startX][startY] = true;

        //System.out.println("Queue is create "+ !queue.isEmpty());
        while (!queue.isEmpty()) {
            Point current = queue.poll();

            // if the player in target coordinates
            if (current.x == targetX && current.y == targetY) {
                setPlayer(current.x, current.y);
                System.out.println("find the path");
                return true;
            }

            setPlayer(current.x, current.y); // update the location

            // search four direction
            for (int[] direction : DIRECTIONS) {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                // check new coordinate is valid
                if (isVaild(maze, newX, newY, visited)) {
                    queue.add(new Point(newX, newY));
                    visited[newX][newY] = true;
                }
            }
        }

        System.out.println("not found the path");
        return false;

    }

    // set visited array
    boolean[][] setVisited(int[][] maze){
        int width = maze[0].length;
        int height = maze.length;

        boolean[][] visited = new boolean[height][width];
        return visited;
    }
    // let the robot going
    void setPlayer(int x, int y) {

        Platform.runLater(() -> {
            playerView.setX(x);
            playerView.setY(y);
            colorText.setText(x +","+y);
        });

        try {
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    boolean isVaild(int[][]maze, int x, int y, boolean[][]visited){
        int width = maze[0].length;
        int height = maze.length;
        // check it is in range , it is in the path(0 is path in maze), and it is not visited
        boolean result = (x >= 0 && y >= 0 && x < height && y < width)&& (maze[x][y] == 0) && (!visited[x][y]);
        System.out.println(x +" " +y );
        System.out.println("isValid " + result);
        return  result;
    }


}