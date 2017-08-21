package SwordsAndShields.ui;

import javax.swing.*;

public class GUIController {
    private static JFrame frame;

    public GUIController(){
        this.frame = new StartMenuFrame(this);
    }

    public void startGame(){
        this.frame = new BoardFrame(this);
    }

    public static void main(String[] args) {
        GUIController controller = new GUIController();
    }

    public void startScreen() {
        this.frame = new StartMenuFrame(this);    }
}
