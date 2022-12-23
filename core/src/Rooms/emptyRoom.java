package Rooms;

import Map.Map;

public class emptyRoom extends Room {
    public emptyRoom(int row, int col, Map parentMap) {
        super(row,col,parentMap);
        type = "empty";
    }
}
