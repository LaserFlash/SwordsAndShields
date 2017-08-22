package SwordsAndShields.ui;

import javax.swing.*;

public abstract class Frame extends JFrame {
    public Frame(String s){
        super(s);
    }

    public abstract void  setGreenActive();
    public abstract void setYellowActive();
    public abstract void setGreenYellowInactive();
}
