package sample.model.base_class;

import javafx.scene.layout.Pane;

public abstract class Coordinate {
    protected double x ,y;
    protected Pane pane;
    public Coordinate() { }
    public Coordinate(double x, double y, Pane pane) {
        this.x = x;
        this.y = y;
        this.pane = pane;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
