package SwordsAndShields.ui;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.cells.PieceCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;


public class PiecesCanvas extends CanvasPanel {

    private final GUIController controller;

    private String heading;
    private List<PieceCell> pieces;

    private final String rotationHeading = "Select Rotated Piece";
    private final String normalHeading;

    private final List<PieceCell> availablePieces;

    private final Color pieceBG;

    public boolean active = false;

    public PiecesCanvas(String s, Color c, List<PieceCell> pieces, GUIController controller, Game game) {
        super(controller,game);
        this.controller = controller;

        this.normalHeading = s;
        this.heading = normalHeading;
        this.availablePieces = pieces;
        this.pieces = this.availablePieces;
        this.pieceBG = c;
    }

    @Override
    protected void selectPiece(Point p) {
        if (!active){return;}   //Disable selecting if canvas not active

        if (controller.getState() != GUITurnState.Create && controller.getState() != GUITurnState.Create_Rotate){return;} //Disable selection if GUI is in different state.

        PieceCell piece = null;
        try {
            piece = getSelectedPiece(p);
        }
        /*
         * If nullptr exception or index out of bounds thrown then a piece was not selected, so go back to giving \
         * option to select a piece
         */
        catch (NullPointerException | IndexOutOfBoundsException e){
            revertToAvailable();
            controller.setState(GUITurnState.Create);
            return;
        }

        if (piece == null){return;} //Shouldn't occur but just incase

        /*
         * In case of user selecting a particular rotation of a piece to place
         */
        if (controller.getState() == GUITurnState.Create_Rotate){
            controller.createPiece(pieces.get(0),pieces.indexOf(piece));
            controller.setState(GUITurnState.Move_Rotate);
            /*
             * User selected piece to create and was created
             */
            revertToAvailable();
            return;
        }
        /*
         * Draw possible rotations of the piece
         * Requires cloning so pieces don't all rotate
         */
        this.heading = this.rotationHeading;
        this.pieces = new ArrayList<>();

        PieceCell tmp = piece;
        for (int i = 0; i < Direction.values().length; i++){
            pieces.add(tmp);
            tmp = tmp.clone();
            tmp.rotate(Direction.EAST);
        }
        controller.setState(GUITurnState.Create_Rotate);
        repaint();
    }

    private void revertToAvailable(){
        this.heading = this.normalHeading;
        this.pieces = this.availablePieces;
        repaint();
    }

    private PieceCell getSelectedPiece(Point p) {
        int i =  p.x / (DrawPiece.xPadding + DrawPiece.size) + ((p.y - DrawPiece.yPadding)/ (DrawPiece.xPadding + DrawPiece.size)) * 4;
        return pieces.get(i);
    }



    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRoundRect(0,0,this.getBounds().width,this.getBounds().height,20,20);
        g.drawChars(heading.toCharArray(),0,heading.length(),DrawPiece.xPadding,20);
        int x = DrawPiece.xPadding;
        int y = DrawPiece.yPadding;
        int i = 0;
        for (PieceCell p: pieces){
            if (i > 3){
                i=0;
                x = DrawPiece.xPadding;
                y+= DrawPiece.size + DrawPiece.xPadding;
            }
            p.draw(g,x,y,pieceBG);
            x+= DrawPiece.size + DrawPiece.xPadding;
            i++;
        }
    }

}
