package sample.model.graphic;

public class ModelSpec {
    // map
    public static int mapSpots = 10; //10x10 for all map
    //______________
    public static double mapSquareSize = 20; //chieu dai 1 o
    public static double mapSize = 400; // chieu dai canh map
    //--------------

    // lineupMap
    public static double lineupMapSize = 500; //chieu dai lineupMap
    public static double posLineUpMapX = 50; //vi tri lineupMap
    public static double posLineUpMapY = 150; //vi tri lineupMap
    public static double lineupMapSquareSize = lineupMapSize / mapSpots; //50
    //--------------

    // container lineupShip (chua trong rectangle)
    public static double rectWidth = 400; //chieu rong lineupMap
    public static double rectHeight = 600; //chieu dai lineupMap

    public static double posLineUpRectX = 770; //vi tri linupMap
    public static double posLineUpRectY = 80; //vi tri lineupMap
    //----------------

    //-----game
    public static double posPlayerMapX = 100; //vi tri playerMap
    public static double posPlayerMapY = 275; //vi tri playerMap
    public static double gameMapSize = 400; // chieu dai game map
    public static double gameMapSquareSize = gameMapSize / mapSpots; //40
    public static double posBotMapX = 780; //vi tri botMap
    public static double posBotMapY = 275; //vi tri botMap
    //

    public static boolean pointInMap(int x, int y){
        return 0<=x && x < mapSpots && 0<=y && y<mapSpots;
    }
}
