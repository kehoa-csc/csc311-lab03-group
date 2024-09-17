package org.example.csc311lab03group;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;

public class MazeController {
    @FXML
    private ImageView playerView;

    @FXML
    private ImageView MazeView;
    @FXML
    private Text colorText;

    @FXML
    private Pane pane;

    private final int startX = 10;
    private final int startY = 260;
    private final int targetX = 580;
    private final int targetY = 250;
    private final int MOVE_STEP = 5;

    @FXML
    public void initialize(){
        String path = "/org/example/csc311lab03group/";
        Image playerImage = new Image(getClass().getResource(path+"robot.png").toExternalForm());
        playerView.setImage(playerImage);

        Image mazeImage = new Image(getClass().getResource(path+"maze.png").toExternalForm());
        MazeView.setImage(mazeImage);

        playerView.setX(startX);
        playerView.setY(startY);
        MazeView.setX(0);
        MazeView.setY(0);

    }

    @FXML
    void MoveRobot(ActionEvent event) {
       int[][] maze = imageTo2DArray(MazeView.getImage());
       findPath(maze, startX, startY,targetX,targetY);

    }
    @FXML
    void Restart(ActionEvent event) {
        playerView.setX(startX);
        playerView.setY(startY);
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
        if(canMove(maze,X,Y,event)) {
            setPlayer(X, Y);
        }
    }

    // check for keyboard moving
    boolean canMove(int[][]maze, int x, int y,KeyEvent event){
        int playerWidth = (int) playerView.getFitWidth();
        int playerHeight = (int) playerView.getFitHeight();

        switch (event.getCode()){
            case UP:
                // check top edge is the wall (0,-1) to (24,-1)
                return isOk(maze,x,y-1)&&isOk(maze,x+playerWidth-1,y-1);
            case DOWN:
                // check down edge is the wall (0,25) to (24,25)
                return isOk(maze,x,y+playerHeight)&&isOk(maze,x+playerWidth-1,y+playerHeight);
            case LEFT:
                // check left edge is the wall (-1,0) to (-1,24)
                return isOk(maze,x-1,y)&&isOk(maze,x-1,y+playerHeight-1);
            case RIGHT:
                // check left edge is the wall (25,0) to (25,24)
                return isOk(maze,x+playerWidth,y)&&isOk(maze,x+playerWidth,y+playerHeight-1);
            default:
                return  false;
        }

    }
    boolean isOk(int[][]maze, int x, int y){
        int width = maze[0].length;
        int height = maze.length;
        // check it is in range , it is in the path(0 is path in maze)
        return (x >= 10 && y >= 10 && x < height && y < width)&& (maze[x][y] == 0);
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
                if (isValid(maze,newX, newY,visited)) {
                    Point next = new Point(newX, newY);

                    queue.add(next);
                    visited[newX][newY] = true;
                    cameFrom.put(next, current); // add location to the path

                }
            }
        }

        // 如果找到终点，则回溯路径
        if (end != null) {
            List<Point> path = new ArrayList<>();
            Point current = end;

            // 通过前驱节点回溯路径
            while (current != null) {
                path.add(current);
                current = cameFrom.get(current);
            }

            // 由于是从终点回溯到起点，需要反转路径
            Collections.reverse(path);

            // 显示路径并绘制路径点
            showPath(path, pane);

            return true;
        } else {
            System.out.println("not found the path");
            return false;
        }

    }

    // set visited array
    boolean[][] setVisited(int[][] maze){
        int width = maze[0].length;
        int height = maze.length;

        boolean[][] visited = new boolean[height][width];
        return visited;
    }

    // shows path in every steps
    void showPath(List<Point> path,Pane pane){
        Timeline timeline = new Timeline();

        for (int i = 0; i < path.size(); i++) {
            final Point step = path.get(i);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 10), event -> {
                playerView.setX(step.x);
                playerView.setY(step.y);
                colorText.setText(step.x + "," + step.y);

                // 绘制路径点
                drawPathMarker(step.x, step.y, pane);
            }));
        }

        timeline.setCycleCount(1);
        timeline.play();
    }

    // 在 Pane 上绘制圆圈标记路径
    void drawPathMarker(int x, int y, Pane pane) {
        // 创建一个圆圈表示路径
        Circle circle = new Circle(x + 12.5, y + 12.5, 5); // 圆心是 (x + 半径), 半径为 5
        circle.setFill(Color.GREEN); // 设置颜色为绿色

        // 将圆圈添加到 Pane
        pane.getChildren().add(circle);
    }
    // shows the robot going
    void setPlayer(int x, int y) {
        playerView.setX(x);
        playerView.setY(y);
        colorText.setText(x + "," + y);
    }



    boolean isValid(int[][] maze, int x, int y, boolean[][] visited) {
        int imgSize = 25; // 图片大小25x25

        // 检查图片的四个角是否都在有效路径上
        if (x < 0 || y < 0 || x + imgSize >= maze.length || y + imgSize >= maze[0].length) {
            return false; // 图片超出边界
        }

        // 确保四个角都在有效路径中（路径值为0）
        for (int i = 0; i < imgSize; i++) {
            for (int j = 0; j < imgSize; j++) {
                if (maze[x + i][y + j] != 0) {
                    return false; // 任意一个点处有墙
                }
            }
        }

        // 检查当前点是否已经访问过
        return !visited[x][y];
    }


}