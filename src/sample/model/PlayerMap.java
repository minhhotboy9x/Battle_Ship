package sample.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import sample.bot.Bot;

import java.util.ArrayList;

public class PlayerMap extends LineupMap {
    public GameShip [][] playerShip;
    public static ArrayList<Integer> remainingShip = new ArrayList<>(); //luu do dai cua thuyen con lai tren map

    public PlayerMap(double x, double y, Pane pane, double size, int spots) {
        super(x, y, pane, size, spots);
        playerShip = new GameShip[spots][spots];
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
                }
        }
    }


    double getPosX(int idX){
        return x + idX*super.getSquareSize();
    }

    double getPosY(int idY){
        return y + idY*super.getSquareSize();
    }

    public boolean shot(int i, int j) // ban vao o i,j
    {
        double posX = getPosX(i);
        double posY = getPosY(j);
        double ra = super.getSquareSize()/4;
        double cen = super.getSquareSize()/2;
        Circle c;
        if(playerShip[i][j]!=null) {
            playerShip[i][j].hit();
            Bot.map[i][j]=2;
            if(playerShip[i][j].isAlive()==false) {
                markDown(playerShip[i][j]);
                remainingShip.remove(Integer.valueOf(playerShip[i][j].getLength()));
            }
            c = new Circle(posX+cen, posY+cen, ra, Color.rgb(178,34,34));
            c.setStrokeWidth(0.0);

        }
        else {
            Bot.map[i][j]=1;
            c = new Circle(posX+cen, posY+cen, ra, Color.rgb(0,0,255));
            c.setStrokeWidth(0.0);
        }
        pane.getChildren().add(c);
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