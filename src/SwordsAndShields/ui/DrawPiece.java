package SwordsAndShields.ui;

import java.awt.*;

public class DrawPiece {

    public static final int xPadding = 10;
    public static final int yPadding = 30;

    public static final int size = 50;
    public static final int itemWidth = size/8;
    public static final int shieldHeight = size;
    public static final int swordHeight = size/2;

    public static final Color yellowBG = Color.yellow;
    public static final Color greenBG = Color.green;

    public static final Color alt1 = Color.lightGray;
    public static final Color alt2 = Color.gray;


    public static void drawNullCell(Graphics g, int x, int y, Color bg){
        g.setColor(bg);
        g.fillRect(x,y,size,size);
    }
}
