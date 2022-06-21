package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static sample.Intro.soundButton;
import static sample.Intro.soundButtonClick;

public class SelectNation extends Application {
    public static int nation = 1; // type of fleet
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

        Button nation1 = new Button("nation1");
        Button nation2 = new Button("nation2");
        nation1.setOnAction(e->{
            nation = 1;
            toSetLineUp(e);
        });
        nation2.setOnAction(e->{
            nation = 2;
            toSetLineUp(e);
        });
        nation1.getStyleClass().add("nation");
        nation2.getStyleClass().add("nation");

        hBox.getChildren().addAll(nation1, nation2);

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

    private void toSetLineUp(ActionEvent e) {
        SetLineup setLineup = new SetLineup();
        Stage primaryStage = (Stage) ((Node) e.getTarget()).getScene().getWindow();
        try {
            setLineup.start(primaryStage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
