package SwordsAndShields.model.actions;

import SwordsAndShields.model.Board;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.Player;
import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.NonCell;
import SwordsAndShields.model.cells.PieceCell;
import SwordsAndShields.model.cells.PlayerCell;

import java.awt.*;

/**
 * Action to move a piece
 * Updating it on the board and in the players inventory if required
 *      e.g moves kills the piece by taking it off the board
 *
 * Can be undone
 */
public class MoveAction implements Action {


    private final Game g;
    private final Board board;
    private final PieceCell p;
    private final char pieceID;
    private final Point start;
    private final Point end;
    private final Player player;


    /**
     * @param g Game
     * @param p cell to move
     * @param id id of cell being moved
     * @param start the original location of the piece
     * @param end the final location of the piece
     */
    public MoveAction(Game g, PieceCell p, char id, Point start, Point end){
        this.g = g;
        this.p = p;
        this.start = start;
        this.end = end;
        this.board = g.getBoard();
        this.player = g.getCurrentPlayer();
        this.pieceID = id;
    }

    @Override
    public void performAction() {
        board.removeCell(start.y,start.x);
        /*
        * Moving the piece off the board kills it.
        */
        if (end.x < 0 || end.y < 0 || end.x >= board.getNumCols() || end.y >= board.getNumRows()){
            player.killPieceOnBoard(pieceID);
        }else if(board.getCellAt(end.y,end.x) != null) {
            BoardCell cell = board.getCellAt(end.y,end.x);
            if (cell.getClass() == NonCell.class || cell.getClass() == PlayerCell.class){
                player.killPieceOnBoard(pieceID);
            }

        }else {
            board.addCell(p, end.y, end.x);
        }
        g.addPieceUsed(p);
    }

    @Override
    public void undoAction() {
        board.addCell(p,start.y,start.x);
        /*
        * Piece had previously been killed by moving off board
        */
        if (end.x < 0 || end.y < 0 || end.x >= board.getNumCols() || end.y >= board.getNumRows() || board.getCellAt(end.y,end.x).getClass() == NonCell.class){
            player.revivePiece(pieceID);
        }else {
            board.removeCell(end.y, end.x);
        }
        g.removePieceUsed(p);
    }
}
