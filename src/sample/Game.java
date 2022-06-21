package sample;

import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static sample.Intro.soundButton;

public class Game {
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("sample/css/style.css");



        primaryStage.setResizable(false);
        root.getChildren().addAll(soundButton, Intro.nameLabel);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
