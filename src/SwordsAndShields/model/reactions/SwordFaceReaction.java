package SwordsAndShields.model.reactions;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.PieceCell;
import SwordsAndShields.model.cells.PlayerCell;

/**
 * Reaction that occurs between a Sword and a Player Face
 * Game is won by the player whose face was not hit by the sword
 */
public class SwordFaceReaction extends Reaction {

    public SwordFaceReaction(){}

    public SwordFaceReaction(BoardCell a, BoardCell b, Direction d, Game g){
        this.pair = new ReactionPair(a,b);
        this.d = d;
        this.game = g;
    }

    @Override
    public boolean reactionTypeCheck(BoardCell a, BoardCell b, Direction d) {
        if (a.getSide(d) == PieceCell.SideType.SWORD){
            if (b.getClass() == PlayerCell.class){
                return true;
            }
        }
        if (b.getSide(Direction.opposite(d)) == PieceCell.SideType.SWORD){
            if (a.getClass() == PlayerCell.class){
                return true;
            }
        }

        return false;
    }

    @Override
    public void performAction() {
        if (pair.cellA.getClass() == PlayerCell.class){
            game.setWon(pair.cellA);
        }else {
            game.setWon(pair.cellB);
        }

    }

    @Override
    public void undoAction() {
        game.undoSetWon();
    }
}
