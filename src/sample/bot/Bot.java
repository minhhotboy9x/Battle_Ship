package sample.bot;

import sample.model.graphic.ModelSpec;

import java.util.ArrayList;

public class Bot {
    public static int mode = 0; //che do 1: de, 2: kho
    public static int map[][] = new int[ModelSpec.mapSpots][ModelSpec.mapSpots]; //get info of map
    // 0 chua ban, 1 ban hut, 2 ban trung, 3 thuyen bi ha
    ArrayList<Integer> playShipList;
}
