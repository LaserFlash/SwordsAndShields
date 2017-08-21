package tests;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.cells.PieceCell;
import org.junit.Test;

import static org.junit.Assert.*;

public class PieceCellTests {

    @Test
    public void textRep_Correctness() {



        PieceCell cell = new PieceCell('a',
                new PieceCell.Sides(
                        PieceCell.SideType.SWORD,
                        PieceCell.SideType.SHIELD,
                        PieceCell.SideType.EMPTY,
                        PieceCell.SideType.SWORD
                )
        );
        /*
                |
              - a #

         */
        char[][] rep = new char[][]{
                {' ','|',' '},
                {'-','a','#'},
                {' ',' ',' '}
        };

        assertEquals(rep,cell.textRep());

    }
    @Test
    public void rotateAndTextRep_Correctness() {



        PieceCell cell = new PieceCell('a',
                new PieceCell.Sides(
                    PieceCell.SideType.SWORD,
                    PieceCell.SideType.SHIELD,
                    PieceCell.SideType.EMPTY,
                    PieceCell.SideType.SWORD
                )
        );
        /*
                |
              - a #

         */
        char[][] rep = new char[][]{
                {' ','|',' '},
                {'-','a','#'},
                {' ',' ',' '}
        };

        assertEquals(rep,cell.textRep());

        cell.rotate(Direction.EAST);

        rep = new char[][]{
                {' ','|',' '},
                {' ','a','-'},
                {' ','#',' '}
        };

        assertEquals(rep,cell.textRep());


        cell.rotate(Direction.WEST);

        rep = new char[][]{
                {' ','|',' '},
                {'-','a','#'},
                {' ',' ',' '}
        };

        assertEquals(rep,cell.textRep());

        cell.rotate(Direction.SOUTH);

        rep = new char[][]{
                {' ',' ',' '},
                {'#','a','-'},
                {' ','|',' '}
        };

        assertEquals(rep,cell.textRep());

        cell.rotate(Direction.NORTH);

        rep = new char[][]{
                {' ',' ',' '},
                {'#','a','-'},
                {' ','|',' '}
        };

        assertEquals(rep,cell.textRep());

    }

}