package ca.karlstewart.dogseeker;

import org.junit.Test;

import ca.karlstewart.dogseeker.model.GameBoardTile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GameBoardTileTest {
    @Test
    // Test constructor
    public void gameBoardTileConstructor() {
        GameBoardTile tile1 = new GameBoardTile(false);
        GameBoardTile tile2 = new GameBoardTile(false);
        GameBoardTile tile3 = new GameBoardTile(false);
        System.out.println(tile1.getDogVoice());
        System.out.println(tile2.getDogVoice());
        System.out.println(tile3.getDogVoice());
        assertFalse(tile1.isDog());
        assertFalse(tile2.isDog());
        assertFalse(tile3.isDog());
    }
}
