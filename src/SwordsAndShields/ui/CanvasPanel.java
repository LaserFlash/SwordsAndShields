package SwordsAndShields.ui;

import SwordsAndShields.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract  class CanvasPanel extends JPanel{
    protected final GUIController controller;
    protected final Game game;

    CanvasPanel(GUIController c, Game g){
        controller = c;
        game = g;
        addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e){
                selectPiece(e.getPoint());
            }

            public void mouseEntered(MouseEvent arg0) {}
            public void mouseExited(MouseEvent arg0) {}
            public void mousePressed(MouseEvent arg0) {}
            public void mouseReleased(MouseEvent arg0) {}
        });
    }

    /**
     * Given a point, calculate which piece the user is attempting to select
     * @param p point of click/selection
     */
    protected abstract void selectPiece(Point p);


}
