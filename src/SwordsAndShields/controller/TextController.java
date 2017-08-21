package SwordsAndShields.controller;

import SwordsAndShields.model.*;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Controller for the game, gets and displays information through a text interface
 */
public class TextController {
    private final PrintStream out;
    private final Scanner in;
    private final TextCommandMap commandMap;
    private Game game;

    public TextController(Game game) {
        this.game = game;
        this.commandMap = new TextCommandMap(game);
        this.out = System.out;
        this.in = new Scanner(System.in);
    }

    public TextController(Game game,Scanner sc, PrintStream stream) {
        this.game = game;
        this.commandMap = new TextCommandMap(game);
        this.out = stream;
        this.in = sc;
    }

    /**
     * Loop that runs the game, getting user input and displaying the game.
     */
    public void GameLoop(){
        out.println(getBoardString());
        out.println(getGameInformationString());

        while (in.hasNext()){
            String line = in.nextLine();

            TurnState gameTurnState = game.getTurnState();
            Command command = gameTurnState.getCommand(commandMap);

            /*
                Stop the loop when victory condition meet
             */
            if (gameTurnState == TurnState.GAME_FINISHED){
                out.println(game.getWinner().getName());
                return;
            }

            try {
                command.parseExecute(line);
                out.println(getBoardString());
            }catch (ParseException | IllegalMoveException e){
                String m = "Your move was not performed \n";
                out.println(m + e.getMessage());
                out.println();
            }

            out.println(getGameInformationString());
        }

    }


    /**
     * Generate the text representation for the board
     * @return String representation of board
     */
    private String getBoardString(){
        StringBuilder b = new StringBuilder();
        b.append(TextRep.convertToString(game.getBoardRep()));
        return b.toString();
    }

    /**
     * Get the commands that could be used and the form they would be entered in
     * @return
     */
    private String getGameInformationString(){
        StringBuilder b = new StringBuilder();
        b.append(game.getCurrentPlayer().getName() + " ");
        b.append(game.getTurnState().getCommand(commandMap).getValidForm());

        return b.toString();
    }

    public static void main(String[] args) {
        TextController controller = new TextController(new Game(new Board()));
        controller.GameLoop();
    }
}

