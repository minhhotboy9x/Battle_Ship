package sample;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.bot.Bot;
import sample.model.*;
import sample.model.data.Data;
import sample.model.data.DataControl;
import sample.model.graphic.ModelSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import static sample.Intro.soundButton;

public class Game {
    public ArrayList<GameShip> playerFleet;
    public static int turn = 0; //0: player's turn, 1: bot's turn
    public static int win = 0; //1 win, 2 lose
    private final Pane root = new Pane();

    private int scoreBefore;  // biến kiểm tra xem lượt chơi hiện tại điểm có bị thay đổi ko

    public void start(Stage primaryStage) {
        Scene scene = new Scene(root, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("sample/css/style.css");


        //---Lấy dữ liệu username từ màn hình chọn khó dễ-----
        Difficulty difficulty = new Difficulty();
        String userName = difficulty.userNameText;
        //----------------------------------------------

//        //--- Label hiển thị userName-----
//        Label userNameLabel = new Label(userName);
//        userNameLabel.setId("userNameLabel_Game");
//        userNameLabel.setTranslateX(100);
//        userNameLabel.setTranslateY(50);
//        //-----------------------------------




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
        //-----------------------------

        //--- Màn hình hiển thị điểm số ------
        PaintScore paintScore = new PaintScore();
        //----------------------------



        //-------chuyen man hinh---------
        Button nextButton = new Button("Next"); // nút chuyển màn hình
        nextButton.setTranslateX(600);
        nextButton.setTranslateY(300);
        nextButton.setVisible(false);
        //--------------------

        //-------hiển thị win hoặc lose-------
        Label resultWinLabel = new Label("You win!");
        resultWinLabel.setId("resultWinLabel_Game");
        resultWinLabel.setTranslateX(580);
        resultWinLabel.setTranslateY(350);
        resultWinLabel.setVisible(false);

        Label resultLoseLabel = new Label("You Lose!");
        resultLoseLabel.setId("resultLoseLabel_Game");
        resultLoseLabel.setTranslateX(580);
        resultLoseLabel.setTranslateY(350);
        resultLoseLabel.setVisible(false);
        //--------------------------------------



        //---------game on------------
        reset(); //win và turn là 2 biến static nên cần đặt lại

        scoreBefore = LineupMap.score;   // gán điểm

        Timer game = new Timer("game");
        TimerTask gameStart = new TimerTask() { // tao thread chay game
            @Override
            public void run() {
                if(playerMap.remainingShip.size()==0) //lose
                    win=2;
                if(botMap.botFleet.size()==0) //win
                    win=1;
                if(win!=0) { //neu co kq thang thua -> end thread
                    //--- ghi kết quả vào file khi chơi xong-----------
                    DataControl dataControl = new DataControl();
                    var dataFileName = "DATA.txt";
                    Data data = new Data(userName, LineupMap.score);
                    try {
                        dataControl.writeDataToFile(data, dataFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //-----------------------------


                    //System.out.println(win);
                    botMap.setPressDisable();
                    for(GameShip ship: botMap.botFleet)
                        ship.showUp();
                    nextButton.setVisible(true);
                    if(win == 1) resultWinLabel.setVisible(true);
                    if(win == 2) resultLoseLabel.setVisible(true);

                    game.cancel(); //cancel timer not executing thread
                    return;
                }

                if (scoreBefore != LineupMap.score) {   // nếu điểm trước đó và điểm hiện tại khác nhau thì cập nhật
                    paintScore.paint();   // vẽ điểm và user name
                    scoreBefore = LineupMap.score;  // cập nhật điểm hiện tại
                }

                if(turn == 0) {
                    botMap.setPressEnable(); // enable press action
                }
                else if(turn == 1){
                    botMap.setPressDisable(); // disable press action
                    //bot.play(); exception: Not on FX application thread; currentThread = game
                    // => use Platform.runlater
                    try {
                        waitForRunLater(bot);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        };
        game.schedule(gameStart, 100, 1); //chay thread game
        //---------------------------------------
        /*Button testButton = new Button("test");
        testButton.setTranslateX(300);
        testButton.setTranslateY(300);
        testButton.setOnAction(e->{
            System.out.println(playerMap.remainingShip.size()+" "+
                    botMap.botFleet.size()+" "+ok);
        });
        root.getChildren().addAll(testButton);
        */

        //----------------------------
        nextButton.setOnAction(e -> { //action for switch scene
            HighScore highScore = new HighScore();
            try {
                highScore.start(primaryStage);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //--------------------
        primaryStage.setOnHidden(e->{ // nếu đóng cửa sổ -> delete thread
            game.cancel();
        });
        primaryStage.setResizable(false);
        root.getChildren().addAll(soundButton, Intro.nameLabel, nextButton, paintScore, resultWinLabel, resultLoseLabel);
        primaryStage.setScene(scene);
        primaryStage.show();
        paintScore.paint();  // vẽ layout hiển thị điểm và username
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
    public static void reset() {// set lai bien static
        win = 0;
        turn = 0;
        LineupMap.score = 0;
    }
    public static void waitForRunLater(Bot bot) throws InterruptedException {
        //dung phuong phap den bao
        Semaphore semaphore = new Semaphore(0);
        /*
        runLater. Run the specified Runnable on the JavaFX Application Thread
        at some unspecified time in the future.
        This method, which may be called from any thread,
        will post the Runnable to an event queue and then return immediately to the caller.
        The Runnables are executed in the order they are posted.
         */
        Platform.runLater(() -> {
            bot.play();
            semaphore.release(); //permit++;
        });
        semaphore.acquire();
        //if(permits<=0) -> block
        //else invoke acquire()
    }
}
