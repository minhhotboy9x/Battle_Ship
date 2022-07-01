package sample.model;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import sample.model.base_class.Coordinate;

public class Shoot extends Coordinate {
    double radius;
    Circle c;
    public Shoot(double x, double y, double radius, Pane pane) {
        super(x, y, pane);
        c = new Circle(radius);
        c.setCenterX(x);
        c.setCenterY(y);
        c.setStrokeWidth(0.0);
    }

    public void draw(String s){
        Image img = new Image(Shoot.class.getResource("../../resource/image/shoot/"+ s +".png").toString());
        c.setFill(new ImagePattern(img));
        c.setViewOrder(1.0);
        pane.getChildren().add(c);
    }
}
