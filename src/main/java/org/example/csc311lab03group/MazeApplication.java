package org.example.csc311lab03group;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MazeApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,720,460);

        scene.setOnKeyPressed(event -> {
            MainController controller = loader.getController();
            controller.handleKeyPress(event);
        });

        stage.setTitle("Maze");
        stage.setScene(scene);
        stage.show();

        Platform.runLater(root::requestFocus);
    }


    public static void main(String[] args) {
        launch();
    }
}