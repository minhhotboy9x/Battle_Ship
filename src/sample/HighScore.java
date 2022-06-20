package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HighScore extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private TableView table = new TableView();


    @Override
    public void start(Stage stage)throws IOException {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("sample/css/style.css");

        // tao label
        final Label label = new Label("High Score");
        label.setId("highScoreLabel");

        // tao bang table
        table.setEditable(true);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(250);
        nameCol.setMaxWidth(250);
        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setMinWidth(120);
        scoreCol.setMaxWidth(120);
        TableColumn rankCol = new TableColumn("Ranking");
        rankCol.setMinWidth(120);
        rankCol.setMaxWidth(120);
        table.getColumns().addAll(rankCol , nameCol, scoreCol);


        table.setLayoutX(400);
        table.setLayoutY(200);
        table.setMinWidth(490);

        //set return button
        Image returnImage = new Image("resource/image/button/mute.png");
        ImageView returnImageView = new ImageView(returnImage);

        returnImageView.setFitWidth(50);
        returnImageView.setFitHeight(50);



        Button returnButton = new Button("", returnImageView);
        returnButton.setLayoutX(320);
        returnButton.setLayoutY(200);
        returnButton.setOnAction(e->{
            //change scene
            Intro introScene = new Intro();
            try {

                introScene.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });



        root.getChildren().addAll(label, table, returnButton);

        stage.setResizable(false);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();

    }
}
