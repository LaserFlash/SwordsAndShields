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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoveActionTests {

    @Test
    public void movePiece_CheckExistence(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "move A right"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertEquals('A',b.getCellAt(2,3).getID());
        assertTrue(g.getCurrentPlayer().getUsedPieceIDs().contains('A'));

        assertFalse(g.getCurrentPlayer().getUnusedPieceIDs().contains('A'));
        assertEquals(TurnState.MOVE_ROTATE,g.getTurnState());
    }
    @Test
    public void movePiece_Undo(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "move A right",
                "undo"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertEquals(null,b.getCellAt(2,3));
        assertEquals('A',b.getCellAt(2,2).getID());
        assertTrue(g.getCurrentPlayer().getUsedPieceIDs().contains('A'));

        assertEquals(TurnState.MOVE_ROTATE,g.getTurnState());
    }


    @Test
    public void movePiece_OffBoard_CheckExistence(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "move A right",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A up",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A up",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A up"

        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertTrue(b.isClear());
        assertTrue(g.getCurrentPlayer().inGrave('A'));

        assertFalse(g.getCurrentPlayer().getUnusedPieceIDs().contains('A'));
        assertEquals(TurnState.MOVE_ROTATE,g.getTurnState());
    }
    @Test
    public void movePiece_OffBoard_Undo(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "move A right",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A up",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A up",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A up",
                "undo"

        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertEquals('A',b.getCellAt(0,3).getID());
        assertTrue(g.getCurrentPlayer().getUsedPieceIDs().contains('A'));
        assertFalse(g.getCurrentPlayer().inGrave('A'));

        assertEquals(TurnState.MOVE_ROTATE,g.getTurnState());
    }
}
