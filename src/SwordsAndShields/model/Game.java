package SwordsAndShields.model;

import SwordsAndShields.model.actions.*;
import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.NonCell;
import SwordsAndShields.model.cells.PieceCell;
import SwordsAndShields.model.cells.PlayerCell;
import SwordsAndShields.model.reactions.Reaction;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * The Swords and Shields game
 * Stores keeps track of the [layer and board
 * while providing actual game functionality
 */
public class Game {

    /* Corner offset on board */
    private static final int PLAYER_OFFSET = 1;
    private static final int CREATION_OFFSET = 2;

    private final Board board;
    private final List<Player> players;

    private int currentPlayer;
    private TurnState turnState = TurnState.CREATE;
    private Set<PieceCell> piecesUsedTurn;

    private Map<String,Reaction> reactions;
    private Player won = null;

    private Stack<Action> undoStack;

    public Game(Board board){
        assert board.isEmpty();

        undoStack = new Stack<>();
        piecesUsedTurn = new HashSet<>();

        this.board = board;
        this.players = new ArrayList<>();
        this.reactions = new HashMap<>();

        /*
         * Create the games player
         */
        this.players.add(new Player(
                new PlayerCell(PlayerCell.Token.GREEN),
                true,
                CREATION_OFFSET,
                CREATION_OFFSET
        ));

        this.players.add(new Player(
                new PlayerCell(PlayerCell.Token.YELLOW),
                false,
                board.getNumRows() - CREATION_OFFSET - 1,
                board.getNumCols() - CREATION_OFFSET - 1
        ));

        this.currentPlayer = 0;

        setupBoardPlayerCell();
        setupBoardBlankCell();

    }

    /**
     * Add the player cells to the game board
     */
    private void setupBoardPlayerCell(){
        board.addCell(
                players.get(0).getPlayerCell(),
                PLAYER_OFFSET,
                PLAYER_OFFSET
        );
        board.addCell(
                players.get(1).getPlayerCell(),
                board.getNumRows() - 1 - PLAYER_OFFSET,
                board.getNumCols() - 1 - PLAYER_OFFSET
        );
    }

    /**
     * Add the blank cells that are present behind the player cells
     */
    private void setupBoardBlankCell(){
        for (int r = 0; r <= PLAYER_OFFSET; r++){
            for (int c = 0; c <= PLAYER_OFFSET; c++){
                if (r == c && c == PLAYER_OFFSET){
                    break;
                }
                board.addCell(new NonCell(), board.getNumRows() -1 - r, board.getNumCols() -1 - c);
                board.addCell(new NonCell(), r, c);
            }
        }
    }

    /**
     * Move to the next turn state without performing an action
     */
    public void passTurnState(){
        if (this.turnState == TurnState.MOVE_ROTATE && this.turnState.next() == TurnState.CREATE){
            this.currentPlayer = ++currentPlayer >= players.size() ? 0 : 1;
            undoStack.clear();
            piecesUsedTurn.clear();
        }
        this.turnState = turnState.next();
    }

    /**
     * Create a piece on the game board
     * Ensures doing so follows game rules
     * @param id
     * @param rotation
     * @throws IllegalMoveException
     */
    public void createPiece(char id, Direction rotation) throws IllegalMoveException {
        Player currentP = players.get(currentPlayer);
        int creationRow = currentP.getCreationRow();
        int creationCol = currentP.getCreationCol();

        if (board.getCellAt(creationRow,creationCol) != null){
            throw new IllegalMoveException("There is already a piece on the creation cell");
        }
        performAction(new CreateAction(this,creationRow,creationCol,id,rotation));
    }

    /**
     * Move a given piece checking that it can be moved and doing so follows game rules
     *
     * @param p
     * @param d
     * @throws IllegalMoveException
     */
    public void movePiece(char p, Direction d) throws IllegalMoveException {
        Player player = players.get(currentPlayer);
        PieceCell piece = player.findPieceOnBoard(p);

        if (piece == null) { throw new IllegalMoveException("This piece is not on the board");}
        if (piecesUsedTurn.contains(piece)){ throw new IllegalMoveException("Piece can only be used once per turn");}

        Point point = board.getRowColOf(piece);
        Point newPoint = board.getRowColOf(piece);

        if (point == null) { throw new IllegalMoveException("This piece is not on the board");}

        switch (d){
            case NORTH:
                newPoint.y--;
                break;
            case EAST:
                newPoint.x++;
                break;
            case SOUTH:
                newPoint.y++;
                break;
            case WEST:
                newPoint.x--;
                break;
            default:
                throw new IllegalMoveException("Not a suitable direction");
        }

        if (!(newPoint.x < 0 || newPoint.y < 0 || newPoint.x >= board.getNumCols() || newPoint.y >= board.getNumRows())){
            BoardCell cellAtNewPos = board.getCellAt(newPoint.y,newPoint.x);
            if (cellAtNewPos != null){
                performAction(new PushAction(this,piece,p,point,newPoint));
                return;
            }
        }

        performAction(new MoveAction(this,piece,p,point,newPoint));

    }


    /**
     * Rotate a given piece checking that it exits
     * and rotating it follows game rules
     *
     * @param p ID or key for the piece
     * @param d Direction to rotate to
     * @throws IllegalMoveException
     */
    public void rotatePiece(char p, Direction d) throws IllegalMoveException {
        Player player = players.get(currentPlayer);
        PieceCell piece = player.findPieceOnBoard(p);

        if (piece == null) { throw new IllegalMoveException("This piece is not on the board");}
        if (piecesUsedTurn.contains(piece)){ throw new IllegalMoveException("Piece can only be used once per turn");}

        performAction(new RotateAction(this,piece,d));
    }

    /**
     * Given the id or key for a reaction pair preform that reaction
     * @param key
     */
    public void react(String key) throws IllegalMoveException {
        Reaction r = reactions.get(key);
        if (r == null) { throw new IllegalMoveException("That reaction pair does not exist"); }
        performAction(r);
        this.turnState = turnState.next();
    }

    /**
     * Undo the last action performed
     * @throws IllegalMoveException
     */
    public void undo() throws IllegalMoveException {
        if (undoStack.isEmpty()){
            throw new IllegalMoveException("Nothing to undo");
        }
        undoStack.pop().undoAction();

        checkForReactions();
        if (this.turnState == TurnState.REACTIONS){
            if (!reactions.isEmpty()){
                this.turnState = turnState.next();
            }
        }
        if (!reactions.isEmpty()){
            this.turnState = TurnState.REACTIONS;
        }

    }

    /**
     * Check for reactions
     */
    private void checkForReactions(){
        if (turnState == TurnState.GAME_FINISHED){
            this.reactions = new HashMap<>();
            return;
        }
        this.reactions = Reaction.findReactions(this,board);
    }


    /**
     * Add a piece to those used this turn
     * @param p
     */
    public void addPieceUsed(PieceCell p){
        piecesUsedTurn.add(p);
    }

    /**
     * Remove a piece from those used this turn
     * @param p
     */
    public void removePieceUsed(PieceCell p){
        piecesUsedTurn.remove(p);
    }

    /**
     *
     * @return the game board
     */
    public Board getBoard(){
        return board;
    }

    /**
     * Move to the next turn
     */
    public void nextTurn(){
        this.turnState = turnState.next();
    }

    public char[][] getBoardRep(){
        return board.textRep();
    }

    public TurnState getTurnState(){
        return turnState;
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }

    /**
     * Perform the given action
     * Adding it to the undo stack to allow for undoing the action
     * @param action
     */
    private void performAction(Action action){
        action.performAction();
        undoStack.push(action);
        checkForReactions();
        if (!this.reactions.isEmpty()){
            this.turnState = TurnState.REACTIONS;
        }
    }

    /**
     * Given a piece / cell find it on the board and kill it
     * Rempves the cell from the board and the players alive pieces
     * @param cell
     */
    public void killPieceOnBoard(BoardCell cell) {
        for (Player p: players){
            p.killPieceOnBoard(cell.getID());
        }
        Point p = board.getRowColOf(cell);
        board.removeCell(p.y,p.x);
    }

    /**
     * Given a location of a cell and the cell add it to the board
     * and revive it in the players placedPieces
     * @param cell
     * @param point
     */
    public void revivePieceOnBoard(BoardCell cell,Point point) {
        for (Player p: players){
            p.revivePiece(cell.getID());
        }

        board.addCell(cell,point.y,point.x);
    }


    /**
     * Game victory condition has been meet, find the winner and record
     * @param lost the player who lost
     */
    public void setWon(BoardCell lost) {
        for (Player p : players){
            if (p.getPlayerCell() != lost){
                this.won = p;
                turnState = TurnState.GAME_FINISHED;
            }
        }
    }

    public void undoSetWon() {
        this.won = null;
    }

    public Player getWinner() {
        return won;
    }

    public Set<String>  getReactionNames(){
        return reactions.keySet();
    }

    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }

    public Collection<PieceCell> getGreenPiecesAvailable() {
        return players.get(0).getUnusedPieces();
    }
    public Collection<PieceCell> getYellowPiecesAvailable() {
        return players.get(1).getUnusedPieces();
    }

    public Collection<PieceCell> getGreenPiecesDead() {
        return players.get(0).getDeadPieces();
    }
    public Collection<PieceCell> getYellowPiecesDead() {
        return players.get(1).getDeadPieces();
    }

    public Collection<PieceCell> getGreenPiecesOnBoard(){ return players.get(0).getPiecesOnBoard();}
    public Collection<PieceCell> getYellowPiecesOnBoard(){ return players.get(1).getPiecesOnBoard();}
}
