package sample;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.bot.Bot;


import static sample.Intro.soundButton;
import static sample.Intro.soundButtonClick;

public class Difficulty extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("sample/css/style.css");

        //difficulty
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setLayoutX(465);
       hBox.setLayoutY(380);
        hBox.setSpacing(150);

        Button normalButton = new Button("Normal");
        normalButton.setId("normalButton");
        Button hardButton = new Button("Hard");
        hardButton.setId("hardButton");
        normalButton.getStyleClass().add("difficulty");
        hardButton.getStyleClass().add("difficulty");

        normalButton.setOnAction(e->{
            try {
                this.changeScene(primaryStage);
                Bot.mode = 1;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        hardButton.setOnAction(e->{
            try {
                this.changeScene(primaryStage);
                Bot.mode = 2;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        hBox.getChildren().addAll(normalButton, hardButton);
        //---------------------------------

        // soundButton
        // set lai thuoc tinh de debug
        soundButton.setId("soundButton");
        soundButton.setLayoutX(1200);
        soundButton.setLayoutY(650);
        soundButton.setOnAction(e->{
            soundButtonClick();
        });
        //---------------------------

        root.getChildren().addAll(soundButton, hBox, Intro.nameLabel);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void changeScene(Stage stage) throws Exception { //ham chuyen scene
        SelectNation selectNation = new SelectNation();
        selectNation.start(stage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
