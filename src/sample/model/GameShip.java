package sample.model;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import sample.model.base_class.ShipModel;

public class GameShip extends ShipModel { //get infor from SHIP
    private int idX, idY;
    private int health;

    String s;
    public GameShip(double x, double y, int length, int vertical, double squareSize, Pane myPane) {
        super(x, y, myPane, length, vertical, squareSize);
        this.health = length;
        //--set toa do
        r.setTranslateX(x);
        r.setTranslateY(y);
        r.setHeight(squareSize * Math.max(vertical * length, 1));
        r.setWidth(squareSize * Math.max((1-vertical) * length, 1));
        this.r.setEffect(new DropShadow(8, Color.RED));
        r.setViewOrder(1.0);
        //
        pane.getChildren().add(r);
    }

    public void setIdX(int idX) {
        this.idX = idX;
    }

    public void setIdY(int idY) {
        this.idY = idY;
    }

    public int getIdX() {
        return idX;
    }

    public int getIdY() {
        return idY;
    }

    public void setImage(LineUpShip ship) { //get image tu lineupship
        Image image = ship.getImage();
        this.r.setFill(new ImagePattern(image));
    }

    public void setImage() { // tu set image khi botmap generate
        Image image = new Image(
                GameShip.class.getResource("../../resource/image/ship/bot/"+ s + vertical +".png").toString());
        r.setFill(new ImagePattern(image));
    }

    public void setS(String s) {
        this.s = s;
    }

    public void hit() { health-- ;}

    public void showUp() {
        r.setViewOrder(1);
        this.setImage();
    }

    boolean isAlive() { return health>0;}
}
