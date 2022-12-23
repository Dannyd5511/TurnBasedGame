package Map;

import Rooms.*;
import java.util.Random;
import java.lang.Math;

public class Map {
    private char[][] mapGrid;
    private Room[][] roomGrid;
    private int mapSize;
    private int center;
    private final int mapNumber;
    private final Random r = new Random();

    /**
     * Initializes and creates a random floor map
     * @param mapNumber what map number being generated (1, 2, or 3)
     */
    public Map(int mapNumber) {
        this.mapNumber = mapNumber;
        if(mapNumber == 1) {
            makeNewMap(9);
        }
        else if(mapNumber == 2) {
            makeNewMap(13);
        }
        else if(mapNumber == 3) {
            makeNewMap(17);
        }
        generateMap();
    }

    /**
     * Returns the value of an accessed room given x and y coordinates
     * @param x x value of accessed room
     * @param y y value of accessed room
     * @return type of accessed room
     */
    public String getRoomType(int x, int y) {
        return roomGrid[x][y].getType();
    }

    /**
     * Calls the enterRoom function of accessed room given x and y coordinates
     * @param x x coordinate of accessed room
     * @param y y coordinate of accessed room
     */
    public void enterRoom(int x, int y) {
        roomGrid[x][y].enterRoom();
    }

    /**
     * Creates an empty 2 dimension character array for the map
     * @param size how big the map will be across/up and down
     */
    private void makeNewMap(int size) {
        this.mapSize = size;
        this.center = size/2;
        mapGrid = new char[size][size];
        roomGrid = new Room[size][size];
        fillEmptyMap();
    }

    /**
     * Completely fills new map with empty spaces
     */
    private void fillEmptyMap() {
        for(int i=0; i<mapSize; i++) {
            for(int j=0; j<mapSize; j++) {
                mapGrid[i][j]=' ';
            }
        }
    }

    /**
     * Fills empty map with necessary rooms
     */
    private void generateMap() {
        //set the starting tile in the middle as an empty room
        mapGrid[center][center] = 'O';
        createMapBranches();
        for(int i=0; i<mapNumber; i++) {
            markSpecialRooms();
        }
        makeRoomArray();
        //if map isn't big enough, redo entirely
        if(countEnemyRooms() != 4 * mapNumber + 1) {
            if(mapNumber == 1) {
                makeNewMap(9);
            }
            else if(mapNumber == 2) {
                makeNewMap(13);
            }
            else if(mapNumber == 3) {
                makeNewMap(17);
            }
            generateMap();
        }
    }

    /**
     * Creates four branches, one from each end of the start room, then places a boss in the furthest away spot
     */
    private void createMapBranches() {
        char[] shuffledDirectionList = shuffleDirectionList();
        int[] branchLengths = new int[4];
        branchLengths[0] = 2 * mapNumber + 2;
        branchLengths[1] = 2 * mapNumber;
        branchLengths[2] = r.nextInt(2 * mapNumber) + 1;
        branchLengths[3] = 2 * mapNumber - branchLengths[2];
        for(int i=0; i<4; i++) {
            createBranch(branchLengths[i],shuffledDirectionList[i]);
        }
        createBossRoom();
    }

    /**
     * Returns the four directions in a shuffled order to randomize what direction the generation starts in
     * @return shuffled list of N, E, S, and W
     */
    private char[] shuffleDirectionList() {
        char[] directionList = {'N','E','S','W'};
        Random r = new Random();
        int offset = r.nextInt(4);
        char[] shuffledDirectionList = new char[4];
        for(int i=0; i<4; i++) {
            shuffledDirectionList[i] = directionList[offset%4];
            offset++;
        }
        return shuffledDirectionList;
    }

    /**
     * Places a boss room in the furthest away room from the start
     */
    private void createBossRoom() {
        int[] bossRoom = {center,center};
        for(int i=0; i<mapSize; i++) {
            for(int j=0; j<mapSize; j++) {
                if(mapGrid[i][j] == 'X') {
                    int[] roomToCheck = {i, j};
                    if (distanceFromCenter(bossRoom) < distanceFromCenter(roomToCheck)) {
                        if(countRoomNeighbors(roomToCheck[0],roomToCheck[1]) == 1) {
                            bossRoom = roomToCheck;
                        }
                    }
                }
            }
        }
        mapGrid[bossRoom[0]][bossRoom[1]] = 'B';
    }

