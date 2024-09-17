package org.example.csc311lab03group;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MazeApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("maze-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> {
            MazeController controller = loader.getController();
            controller.handleKeyPress(event);
        });

        stage.setTitle("Maze");
        stage.setScene(scene);
        stage.show();

        root.requestFocus();
    }


    public static void main(String[] args) {
        launch();
    }
}