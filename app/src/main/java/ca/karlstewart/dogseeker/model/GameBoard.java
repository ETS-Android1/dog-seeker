/*
    Stores a 2D array of game tiles, and handles calculation for count of row+column dogs present

*/

package ca.karlstewart.dogseeker.model;

import java.util.stream.Stream;

public class GameBoard {
    private GameBoardTile[][] board;
    private int width;
    private int height;
    private int dogsFound = 0;
    private int scansUsed = 0;
    private int[] hiddenDogsInColumn; // Stores number of hidden dogs in the corresponding column ([0] = first column)
    private int[] hiddenDogsInRow;    // Stores number of hidden dogs in the corresponding row ([0] = first row)

    public GameBoard(GameOptions options){
        // Ensure options object is valid
        if (options == null){
            throw new NullPointerException("GameOptions instance must be initialized before creating GameBoard");
        }

        // Initialize member variables using options data
        width = options.getBoardWidth();
        height = options.getBoardHeight();

        // Generate game board using default construction for GameBoardTile
        board = Stream.generate( () -> {
            return Stream.generate(GameBoardTile::new)  // Generate a row of new GameBoardTiles
                    .limit(width)                       // Generate width tiles in this row
                    .toArray(GameBoardTile[]::new);     // Store this row as a GameBoardTiles[] array
            }).limit(height)                    // Only generate height number of rows
            .toArray(GameBoardTile[][]::new);   // Store rows as a GameBoardTiles[][] 2D array


        hiddenDogsInColumn = new int[width]; // array components initialized with default int value 0
        hiddenDogsInRow = new int[height];
        // Add new play to play count in options
        options.addNewPlay();

        // Add dogs to board
        for (int i = 0; i < options.getNumDogs(); i++){
            // Generate random indices
            int dogXPos = (int) Math.floor( Math.random()*width ); // rand int in [0, width)
            int dogYPos = (int) Math.floor( Math.random()*height ); // rand int in [0, height)
            if (board[dogYPos][dogXPos].isDog()) {
                // Dogs need their personal space. Another iter needed to find suitable dog spot
                i--;
            }
            else {
                // Tile has no dog, insert a dog.
                board[dogYPos][dogXPos].setIsDog(true);
                // Maintain dogsInRow and dogsInColumn arrays
                hiddenDogsInColumn[dogXPos]++;
                hiddenDogsInRow[dogYPos]++;
            }
        }

        // Update nearby dogs for board tiles
        updateDogsNearby();
    }

    // Updates dogsNearby for each GameBoardTile in board
    public void updateDogsNearby() {
        for (int currY = 0; currY < height; currY++) {
            for (int currX = 0; currX < width; currX++) {
                board[currY][currX].setDogsNearby(hiddenDogsInColumn[currX] + hiddenDogsInRow[currY]);
            }
        }
    }

    // PET Scanner updates hiddenDog vars, and returns the tile at the scanX, scanY position in board
    public GameBoardTile scanTile(int scanX, int scanY) {
        GameBoardTile scannedTile = board[scanY][scanX];
        if (scannedTile.isDog() & !scannedTile.isFound()) {
            // Dog found for first time, reduce count of hidden dogs for this row and column
            hiddenDogsInColumn[scanX]--;
            hiddenDogsInRow[scanY]--;
            updateDogsNearby();
            dogsFound++;
            scannedTile.setFound(true);
        }
        scansUsed++;
        return scannedTile;
    }

    public GameBoardTile getTile(int x, int y) {
        return board[y][x];
    }

    // Return given dog's voice
    public GameBoardTile.dogVoiceType getDogVoiceType(int x, int y) {
        return board[y][x].getDogVoice();
    }

    // Return count of nearby dogs for given tile
    public int getTileDogsNearby(int scanX, int scanY) {
        return board[scanY][scanX].getDogsNearby();
    }

    public int getDogsFound() {
        return dogsFound;
    }

    public int getScansUsed() {
        return scansUsed;
    }
}
