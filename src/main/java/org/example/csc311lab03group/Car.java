package org.example.csc311lab03group;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class Car {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}
