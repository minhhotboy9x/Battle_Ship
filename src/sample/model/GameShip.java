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
    public GameShip(double x, double y, int length, int vertical, double squareSize, Pane myPane) {
        super(x, y, myPane, length, vertical, squareSize);
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

    public void setImage(LineUpShip ship) {
        Image image = ship.getImage();
        this.r.setFill(new ImagePattern(image));
    }

    public void hit() { health-- ;}

    boolean isAlive() { return health>0;}
}
