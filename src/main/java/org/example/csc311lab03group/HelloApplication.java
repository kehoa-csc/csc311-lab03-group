package org.example.csc311lab03group;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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