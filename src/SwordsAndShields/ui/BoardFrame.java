package SwordsAndShields.ui;

import SwordsAndShields.model.Board;
import SwordsAndShields.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardFrame extends JFrame implements ActionListener{
    private final JToolBar toolBar;

    private final Canvas greenAvailable;
    private final Canvas yellowAvailable;
    private final Canvas boardCanvas;
    private final Canvas greenCemetery;
    private final Canvas yellowCemetery;

    private final Game game;

    private final GUIController controller;

    public BoardFrame(GUIController controller){
        super(StartMenuFrame.TITLE);
        this.controller = controller;
        /*
         * Setup Game and start
         */
        this.game = new Game(new Board());

        /*
         * Setup GUI display
         */
        this.toolBar = setupToolBar();
        setLayout(new BorderLayout());
        add(toolBar,BorderLayout.PAGE_START);
        /*
         * Setup board display
         */
        this.greenAvailable = new PiecesCanvas("Green Available", Color.green, game.getGreenPiecesAvailable());
        this.yellowAvailable = new PiecesCanvas("Yellow Available", Color.yellow, game.getYelllowPiecesAvailable());
        this.greenCemetery = new PiecesCanvas("Green Dead", Color.green, game.getGreenPiecesDead());
        this.yellowCemetery = new PiecesCanvas("Yellow Dead", Color.yellow, game.getYellowPiecesDead());
        this.boardCanvas = new BoardCanvas();

        JPanel gPanel = new JPanel();
        gPanel.setLayout(new BorderLayout());
        greenAvailable.setSize(new Dimension(250,400));
        greenCemetery.setSize(new Dimension(250,400));
        gPanel.add(greenAvailable,BorderLayout.NORTH);
        gPanel.add(greenCemetery,BorderLayout.SOUTH);
        add(gPanel,BorderLayout.WEST);

        JPanel bPanel = new JPanel();
        bPanel.setLayout(new BorderLayout());
        boardCanvas.setSize(200,200);
        bPanel.add(boardCanvas,BorderLayout.CENTER);
        add(bPanel,BorderLayout.CENTER);

        JPanel yPanel = new JPanel();
        yPanel.setLayout(new BorderLayout());
        yellowAvailable.setSize(new Dimension(250,400));
        yellowCemetery.setSize(new Dimension(250,400));
        yPanel.add(yellowAvailable,BorderLayout.NORTH);
        yPanel.add(yellowCemetery,BorderLayout.SOUTH);
        add(yPanel,BorderLayout.EAST);

        pack();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scrnsize = toolkit.getScreenSize();
        setBounds((scrnsize.width - getWidth()) / 2,
                (scrnsize.height - getHeight()) / 2, getWidth(), getHeight());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        bPanel.setVisible(true);
        gPanel.setVisible(true);
        yPanel.setVisible(true);
        setVisible(true);
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
    public void repaint(){
        boardCanvas.repaint();
        yellowAvailable.repaint();
        yellowCemetery.repaint();
        greenAvailable.repaint();
        greenCemetery.repaint();
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
