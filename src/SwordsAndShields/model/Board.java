package SwordsAndShields.model;

import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.NonCell;
import SwordsAndShields.model.cells.PlayerCell;

import java.awt.*;


/**
 * Object representing the game board.
 * Allows manipulation of cells, such as adding and removing
 */
public class Board implements TextRep {
    private static final int ROWS = 10;
    private static final int COLS = 10;

    private BoardCell[][] cells;

    public Board(){
        this.cells = new BoardCell[ROWS][COLS];
    }

    /**
     * Put the provided cell onto the board at a given location
     * @param boardCell
     * @param row
     * @param col
     */
    public void addCell(BoardCell boardCell, int row, int col) {
        if (getCellAt(row,col) != null){
            return;
        }
        cells[row][col] = boardCell;
    }

    /**
     * Removes the cell from the given location
     * No being present is a null value for that location
     * @param row
     * @param col
     * @return  the cell removed
     */
    public BoardCell removeCell(int row, int col){
        BoardCell cell = cells[row][col];
        cells[row][col] = null;
        return cell;
    }

    /**
     * Checks if the board contains cells
     * @return true for no cells, false otherwise
     */
    public boolean isEmpty(){
        for(int r = 0; r < cells.length; r++){
            for(int c = 0; c < cells[0].length; c++){
                if(cells[r][c] != null) return false;
            }
        }
        return true;
    }

    /**
     * Return the number of Rows making up the board
     * @return
     */
    public int getNumRows(){
        return ROWS;
    }

    /**
     * Return the number of Columns making up the board
     * @return
     */
    public int getNumCols(){
        return COLS;
    }


    /**
     * Find the cell at a give location and return it
     * A null return indicates no cell being present
     * @param row
     * @param col
     * @return the cell at given location
     */
    public BoardCell getCellAt(int row, int col){
        return cells[row][col];
    }

    /**
     * Given a cell find it on the boart and return its locatino
     * @param cell
     * @return Point representation of the location, x = col, y = row
     */
    public Point getRowColOf(BoardCell cell){
        for (int r = 0; r < cells.length; r++){
            for (int c = 0; c < cells[0].length; c++){
                if (cells[r][c] == cell){
                    return new Point(c,r);
                }
            }
        }
        return null;
    }

    /**
     * Ignoring player cells and NonCells check if the board is empty
     *      i.e. empty if board does not contain pieces
     * @return
     */
    public boolean isClear(){
        for(int r = 0; r < cells.length; r++){
            for(int c = 0; c < cells[0].length; c++){
                if(cells[r][c] != null){
                    if (cells[r][c].getClass() != NonCell.class && cells[r][c].getClass() != PlayerCell.class) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    @Override
    public char[][] textRep() {

        int height = cells.length * BoardCell.TEXTREP_SIZE;
        int width = cells[0].length * BoardCell.TEXTREP_SIZE;
        char[][] rep = TextRep.initialTextRep(height,width);

        for(int r = 0; r < cells.length; r++){
            for (int c = 0; c < cells[0].length; c++){
                BoardCell cell = cells[r][c];
                if (cell == null) { continue; }

                char[][] cellRep = cell.textRep();

                TextRep.insertRep(cellRep,rep,r * BoardCell.TEXTREP_SIZE,c * BoardCell.TEXTREP_SIZE);
            }
        }

        return rep;
    }

}
