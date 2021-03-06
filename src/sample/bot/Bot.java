package sample.bot;

import sample.model.PlayerMap;
import sample.model.Point;
import sample.model.graphic.ModelSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class Bot {
    public static int mode = 2; //che do 1: de, 2: kho
    public static int map[][] ; //get info of map when playing
    // 0 chua ban, 1 ban hut, 2 ban trung, 3 thuyen bi ha, 4 o phe vat
    ArrayList<Point> points, hittingPoints;
    public boolean targetMode; //mode play
    PlayerMap playerMap;
    private final int[] dx = {1, 0, -1, 0};
    private final int[] dy = {0, 1, 0, -1};

    public Bot(PlayerMap playerMap) {
        this.playerMap = playerMap;
        targetMode = false;
        points = new ArrayList<>();
        hittingPoints = new ArrayList<>();
        map = new int[ModelSpec.mapSpots][ModelSpec.mapSpots];

        // chon cac diem dan cheo
        if(mode == 1) {
            for (int i = 0; i < ModelSpec.mapSpots; i++)
                for (int j = 0; j < ModelSpec.mapSpots; j++) {
                    points.add(new Point(i, j));
                }
        }
        else {
            for (int i = 0; i < ModelSpec.mapSpots; i++)
                for (int j = 0; j < ModelSpec.mapSpots; j++) {
                    if ((i + j) % 2 == 1)
                    {
                        points.add(new Point(i, j));
                    }
                }
        }
        Collections.shuffle(points); // tron cac diem random
    }
    //--------------------
    public void removeNoShipCoordinates() { // xoa cac point chac chan k co thuyen
        int spots = ModelSpec.mapSpots;
        int[][] cal1 = new int[spots][spots];
        int[][] cal2 = new int[spots][spots];

        for(int i=0; i<spots; i++){
            for(int j=0; j<spots; j++) {
                if(this.map[i][j] == 0 || this.map[i][j] == 2)
                    cal1[i][j] = 1 + cal1[i][Math.max(0,j-1)];
            }

            for(int j=spots-2; j>=0; j--) {
                if(this.map[i][j] == 0 || this.map[i][j] == 2)
                    cal1[i][j] = Math.max(cal1[i][j], cal1[i][j+1]);
            }
        }

        for(int j=0; j<spots; j++){
            for(int i=0; i<spots; i++) {
                if(this.map[i][j] == 0 || this.map[i][j] == 2)
                    cal2[i][j] = 1 + cal2[Math.max(0, i-1)][j];
            }

            for(int i=spots-2; i>=0; i--) {
                if(this.map[i][j] == 0 || this.map[i][j] == 2)
                    cal2[i][j] = Math.max(cal2[i][j], cal2[i+1][j]);
            }
        }

        for(int i=0; i<spots; i++) {
            for (int j = 0; j < spots; j++)
                if (cal1[i][j] < Collections.min(playerMap.remainingShip) && cal2[i][j] < Collections.min(playerMap.remainingShip)) {
                    points.remove(new Point(i, j));
                    map[i][j]=4; //o phe vat
                }
        }
    }

    public void inHuntMode() {
        Iterator itr = points.iterator();
        while (itr.hasNext()) { // lo???i b??? nh???ng ??i???m ???? b???n
            Point c = (Point) itr.next();
            if (map[c.getX()][c.getY()]!=0)
                itr.remove();
        }

        int x = points.get(0).getX();
        int y = points.get(0).getY();

        points.remove(0);
        this.targetMode = this.playerMap.shoot(x, y);
        if(this.targetMode) {
            hittingPoints.add(new Point(x, y));
        }
    }

    public void inTargetMode() {

        interface MyFunction {
            void run();
        }

        interface MyFunction2 {
            boolean run(int i, int j);
        }

        MyFunction func1 = () -> {
            int x=0, y=0;
            Random rand = new Random();
            int i = rand.nextInt(4);
            while(true)
            {
                x = dx[(i+1)%4]+hittingPoints.get(0).getX();
                y = dy[(i+1)%4]+hittingPoints.get(0).getY();
                i=(i+1)%4;
                if(ModelSpec.pointInMap(x, y) && map[x][y]==0)
                    break;
            }
            if(this.playerMap.shoot(x, y))
                hittingPoints.add(new Point(x, y));
        }; //chay khi trong hitting point chi co 1 Point

        MyFunction2 func2 = (i, j) -> {
            Point a = hittingPoints.get(i);
            Point b = hittingPoints.get(j);
            Point c = a.add(a.subtract(b));
            boolean ok = false;
            if(ModelSpec.pointInMap(c.getX(), c.getY()) && map[c.getX()][c.getY()]==0) {
                ok = true;
                if(this.playerMap.shoot(c.getX(), c.getY()))
                    hittingPoints.add(c);
            }
            return ok;
        }; //chay khi trong hitting point c?? > 1 Point

        if(hittingPoints.size()==1) { //khi b???n tr??ng 1 ??i???m => lu??n t??m ???????c
            func1.run();
        }
        else { // co >1 diem dang hit
            Collections.sort(hittingPoints);
            // ok kiem tra khi hunting 2 dau ma k gay thuyen
            boolean ok = func2.run(hittingPoints.size()-1, hittingPoints.size()-2);
            if(!ok) {
                ok = func2.run(0, 1);
            }

            if(!ok) { //neu ok=false => roi vao th chan 2 dau
                // lay mot diem r do lai theo funct
                Point c = hittingPoints.get(0);
                hittingPoints.clear();
                hittingPoints.add(c);
                func1.run();
            }
        }

        Iterator itr = hittingPoints.iterator();
        while (itr.hasNext()) { // lo???i b??? nh???ng ??i???m ??a xac dinh la thuyen chim
            Point c = (Point) itr.next();
            if (map[c.getX()][c.getY()]!=2)
                itr.remove();
        }

        if(hittingPoints.isEmpty()) {
            for(int i = 0; i<ModelSpec.mapSpots; i++)
                for(int j = 0; j<ModelSpec.mapSpots; j++)
                    if(map[i][j]==2) {
                        hittingPoints.add(new Point(i, j));
                    }
            if(hittingPoints.isEmpty()) // van rong
                this.targetMode = false;
        }

    }
    //---------------------------
    public void hard() {
        removeNoShipCoordinates();
        if(!targetMode)
            inHuntMode();
        else
            inTargetMode();
    }
    public void easy() {
        if(points.isEmpty())
            return;
        int x = points.get(0).getX();
        int y = points.get(0).getY();
        points.remove(0);
        this.targetMode = this.playerMap.shoot(x, y);
    }

    public void play() {
        if(mode==1){
            easy();
        }
        else{
            hard();
        }
    }
}
