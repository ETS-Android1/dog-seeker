/*
    Stores a 2D array of game tiles, and handles calculation for count of row+column dogs present

*/

package ca.karlstewart.dogseeker.model;

public class GameBoard {
    private GameBoardTile[][] board;
    private int width;
    private int height;

    public GameBoard(int w, int h){
        width = w;
        height = h;
        board = new GameBoardTile[w][h];

        GameBoardTile first = board[0][0];

    }


}
