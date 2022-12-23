package Rooms;

import Map.Map;

public class bossRoom extends Room{
    public bossRoom(int row, int col, Map parentMap) {
        super(row,col,parentMap);
        type = "boss";
    }

    public void enterRoom() {
        super.enterRoom();
        //plus all boss room specific initialization
    }
}
