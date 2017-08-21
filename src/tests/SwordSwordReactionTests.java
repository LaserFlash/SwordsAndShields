package tests;

import SwordsAndShields.controller.TextController;
import SwordsAndShields.model.Board;
import SwordsAndShields.model.Game;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SwordSwordReactionTests {

    @Test
    public void ensureBothPiecesDie(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "move A right",
                "pass",
                "pass",
                "pass",
                "create G 0",
                "react A G"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertEquals(null, b.getCellAt(2,2));
        assertEquals(null, b.getCellAt(2,3));
        assertTrue(b.isClear());
    }

    @Test
    public void ensureBothPiecesDie_UndoRevives(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "move A right",
                "pass",
                "pass",
                "pass",
                "create G 0",
                "react A G",
                "undo"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertNotEquals(null, b.getCellAt(2,2));
        assertNotEquals(null, b.getCellAt(2,3));
    }
}
