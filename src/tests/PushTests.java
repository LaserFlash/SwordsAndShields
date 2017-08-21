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

import static junit.framework.TestCase.assertEquals;

public class PushTests {
    @Test
    public void pushCell_general() {


        List<String> lines = Arrays.asList(
                "create C 0",
                "move C right",
                "pass",
                "pass",
                "pass",
                "create E 0",
                "move E right"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n", lines))), System.out);
        controller.GameLoop();

        assertEquals(null, b.getCellAt(2, 5));
        assertEquals('C', b.getCellAt(2, 4).getID());
        assertEquals('E', b.getCellAt(2, 3).getID());
    }

    @Test
    public void pushCell_general2() {


        List<String> lines = Arrays.asList(
                "create C 0",
                "move C right",
                "pass",
                "pass",
                "pass",
                "create E 0",
                "move E right",
                "pass",
                "pass",
                "pass",
                "create A 0",
                "move A right"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n", lines))), System.out);
        controller.GameLoop();

        assertEquals(null, b.getCellAt(2, 6));
        assertEquals('C', b.getCellAt(2, 5).getID());
        assertEquals('E', b.getCellAt(2, 4).getID());
        assertEquals('A', b.getCellAt(2, 3).getID());
    }

    @Test
    public void pushCell_general_undo() {

        List<String> lines = Arrays.asList(
                "create C 0",
                "move C right",
                "pass",
                "pass",
                "pass",
                "create E 0",
                "move E right",
                "undo"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n", lines))), System.out);
        controller.GameLoop();

        assertEquals(null, b.getCellAt(2, 4));
        assertEquals('C', b.getCellAt(2, 3).getID());
        assertEquals('E', b.getCellAt(2, 2).getID());
    }

    @Test
    public void pushCell_general2_undo() {


        List<String> lines = Arrays.asList(
                "create C 0",
                "move C right",
                "pass",
                "pass",
                "pass",
                "create E 0",
                "move E right",
                "pass",
                "pass",
                "pass",
                "create A 0",
                "move A right",
                "undo"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n", lines))), System.out);
        controller.GameLoop();

        assertEquals('C', b.getCellAt(2, 4).getID());
        assertEquals('E', b.getCellAt(2, 3).getID());
        assertEquals('A', b.getCellAt(2, 2).getID());
    }

    @Test
    public void pushCell_SwordShield() {

        List<String> lines = Arrays.asList(
                "create E 0",
                "move E right",
                "pass",
                "pass",
                "pass",
                "create H 270",
                "move E right",
                "move H right",
                "pass",
                "pass",
                "pass",
                "create I 0",
                "react I H"

        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n", lines))), System.out);
        controller.GameLoop();

        assertEquals('I', b.getCellAt(2, 2).getID());
        assertEquals(null, b.getCellAt(2, 3));
        assertEquals('H', b.getCellAt(2, 4).getID());
        assertEquals('E', b.getCellAt(2, 5).getID());
    }

    @Test
    public void pushCell_SwordShield_undo() {

        List<String> lines = Arrays.asList(
                "create E 0",
                "move E right",
                "pass",
                "pass",
                "pass",
                "create H 270",
                "move E right",
                "move H right",
                "pass",
                "pass",
                "pass",
                "create I 0",
                "react I H",
                "undo"

        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n", lines))), System.out);
        controller.GameLoop();

        assertEquals('I', b.getCellAt(2, 2).getID());
        assertEquals(null, b.getCellAt(2, 5));
        assertEquals('H', b.getCellAt(2, 3).getID());
        assertEquals('E', b.getCellAt(2, 4).getID());
        assertEquals(TurnState.REACTIONS,g.getTurnState());
    }

}
