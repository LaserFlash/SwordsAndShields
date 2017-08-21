package SwordsAndShields.model.reactions;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.PieceCell;

import java.awt.*;

/**
 * Reaction that occurs between a Sword and Empty Side
 * Piece with the empty side is killed
 *      removed from board and added to cemetery
 */
public class SwordNothingReaction extends Reaction {

    Point p;

    public SwordNothingReaction(){}

    public SwordNothingReaction(BoardCell a, BoardCell b, Direction d, Game g){
        this.pair = new ReactionPair(a,b);
        this.d = d;
        this.game = g;
    }



    @Override
    public boolean reactionTypeCheck(BoardCell a, BoardCell b, Direction d) {
        if (a.getSide(d) == PieceCell.SideType.SWORD){
            if (b.getSide(Direction.opposite(d)) == PieceCell.SideType.EMPTY){
                return true;
            }
        }

        if (a.getSide(d) == PieceCell.SideType.EMPTY){
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

        if (a.getSide(d) == PieceCell.SideType.SWORD){
            p = game.getBoard().getRowColOf(b);
            game.killPieceOnBoard(b);
        }else{
            p = game.getBoard().getRowColOf(a);
            game.killPieceOnBoard(a);
        }

    }

    @Override
    public void undoAction() {
        BoardCell a = pair.cellA;
        BoardCell b = pair.cellB;

        if (a.getSide(d) == PieceCell.SideType.SWORD){
            game.revivePieceOnBoard(b,p);
        }else{
            game.revivePieceOnBoard(a,p);
        }
    }
}
