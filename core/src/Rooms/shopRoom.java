package Rooms;

import Map.Map;

public class shopRoom extends Room {
    public shopRoom(int row, int col, Map parentMap) {
        super(row,col,parentMap);
        type = "shop";
    }

    public void enterRoom() {
        super.enterRoom();
        //plus all shop room specific initialization
    }
}
