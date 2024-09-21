package org.example.csc311lab03group;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static KeyCode currKey = KeyCode.ENTER; //enter is not used for anything

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Maze");
        stage.setScene(scene);
        stage.show();

        // Get the maze image from the resources file
        Image mazesImage = new Image(getClass().getResourceAsStream("Maze2.png"));

    // Use PixelReader to get pixel data in the maze
    PixelReader pr = mazesImage.getPixelReader();
    // Get the controller from the FXML loader
    HelloController hc = fxmlLoader.getController();

    // Initialize car in the HelloController using PixelReader
        hc.carInitialized(pr);
        scene.setOnKeyPressed(e -> {
        currKey = e.getCode();
    });
        scene.setOnKeyReleased(e -> {
        currKey = KeyCode.ENTER;
    });
}


    public static void main(String[] args) {
        launch();
    }
}