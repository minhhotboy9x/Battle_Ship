package sample;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.model.Map;
import static sample.Intro.soundButton;
import static sample.Intro.soundButtonClick;

import sample.model.Ship;
import sample.model.graphic.ModelSpec;

public class ShipLineup extends Application {
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("sample/css/style.css");

        // vẽ map
        Map lineupMap = new Map(ModelSpec.posLineUpMapX, ModelSpec.posLineUpMapY, root, ModelSpec.lineupMapSize, ModelSpec.mapSpots);
        lineupMap.drawMap();
        //----------------------------

        // vẽ ship container
        Rectangle rect = new Rectangle();
        rect.setStyle("-fx-fill: rgba(255,255,255,0.5);");
        rect.setHeight(ModelSpec.rectHeight);
        rect.setWidth(ModelSpec.rectWidth);
        //rect.setOnMouseClicked(e->System.out.println("1"));
        rect.setViewOrder(2.0);
        rect.setStroke(Color.BLACK);
        rect.setTranslateX(ModelSpec.posLineUpRectX); //750
        rect.setTranslateY(ModelSpec.posLineUpRectY); //80
        //----------------------------

        //draw ship on container
        Ship ship1 = new Ship(800, 100, 5, 1, ModelSpec.lineupMapSquareSize, lineupMap, root);
        Ship ship2 = new Ship(800, 400, 4, 1, ModelSpec.lineupMapSquareSize, lineupMap, root);
        Ship ship3 = new Ship(950, 200, 3, 1, ModelSpec.lineupMapSquareSize, lineupMap, root);
        Ship ship4 = new Ship(1000, 500, 3, 1, ModelSpec.lineupMapSquareSize, lineupMap, root);
        Ship ship5 = new Ship(1050, 350, 2, 1, ModelSpec.lineupMapSquareSize, lineupMap, root);
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
        root.getChildren().addAll(soundButton, rect);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
