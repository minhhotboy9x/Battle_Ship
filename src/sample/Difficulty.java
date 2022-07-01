package sample;
import javafx.application.Application;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.bot.Bot;
import sample.model.data.Data;

import static sample.Intro.soundButton;
import static sample.Intro.soundButtonClick;

public class Difficulty extends Application{
    static public String userNameText;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("sample/css/style.css");

        Label username = new Label(" UserName:");
        username.setId("username");
        username.setTextFill(Color.BLACK);
        username.setFont(Font.font(24));
        username.setLayoutX(430);
        username.setLayoutY(350);

        TextField fieldName = new TextField();
        fieldName.setId("fieldName");
        fieldName.setFont(Font.font(24));
        fieldName.setLayoutX(590);
        fieldName.setLayoutY(350);

        Button normalButton = new Button("Normal");
        normalButton.setId("normalButton");
        Button hardButton = new Button("Hard");
        hardButton.setId("hardButton");
        normalButton.getStyleClass().add("difficulty");
        hardButton.getStyleClass().add("difficulty");

        normalButton.setDisable(true);
        hardButton.setDisable(true);

        //  Khi nhập user name mới cho hiện chọn chế độ khó dễ
        fieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            hardButton.setDisable(newValue.trim().isEmpty());
            normalButton.setDisable(newValue.trim().isEmpty());
        });
        //----------------------------------



        normalButton.setOnAction(e->{
            try {

//                String userName = normalButton.getText();
//                Data data = new Data();
//                data.setUserName(userName);

                this.changeScene(primaryStage);
                Bot.mode = 1;
                userNameText = fieldName.getText();



            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        hardButton.setOnAction(e->{
            try {
                this.changeScene(primaryStage);
                Bot.mode = 2;
                userNameText = fieldName.getText();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        //---------------------------------

        // tao gridPane
        GridPane gridPane = new GridPane();
        gridPane.setId("gridPane");
        gridPane.setMinSize(450, 30);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setLayoutX(400);
        gridPane.setLayoutY(300);

        gridPane.add(username, 0 , 0);
        gridPane.add(fieldName, 1, 0);
        gridPane.add(normalButton, 0, 1);
        gridPane.setHalignment(hardButton, HPos.RIGHT);
        gridPane.add(hardButton, 1, 1);

        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);  // khoang cach giua 2 hang
        gridPane.setVgap(10);  // khoang cach giua 2 cot
        //-----------

        // soundButton
        // set lai thuoc tinh de debug
        soundButton.setId("soundButton");
        soundButton.setLayoutX(1200);
        soundButton.setLayoutY(650);
        soundButton.setOnAction(e->{
            soundButtonClick();
        });
        //---------------------------

        //root.getChildren().addAll(soundButton, gridPane ,hBox, Intro.nameLabel);
        root.getChildren().addAll(soundButton, gridPane, Intro.nameLabel);
        primaryStage.setScene(scene);
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
