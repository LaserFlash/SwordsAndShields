package tests;

import SwordsAndShields.model.cells.NonCell;
import org.junit.Test;

import static org.junit.Assert.*;

public class NonCellTests {

    @Test
    public void textRep_Correctness() {



        NonCell cell = new NonCell();
        /*


         */
        char[][] rep = new char[][]{
                {' ',' ',' '},
                {' ',' ',' '},
                {' ',' ',' '}
        };

        assertEquals(rep,cell.textRep());

    }
}