package sample;

import javafx.application.Platform;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static sample.Intro.soundButton;

public class Game {
    public ArrayList<GameShip> playerFleet;
    public static int turn = 0; //0: player's turn, 1: bot's turn
    public static int win = 0; //1 win, 2 lose
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

        //-----tao bot ship-------
        botMap.generate(5, "a");
        botMap.generate(4, "b");
        botMap.generate(3, "c");
        botMap.generate(3, "d");
        botMap.generate(2, "e");
        //-----------------------

        //-----tao bot---------
        Bot bot = new Bot(playerMap);
        //--------------------

        //---------game on------------

        Timer game = new Timer("game");
        TimerTask gameStart = new TimerTask() { // tao luong chay game
            @Override
            public void run() {
                if(win!=0) {
                    botMap.setPressDisable();
                    for(GameShip ship: botMap.botFleet){
                        ship.showUp();
                    }
                    game.cancel();
                }

                if(turn == 0) {
                    botMap.setPressEnable();
                }
                else {
                    botMap.setPressDisable();
                    try {
                        waitForRunLater(bot);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(PlayerMap.remainingShip.size()==0 || botMap.botFleet.size()==0)
                {
                    botMap.setPressDisable();
                    game.cancel();
                    if(PlayerMap.remainingShip.size()==0)
                        win=2;
                    else
                        win=1;
                }
            }

        };
        game.schedule(gameStart, 100, 1); //chay game
        primaryStage.setOnHidden(e->{
            game.cancel();
            Platform.exit();
        });
        //----------------------------

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

    public static void waitForRunLater(Bot bot) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(() -> {
            bot.play();
            semaphore.release();
        });
        semaphore.acquire();

    }
}
