package tests;

import SwordsAndShields.model.Board;
import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.NonCell;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTests {

    @Test
    public void isEmpty_BoardEmptyCheck() {
        Board b = new Board();
        assertTrue(b.isEmpty());

        for (int r = 0; r < b.getNumRows(); r++){
            for (int c = 0; c < b.getNumCols(); c++){
                assertEquals(null,b.getCellAt(r,c));
            }
        }

        b.addCell(new NonCell(),0,0);
        assertFalse(b.isEmpty());

    }

    @Test
    public void isEmpty_BoardNotEmptyCheck() {
        Board b = new Board();
        b.addCell(new NonCell(),0,0);
        assertFalse(b.isEmpty());
    }


    @Test
    public void addCell_Success() {
        Board b = new Board();
        BoardCell cell= new NonCell();
        b.addCell(cell,0,0);

        assertEquals(cell,b.getCellAt(0,0));
    }
    @Test
    public void addCell_CellPresent() {
        Board b = new Board();
        BoardCell cell= new NonCell();
        b.addCell(cell,0,0);
        b.addCell(new NonCell(),0,0);

        assertEquals(cell,b.getCellAt(0,0));
    }

    @Test
    public void removeCell_BoardReturnsAndRemovesCell() {
        Board b = new Board();
        BoardCell cell= new NonCell();
        b.addCell(cell,0,0);

        assertEquals(cell,b.getCellAt(0,0));
        assertEquals(cell,b.removeCell(0,0));
        assertTrue(b.isEmpty());
    }



    @Test
    public void getCellAt_ReturnsCell() {
        Board b = new Board();
        List<BoardCell> cells = new ArrayList<>();

        for (int r = 0; r < b.getNumRows(); r++){
            for (int c = 0; c < b.getNumCols(); c++){
                cells.add(new NonCell());
                b.addCell(cells.get(r*b.getNumRows() + c),r,c);
            }
        }

        for (int r = 0; r < b.getNumRows(); r++){
            for (int c = 0; c < b.getNumCols(); c++){
                assertEquals("Occurred at r:" + r + " c:" + c ,cells.get(r*b.getNumRows() + c),b.getCellAt(r,c));
            }
        }

    }

    @Test
    public void getRowColOf_PresentCell() {
        Board b = new Board();
        List<BoardCell> cells = new ArrayList<>();

        for (int r = 0; r < b.getNumRows(); r++){
            for (int c = 0; c < b.getNumCols(); c++){
                cells.add(new NonCell());
                b.addCell(cells.get(r*b.getNumRows() + c),r,c);
            }
        }

        for (int r = 0; r < b.getNumRows(); r++){
            for (int c = 0; c < b.getNumCols(); c++){
               assertEquals(new Point(c,r) ,b.getRowColOf(cells.get(r*b.getNumRows() + c)));
            }
        }
    }

    @Test
    public void getRowColOf_CellNotPresent() {
        Board b = new Board();
        List<BoardCell> cells = new ArrayList<>();

        for (int r = 0; r < b.getNumRows(); r++){
            for (int c = 0; c < b.getNumCols(); c++){
                cells.add(new NonCell());
                b.addCell(cells.get(r*b.getNumRows() + c),r,c);
            }
        }

        for (int r = 0; r < b.getNumRows(); r++){
            for (int c = 0; c < b.getNumCols(); c++){
                assertEquals(null ,b.getRowColOf(new NonCell()));
            }
        }
    }


}
