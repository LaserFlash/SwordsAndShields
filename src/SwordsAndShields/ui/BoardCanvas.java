package SwordsAndShields.ui;

import java.awt.*;

public class BoardCanvas extends Canvas {

    @Override
    public void paint(Graphics g){
        g.drawRoundRect(0,0,this.getBounds().width,this.getBounds().height,20,20);
    }
}
