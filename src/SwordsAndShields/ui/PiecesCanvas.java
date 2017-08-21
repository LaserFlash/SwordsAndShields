package SwordsAndShields.ui;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.cells.PieceCell;

import java.awt.*;
import java.util.Collection;


public class PiecesCanvas extends Canvas {

    private static final int size = 50;
    private static final int itemWidth = size/8;
    private static final int shieldHeight = size;
    private static final int swordHeight = size/2;

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
                y+= size + 10;
            }
            drawPiece(g,p,x,y);
            x+=size +10;
            i++;
        }
    }

    private void drawPiece(Graphics g, PieceCell p, int x, int y){

        g.setColor(pieceBG);
        g.fillRect(x,y,size,size);
        g.setColor(Color.darkGray);

        switch (p.getSide(Direction.NORTH)){
            case SHIELD:
                g.fillRect(x,y,shieldHeight,itemWidth);
                break;
            case SWORD:
                g.fillRect(x + size/2 - itemWidth/2,y,itemWidth,swordHeight);
                break;
        }

        switch (p.getSide(Direction.EAST)){
            case SHIELD:
                g.fillRect(x + size - itemWidth, y, itemWidth, shieldHeight);
                break;
            case SWORD:
                g.fillRect(x + size/2 - itemWidth/2, y, itemWidth, swordHeight);
                break;
        }

        switch (p.getSide(Direction.SOUTH)){
            case SHIELD:
                g.fillRect(x, y + size - itemWidth, shieldHeight, itemWidth);
                break;
            case SWORD:
                g.fillRect(x + size/2 - itemWidth/2, y + size/2, itemWidth, swordHeight);
                break;
        }

        switch (p.getSide(Direction.WEST)){
            case SHIELD:
                g.fillRect(x, y, itemWidth, shieldHeight);
                break;
            case SWORD:
                g.fillRect(x , y + size/2 - itemWidth/2, swordHeight, itemWidth);
                break;
        }

    }

}
