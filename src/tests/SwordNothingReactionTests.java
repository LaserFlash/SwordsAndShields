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

public class SwordNothingReactionTests {

    @Test
    public void ensurePieceDies(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "move A right",
                "pass",
                "pass",
                "pass",
                "create B 0",
                "react A B"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertEquals(null, b.getCellAt(2,2));
        assertNotEquals(null, b.getCellAt(2,3));
    }

    @Test
    public void ensurePieceDies_undoRevives(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "move A right",
                "pass",
                "pass",
                "pass",
                "create B 0",
                "react A B",
                "undo"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        assertNotEquals(null, b.getCellAt(2,2));
        assertNotEquals(null, b.getCellAt(2,3));
        assertEquals(TurnState.REACTIONS,g.getTurnState());
    }

    @Test
    public void ensurePieceDies_DifferentPlayerReaction(){
            List<String> lines = Arrays.asList(
                    "create A 90",
                    "move a right",
                    "pass",
                    "create e 0",
                    "move e up",
                    "pass",
                    "pass",
                    "move A right",
                    "pass",
                    "pass",
                    "move e up",
                    "pass",
                    "pass",
                    "move A right",
                    "pass",
                    "pass",
                    "move e up",
                    "pass",
                    "pass",
                    "move A right",
                    "pass",
                    "pass",
                    "move e up",
                    "pass",
                    "pass",
                    "move A right",
                    "pass",
                    "pass",
                    "move e up",
                    "react A e"
            );

            Board b = new Board();
            Game g = new Game(b);
            TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n", lines))), System.out);
            controller.GameLoop();

            assertEquals(null, b.getCellAt(2, 7));
            assertNotEquals(null, b.getCellAt(2, 6));
        }
}
