package Rooms;

import Map.Map;

public class voidRoom extends Room {
    public voidRoom(int row, int col, Map parentMap) {
        super(row,col,parentMap);
        type = "void";
    }
}
