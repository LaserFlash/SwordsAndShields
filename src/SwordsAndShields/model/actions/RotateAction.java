package SwordsAndShields.model.actions;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.cells.PieceCell;

/**
 * The Action used to rotate a given cell to a given direction
 */
public class RotateAction implements Action {

    private final Game g;
    private final PieceCell p;
    private final Direction d;

    /**
     * @param g Game
     * @param p The Cell to rotate
     * @param d Direction to rotate to
     */
    public RotateAction(Game g, PieceCell p, Direction d){
        this.g = g;
        this.p = p;
        this.d = d;

    }

    @Override
    public void performAction() {
        p.rotate(d);
        g.addPieceUsed(p);
    }

    @Override
    public void undoAction() {
        p.rotate(Direction.undoDirection(d));
        g.removePieceUsed(p);
    }

}