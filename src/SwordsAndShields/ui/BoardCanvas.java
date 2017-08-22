package SwordsAndShields.ui;

import SwordsAndShields.model.Board;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.cells.BoardCell;
import SwordsAndShields.model.cells.PieceCell;
import SwordsAndShields.model.cells.PlayerCell;

import javax.swing.*;
import java.awt.*;

public class BoardCanvas extends JPanel {

    private final Game game;

    public BoardCanvas(Game game){
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRoundRect(0,0,this.getBounds().width,this.getBounds().height,20,20);
        Board b = game.getBoard();
        Color altPattern = Color.white;
        altPattern = altPattern(altPattern);
        int x = DrawPiece.xPadding;
        int y = DrawPiece.yPadding;
        for (int r = 0; r < b.getNumRows(); r++){
            for (int c = 0; c < b.getNumCols();c++){
                BoardCell cell = b.getCellAt(r,c);
                if (cell != null) {
                    cell.draw(g, x, y, getCellColor(cell));
                }else{
                    DrawPiece.drawNullCell(g,x,y,altPattern);
                }
                altPattern = altPattern(altPattern);
                x += DrawPiece.size;
            }
            altPattern = altPattern(altPattern);
            y+=DrawPiece.size;
            x = DrawPiece.xPadding;
        }

    }

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

    private Color altPattern(Color color){
        if(color == DrawPiece.alt1) {
            return DrawPiece.alt2;
        }
        if (color == DrawPiece.alt2) {
           return DrawPiece.alt1;
        }

        return DrawPiece.alt1;
    }
}
