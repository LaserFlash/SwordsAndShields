package SwordsAndShields.ui;

import SwordsAndShields.model.Board;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.cells.BoardCell;

import java.awt.*;

public class BoardCanvas extends Canvas {

    private final Game game;

    public BoardCanvas(Game game){
        this.game = game;
    }

    @Override
    public void paint(Graphics g){
        g.drawRoundRect(0,0,this.getBounds().width,this.getBounds().height,20,20);
        Board b = game.getBoard();
        int x = 10;
        int y = 30;
        for (int r = 0; r < b.getNumRows(); r++){
            for (int c = 0; c < b.getNumCols();c++){
                BoardCell cell = b.getCellAt(r,c);
                if (cell != null) {
                    cell.draw(g, x, y, Color.BLUE);
                }
                x += DrawPiece.size;
            }
            y+=DrawPiece.size;
            x = 10;
        }

    }
}
