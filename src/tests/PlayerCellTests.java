package tests;

import SwordsAndShields.model.cells.PlayerCell;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerCellTests {
    @Test
    public void textRep() {
        PlayerCell p = new PlayerCell(PlayerCell.Token.YELLOW);

        /*
                + + +
                + 1 +
                + + +
         */
        char[][] rep = new char[][]{
                {'+','+','+'},
                {'+','1','+'},
                {'+','+','+'},
        };

        assertEquals(rep,p.textRep());

        p = new PlayerCell(PlayerCell.Token.GREEN);

        /*
                + + +
                + 0 +
                + + +
         */
        rep = new char[][]{
                {'+','+','+'},
                {'+','0','+'},
                {'+','+','+'},
        };

        assertEquals(rep,p.textRep());
    }

    @Test
    public void getName_TokenCheck() {
        PlayerCell p = new PlayerCell(PlayerCell.Token.YELLOW);
        assertEquals(PlayerCell.Token.YELLOW + "(1)",p.getName());

        p = new PlayerCell(PlayerCell.Token.GREEN);
        assertEquals(PlayerCell.Token.GREEN + "(0)",p.getName());
    }

}