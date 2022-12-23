package Rooms;

import Map.Map;

public class itemRoom extends Room {
    public itemRoom(int row, int col, Map parentMap) {
        super(row,col,parentMap);
        type = "item";
    }

    public void enterRoom() {
        super.enterRoom();
        //plus all item room specific initialization
    }
}
