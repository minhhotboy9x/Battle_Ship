package sample.model.base_class;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import sample.model.Coordinate;

public abstract class ShipModel extends Coordinate {
    protected int length; // number of cell
    protected int vertical; // 0 horizontal, 1 vertical
    protected double squareSize;
    protected Rectangle r;

    public int getLength() {
        return length;
    }

    public int getVertical() {
        return vertical;
    }

    public ShipModel(double x, double y, Pane pane, int length, int vertical, double squareSize) {
        super(x, y, pane);
        this.length = length;
        this.vertical = vertical;
        this.squareSize = squareSize;
        this.r = new Rectangle();
    }
    public double getHeight(){ return Math.max(length * vertical * squareSize, squareSize); }

    public double getWidth(){ return Math.max(length * (1-vertical) * squareSize, squareSize); }

    public Rectangle getR(){return r;};
}
