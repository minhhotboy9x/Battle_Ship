package sample.model;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import sample.model.graphic.ModelSpec;

public class BotMap extends LineupMap {

    public BotMap(double x, double y, Pane pane, double size, int spots) {
        super(x, y, pane, size, spots);
    }

    public void drawMap() {
        super.drawMap();
        int spots = ModelSpec.mapSpots;
        Rectangle[][] grid = super.getGrid();
        for (int i = 0; i < spots; i++)
            for (int j = 0; j < spots; j++) {
                int x = i;
                int y = j;
                grid[i][j].setOnMousePressed((e) -> shot(e, x, y));
            }

    }

    void shot(MouseEvent event, int x, int y) {
        System.out.println(1);
        Rectangle[][] grid = super.getGrid();
        grid[x][y].setOnMousePressed(null);
    }

    double getPosX(int idX){
        return x + idX*super.getSquareSize();
    }

    double getPosY(int idY){
        return y + idY*super.getSquareSize();
    }

}
