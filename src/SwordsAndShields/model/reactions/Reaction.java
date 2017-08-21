package SwordsAndShields.model.reactions;

import SwordsAndShields.model.Board;
import SwordsAndShields.model.Direction;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.actions.Action;
import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.PieceCell;
import SwordsAndShields.model.cells.PlayerCell;

import java.util.HashMap;
import java.util.Map;

/**
 * A reaction template
 * Implements Action as reactions are a type of action and so need a performAction()
 * and a undo().
 */
public abstract class Reaction implements Action{

    protected ReactionPair pair;
    protected Direction d;
    protected Game game;


    /**
     * Given the game and board searches for adjacent pieces and attempts to create a reaction with them
     * @param g
     * @param board
     * @return
     */
    public static Map<String,Reaction> findReactions(Game g,Board board){
        Map<String,Reaction> map = new HashMap<>();
        for (int r = 0; r < board.getNumRows(); r++){
            for (int c = 0; c < board.getNumCols(); c++){
                BoardCell a = board.getCellAt(r,c);
                BoardCell b;
                if (a == null){ continue;}

                if (r-1 >= 0){
                    b = board.getCellAt(r-1,c);
                    reactionCheck(map,a,b,Direction.NORTH,g);
                }
                if (r+1 < board.getNumRows()){
                    b = board.getCellAt(r+1,c);
                    reactionCheck(map,a,b,Direction.SOUTH,g);
                }
                if (c+1 < board.getNumCols()){
                    b = board.getCellAt(r,c+1);
                    reactionCheck(map,a,b,Direction.EAST,g);
                }
                if (c-1 >= 0){
                    b = board.getCellAt(r,c-1);
                    reactionCheck(map,a,b,Direction.WEST,g);
                }

            }
        }
        return map;
    }

    /**
     * Given adjacent pieces / cells create a reaction based on the items on the touching sides
     *
     * @param map of reactions
     * @param a cell / piece a
     * @param b cell / piece b
     * @param d side of cell a involved in reaction
     * @param g game
     * @return
     */
    static Map<String, Reaction> reactionCheck(Map<String, Reaction> map, BoardCell a, BoardCell b, Direction d, Game g) {
        if (b == null){
            return map;
        }
        if (a.getClass() != PieceCell.class && a.getClass() != PlayerCell.class){
            return map;
        }

        if (b.getClass() != PieceCell.class && b.getClass() != PlayerCell.class){
            return map;
        }



        Reaction r = new SwordNothingReaction();
        if (r.reactionTypeCheck(a,b,d)){
            map.put(a.getID() + " " + b.getID(),new SwordNothingReaction(a,b,d,g));
        }
        r = new SwordSwordReaction();
        if (r.reactionTypeCheck(a,b,d)){
            map.put(a.getID() + " " + b.getID(),new SwordSwordReaction(a,b,d,g));
        }
        r = new SwordShieldReaction();
        if (r.reactionTypeCheck(a,b,d)){
            map.put(a.getID() + " " + b.getID(), new SwordShieldReaction(a,b,d,g));
        }
        r = new SwordFaceReaction();
        if (r.reactionTypeCheck(a,b,d)){
            map.put(a.getID() + " " + b.getID(), new SwordFaceReaction(a,b,d,g));
        }

        return map;

    }

    /**
     * Check if the given cells could create the provided reaction
     * @param a
     * @param b
     * @param d
     * @return
     */
    abstract public boolean reactionTypeCheck(BoardCell a,BoardCell b, Direction d);




}
