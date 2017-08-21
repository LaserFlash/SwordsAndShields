package SwordsAndShields.model.cells;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.TextRep;

/**
 * BoardCell represents a cell on the board
 */
public abstract class BoardCell implements TextRep{

    /**
     * Size of the text representation for formatting
     */
    public static final int TEXTREP_SIZE = 3;

    /**
     * Default format for text appearance
     *  .  .  .
     *  .  .  .
     *  .  .  .
     * @return
     */
    public static char[][] initialTextRep() { return TextRep.initialTextRep(TEXTREP_SIZE,TEXTREP_SIZE,BLANK_TEXT);}

    public PieceCell.SideType getSide(Direction direction){
        return PieceCell.SideType.EMPTY;
    }

    /**
     * Get the ID of the cell
     * Can be overridden by extending classes
     * @return
     */
    public char getID(){
        return ' ';
    }
}
