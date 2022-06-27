package sample;

import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.bot.Bot;
import sample.model.BotMap;
import sample.model.GameShip;
import sample.model.LineUpShip;
import sample.model.PlayerMap;
import sample.model.graphic.ModelSpec;
import java.util.ArrayList;

import static sample.Intro.soundButton;

public class Game {
    public ArrayList<GameShip> playerFleet;
    Pane root = new Pane();
    public void start(Stage primaryStage) {
        Scene scene = new Scene(root, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("sample/css/style.css");

        //---playerMap----
        PlayerMap playerMap= new PlayerMap(ModelSpec.posPlayerMapX , ModelSpec.posPlayerMapY,
                root, ModelSpec.gameMapSize, ModelSpec.mapSpots);
        playerMap.drawMap();
        playerMap.getReferenceShip(playerFleet);
        //---------------

        //-----botMap------------
        BotMap botMap = new BotMap(ModelSpec.posBotMapX , ModelSpec.posBotMapY,
                root, ModelSpec.gameMapSize, ModelSpec.mapSpots);
        botMap.drawMap();
        //------------------------

        //-----tao bot---------
        Bot bot = new Bot(playerMap);
        Button test = new Button("test");
        test.setTranslateX(50);
        test.setTranslateY(50);
        root.getChildren().add(test);

        test.setOnAction(e->{
            if(bot.mode == 1)
                bot.playEasy();
            else
                bot.playHard();
        });
        //--------------------

        //---botMap-----

        //--------------
        primaryStage.setResizable(false);
        root.getChildren().addAll(soundButton, Intro.nameLabel);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void getFleet(ArrayList<LineUpShip> fleet) { // chuyền info ship từ scene trước sang
        playerFleet = new ArrayList<>();
        for(LineUpShip ship : fleet) {
            Pair<Integer, Integer> idPoint = ship.getIdMap(); // idX, idY
            double posX = ModelSpec.posPlayerMapX + ModelSpec.gameMapSquareSize * idPoint.getKey();
            double posY = ModelSpec.posPlayerMapY + ModelSpec.gameMapSquareSize * idPoint.getValue();
            //System.out.println(idPoint.getKey()+" "+idPoint.getValue());
            int length = ship.getLength();
            int vertical = ship.getVertical();
            GameShip playerShip = new GameShip(posX, posY, length, vertical, ModelSpec.gameMapSquareSize, root);
            playerShip.setImage(ship);
            playerShip.setIdX(idPoint.getKey());
            playerShip.setIdY(idPoint.getValue());
            playerFleet.add(playerShip);
        }
    }

}
