package SwordsAndShields.model;


import SwordsAndShields.model.cells.PieceCell;
import SwordsAndShields.model.cells.PlayerCell;

import java.util.*;

/**
 * A Player in the game
 */
public class Player {

    private final PlayerCell cell;  //Cell that represents the player
    private boolean caps;
    private final int creationRow;
    private final int creationCol;

    /* Pieces that can be placed */
    private Map<Character,PieceCell> pieces;
    /* Pieces on the board */
    private Map<Character,PieceCell> piecesOnBoard;
    private Map<Character,PieceCell> grave;

    /**
     * @param cell      represents player on board
     * @param b         piece ids capitalisation
     * @param creationR row that pieces will be created
     * @param creationC col that pieces will be created
     */
    public Player(PlayerCell cell, boolean b, int creationR, int creationC){
        this.cell = cell;
        this.caps = b;
        this.creationRow = creationR;
        this.creationCol = creationC;

        this.piecesOnBoard = new HashMap<>();
        this.grave = new HashMap<>();

        this.pieces = new HashMap<>();

        for (int i = 0; i < PieceCell.Permutations.length; i++){
            PieceCell.Sides sides = PieceCell.Permutations[i].clone();
            Character id = (char)('a' + i);
            if (caps){
                id = Character.toUpperCase(id);
            }
            PieceCell pieceCell = new PieceCell(id, sides);
            pieces.put(id,pieceCell);
        }
    }

    /**
     * Use a piece by placing it on the board
     * @param id
     * @return
     */
    public PieceCell usePiece(char id){
        PieceCell p = pieces.remove(id);
        piecesOnBoard.put(id,p);
        return p;
    }

    /**
     * Remove piece from board
     * Goes back to pieces list
     * @param id
     */
    public void undoUsePiece(char id){
        pieces.put(id,piecesOnBoard.remove(id));
    }

    public boolean inGrave(char id){
        return grave.containsKey(id);
    }

    public void killPieceOnBoard(char id){
        grave.put(id, piecesOnBoard.remove(id));
    }

    public PlayerCell getPlayerCell() {
        return cell;
    }

    public Set<Character> getUnusedPieceIDs(){
        return pieces.keySet();
    }

    public PieceCell findPieceOnBoard(char id){
        return piecesOnBoard.get(id);
    }

    public int getCreationRow(){
        return creationRow;
    }
    public  int getCreationCol(){
        return creationCol;
    }

    public String getName(){
        return cell.getName();
    }

    public void revivePiece(char p) {
        piecesOnBoard.put(p,grave.remove(p));
    }

    public Collection<Character> getUsedPieceIDs() {
        return piecesOnBoard.keySet();
    }

    public Collection<Character> getDeadPieceIDs(){return grave.keySet();}
}