    /**
     * Find the distance of a given room from the map's center
     * @param baseRoom room to find the distance from
     * @return distance from base room to center
     */
    private int distanceFromCenter(int[] baseRoom) {
        int distance = 0;
        distance += Math.abs(baseRoom[0]-center);
        distance += Math.abs(baseRoom[1]-center);
        return distance;
    }

    /**
     * Runs recursive function to create a branch in a given direction
     * @param direction Direction the branch goes in
     */
    private void createBranch(int branchLength, char direction) {
        int[] startRoom = determineStartRoom(direction);
        createBranchRecurse(startRoom[0],startRoom[1],branchLength);
    }

    /**
     * Finds the room generation should start in depending on direction of the branch
     * @param direction The direction the branch goes
     * @return The room generation should start in
     */
    private int[] determineStartRoom(char direction) {
        int[] startRoom = new int[2];
        if(direction == 'N') {
            startRoom[0] = center-1; startRoom[1] = center;
        }
        else if(direction == 'E') {
            startRoom[0] = center; startRoom[1] = center+1;
        }
        else if(direction == 'S') {
            startRoom[0] = center+1; startRoom[1] = center;
        }
        else if(direction == 'W') {
            startRoom[0] = center; startRoom[1] = center-1;
        }
        return startRoom;

    }

    /**
     * Recursive algorithm to randomly place rooms in a branch
     * @param row Row value currently being accessed
     * @param col Column value currently being accessed
     * @param remainingLength Number of rooms left to place in the branch
     */
    private void createBranchRecurse(int row, int col, int remainingLength) {
        if(remainingLength == 0) {
            return;
        }
        mapGrid[row][col] = 'X';
        char[] directionList = shuffleDirectionList();
        int i=0;
        while (i<4) {
            int nextDirection = directionList[i];
            if(r.nextInt(100) < 15 * mapNumber * i+1) {
                backtrackToLastCell(row,col,remainingLength);
                break;
            }
            else if (roomNotVisited(row, col, nextDirection) && roomNotCrowded(row, col, nextDirection)) {
                recurseToNewCell(row, col, nextDirection, remainingLength);
                break;
            }
            else if(i==3) {
                backtrackToLastCell(row,col,remainingLength);
            }
            i++;
        }
    }

    /**
     * Backtracks the generation to a cell already placed to avoid linear generation
     * @param row row being backtracked from
     * @param col column being backtracked from
     * @param remainingLength number of rooms left to generate (passed through because necessary to recurse)
     */
    private void backtrackToLastCell(int row, int col, int remainingLength) {
        char[] directionList = shuffleDirectionList();
        for(int i=0; i<4; i++) {
            if(roomVisited(row,col,directionList[i])) {
                recurseToNewCell(row,col,directionList[i],remainingLength+1);
                return;
            }
        }
    }

    /**
     * Checks whether a specific cell has been visit / set to an empty room
     * @param row row being checked from
     * @param col column being checked from
     * @param nextDirection direction being checked in
     * @return whether the room is visited / marked X
     */
    private boolean roomVisited(int row, int col, int nextDirection) {
        if(nextDirection == 'N') {
            if(row!=0) {
                return mapGrid[row-1][col] == 'X';
            }
        }
        if(nextDirection == 'E') {
            if(col != mapSize-1) {
                return mapGrid[row][col+1] == 'X';
            }
        }
        if(nextDirection == 'S') {
            if(row != mapSize-1) {
                return mapGrid[row+1][col] == 'X';
            }
        }
        if(nextDirection == 'W') {
            if(col != 0) {
                return mapGrid[row][col-1] == 'X';
            }
        }
        return false;
    }

    /**
     * Checks whether a room in a given direction is able to be accessed/edited
     * @param row row checking from
     * @param col column checking from
     * @param nextDirection direction being checked
     * @return whether the direction is valid
     */
    private boolean roomNotVisited(int row, int col, int nextDirection) {
        if(nextDirection == 'N') {
            if(row!=0) {
                return mapGrid[row-1][col] == ' ';
            }
        }
        if(nextDirection == 'E') {
            if(col != mapSize-1) {
                return mapGrid[row][col+1] == ' ';
            }
        }
        if(nextDirection == 'S') {
            if(row != mapSize-1) {
                return mapGrid[row+1][col] == ' ';
            }
        }
        if(nextDirection == 'W') {
            if(col != 0) {
                return mapGrid[row][col-1] == ' ';
            }
        }
        return false;
    }

