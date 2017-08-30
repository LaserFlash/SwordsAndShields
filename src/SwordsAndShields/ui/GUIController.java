package SwordsAndShields.ui;

import SwordsAndShields.model.Board;
import SwordsAndShields.model.Direction;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.IllegalMoveException;
import SwordsAndShields.model.cells.PieceCell;

public class GUIController {
    private static Frame frame;
    private Game game;

    private GUITurnState state;

    public GUIController(){
        this.frame = new StartMenuFrame(this);
    }

    public void startGame(){
        this.game = new Game(new Board());
        this.frame = new BoardFrame(this, game);
        this.state = GUITurnState.Create;
    }



    public void startScreen() {this.frame = new StartMenuFrame(this);}

    public GUITurnState getState() {
        return state;
    }
    public void previousState() {
        this.state = state.previousMajorState();
        update();
    }
    public void nextState() {
        this.state = state.nextMajorState();
        update();
    }


    public void createPiece(PieceCell pieceCell, int i) {
        try {
            game.createPiece(pieceCell.getID(), Direction.directionFromNumber(i));
            frame.setGreenYellowInactive();
            update();
            frame.repaint();
        }catch (IllegalMoveException e){

        }
    }

    public static void main(String[] args) {
        GUIController controller = new GUIController();
    }

    public void passState() {
        this.state = state.passState();
        game.passTurnState();
        update();
    }

    public void setState(GUITurnState state) {
        this.state = state;
        update();
    }

    private void update(){
        if (frame.getClass() == BoardFrame.class){
            BoardFrame f = (BoardFrame) frame;
            f.update();
            f.repaint();
        }

    }
}
