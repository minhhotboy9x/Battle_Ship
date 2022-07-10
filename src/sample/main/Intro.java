package sample.main;

import javafx.application.Application;

import javafx.css.PseudoClass;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.sound.AudioPlayer;


public class Intro extends Application {

    // background song
    public static AudioPlayer backgroundSound = new AudioPlayer("Kaizoku_Sentai_Gokaiger_BGM_-_Kaiz_(getmp3.pro)");
    public static Button soundButton = new Button();
    public static Label nameLabel = new Label();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("sample/css/style.css");
        //
        backgroundSound.run();
        //-----------

        //set ten intro
        Image img = new Image(getClass().getResource("../../resource/image/intro/Battleship.gif").toString());
        ImageView view = new ImageView(img);
        nameLabel.setGraphic(view);
        nameLabel.setTranslateX(481);
        nameLabel.setTranslateY(50);
        //

        // play button
        Button playButton = new Button("Play");
        playButton.setId("playButton");

        playButton.setLayoutX(525);
        playButton.setLayoutY(380);
        playButton.setOnAction(e->{ //change scene
            Difficulty secondScene = new Difficulty();
            try {
                secondScene.start(primaryStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        //-------


        // high score button
        Button highScoreButton = new Button("High Score");
        highScoreButton.setId("highScoreButton");

        highScoreButton.setLayoutX(525);
        highScoreButton.setLayoutY(450);
        highScoreButton.setOnAction(e->{ //change scene
            HighScore highScoreScene = new HighScore();
            try {
                highScoreScene.start(primaryStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        //-------

        // exit button
        Button exitButton = new Button("Exit");
        exitButton.setId("exitButton");

        exitButton.setLayoutX(525);
        exitButton.setLayoutY(520);
        exitButton.setOnAction(e->{
            ((Stage)root.getScene().getWindow()).close();
        });
        //-------

        //soundButton
        soundButton.setId("soundButton");
        soundButton.setLayoutX(1200);
        soundButton.setLayoutY(650);
        soundButton.setOnAction(e->{
            soundButtonClick();
        });
        //
        root.getChildren().addAll(playButton, highScoreButton, exitButton ,soundButton, nameLabel);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setTitle("Battle_ship");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void soundButtonClick() {
        backgroundSound.mute();
        if(AudioPlayer.isMuted()) {
            soundButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("muted"), true);
        }
        else {
            soundButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("muted"), false);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
