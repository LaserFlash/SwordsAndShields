package SwordsAndShields.ui;

import SwordsAndShields.model.Board;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.IllegalMoveException;
import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.PieceCell;
import SwordsAndShields.model.cells.PlayerCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardCanvas extends CanvasPanel {


    public BoardCanvas(GUIController controller,Game game){
        super(controller,game);
    }

    @Override
    protected void selectPiece(Point p) {

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRoundRect(0,0,this.getBounds().width,this.getBounds().height,20,20);
        Board b = game.getBoard();
        Color altPattern = Color.white;
        altPattern = altPattern(altPattern);
        int x = DrawPiece.xPadding;
        int y = DrawPiece.yPadding;
        for (int r = 0; r < b.getNumRows(); r++){
            for (int c = 0; c < b.getNumCols();c++){
                BoardCell cell = b.getCellAt(r,c);
                if (cell != null) {
                    cell.draw(g, x, y, getCellColor(cell));
                }else{
                    DrawPiece.drawNullCell(g,x,y,altPattern);
                }
                altPattern = altPattern(altPattern);
                x += DrawPiece.size;
            }
            altPattern = altPattern(altPattern);
            y+=DrawPiece.size;
            x = DrawPiece.xPadding;
        }

    }

    /**
     * Get the colour that the cell/piece should be drawn.
     * Depends on what player the cell belongs to.
     * @param cell
     * @return
     */
    private Color getCellColor(BoardCell cell){
        if (cell.getClass() == PieceCell.class){
            if (game.getGreenPiecesOnBoard().contains(cell)){
                return DrawPiece.greenBG;
            }
            if (game.getYellowPiecesOnBoard().contains(cell)){
                return DrawPiece.yellowBG;
            }
        }
        else if(cell.getClass() == PlayerCell.class){
            if (game.checkGreenPlayerCell(cell)){
                return DrawPiece.greenBG;
            }
            if (game.checkYellowPlayerCell(cell)){
                return DrawPiece.yellowBG;
            }
        }
        return Color.white;
    }

    /**
     * Manage the colour to form an alternating pattern for a tile affect
     * @param color
     * @return
     */
    private Color altPattern(Color color){
        if(color == DrawPiece.alt1) {
            return DrawPiece.alt2;
        }
        if (color == DrawPiece.alt2) {
           return DrawPiece.alt1;
        }

        return DrawPiece.alt1;
    }


    /**
     * Calculate which piece was selected
     * @param p
     * @return
     * @throws IllegalMoveException
     */
    private PieceCell getSelectedPiece(Point p) throws IllegalMoveException {
        int i =  p.x / (DrawPiece.xPadding + DrawPiece.size) + ((p.y - DrawPiece.yPadding) / (DrawPiece.xPadding + DrawPiece.size)) * 4;
        BoardCell cell = game.getBoard().getCellAt(i%game.getBoard().getNumCols(),i-i%game.getBoard().getNumCols());
        if (cell == null){
            throw new IllegalMoveException("No piece selected");
        }
        if (cell.getClass() != PieceCell.class){
            throw new IllegalMoveException("Not a piece");
        }
        return (PieceCell) cell;
    }
}
