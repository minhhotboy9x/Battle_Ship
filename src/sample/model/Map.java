package sample.model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import sample.model.graphic.ModelSize;

public class Map extends Coordinate{
    private double beginX; // toa độ đặt map
    private double beginY; // toa độ đặt map
    private Pane pane;
    private double size; // do dai canh map
    private int spots ; //so o vuong moi hang va cot
    private double squareSize; // do dai mot o vuong
    private int[][] stateCell; //0:ko co thuyen, 1: co (dung de xac dinh vi tri thuyen)
    private Rectangle[][] grid;

    public Map(double x, double y, double beginX, double beginY, Pane pane, double size, int spots) {
        super(x, y);
        this.beginX = beginX;
        this.beginY = beginY;
        this.pane = pane;
        this.size = size;
        this.spots = spots;
        this.squareSize = size/spots;
        stateCell = new int[spots][spots];
        grid = new Rectangle[spots][spots];
    }

    public void drawMap() {

    }
}
