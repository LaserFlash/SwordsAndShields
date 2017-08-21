package SwordsAndShields.ui;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.cells.PieceCell;

import java.awt.*;


public class DrawPiece {

    public static final int size = 50;
    public static final int itemWidth = size/8;
    public static final int shieldHeight = size;
    public static final int swordHeight = size/2;

    public static void drawPiece(Graphics g, PieceCell p, int x, int y, Color bg) {
        g.setColor(bg);
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
