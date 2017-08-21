package SwordsAndShields.model.actions;

import SwordsAndShields.model.Board;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.Player;
import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.PieceCell;


import java.awt.*;


/**
 * Action that occurs when a piece pushes another piece in order to take its spot
 * This action can be chained with other PushActions if multiple pieces are in a row
 */
public class PushAction implements Action {

    private final Game g;
    private final Board board;
    private final PieceCell p;
    private final char pieceID;
    private final Point start;
    private final Point end;
    private final Player player;

    private PushAction followingPush;



    /**
     * @param g Game
     * @param p cell to move
     * @param id id of cell being moved
     * @param start the original location of the piece
     * @param end the final location of the piece
     */
    public PushAction(Game g, PieceCell p, char id, Point start, Point end){
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
        BoardCell other = board.getCellAt(end.y,end.x);

        if (other != null)
            if (other.getClass() == PieceCell.class){
            int x = end.x - start.x;
            int y = end.y - start.y;
            followingPush = new PushAction(g,(PieceCell) other,other.getID(),end,new Point(end.x + x, end.y + y));
            followingPush.checkOtherPush();
        }

        doAction();


    }

    private void doAction() {
        if (followingPush != null) {
            followingPush.doAction();
        }
        board.removeCell(start.y, start.x);
        board.addCell(p, end.y, end.x);
    }

    public PushAction checkOtherPush() {

        BoardCell other = board.getCellAt(end.y,end.x);
        if (other != null){
            if (other.getClass() == PieceCell.class) {
                int x = end.x - start.x;
                int y = end.y - start.y;
                followingPush = new PushAction(g, (PieceCell) other, other.getID(), end, new Point(end.x + x, end.y + y));
                followingPush.checkOtherPush();
            }
        }

        return this;
    }

    @Override
    public void undoAction() {
        doUndoAction();
    }

    private void doUndoAction() {
        board.removeCell(end.y, end.x);
        board.addCell(p, start.y, start.x);
        if (followingPush != null) {
            followingPush.doUndoAction();
        }

    }


}
