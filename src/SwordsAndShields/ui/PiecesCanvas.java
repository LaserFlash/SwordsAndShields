package SwordsAndShields.ui;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.cells.PieceCell;

import java.awt.*;
import java.util.Collection;


public class PiecesCanvas extends Canvas {

    private final String heading;
    private final Collection<PieceCell> pieces;

    private final Color pieceBG;

    public PiecesCanvas(String s, Color c, Collection<PieceCell> pieces) {
        this.heading = s;
        this.pieces = pieces;
        this.pieceBG = c;
    }

    @Override
    public void paint(Graphics g){
        g.drawRoundRect(0,0,this.getBounds().width,this.getBounds().height,20,20);
        g.drawChars(heading.toCharArray(),0,heading.length(),10,20);
        int x = 10;
        int y = 30;
        int i = 0;
        for (PieceCell p: pieces){
            if (i > 3){
                i=0;
                x = 10;
                y+= DrawPiece.size + 10;
            }
            DrawPiece.drawPiece(g,p,x,y,pieceBG);
            x+= DrawPiece.size +10;
            i++;
        }
    }

}
