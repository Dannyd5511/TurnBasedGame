package Rooms;

import Map.Map;

public class Room {
    String type;
    private final int row;
    private final int col;
    private final Map parentMap;

    public Room(int row,int col, Map parentMap) {
        this.row = row;
        this.col = col;
        this.parentMap = parentMap;
    }

    public String getType() {
        return type;
    }

    /**
     * Creates everything inside the room as it's entered, different version in most subclasses
     */
    public void enterRoom() {
    }

    /**
     * @return whether an upwards neighbor exists
     */
    public boolean checkNeighborUp() {
        return !parentMap.getRoomType(row-1, col).equals("void");
    }

    /**
     * @return whether a downwards neighbor exists
     */
    public boolean checkNeighborDown() {
        return !parentMap.getRoomType(row+1,col).equals("void");
    }

    /**
     * @return whether a leftwards neighbor exists
     */
    public boolean checkNeighborLeft() {
        return !parentMap.getRoomType(row,col-1).equals("void");
    }

    /**
     * @return whether a rightwards neighbor exists
     */
    public boolean checkNeighborRight() {
        return !parentMap.getRoomType(row,col+1).equals("void");
    }
}
