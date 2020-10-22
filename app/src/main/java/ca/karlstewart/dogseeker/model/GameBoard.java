/*
    Stores a 2D array of game tiles, and handles calculation for count of row+column dogs present

*/

package ca.karlstewart.dogseeker.model;

public class GameBoard {
    private GameBoardTile[][] board;
    int width;
    int height;
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
        board = new GameBoardTile[width][height]; // uses default constructor, gives dogs random voices, isDog false
        hiddenDogsInColumn = new int[width]; // array components initialized with default int value 0
        hiddenDogsInRow = new int[height];

        // Add dogs to board
        for (int i = 0; i < options.getNumDogs(); i++){
            // Generate random indices
            int dogXPos = (int) Math.floor( Math.random()*width ); // rand int in [0, width)
            int dogYPos = (int) Math.floor( Math.random()*height ); // rand int in [0, height)
            if (board[dogXPos][dogYPos].isDog()) {
                // Dogs need their personal space. Another iter needed to find suitable dog spot
                i--;
            }
            else {
                // Tile has no dog, insert a dog.
                board[dogXPos][dogYPos].setIsDog(true);
                // Maintain dogsInRow and dogsInColumn arrays
                hiddenDogsInColumn[dogXPos]++;
                hiddenDogsInRow[dogYPos]++;
            }
        }
    }

    // Updates dogsNearby for each GameBoardTile in board
    public void updateDogsNearby() {
        for (int currY = 0; currY < height; currY++) {
            for (int currX = 0; currX < width; currX++) {
                board[currX][currY].setDogsNearby(hiddenDogsInColumn[currX] + hiddenDogsInRow[currY]);
            }
        }
    }

    // PET Scanner updates hiddenDog vars, and returns the tile at the scanX, scanY position in board
    public GameBoardTile scanTile(int scanX, int scanY) {
        GameBoardTile scannedTile = board[scanX][scanY];
        if (scannedTile.isDog()) {
            // Dog found, reduce count of hidden dogs for this row and column
            hiddenDogsInColumn[scanX]--;
            hiddenDogsInRow[scanY]--;
            updateDogsNearby();
        }
        return scannedTile;
    }

    // Return given dog's voice
    public GameBoardTile.dogVoiceType getDogVoiceType(int x, int y) {
        return board[x][y].getDogVoice();
    }

    // Return count of nearby dogs for given tile
    public int getTileDogsNearby(int scanX, int scanY) {
        return board[scanX][scanY].getDogsNearby();
    }
}
