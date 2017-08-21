package SwordsAndShields.model.reactions;

import SwordsAndShields.model.Board;
import SwordsAndShields.model.Direction;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.Player;
import SwordsAndShields.model.actions.PushAction;
import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.PieceCell;

import java.awt.*;

/**
 * Reaction that occurs between a Sword and Shield Side
 * Sword piece is pushed back a cell
 */
public class SwordShieldReaction extends Reaction {

    Point point;
    Board board;
    PushAction push;

    public SwordShieldReaction(){}

    public SwordShieldReaction(BoardCell a, BoardCell b, Direction d, Game g){
        this.pair = new ReactionPair(a,b);
        this.d = d;
        this.game = g;
        this.board = g.getBoard();
    }

    @Override
    public boolean reactionTypeCheck(BoardCell a, BoardCell b, Direction d) {
        if (a.getSide(d) == PieceCell.SideType.SWORD){
            if (b.getSide(Direction.opposite(d)) == PieceCell.SideType.SHIELD){
                return true;
            }
        }

        if (a.getSide(d) == PieceCell.SideType.SHIELD){
            if (b.getSide(Direction.opposite(d)) == PieceCell.SideType.SWORD){
                return true;
            }
        }
        return false;
    }

    @Override
    public void performAction() {
        BoardCell a = pair.cellA;
        BoardCell b = pair.cellB;
        Point p;
        if (a.getSide(d) == PieceCell.SideType.SWORD){
            p = game.getBoard().getRowColOf(a);
            point = p;
            pushBack(a,Direction.opposite(d),p);
        }else{
            p = game.getBoard().getRowColOf(b);
            point = p;
            pushBack(b,d,p);
        }
    }


    /**
     * Given a cell and a direction move the cell
     * Intended use is for reactions that cause a piece to be moved
     * @param cell
     * @param d
     * @param p
     */
    public void pushBack(BoardCell cell, Direction d,Point p) {

        Point newP = new Point(p.x, p.y);

        switch (d) {
            case WEST:
                newP.x--;
                break;
            case EAST:
                newP.x++;
                break;
            case SOUTH:
                newP.y++;
                break;
            case NORTH:
                newP.y--;
        }

        if (newP.x < 0 || newP.y < 0 || newP.x >= board.getNumCols() || newP.y >= board.getNumRows()) {
            game.killPieceOnBoard(cell);
            board.removeCell(p.y,p.x);
            return;
        }

        if (board.getCellAt(newP.y,newP.x) != null){
            int x = newP.x - p.x;
            int y = newP.y - p.y;

            push = new PushAction(game,(PieceCell) board.getCellAt(newP.y,newP.x),board.getCellAt(newP.y,newP.x).getID(),newP,new Point(newP.x + x,newP.y + y));
            push.performAction();
        }

        board.addCell(cell,newP.y,newP.x);
        board.removeCell(p.y,p.x);




    }
    @Override
    public void undoAction() {
        BoardCell a = pair.cellA;
        BoardCell b = pair.cellB;

        if (a.getSide(d) == PieceCell.SideType.SWORD){
            pullBack(a,point);
        }else{
            pullBack(b,point);
        }
    }


    /**
     * Similar to pushBack, However can revive a piece if it was killed
     * @param cell
     * @param p
     */
    public void pullBack(BoardCell cell, Point p) {


        Point newP = board.getRowColOf(cell);
        board.removeCell(newP.y, newP.x);
        board.addCell(cell,p.y,p.x);


        if (p.x < 0 || p.y < 0 || p.x >= board.getNumCols() || p.y >= board.getNumRows()) {
            game.revivePieceOnBoard(cell,p);
        }

        if (push != null){
            push.undoAction();
        }

    }
}
