package sample.model;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static sample.model.graphic.ModelSpec.mapSpots;

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
        r.setFill(Color.BLUE);
        draw(x, y); //ve ra container ngay khi khoi tao
        //---------------------
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
    public void changeShape(double x, double y) { //xoay tau
        vertical = 1-vertical;
        draw(x, y);
    }

    public double getHeight(){ return Math.max(length * vertical * squareSize, squareSize); }
    public double getWidth(){ return Math.max(length * (1-vertical) * squareSize, squareSize); }
    //----------------------------------------
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
    public boolean inMap(int idX, int idY) { //check if ship in ship is in lineupMap by check id
        double endX, endY;
        if(vertical==1){
            endX = idX ;
            endY = idY + length - 1;
        }
        else {
            endX = idX + length - 1;
            endY = idY;
        }
        return  0<= idX && idX < mapSpots && 0<= idY && idY < mapSpots
                && 0<= endX && endX < mapSpots && 0<= endY && endY < mapSpots ;
    }
    public boolean isCollision(int idX, int idY){
        for(int i=idX; i<idX + Math.max(1, length*(1-vertical)); i++)
            for (int j=idY; j<idY + Math.max(1, length*vertical); j++){
                if(lineupMap.getStateCell(i, j) == 1) {
                    return true;
                }
            }
        return false;
    }
    //--------------------------------------------

    //-----------------method for this.r-----------------
    private void draggedMouse(MouseEvent event) {
        if(event.getButton() == MouseButton.SECONDARY) //neu chuọt phai thi bo
            return;
        r.setOnMousePressed(e -> draggedRotate(e)); //xoay khi đang drag
        r.setTranslateX(event.getSceneX() - mouseAnchorX);
        r.setTranslateY(event.getSceneY() - mouseAnchorY);
    }

    private void releasedMouse(MouseEvent event) {
        if(event.getButton() == MouseButton.SECONDARY)
            return;

        int idX = (int) ((r.getTranslateX() - lineupMap.getX()) / squareSize); //idX of lineupMap
        int idY = (int) ((r.getTranslateY() - lineupMap.getY()) / squareSize); //idY of lineupMap
        // System.out.println(idX+" "+idY);
        double boundX = (lineupMap.getX() + idX * squareSize + squareSize);
        double boundY = (lineupMap.getY() + idY * squareSize + squareSize);

        if (r.getTranslateX() + this.getWidth() <= lineupMap.getX() + lineupMap.getSize()) {
            double d1 = Math.abs(boundX - r.getTranslateX()); //kc tu bound den x ben trai
            double d2 = Math.abs(boundX - r.getTranslateX() - squareSize); //kc tu bound den x ben phai
            if (d1 < d2)
                idX++;
        }
        if (r.getTranslateY() + this.getHeight() <= lineupMap.getY() + lineupMap.getSize()) {
            double d1 = Math.abs(boundY - r.getTranslateY()); //kc tu bound den y ben tren
            double d2 = Math.abs(boundY - r.getTranslateY() - squareSize); //kc tu bound den y ben duoi
            if (d1 < d2)
                idY++;
        }

        if (!this.inMap(idX, idY) || isCollision(idX, idY)) // k nam trong map hoặc đụng thuyen -> về container
            this.draw(this.x, this.y);
        else {// vo map
            this.draw(lineupMap.getX() + idX * squareSize, lineupMap.getY() + idY * squareSize);
            for (int i = idX; i < idX + Math.max(1, length * (1 - vertical)); i++)
                for (int j = idY; j < idY + Math.max(1, length * vertical); j++) {
                    lineupMap.setStateCell(i, j, 1);
                }
        }
        r.setOnMousePressed(e->pressedMouse(e));
    }

    private void pressedMouse(MouseEvent event) {
        mouseAnchorX = event.getX();
        mouseAnchorY = event.getY();
        if(this.inMap()) {
            if(event.getButton()==MouseButton.PRIMARY)
            {
                int idX = (int) ((this.r.getTranslateX() - lineupMap.getX()) / squareSize); //idX of map
                int idY = (int) ((this.r.getTranslateY() - lineupMap.getY()) / squareSize); //idY of map
                for (int i = idX; i < idX + Math.max(1, length * (1 - vertical)); i++)
                    for (int j = idY; j < idY + Math.max(1, length * vertical); j++) {
                        lineupMap.setStateCell(i, j, 0);
                    }
            }
        }
        else {
            if(event.getButton()==MouseButton.SECONDARY) { //xoay tau quanh x, y
                this.changeShape();
            }
        }
    }

    private void draggedRotate(MouseEvent event) { //xoay thuyen khi dang keo = chuot phai
        if(event.getButton()==MouseButton.SECONDARY) {

            double gocX = event.getSceneX() ;
            double gocY = event.getSceneY();
            double uX, uY;
            double vX, vY;
            double oldAnchorX = mouseAnchorX;
            double oldAnchorY = mouseAnchorY;
            if(vertical==1) {
                uX = r.getTranslateX() + squareSize;
                uY = r.getTranslateY();

                vX = gocX + (uY - gocY);
                vY = gocY - (uX - gocX);

                mouseAnchorX = 0 + oldAnchorY;
                mouseAnchorY = 50 - oldAnchorX;
            }
            else {
                uX = r.getTranslateX();
                uY = r.getTranslateY() + squareSize;

                vX = gocX - (uY - gocY);
                vY = gocY + (uX - gocX);

                mouseAnchorX = 50 - oldAnchorY;
                mouseAnchorY = oldAnchorX;
            }
            System.out.println(mouseAnchorX + " " + mouseAnchorY
                    +" \n"+ oldAnchorX +" "+ oldAnchorY+
                    vertical+'\n');
            this.changeShape(vX , vY);
        }
    }
    //-----------------------------------------------------------------
}
