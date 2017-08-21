package SwordsAndShields.ui;

import SwordsAndShields.model.Board;
import SwordsAndShields.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardFrame extends JFrame implements ActionListener{
    private final JToolBar toolBar;

    private final Canvas greenCreated;
    private final Canvas yellowCreated;
    private final Canvas boardCanvas;
    private final Canvas greenCemetery;
    private final Canvas yellowCemetery;

    private final Game game;

    private final GUIController controller;

    public BoardFrame(GUIController controller){
        super(StartMenuFrame.TITLE);
        this.controller = controller;

        /*
         * Setup GUI display
         */
        this.toolBar = setupToolBar();
        setLayout(new BorderLayout());
        add(toolBar,BorderLayout.NORTH);
        /*
         * Setup board display
         */

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        /*
         * Setup Game and start
         */
        this.game = new Game(new Board());
    }

    private JToolBar setupToolBar(){
        JToolBar bar = new JToolBar();

        JButton undo = new JButton("Undo");
        undo.setActionCommand("undo");
        undo.addActionListener(this);

        JButton pass = new JButton("Pass");
        pass.setActionCommand("pass");
        pass.addActionListener(this);

        JButton surrender = new JButton("Surrender\n");
        surrender.setActionCommand("surrender");
        surrender.addActionListener(this);

        bar.add(undo);
        bar.add(pass);
        bar.add(surrender);

        return bar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "surrender" :
                //Todo message based on actual winner
                JDialog dialog = new JDialog(this,"Game Over");
                dialog.add(new JLabel("Game Over"),BorderLayout.CENTER);
                dialog.pack();
                dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                dialog.setModal(true);  //Make it so Frame waits for dialog to close
                dialog.setVisible(true);

                controller.startScreen();
                this.dispose();
        }
    }
}
