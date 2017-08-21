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

public class SwordFaceReactionTests {
    @Test
    public void ensureGameEnds_Suicide(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "move A up",
                "react A 0",
                "pass",
                "create A 0"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertEquals("YELLOW(1)",g.getWinner().getName());
        assertEquals(null, b.getCellAt(7,7));

    }

    @Test
    public void ensureGameEnds_KillOtherPlayer(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "move A right",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A right",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A right",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A right",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A right",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A right",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A down",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A down",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A down",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A down",
                "pass",
                "pass",
                "pass",
                "pass",
                "move A down",
                "react A 1",
                "pass",
                "create A 0"

        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertEquals("GREEN(0)",g.getWinner().getName());
        assertEquals(null, b.getCellAt(7,7));

    }
}
