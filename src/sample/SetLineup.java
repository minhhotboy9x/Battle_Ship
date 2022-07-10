package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.model.LineupMap;
import static sample.Intro.soundButton;
import static sample.Intro.soundButtonClick;
import sample.model.LineUpShip;
import sample.model.graphic.ModelSpec;
import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class SetLineup extends Application {
    public static int countShip = 0;
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("sample/css/style.css");

        // vẽ map
        LineupMap lineupMap = new LineupMap(ModelSpec.posLineUpMapX, ModelSpec.posLineUpMapY, root, ModelSpec.lineupMapSize, ModelSpec.mapSpots);
        lineupMap.drawMap();
        //----------------------------

        // vẽ ship container
        Rectangle rect = new Rectangle();
        rect.setStyle("-fx-fill: rgba(230,230,250, 0.50);");
        rect.setHeight(ModelSpec.rectHeight);
        rect.setWidth(ModelSpec.rectWidth);
        rect.setStrokeWidth(2);
        rect.setViewOrder(2.0);
        rect.setStroke(Color.BLACK);
        rect.setArcHeight(20.0);
        rect.setArcWidth(20.0);
        rect.setTranslateX(ModelSpec.posLineUpRectX); //750
        rect.setTranslateY(ModelSpec.posLineUpRectY); //80
        //----------------------------

        //draw ship on container
        ArrayList<LineUpShip> fleet = new ArrayList<>();
        fleet.add(new LineUpShip(800, 100, 5, 1, ModelSpec.lineupMapSquareSize, lineupMap, root, "a"));
        fleet.add(new LineUpShip(800, 400, 4, 1, ModelSpec.lineupMapSquareSize, lineupMap, root, "b"));
        fleet.add(new LineUpShip(950, 200, 3, 1, ModelSpec.lineupMapSquareSize, lineupMap, root, "c"));
        fleet.add(new LineUpShip(1000, 500, 3, 1, ModelSpec.lineupMapSquareSize, lineupMap, root, "d"));
        fleet.add(new LineUpShip(1050, 350, 2, 1, ModelSpec.lineupMapSquareSize, lineupMap, root, "e"));

        //------------------------------

        // -------------Ready button----------------
        Button readyButton = new Button("Ready");
        readyButton.setId("readyButton");
        readyButton.setTranslateX(600);
        readyButton.setTranslateY(400);
        readyButton.setPrefSize(100, 30);
        readyButton.setViewOrder(2.0);

        Timer checkThread = new Timer("CheckCountShip");
        countShip = 0;
        TimerTask checkExecution = new TimerTask() {
            @Override
            public void run() {
                //du thuyen -> enable readyButton
                readyButton.setDisable(countShip != 5);
            }
        };

        readyButton.setOnAction(e->{ //chuyen game
            countShip = 0;
            Game game = new Game();
            game.getFleet(fleet);
            try {
                checkThread.cancel();
                game.start(primaryStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        checkThread.schedule(checkExecution, 1,1);
        primaryStage.setOnHidden(e->checkThread.cancel());
        //------------------------------

        //reset xep tau

//        Image resetImage = new Image("resource/image/button/reset1.png");
//        ImageView resetImageView = new ImageView(resetImage);
//        resetImageView.setFitWidth(50);
//        resetImageView.setFitHeight(50);
//
//        Button resetButton = new Button("", resetImageView);
//        resetButton.setId("resetButton");
//        resetButton.setLayoutX(1190);
//        resetButton.setLayoutY(580);
//        resetButton.setBackground(null);
//        //  an de reset man hinh
//        resetButton.setOnAction(event -> {
//            try {
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });

        // soundButton
        // set lai thuoc tinh de debug
        soundButton.setId("soundButton");
        soundButton.setLayoutX(1200);
        soundButton.setLayoutY(650);
        soundButton.setOnAction(e -> soundButtonClick());
        //-----------------------------
        primaryStage.setResizable(false);
        root.getChildren().addAll(soundButton, rect, readyButton);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
