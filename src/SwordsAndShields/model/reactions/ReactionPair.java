package SwordsAndShields.model.reactions;

import SwordsAndShields.model.cells.BoardCell;

/**
 * Store of two cells / pieces that can react together
 */
public class ReactionPair{
    BoardCell cellA;
    BoardCell cellB;



    public ReactionPair(BoardCell a, BoardCell b){
        this.cellA = a;
        this.cellB = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()){
            return false;
        }
        ReactionPair o = (ReactionPair) obj;
        return (cellA == o.cellA && cellB == o.cellB) || (cellA == o.cellB && cellB == o.cellA);
    }
}