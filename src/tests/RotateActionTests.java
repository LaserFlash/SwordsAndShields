package tests;

import SwordsAndShields.controller.TextController;
import SwordsAndShields.model.Board;
import SwordsAndShields.model.Direction;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.cells.PieceCell;
import org.junit.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


public class RotateActionTests {
    @Test
    public void rotatePiece_checkOrientation(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "rotate A 180"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        PieceCell cell = (PieceCell) b.getCellAt(2,2);
        assertEquals(PieceCell.SideType.SWORD,cell.getSide(Direction.NORTH));
        assertEquals(PieceCell.SideType.SWORD,cell.getSide(Direction.EAST));
        assertEquals(PieceCell.SideType.SWORD,cell.getSide(Direction.SOUTH));
        assertEquals(PieceCell.SideType.SHIELD,cell.getSide(Direction.WEST));
    }
    @Test
    public void rotatePiece_Undo(){
        List<String> lines = Arrays.asList(
                "create A 0",
                "rotate A 180",
                "undo"
        );

        Board b = new Board();
        Game g = new Game(b);
        TextController controller = new TextController(g, new Scanner(new StringReader(String.join("\n",lines))), System.out);
        controller.GameLoop();

        PieceCell cell = (PieceCell) b.getCellAt(2,2);
        assertEquals(PieceCell.SideType.SWORD,cell.getSide(Direction.NORTH));
        assertEquals(PieceCell.SideType.SHIELD,cell.getSide(Direction.EAST));
        assertEquals(PieceCell.SideType.SWORD,cell.getSide(Direction.SOUTH));
        assertEquals(PieceCell.SideType.SWORD,cell.getSide(Direction.WEST));
    }
}
