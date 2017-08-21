package SwordsAndShields.ui;

import SwordsAndShields.model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenuFrame extends JFrame implements ActionListener{
    private static final String TITLE = "Sword and Shield";
    private final JToolBar toolBar;
    private final JPanel contentArea;
    private final JButton start;
    private final JButton info;
    private final JButton quit;


    public StartMenuFrame(){
        super(TITLE);
        setLayout(new BorderLayout());
        this.toolBar = new JToolBar();

        info = new JButton("Info");
        info.setActionCommand("info");

        start = new JButton("Start");
        start.setActionCommand("start");

        quit = new JButton("Quit");
        quit.setActionCommand("quit");

        this.toolBar.add(info);
        this.toolBar.add(start);
        this.toolBar.add(quit);

        info.addActionListener(this);
        start.addActionListener(this);
        quit.addActionListener(this);

        this.toolBar.setFloatable(false);
        this.contentArea = new JPanel();

        add(toolBar,BorderLayout.NORTH);
        add(contentArea,BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }



    public static void main(String[] args) {
        StartMenuFrame controller = new StartMenuFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "info":
                contentArea.removeAll();
                contentArea.add(new JLabel("Author: Bryn Bennett"));
                contentArea.add(new JLabel("A SWEN222 Project"));
                contentArea.updateUI();
                break;

            case "start":
                contentArea.removeAll();
                contentArea.add(new BoardFrame());
                contentArea.updateUI();
                start.setVisible(false);
                info.setVisible(false);
                break;

            case "quit":
                contentArea.removeAll();
                this.dispose();
                break;
        }
    }
}

