package SwordsAndShields.model.cells;

import java.awt.*;

/**
 * NonCell is a type of BoardCell
 * This represents a cell that "isn't there',
 * such as those behind the player cells.
 */
public class NonCell extends BoardCell {


    @Override
    /**
     * Default appearance for text representation is blank (no characters)
     */
    public char[][] textRep() {
        char[][] rep = initialTextRep();
        rep[0][0] = ' ';
        rep[0][2] = ' ';
        rep[2][0] = ' ';
        rep[2][2] = ' ';
        return rep;
    }

    @Override
    public void draw(Graphics g, int x, int y, Color bg) {

    }
}
