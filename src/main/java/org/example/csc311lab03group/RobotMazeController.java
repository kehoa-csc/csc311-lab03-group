package org.example.csc311lab03group;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.*;

public class RobotMazeController {


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView MazeView;

    @FXML
     ImageView playerView;

    private final Group pathMarkers = new Group(); // Used to store path markers

    private int startX = 0;
    private int startY = 0;
    private int targetX = 0;
    private int targetY = 0;

    @FXML
    public void initialize(){
        loadRobotMaze();
        anchorPane.getChildren().add(pathMarkers); // Add path marker group to Pane

        Platform.runLater(() -> anchorPane.requestFocus());
    }


    @FXML
    void AntoPlay(ActionEvent event) {
        int[][] mazeMap = imageTo2DArray(MazeView.getImage());
        int currX = (int)playerView.getX();
        int currY = (int)playerView.getY();
        findPath(mazeMap, currX, currY,targetX,targetY);

        anchorPane.requestFocus();
    }

    @FXML
    void clearAll(ActionEvent event) {
        pathMarkers.getChildren().clear();
        playerView.setX(startX);
        playerView.setY(startY);

        anchorPane.requestFocus();

    }

    // a loading package for robot Maze
    void loadRobotMaze(){
        String player ="robot.png";
        String maze = "maze.png";
        startX = 10;
        startY = 260;
        targetX = 580;
        targetY = 245;

        loadImage(playerView,MazeView,player,maze,startX,startY);
        playerView.setFitWidth(20);
        playerView.setFitHeight(20);
        playerView.setX(startX);
        playerView.setY(startY);
    }

    public void loadImage(ImageView playerView,ImageView MazeView,String player, String maze,int startX,int startY){
        String path = "/org/example/csc311lab03group/";
        Image playerImage = new Image(getClass().getResource(path+ player).toExternalForm());
        playerView.setImage(playerImage);

        Image mazeImage = new Image(getClass().getResource(path+maze).toExternalForm());
        MazeView.setImage(mazeImage);

        // set initial position of player
        playerView.setX(startX);
        playerView.setY(startY);

        //set the size of imageview to its image
        MazeView.setFitWidth(mazeImage.getWidth());
        MazeView.setFitHeight(mazeImage.getHeight());
        MazeView.setPreserveRatio(true);

        //System.out.println(mazeImage.getWidth() + " " + mazeImage.getHeight());
        //System.out.println(MazeView.getFitWidth() + " " + MazeView.getFitHeight());

    }


    // keyboard controller
    public void handleKeyPress(KeyEvent event) {

        int X = (int)playerView.getX();
        int Y = (int)playerView.getY();

        int MOVE_STEP = 5;
        switch (event.getCode()) {
            case UP:
                Y -= MOVE_STEP;
                playerView.setRotate(-90);
                break;
            case DOWN:
                Y += MOVE_STEP;
                playerView.setRotate(90);
                break;
            case LEFT:
                X -= MOVE_STEP;
                playerView.setRotate(0);
                break;
            case RIGHT:
                X += MOVE_STEP;
                playerView.setRotate(0);
                break;
            default:
                break;
        }
        int[][] mazeMap = imageTo2DArray(MazeView.getImage());
        //System.out.println(mazeMap[0].length +" "+mazeMap.length);
        //System.out.println(isValid(mazeMap,X,Y) + "："+X + " "+Y);
        // update the robot position
        if(isValid(mazeMap,X,Y)) {
            playerView.setX(X);
            playerView.setY(Y);
        }
    }

    // translate the maze image to be a 2D Array, 0 is the path, 1 is the wall
    int[][] imageTo2DArray(Image mazeImg) {
        // get width and height of maze image
        int widthMaze = (int) mazeImg.getWidth();
        int heightMaze = (int)mazeImg.getHeight();
        //System.out.println(widthMaze + " " + heightMaze);

        int[][] maze = new int[widthMaze][heightMaze];



        for (int i = 0; i < widthMaze; i++) {
            for (int j = 0; j < heightMaze; j++) {

                Color pixel =mazeImg.getPixelReader().getColor(i ,j);

                if (pixel.equals(Color.WHITE)) { // white is path is 0, other is wall is 1
                    maze[i][j] = 0;
                } else {
                    maze[i][j] = 1;
                }

            }
        }
        return maze;
    }
    static class Point{ // save x and y coordinate
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // find the path in maze , use BFS(Breadth-First Search)
    void findPath(int[][] maze, int startX, int startY, int targetX, int targetY) {
        // define as up , down, right,left
        final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        boolean[][] visited = setVisited(maze);
        Queue<Point> queue = new LinkedList<>();

        Point start = new Point(startX, startY);
        queue.add(start); // add start location point
        visited[startX][startY] = true;

        Map<Point, Point> cameFrom = new HashMap<>(); // save the path
        cameFrom.put(start, null);

        Point end = null;

        //System.out.println("Queue is create "+ !queue.isEmpty());
        while (!queue.isEmpty()) {
            Point current = queue.poll();

            // if the player in target coordinates
            if (current.x == targetX && current.y == targetY) {
                end = current;
                System.out.println("find the path");
                break;
            }

            // search four direction
            for (int[] direction : DIRECTIONS) {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                // check new coordinate is valid
                if (isValid(maze,newX, newY)) {
                    Point next = new Point(newX, newY);
                    if(!visited[newX][newY]) { // not visited is false
                        queue.add(next);
                        visited[newX][newY] = true;
                        cameFrom.put(next, current); // add location to the path
                    }

                }
            }
        }

        // If the end point is found, retrace the path
        if (end != null) {
            List<Point> path = new ArrayList<>();
            Point current = end;

            // Retracing paths through predecessor nodes
            while (current != null) {
                path.add(current);
                current = cameFrom.get(current);
            }

            // Since we are backtracking from the end point to the starting point, we need to reverse the path.
            Collections.reverse(path);

            showPath(path);

        } else {
            System.out.println("not found the path");
        }

    }

    // set visited array
    boolean[][] setVisited(int[][] maze){
        int width = maze[0].length;
        int height = maze.length;

        return new boolean[height][width];
    }

    // shows path in every steps
    void showPath(List<Point> path){
        Timeline timeline = new Timeline();

        for (int i = 0; i < path.size(); i++) {
            final Point step = path.get(i);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 5), event -> {
                playerView.setX(step.x);
                playerView.setY(step.y);
                // draw waypoints
                drawPathMarker(step.x, step.y);
            }));
        }

        timeline.setCycleCount(1);
        timeline.play();
    }

    // Draw a circle on the Pane to mark the path
    void drawPathMarker(int x, int y) {
        // Create a circle to represent the path
        Circle circle = new Circle(x + 12.5, y + 12.5, 5); // The center of the circle is (x + radius), and the radius is 5
        circle.setFill(Color.GREEN); // Set color to green

        // Add circles to pathMarkers group
        pathMarkers.getChildren().add(circle);
    }


    // check the position in maze are on valid paths
    boolean isValid(int[][] maze, int x, int y) {
        int imgWidth = (int)playerView.getFitWidth();
        int imgHeight = (int)playerView.getFitHeight();

        // Check whether the four corners of the image are on valid paths
        if (x < 0 || y < 0 || x + imgWidth >= maze.length || y + imgHeight >= maze[0].length) {
            return false; // Image out of bounds
        }
        // Make sure all four corners are in a valid path (path is 0)
        for (int i = 0; i < imgWidth; i++) {
            for (int j = 0; j < imgHeight; j++) {
                if (maze[x + i][y + j] != 0) {
                    return false; // There is a wall at any point
                }
            }
        }
        return true;
    }


}