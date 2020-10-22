/*
    Stores a 2D array of game tiles, and handles calculation for count of row+column dogs present

*/

package ca.karlstewart.dogseeker.model;

public class GameBoard {
    private GameBoardTile[][] board;

    public GameBoard(GameOptions options){
        // Ensure options object is valid
        if (options == null){
            throw new NullPointerException("GameOptions instance must be initialized before creating GameBoard");
        }

        int width = options.getBoardWidth();
        int height = options.getBoardHeight();
        board = new GameBoardTile[width][height];
        // Add dogs to board
        GameBoardTile first = board[0][0];
        for (int i = 0; i < options.getNumDogs(); i++){
            // Generate random indices
            int dogXPos = (int) Math.floor( Math.random()*width );
            int dogYPos = (int) Math.floor( Math.random()*height );
            if (board[dogXPos][dogYPos].isDog()) {
                // Dogs need their personal space. Another iter needed to find suitable dog spot
                i--;
            }
            else {
                // Tile has no dog, insert a dog.
                board[dogXPos][dogYPos].setIsDog(true);
            }
        }
    }
    //TODO: add PET scanning, add hidden dog detection
    public GameBoardTile.dogVoiceType getDogVoiceType(int x, int y) {
        return board[x][y].getDogVoice();
    }
}
