package SwordsAndShields.ui;

import SwordsAndShields.model.*;
import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.PieceCell;
import SwordsAndShields.model.cells.PlayerCell;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BoardCanvas extends CanvasPanel implements KeyListener{
    private static final int nearEdge = 10;

    private PieceCell selectedPiece;
    private int rotations = 0;

    public BoardCanvas(GUIController controller, Game game){
        super(controller,game);
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    protected void selectPiece(Point p) {

        /*
         * A piece is being selected
         */
        if (controller.getState() == GUITurnState.Move_Rotate){
            try {
                this.selectedPiece = getSelectedPiece(p).clone();
                controller.nextState();
                repaint();
                requestFocus(true);
            }catch (IllegalMoveException e) {
                this.selectedPiece = null;
                controller.previousState();
                repaint();
            }
        }

        /*
         * A Piece has been selected, performing an action
         */
        if (controller.getState() == GUITurnState.Move_Rotate_Selected && selectedPiece != null){
            Direction d = nearEdge(p);
            try {
                if (d != null) {
                    game.movePiece(selectedPiece.getID(),d);
                    this.selectedPiece = null;
                    controller.previousState();
                } else if (insideCell(p)){
                    selectedPiece.rotate(Direction.EAST);
                    rotations = ++rotations >= 4 ? 0: rotations;
                }else{
                    controller.previousState();
                    game.rotatePiece(selectedPiece.getID(), Direction.directionFromNumber(rotations));
                    rotations = 0;
                    this.selectedPiece = null;
                }
            } catch (IllegalMoveException e) {}
            finally {
                repaint();
            }
        }
    }

    /**
     * Is the given point inside the area the currently selected piece is drawn in.
     * @param pClick
     * @return
     */
    private boolean insideCell(Point pClick) {
        Point pCell = findTopCornerOfCell(selectedPiece);
        if (pCell.x <= pClick.x && pCell.x + DrawPiece.size >= pClick.x){
            if (pCell.y <= pClick.y && pCell.y + DrawPiece.size >= pClick.y)
                return true;
        }
        return false;
    }

    /**
     * Check if the point is near edge of the currently selected piece
     * @param pClick
     * @return the edge the point is near
     */
    private Direction nearEdge(Point pClick) {
        Point pCell = findTopCornerOfCell(selectedPiece);
        if (pCell.x <= pClick.x && pCell.x + nearEdge >= pClick.x){
            return Direction.WEST;
        }
        if (pCell.x + DrawPiece.size >= pClick.x && pCell.x + DrawPiece.size <= pClick.x + nearEdge){
            return Direction.EAST;
        }
        if (pCell.y <= pClick.y && pCell.y + nearEdge >= pClick.y){
            return Direction.NORTH;
        }
        if (pCell.y + DrawPiece.size >= pClick.y && pCell.y + DrawPiece.size <= pClick.y + nearEdge){
            return Direction.SOUTH;
        }

        return null;
    }

    /**
     * Given a cell find the point that would represent its top corner on the JPanel
     * @param selectedCell
     * @return
     */
    private Point findTopCornerOfCell(BoardCell selectedCell){
        Board b = game.getBoard();
        int x = DrawPiece.xPadding;
        int y = DrawPiece.yPadding;
        for (int r = 0; r < b.getNumRows(); r++) {
            for (int c = 0; c < b.getNumCols(); c++) {
                BoardCell cell = b.getCellAt(r, c);
                if (cell != null && cell.equals(selectedCell)) {
                    return new Point(x,y);
                }
                x += DrawPiece.size;
            }
            y += DrawPiece.size;
            x = DrawPiece.xPadding;
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRoundRect(0, 0, this.getBounds().width, this.getBounds().height, 20, 20);
        Board b = game.getBoard();
        Color altPattern = Color.white;
        altPattern = altPattern(altPattern);
        int x = DrawPiece.xPadding;
        int y = DrawPiece.yPadding;
        for (int r = 0; r < b.getNumRows(); r++) {
            for (int c = 0; c < b.getNumCols(); c++) {
                BoardCell cell = b.getCellAt(r, c);
                if (cell != null) {
                    cell.draw(g, x, y, getCellColor(cell));
                } else {
                    DrawPiece.drawNullCell(g, x, y, altPattern);
                }
                altPattern = altPattern(altPattern);
                x += DrawPiece.size;
            }
            altPattern = altPattern(altPattern);
            y += DrawPiece.size;
            x = DrawPiece.xPadding;
        }

        /*
         * Draw dark layer over non-selected pieces
         */
        if (selectedPiece != null) {
            g.setColor(new Color(0, 0, 0, 0.5f));
            g.fillRoundRect(0, 0, this.getBounds().width, this.getBounds().height, 20, 20);
            x = DrawPiece.xPadding;
            y = DrawPiece.yPadding;
            for (int r = 0; r < b.getNumRows(); r++) {
                for (int c = 0; c < b.getNumCols(); c++) {
                    BoardCell cell = b.getCellAt(r, c);
                    if (cell != null && cell.equals(selectedPiece)){
                        selectedPiece.draw(g, x, y, getCellColor(cell));
                        return;
                    }
                    x += DrawPiece.size;
                }
                y += DrawPiece.size;
                x = DrawPiece.xPadding;
            }

        }
    }

    /**
     * Get the colour that the cell/piece should be drawn.
     * Depends on what player the cell belongs to.
     * @param cell
     * @return
     */
    private Color getCellColor(BoardCell cell){
        if (cell.getClass() == PieceCell.class){
            if (game.getGreenPiecesOnBoard().contains(cell)){
                return DrawPiece.greenBG;
            }
            if (game.getYellowPiecesOnBoard().contains(cell)){
                return DrawPiece.yellowBG;
            }
        }
        else if(cell.getClass() == PlayerCell.class){
            if (game.checkGreenPlayerCell(cell)){
                return DrawPiece.greenBG;
            }
            if (game.checkYellowPlayerCell(cell)){
                return DrawPiece.yellowBG;
            }
        }
        return Color.white;
    }

    /**
     * Manage the colour to form an alternating pattern for a tile affect
     * @param color
     * @return
     */
    private Color altPattern(Color color){
        if(color == DrawPiece.alt1) {
            return DrawPiece.alt2;
        }
        if (color == DrawPiece.alt2) {
           return DrawPiece.alt1;
        }

        return DrawPiece.alt1;
    }


    /**
     * Calculate which piece was selected
     * @param p
     * @return
     * @throws IllegalMoveException
     */
    private PieceCell getSelectedPiece(Point p) throws IllegalMoveException {
        int r = (p.y - DrawPiece.yPadding) / DrawPiece.size;
        int c = (p.x - DrawPiece.xPadding) / DrawPiece.size;
        BoardCell cell = game.getBoard().getCellAt(r,c);
        if (cell == null){
            throw new IllegalMoveException("No piece selected");
        }
        if (cell.getClass() != PieceCell.class){
            throw new IllegalMoveException("Not a piece");
        }
        return (PieceCell) cell;
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keyAction(keyEvent.getKeyCode());
    }

    /**
     * Check if selected piece is movable
     * and move in direction based on key
     * @param code
     */
    private void keyAction(int code) {
        if (selectedPiece == null || controller.getState() != GUITurnState.Move_Rotate_Selected){ return;}
        try {
            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT || code == KeyEvent.VK_D) {
                game.movePiece(selectedPiece.getID(), Direction.EAST);
            }
            else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT || code == KeyEvent.VK_A) {
                game.movePiece(selectedPiece.getID(), Direction.WEST);
            }
            else if (code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP || code == KeyEvent.VK_W) {
                game.movePiece(selectedPiece.getID(), Direction.NORTH);
            }
            else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN || code == KeyEvent.VK_S) {
                game.movePiece(selectedPiece.getID(), Direction.SOUTH);
            }else { return; }
            selectedPiece = null;
            controller.previousState();
            repaint();
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
    }
}
