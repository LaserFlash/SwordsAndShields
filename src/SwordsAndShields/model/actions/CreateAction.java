package SwordsAndShields.model.actions;

import SwordsAndShields.model.*;
import SwordsAndShields.model.cells.PieceCell;

/**
 * Action to create a piece
 * Updating it on the board and in the players inventory
 *
 * Can be undone
 */
public class CreateAction implements Action {

    private final Game g;
    private final Board board;
    private final int creationRow;
    private final int creationCol;
    private final char pieceID;
    private final Direction rotation;
    private final Player currentP;

    public CreateAction(Game g, int row, int col, char id, Direction d){
        this.g = g;
        this.board = g.getBoard();
        this.creationRow = row;
        this.creationCol = col;
        this.pieceID = id;
        this.rotation = d;
        this.currentP = g.getCurrentPlayer();
    }

    @Override
    public void performAction() {
        PieceCell piece = currentP.usePiece(pieceID);
        board.addCell(piece,creationRow,creationCol);
        piece.rotate(rotation);
        g.nextTurn();
    }

    @Override
    public void undoAction() {
        currentP.undoUsePiece(pieceID);
        board.removeCell(creationRow,creationCol);
        g.setTurnState(TurnState.CREATE);
    }
}