    /**
     * Checks whether a room in a given direction has two or more neighbors already/is crowded
     * @param row row checking from
     * @param col column checking from
     * @param nextDirection direction being checked
     * @return whether the room is crowded or not
     */
    private boolean roomNotCrowded(int row, int col, int nextDirection) {
        if(nextDirection == 'N') {
            return countRoomNeighbors(row-1,col) < 2;
        }
        if(nextDirection == 'E') {
            return countRoomNeighbors(row,col+1) < 2;
        }
        if(nextDirection == 'S') {
            return countRoomNeighbors(row+1,col) < 2;
        }
        if(nextDirection == 'W') {
            return countRoomNeighbors(row,col-1) < 2;
        }
        return false;
    }

    /**
     * Runs the recursive function to a new cell in a given direction
     * @param row starting row
     * @param col starting column
     * @param nextDirection direction to recurse in
     * @param remainingLength value to update with recursion
     */
    private void recurseToNewCell(int row, int col, int nextDirection, int remainingLength) {
        if(nextDirection == 'N') {
            createBranchRecurse(row - 1, col, remainingLength - 1);
        }
        if(nextDirection == 'E') {
            createBranchRecurse(row, col + 1, remainingLength - 1);
        }
        if(nextDirection == 'S') {
            createBranchRecurse(row + 1, col, remainingLength - 1);
        }
        if(nextDirection == 'W') {
            createBranchRecurse(row, col - 1, remainingLength - 1);
        }
    }

    /**
     * Find the number of placed rooms adjacent to a room
     * @param row Row being examined
     * @param col Column being examined
     * @return Number of adjacent rooms
     */
    private int countRoomNeighbors(int row, int col) {
        int neighborCount = 0;
        if(row != 0) {
            if (mapGrid[row - 1][col] != ' ') {
                neighborCount++;
            }
        }
        if(col != mapSize-1) {
            if (mapGrid[row][col + 1] != ' ') {
                neighborCount++;
            }
        }
        if(row != mapSize-1) {
            if (mapGrid[row + 1][col] != ' ') {
                neighborCount++;
            }
        }
        if(col != 0) {
            if (mapGrid[row][col - 1] != ' ') {
                neighborCount++;
            }
        }
        return neighborCount;
    }

    /**
     * Places a number of item rooms and shop rooms in the map depending on map number
     */
    private void markSpecialRooms() {
        int rooms = countEnemyRooms();
        if(rooms<2) {rooms=2;}
        int itemRoomPlacement = r.nextInt(rooms);
        markEnemyRoom(itemRoomPlacement,'I');
        int shopPlacement = r.nextInt(rooms-1);
        markEnemyRoom(shopPlacement,'S');
    }

    /**
     * Marks one of the enemy rooms with an item room or a shop room
     * @param roomNumber Which of the enemy rooms to change
     * @param roomType What to change the enemy room to
     */
    private void markEnemyRoom(int roomNumber, char roomType) {
        int roomCount = -1;
        for(int i=0; i<mapSize; i++) {
            for(int j=0; j<mapSize; j++) {
                if(mapGrid[i][j] == 'X') {
                    roomCount++;
                }
                if(roomCount == roomNumber) {
                    mapGrid[i][j] = roomType;
                    return;
                }
            }
        }
    }

    /**
     * Count the number of enemy rooms in the map
     * @return Number of enemy rooms in the map
     */
    private int countEnemyRooms() {
        int count = 0;
        for(int i=0; i<mapSize; i++) {
            for(int j=0; j<mapSize; j++) {
                if(mapGrid[i][j]=='X') {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Print the map's character array
     */
    public void printMap() {
        for(int i=0; i<mapSize; i++) {
            for(int j=0; j<mapSize; j++) {
                System.out.print(mapGrid[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * Turns created character array into an array of Rooms.Room subclasses
     */
    private void makeRoomArray() {
        for(int i=0; i<mapSize; i++) {
            for(int j=0; j<mapSize; j++) {
                if(mapGrid[i][j] == ' ') {
                    roomGrid[i][j] = new voidRoom(i,j,this);
                }
                else if(mapGrid[i][j] == 'O') {
                    roomGrid[i][j] = new emptyRoom(i,j,this);
                }
                else if(mapGrid[i][j] == 'X') {
                    roomGrid[i][j] = new enemyRoom(i,j,this);
                }
                else if(mapGrid[i][j] == 'B') {
                    roomGrid[i][j] = new bossRoom(i,j,this);
                }
                else if(mapGrid[i][j] == 'S') {
                    roomGrid[i][j] = new shopRoom(i,j,this);
                }
                else if(mapGrid[i][j] == 'I') {
                    roomGrid[i][j] = new itemRoom(i,j,this);
                }
            }
        }
    }
}
