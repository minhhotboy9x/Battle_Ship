package sample.model;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.Game;
import sample.model.graphic.ModelSpec;

import java.util.ArrayList;
import java.util.Random;

public class BotMap extends LineupMap {
    // view order: map:2, ship,3, ship sap,1
    public GameShip [][] playerShip;
    public ArrayList<GameShip> botFleet;
    public Button[][] cell; // dung button ve bang de styling cho de (do code au)

    public BotMap(double x, double y, Pane pane, double size, int spots) {
        super(x, y, pane, size, spots);
        playerShip = new GameShip[spots][spots];
        botFleet = new ArrayList<>();
        cell = new Button[spots][spots];
    }

    public void drawMap() {
        int spots = ModelSpec.mapSpots;
        double squareSize = super.getSquareSize();
        for(int i=0;i<spots;i++)
            for(int j=0;j<spots;j++) {
                Button btn = new Button();
                btn.setTranslateX(super.x + squareSize*i);
                btn.setTranslateY(super.y + squareSize*j);
                btn.setPrefHeight(squareSize);
                btn.setPrefWidth(squareSize);
                btn.getStyleClass().add("btn");
                btn.setViewOrder(2.0);
                cell[i][j] = btn;
                pane.getChildren().add(btn);
            }
    }

    public void setPressDisable() {
        for (int i = 0; i < ModelSpec.mapSpots; i++)
            for (int j = 0; j < ModelSpec.mapSpots; j++) {
                cell[i][j].setOnAction(null);
            }
    }

    public void setPressEnable() {
        for (int i = 0; i < ModelSpec.mapSpots; i++)
            for (int j = 0; j < ModelSpec.mapSpots; j++)
                if(super.stateCell[i][j]==0) {
                int x = i;
                int y = j;
                cell[i][j].setOnAction(e->shoot(e, x, y));
            }
    }

    public void shoot(ActionEvent event, int i, int j) {
        cell[i][j].setOnMousePressed(null);
        cell[i][j].getStyleClass().remove("btn");
        cell[i][j].getStyleClass().add("nbtn");
        double posX = getPosX(i);
        double posY = getPosY(j);
        double ra = super.getSquareSize()/4;
        double cen = super.getSquareSize()/2;
        Shoot c = new Shoot(posX+cen, posY+cen, ra, pane); //tạo một vien đạn
        if(playerShip[i][j]!=null) { // hit
            playerShip[i][j].hit();
            if(!playerShip[i][j].isAlive()) { //tàu đắm -> hien tau len map
                playerShip[i][j].showUp();
                botFleet.remove(playerShip[i][j]);
            }
            c.draw("hit");
        }
        else { // miss
            c.draw("miss");
        }
        super.stateCell[i][j]=1;
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
