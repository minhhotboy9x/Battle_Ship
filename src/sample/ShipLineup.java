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
import sample.model.graphic.ModelSize;

public class ShipLineup extends Application {
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("sample/css/style.css");
        // vẽ map
        Map lineupMap = new Map(60, 150, root, ModelSize.lineupMapSize, ModelSize.mapSpots);
        lineupMap.drawMap();
        //--------------

        // vẽ ship container
        Rectangle rect = new Rectangle();
        rect.setStyle("-fx-fill: rgba(255,255,255,0.5);");
        rect.setHeight(600);
        rect.setWidth(400);

        rect.setStroke(Color.BLACK);
        rect.setTranslateX(740);
        rect.setTranslateY(80);
        //

        // soundButton
        // set lai thuoc tinh de debug
        soundButton.setId("soundButton");
        soundButton.setLayoutX(1200);
        soundButton.setLayoutY(650);
        soundButton.setOnAction(e -> {
            soundButtonClick();
        });
        root.getChildren().addAll(soundButton, rect);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
