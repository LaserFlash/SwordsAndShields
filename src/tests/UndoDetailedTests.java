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

public class UndoDetailedTests {
    @Test
    public void undoCreation() {


    List<String> lines = Arrays.asList(
            "create A 0",
            "move A right",
            "pass",
            "pass",
            "pass",
            "create B 0",
            "react A B",
            "undo",
            "undo"
    );

    Board b = new Board();
    Game g = new Game(b);
    TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n", lines))), System.out);
    controller.GameLoop();

    assertEquals(TurnState.CREATE, g.getTurnState());
    }
}
