package sample.model;

public class Point implements Comparable<Point>{
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point subtract(Point y){
        return new Point(this.x - y.x, this.y - y.y);
    }

    public Point add(Point y){
        return new Point(this.x + y.x, this.y + y.y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int compareTo(Point y) {
        if(this.x != y.x)
            return this.x-y.x;
        else
            return this.y-y.y;
    }
}
