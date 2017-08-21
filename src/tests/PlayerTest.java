package tests;

import SwordsAndShields.model.Player;
import SwordsAndShields.model.cells.PlayerCell;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void usePiece_validListMovement() {
        Player p = setUpPlayer();
        char id ='A';
        assertTrue(p.getUnusedPieceIDs().contains(id));
        assertFalse(p.findPieceOnBoard(id) != null);
        p.usePiece(id);
        assertFalse(p.getUnusedPieceIDs().contains(id));
        assertTrue(p.findPieceOnBoard(id) != null);
    }

    @Test
    public void undoUsePiece() {
        Player p = setUpPlayer();
        char id ='A';
        assertTrue(p.getUnusedPieceIDs().contains(id));
        assertFalse(p.findPieceOnBoard(id) != null);
        p.usePiece(id);
        p.undoUsePiece(id);
        assertTrue(p.getUnusedPieceIDs().contains(id));
        assertFalse(p.findPieceOnBoard(id) != null);
    }

    @Test
    public void killPieceOnBoard() {
        Player p = setUpPlayer();
        char id = 'A';
        p.usePiece(id);
        assertFalse(p.inGrave(id));
        p.killPieceOnBoard(id);

        assertTrue(p.inGrave(id));
        assertTrue(p.findPieceOnBoard(id) == null);
    }


    @Test
    public void revivePiece() {
        Player p = setUpPlayer();
        char id = 'A';
        p.usePiece(id);
        p.killPieceOnBoard(id);
        assertTrue(p.inGrave(id));
        assertTrue(p.findPieceOnBoard(id) == null);

        p.revivePiece(id);

        assertFalse(p.inGrave(id));
        assertFalse(p.findPieceOnBoard(id) == null);
    }


    private Player setUpPlayer(){
        return new Player(new PlayerCell(PlayerCell.Token.YELLOW),true,0,0);
    }

}