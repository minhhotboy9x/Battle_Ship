package sample.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.Difficulty;


public class PaintScore extends HBox {

    //---Lấy dữ liệu username từ màn hình chọn khó dễ-----
    Difficulty difficulty = new Difficulty();
    String userName = difficulty.userNameText;
    //----------------------------------------------

    private Canvas canvas;

    public PaintScore() {
        this.canvas = new Canvas(1280, 90);   // chiều ngang, chiều dọc

        this.getChildren().add(this.canvas);
    }

    public void paint() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();

        //--- draw background ---
        g.setFill(Color.web("#9CB4CC"));
        g.fillRect(0, 0, 1280, 50);   // x, y, width, height
        //------------------------

        //--- draw user name ------
        g.setFill(Color.BLACK);
        g.setFont(new Font(25));
        g.fillText(userName, 50, 35);   // x, y
        //--------------------

        //--- draw score --------
        g.setFill(Color.BLACK);
        g.setFont(new Font(25));
        g.fillText("Score: " + LineupMap.score, 1100, 35);  // x, y
        //-------------------------

    }
}

