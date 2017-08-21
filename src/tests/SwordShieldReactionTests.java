package tests;

import SwordsAndShields.controller.TextController;
import SwordsAndShields.model.Board;
import SwordsAndShields.model.Game;
import org.junit.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class SwordShieldReactionTests {
    @Test
    public void ensureShieldMoveBack(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "move A right",
                "pass",
                "pass",
                "pass",
                "create C 0",
                "react A C"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertNotEquals(null, b.getCellAt(2,2));
        assertEquals(null, b.getCellAt(2,3));
        assertNotEquals(null, b.getCellAt(2,4));

    }

    @Test
    public void ensureShieldMoveBack_undo(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "move A right",
                "pass",
                "pass",
                "pass",
                "create C 0",
                "react A C",
                "undo"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertEquals('C', b.getCellAt(2,2).getID());
        assertEquals('A', b.getCellAt(2,3).getID());
        assertEquals(null, b.getCellAt(2,4));
    }
}
