package SwordsAndShields.model.reactions;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.PieceCell;

import java.awt.*;

/**
 * Reaction that occurs between a Sword and Sword Side
 * Both Swords are killed
 */
public class SwordSwordReaction extends Reaction{

    Point aP;
    Point bP;

    public SwordSwordReaction(){}

    public SwordSwordReaction(BoardCell a, BoardCell b, Direction d, Game g){
        this.pair = new ReactionPair(a,b);
        this.d = d;
        this.game = g;
    }

    @Override
    public boolean reactionTypeCheck(BoardCell a, BoardCell b, Direction d) {

        if (a.getSide(d) == PieceCell.SideType.SWORD){
            if (b.getSide(Direction.opposite(d)) == PieceCell.SideType.SWORD){
                return true;
            }
        }
        return false;
    }

    public void performAction() {
        BoardCell a = pair.cellA;
        BoardCell b = pair.cellB;

        aP = game.getBoard().getRowColOf(a);
        bP = game.getBoard().getRowColOf(b);

        game.killPieceOnBoard(b);
        game.killPieceOnBoard(a);
    }

    @Override
    public void undoAction() {
        BoardCell a = pair.cellA;
        BoardCell b = pair.cellB;
        game.revivePieceOnBoard(b,bP);
        game.revivePieceOnBoard(a,aP);
    }
}
