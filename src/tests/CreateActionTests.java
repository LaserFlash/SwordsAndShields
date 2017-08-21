package tests;

import SwordsAndShields.controller.TextController;
import SwordsAndShields.model.Board;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.TurnState;
import org.junit.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class CreateActionTests {

    @Test
    public void createPiece_CheckExistence(){
        List<String> lines = Arrays.asList(
                "create A 0"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertEquals('A',b.getCellAt(2,2).getID());
        assertTrue(g.getCurrentPlayer().getUsedPieceIDs().contains('A'));
        assertFalse(g.getCurrentPlayer().getUnusedPieceIDs().contains('A'));
        assertEquals(TurnState.MOVE_ROTATE,g.getTurnState());
    }
    @Test
    public void createPiece_Undo(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "undo"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertEquals(null,b.getCellAt(2,2));
        assertFalse(g.getCurrentPlayer().getUsedPieceIDs().contains('A'));
        assertTrue(g.getCurrentPlayer().getUnusedPieceIDs().contains('A'));
        assertEquals(TurnState.CREATE,g.getTurnState());
    }
}
