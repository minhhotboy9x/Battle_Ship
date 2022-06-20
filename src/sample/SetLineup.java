package sample;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.model.LineupMap;
import sample.model.LineupMap;
import static sample.Intro.soundButton;
import static sample.Intro.soundButtonClick;

import sample.model.Ship;
import sample.model.graphic.ModelSpec;

import java.util.Timer;
import java.util.TimerTask;

public class SetLineup extends Application {
    public static int countShip = 0;
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("sample/css/style.css");

        // vẽ map
        LineupMap lineupMap = new LineupMap(ModelSpec.posLineUpMapX, ModelSpec.posLineUpMapY, root, ModelSpec.lineupMapSize, ModelSpec.mapSpots);
        lineupMap.drawMap();
        //----------------------------

        // vẽ ship container
        Rectangle rect = new Rectangle();
        rect.setStyle("-fx-fill: rgba(169,169,169,0.5);");
        rect.setHeight(ModelSpec.rectHeight);
        rect.setWidth(ModelSpec.rectWidth);
        rect.setStrokeWidth(2);
        rect.setViewOrder(2.0);
        rect.setStroke(Color.BLACK);
        rect.setTranslateX(ModelSpec.posLineUpRectX); //750
        rect.setTranslateY(ModelSpec.posLineUpRectY); //80
        //----------------------------

        //draw ship on container
        Ship ship1 = new Ship(800, 100, 5, 1, ModelSpec.lineupMapSquareSize, lineupMap, root, "a");
        Ship ship2 = new Ship(800, 400, 4, 1, ModelSpec.lineupMapSquareSize, lineupMap, root, "b");
        Ship ship3 = new Ship(950, 200, 3, 1, ModelSpec.lineupMapSquareSize, lineupMap, root, "c");
        Ship ship4 = new Ship(1000, 500, 3, 1, ModelSpec.lineupMapSquareSize, lineupMap, root, "d");
        Ship ship5 = new Ship(1050, 350, 2, 1, ModelSpec.lineupMapSquareSize, lineupMap, root, "e");

        //------------------------------

        // -------------Ready button----------------
        Button readyButton = new Button("Ready");
        readyButton.setTranslateX(600);
        readyButton.setTranslateY(400);
        readyButton.setViewOrder(2.0);

        Timer checkThread = new Timer("CheckCountShip");
        TimerTask checkExecution = new TimerTask() {
            @Override
            public void run() {
                //System.out.println(countShip);
                if(countShip == 5) //du thuyen
                    readyButton.setDisable(false);
                else
                    readyButton.setDisable(true);
            }
        };

        readyButton.setOnAction(e->{ //chuyen game
            Game game = new Game();
            try {
                game.start(primaryStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        checkThread.schedule(checkExecution, 1,1);
        primaryStage.setOnHidden(e->checkThread.cancel());
        //------------------------------

        // soundButton
        // set lai thuoc tinh de debug
        soundButton.setId("soundButton");
        soundButton.setLayoutX(1200);
        soundButton.setLayoutY(650);
        soundButton.setOnAction(e -> {
            soundButtonClick();
        });
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
