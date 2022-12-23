package Rooms;

import Map.Map;

public class enemyRoom extends Room {
    public enemyRoom(int row, int col, Map parentMap) {
        super(row,col,parentMap);
        type = "enemy";
    }

    public void enterRoom() {
        super.enterRoom();
        //plus all enemy room specific initialization
    }
}
