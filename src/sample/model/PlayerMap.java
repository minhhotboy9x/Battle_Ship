package sample.model;

import javafx.scene.layout.Pane;
import sample.main.Game;
import sample.bot.Bot;

import java.util.ArrayList;

public class PlayerMap extends LineupMap {
    public GameShip [][] playerShip;
    public ArrayList<Integer> remainingShip; //luu do dai cua thuyen con lai tren map

    static int hitTheShipBefore = 0; // đánh dấu lượt bắn trước trúng hay trượt
    static int baseScore = 10; // điểm cơ bản bị trừ đi khi địch bắn trúng ta


    public PlayerMap(double x, double y, Pane pane, double size, int spots) {
        super(x, y, pane, size, spots);
        playerShip = new GameShip[spots][spots];
        remainingShip = new ArrayList<>();
        remainingShip.add(5);
        remainingShip.add(4);
        remainingShip.add(3);
        remainingShip.add(3);
        remainingShip.add(2);
    }

    public void getReferenceShip(ArrayList<GameShip> playerFleet) {
        for(GameShip ship: playerFleet) {
            int idX = ship.getIdX();
            int idY = ship.getIdY();
            int length = ship.getLength();
            int vertical = ship.getVertical();
            for (int i = idX; i < idX + Math.max(1, length * (1 - vertical)); i++)
                for (int j = idY; j < idY + Math.max(1, length * vertical); j++) {
                    playerShip[i][j] = ship;
                    stateCell[i][j] = 1;
                }
        }
    }

    public boolean shoot(int i, int j) // ban vao o i,j
    {
        double posX = getPosX(i);
        double posY = getPosY(j);
        double ra = super.getSquareSize()/4;
        double cen = super.getSquareSize()/2;
        Shoot c = new Shoot(posX+cen, posY+cen, ra, pane); //tạo một vien đạn
        if(playerShip[i][j]!=null) {
            playerShip[i][j].hit();

            //--- cập nhật điểm số khi bắn trúng ----------------------------
            if (hitTheShipBefore == 1) {  // nếu lần trước đó bắn trúng
                baseScore = baseScore * 2;  // điểm cơ bản nhân đôi
                //System.out.println("Địch bắn trúng tàu, TRỪ " + baseScore);
                LineupMap.score -= (baseScore);  // cập nhật điểm hiện tại
                //System.out.println("Điểm số hiện tại: " + LineupMap.score);
                //System.out.println();
            } else {   // nếu lần trước đó bắn hụt
                //System.out.println("Địch bắn trúng tàu, TRỪ " + baseScore);
                LineupMap.score -= (baseScore);  // cập nhật điểm hiện tại
                //System.out.println("Điểm số hiện tại: " + LineupMap.score);
                //System.out.println();
            }
            hitTheShipBefore = 1; // đánh dấu đã bắn trúng
            //-------------------------------------------------------------


            Bot.map[i][j]=2;
            if(!playerShip[i][j].isAlive()) { //tau đắm
                markDown(playerShip[i][j]);
                remainingShip.remove(Integer.valueOf(playerShip[i][j].getLength()));
            }
            c.draw("hit");
        }
        else {
            Bot.map[i][j]=1;
            c.draw("miss");

            //--- cập nhật điểm số khi bắn trượt -------------------------
            hitTheShipBefore = 0;   // bắn trượt
            baseScore = 10;  // reset lại điểm cơ bản vi bắn trượt
            // --------------------------------------------------------

        }
        Game.turn = 0;

        return playerShip[i][j]!=null;
    }

    void markDown(GameShip playerShip) { // danh dau lai cac o da xac dinh thuyen sap
        int idX = playerShip.getIdX();
        int idY = playerShip.getIdY();
        int length = playerShip.getLength();
        int vertical = playerShip.getVertical();
        for (int i = idX; i < idX + Math.max(1, length * (1 - vertical)); i++)
            for (int j = idY; j < idY + Math.max(1, length * vertical); j++) {
                Bot.map[i][j]=3;
            }
    }

}
