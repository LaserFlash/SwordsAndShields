package SwordsAndShields.ui;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.cells.PieceCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;


public class PiecesCanvas extends JPanel {

    private String heading;
    private List<PieceCell> pieces;

    private final String rotationHeading = "Select Rotated Piece";
    private final String normalHeading;

    private final List<PieceCell> avaliablePieces;

    private final Color pieceBG;

    public PiecesCanvas(String s, Color c, List<PieceCell> pieces) {
        this.normalHeading = s;
        this.heading = normalHeading;
        this.avaliablePieces = pieces;
        this.pieces = this.avaliablePieces;
        this.pieceBG = c;

        addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e){
                selectPieceForCreation(e.getPoint());
            }

            public void mouseEntered(MouseEvent arg0) {}
            public void mouseExited(MouseEvent arg0) {}
            public void mousePressed(MouseEvent arg0) {}
            public void mouseReleased(MouseEvent arg0) {}
        });
    }

    private void selectPieceForCreation(Point p) {
        PieceCell piece = null;
        try {
            piece = getSelectedPiece(p);
        } catch (NullPointerException | IndexOutOfBoundsException e){
            this.heading = this.normalHeading;
            this.pieces = this.avaliablePieces;
            repaint();
            return;
        }

        if (piece == null){return;}

        this.heading = this.rotationHeading;
        this.pieces = new ArrayList<>();
        PieceCell tmp = piece.clone();

        for (int i = 0; i <= Direction.values().length; i++){
            pieces.add(tmp);
            tmp = tmp.clone();
            tmp.rotate(Direction.EAST);
        }
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
