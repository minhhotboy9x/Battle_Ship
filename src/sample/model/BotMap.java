package sample.model;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import sample.Game;
import sample.model.graphic.ModelSpec;

import java.util.ArrayList;
import java.util.Random;

public class BotMap extends LineupMap {
    // view order: map:2, ship,3, ship sap,1
    public GameShip [][] playerShip;
    public ArrayList<GameShip> botFleet;
    public BotMap(double x, double y, Pane pane, double size, int spots) {
        super(x, y, pane, size, spots);
        playerShip = new GameShip[spots][spots];
        botFleet = new ArrayList<>();
    }

    public void setPressDisable() {
        Rectangle grid[][] = super.getGrid();
        for (int i = 0; i < ModelSpec.mapSpots; i++)
            for (int j = 0; j < ModelSpec.mapSpots; j++) {
                grid[i][j].setOnMousePressed(null);
            }
    }

    public void setPressEnable(){
        Rectangle grid[][] = super.getGrid();
        for (int i = 0; i < ModelSpec.mapSpots; i++)
            for (int j = 0; j < ModelSpec.mapSpots; j++)
                if(super.stateCell[i][j]==0) {
                int x = i;
                int y = j;
                grid[i][j].setOnMousePressed(e->shot(e, x, y));
            }
    }

    public void shot(MouseEvent event, int i, int j) {
        Rectangle[][] grid = super.getGrid();
        grid[i][j].setOnMousePressed(null);
        double posX = getPosX(i);
        double posY = getPosY(j);
        double ra = super.getSquareSize()/4;
        double cen = super.getSquareSize()/2;
        Circle c;
        if(playerShip[i][j]!=null) {
            playerShip[i][j].hit();
            if(!playerShip[i][j].isAlive()) { //tàu đắm -> hien tau len map
                playerShip[i][j].showUp();
                botFleet.remove(playerShip[i][j]);
            }
            c = new Circle(posX+cen, posY+cen, ra, Color.rgb(178,34,34));
        }
        else {
            c = new Circle(posX+cen, posY+cen, ra, Color.rgb(0,0,255));
        }
        super.stateCell[i][j]=1;
        c.setStrokeWidth(0.0);
        pane.getChildren().add(c);
        Game.turn = 1;
    }

    public void generate(int length, String s) {
        Random rand = new Random();
        int vertical = rand.nextInt(2);
        boolean ok = false;
        while(!ok) {
            int idX = rand.nextInt(10);
            int idY = rand.nextInt(10);
            int endX, endY;
            if(vertical==1){
                endX = idX ;
                endY = idY + length - 1;
            }
            else {
                endX = idX + length - 1;
                endY = idY;
            }
            ok = ModelSpec.pointInMap(idX, idY) && ModelSpec.pointInMap(endX, endY); //out boundary?
            if(!ok) // neu out boundary -> random lai
                continue;

            for(int i=idX; i<=endX; i++) { //is collision?
                for(int j=idY;j<=endY;j++) {
                    ok &= (playerShip[i][j]==null);
                    // arrange the ship not adjacent
                    if(i-1>=0) ok &= (playerShip[i-1][j]==null);
                    if(j-1>=0) ok &= (playerShip[i][j-1]==null);
                    if(i+1<ModelSpec.mapSpots) ok &= (playerShip[i+1][j]==null);
                    if(j+1<ModelSpec.mapSpots) ok &= (playerShip[i][j+1]==null);
                }
            }

            if(ok) { //true -> generate
                double posX = getPosX(idX);
                double posY = getPosY(idY);
                GameShip botShip = new GameShip(posX, posY, length, vertical, ModelSpec.gameMapSquareSize, pane);
                //----an tau di
                botFleet.add(botShip);
                botShip.setS(s);
                botShip.getR().setEffect(null);
                botShip.getR().setFill(Color.TRANSPARENT);
                botShip.getR().setViewOrder(3);
                //-------------
                for(int i=idX; i<=endX; i++){ //get reference
                    for(int j=idY;j<=endY;j++)
                        playerShip[i][j] = botShip;
                }
            }
        }
    }


}
