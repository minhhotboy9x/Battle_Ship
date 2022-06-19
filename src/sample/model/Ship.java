package sample.model;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ship extends Coordinate{
    // 1*length or length*1

    private int length; // number of cell
    private int vertical; // 0 horizontal, 1 vertical
    private double squareSize;
    private Pane pane;
    private LineupMap lineupMap;
    private Rectangle r;
    //----xu ly mouse----------
    private double mouseAnchorX; // use for drag and press ship
    private double mouseAnchorY; // use for drag and press ship
    //---------------------
    public Ship(double x, double y, int length, int vertical, double squareSize, LineupMap lineupMap, Pane myPane) {
        super(x, y);
        this.length = length;
        this.vertical = vertical;
        this.squareSize = squareSize;
        this.lineupMap = lineupMap;
        this.pane = myPane;
        this.r = new Rectangle();
        r.setStroke(Color.BLACK);
        r.setFill(Color.TRANSPARENT);
        draw(x, y); //ve ra container ngay khi khoi tao
        //---------------------
        this.r.setOnMouseClicked(event -> clickedMouse(event)); //xoay tau
        this.r.setOnMousePressed(event -> pressedMouse(event)); // giu chuot
        this.r.setOnMouseReleased(event -> releasedMouse(event)); // nha chuot
        this.r.setOnMouseDragged(event -> draggedMouse(event)); // keo chuot
        //---------------------
        pane.getChildren().add(r);
    }

    public void draw(double x, double y)
    {
        r.setTranslateX(x);
        r.setTranslateY(y);
        r.setHeight(Math.max(length * vertical * squareSize, squareSize));
        r.setWidth(Math.max(length * (1-vertical) * squareSize, squareSize));
    }
    public void changeShape() { //xoay tau
        vertical = 1-vertical;
        draw(x, y);
    }

    public double getHeight(){ return Math.max(length * vertical * squareSize, squareSize); }
    public double getWidth(){ return Math.max(length * (1-vertical) * squareSize, squareSize); }

    public boolean inMap() { //check if ship in ship is in lineupMap
        double beginX = this.r.getTranslateX();
        double beginY = this.r.getTranslateY();
        double endX, endY;
        if(vertical==1){
            endX = beginX + squareSize;
            endY = beginY + squareSize*length;
        }
        else {
            endX = beginX + squareSize*length;
            endY = beginY + squareSize;
        }
        return (beginX>=lineupMap.getX() && beginY >=lineupMap.getY())
                && (endX<=lineupMap.getX()+lineupMap.getSize() && endY<=lineupMap.getY()+lineupMap.getSize());
    }

    //-----------------method for this.r-----------------
    private void draggedMouse(MouseEvent event) {
        r.setTranslateX(event.getSceneX()-mouseAnchorX);
        r.setTranslateY(event.getSceneY()-mouseAnchorY);
    }

    private void releasedMouse(MouseEvent event) {
        System.out.println(this.inMap());
    }

    private void pressedMouse(MouseEvent event) {
        mouseAnchorX = event.getX();
        mouseAnchorY = event.getY();
        if(this.inMap()) {
            int idX = (int) ( (this.r.getTranslateX() - lineupMap.getX()) / squareSize); //idX of map
            int idY = (int) ( (this.r.getTranslateY() - lineupMap.getY()) / squareSize); //idY of map
            for(int i = idX; i < idX + Math.max(1, length * (1 - vertical)); i++)
                for (int j = idY; j < idY + Math.max(1, length * vertical); j++) {
                    lineupMap.setStateCell(i, j,0);
                }
        }
    }

    private void clickedMouse(MouseEvent event) {
        if ( event.getButton().equals(MouseButton.PRIMARY)
                && event.getClickCount() == 2
                && !this.inMap() ) {
            this.changeShape();
        }
    }
    //-----------------------------------------------------------------
}
