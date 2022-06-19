package sample.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LineupMap extends Coordinate{
    private Pane pane;
    private double size; // do dai canh map
    private final int spots ; //so o vuong moi hang va cot
    private double squareSize; // do dai mot o vuong
    public int[][] stateCell; //0: ko co thuyen, 1: co (dung de xac dinh vi tri thuyen)

    private Rectangle[][] grid;


    public LineupMap(double x, double y, Pane pane, double size, int spots) {
        super(x, y);
        this.pane = pane;
        this.size = size;
        this.spots = spots;
        this.squareSize = size/spots;
        stateCell = new int[spots][spots];
        grid = new Rectangle[spots][spots];
    }

    public void drawMap() {
        for(int i=0;i<spots;i++)
            for(int j=0;j<spots;j++) {
                Rectangle r = new Rectangle();
                r.setTranslateX(super.x + squareSize*i);
                r.setTranslateY(super.y + squareSize*j);
                r.setHeight(squareSize);
                r.setWidth(squareSize);
                r.setStyle("-fx-fill: rgba(255,255,255,0.4);");
                r.setStroke(Color.rgb(25,25,112));
                grid[i][j] = r;
                pane.getChildren().add(r);
            }
    }

    public void setStateCell(int i, int j , int x) {
        this.stateCell[i][j] = x;
    }

    public double getSize() {
        return size;
    }
}
