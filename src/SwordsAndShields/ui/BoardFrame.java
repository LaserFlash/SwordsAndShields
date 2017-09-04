package SwordsAndShields.ui;

import SwordsAndShields.model.Game;
import SwordsAndShields.model.IllegalMoveException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardFrame extends Frame implements ActionListener{
    private final JToolBar toolBar;

    private final PiecesCanvas greenAvailable;
    private final PiecesCanvas yellowAvailable;
    private final JPanel boardCanvas;
    private final PiecesCanvas greenCemetery;
    private final PiecesCanvas yellowCemetery;

    private final Game game;

    private final GUIController controller;
    private boolean alt = true;

    public BoardFrame(GUIController controller, Game game){
        super(StartMenuFrame.TITLE);
        this.controller = controller;
        /*
         * Setup Game and start
         */
        this.game = game;

        /*
         * Setup GUI display
         */
        this.toolBar = setupToolBar();
        setLayout(new BorderLayout());
        add(toolBar,BorderLayout.PAGE_START);
        /*
         * Setup board display
         */
        this.greenAvailable = new PiecesCanvas("Green Available", Color.green, game.getGreenPiecesAvailable(), controller, game);
        this.yellowAvailable = new PiecesCanvas("Yellow Available", Color.yellow, game.getYellowPiecesAvailable(), controller, game);
        this.greenCemetery = new PiecesCanvas("Green Dead", Color.green, game.getGreenPiecesDead(), controller, game);
        this.yellowCemetery = new PiecesCanvas("Yellow Dead", Color.yellow, game.getYellowPiecesDead(), controller, game);
        this.boardCanvas = new BoardCanvas(controller, game);

        JPanel gPanel = new JPanel();
        gPanel.setLayout(new BorderLayout());
        greenAvailable.setPreferredSize(new Dimension(250,400));
        greenCemetery.setPreferredSize(new Dimension(250,400));
        gPanel.add(greenAvailable,BorderLayout.NORTH);
        gPanel.add(greenCemetery,BorderLayout.SOUTH);
        add(gPanel,BorderLayout.WEST);


        JPanel bPanel = new JPanel();
        bPanel.setLayout(new BorderLayout());
        boardCanvas.setPreferredSize(new Dimension(550,550));
        bPanel.add(boardCanvas,BorderLayout.CENTER);
        add(bPanel,BorderLayout.CENTER);

        JPanel yPanel = new JPanel();
        yPanel.setLayout(new BorderLayout());
        yellowAvailable.setPreferredSize(new Dimension(250,400));
        yellowCemetery.setPreferredSize(new Dimension(250,400));
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

        setGreenActive();
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
                dialog.add(new JLabel(game.getCurrentPlayer().getName() + " Lost"),BorderLayout.CENTER);
                dialog.pack();
                dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                dialog.setModal(true);  //Make it so Frame waits for dialog to close
                dialog.setVisible(true);

                controller.startScreen();
                this.dispose();
                break;
            case "pass" :
                controller.passState();
                if (greenAvailable.active){
                    setGreenYellowInactive();
                }else if(yellowAvailable.active){
                    setGreenYellowInactive();
                }else if (!yellowAvailable.active &! greenAvailable.active){
                    if (alt){
                        setGreenActive();
                    }else {
                        setYellowActive();
                    }
                }
                break;
            case "undo":
                try {
                    game.undo();
                    controller.setState(GUITurnState.fromGameTurnState(game.getTurnState()));
                    if (controller.getState() == GUITurnState.Create){
                        if (!alt){
                            setGreenActive();
                        }else {
                            setYellowActive();
                        }
                    }
                    repaint();
                } catch (IllegalMoveException e1) {}
                break;
        }
    }

    public void update(){
        greenAvailable.updatePieces(game.getGreenPiecesAvailable());
        greenCemetery.updatePieces(game.getGreenPiecesDead());
        yellowAvailable.updatePieces(game.getYellowPiecesAvailable());
        yellowCemetery.updatePieces(game.getYellowPiecesDead());
    }

    @Override
    public void setGreenActive(){
        this.greenAvailable.active = true;
        this.greenCemetery.active = true;
        this.yellowCemetery.active = false;
        this.yellowAvailable.active = false;
        alt = false;
    }

    @Override
    public void setYellowActive(){
        this.greenAvailable.active = false;
        this.greenCemetery.active = false;
        this.yellowCemetery.active = true;
        this.yellowAvailable.active = true;
        alt = true;
    }

    @Override
    public void setGreenYellowInactive(){
        this.greenAvailable.active = false;
        this.greenCemetery.active = false;
        this.yellowCemetery.active = false;
        this.yellowAvailable.active = false;
    }
}
