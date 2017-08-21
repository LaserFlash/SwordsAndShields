package SwordsAndShields.controller;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.Game;
import SwordsAndShields.model.IllegalMoveException;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Parser for turning user input into the command attempting to be performed
 */
public class TextCommandMap implements SwordsAndShields.model.TurnState.CommandMap<Command> {

    public static final String PASS = "pass";
    public static final String MOVE = "move";
    public static final String CREATE = "create";
    public static final String ROTATE= "rotate";
    public static final String UNDO = "undo";
    public static final String REACT = "react";

    private final Game game;

    public TextCommandMap(Game game){
        this.game = game;
    }

    @Override
    public Command creatingCommand() {
        return new Command(){

            @Override
            public void parseExecute(String line) throws ParseException, IllegalMoveException {
                String[] tokens = generateTokens(line,1,3);
                String command = tokens[0];

                switch (command){
                    case PASS: {
                        game.passTurnState();
                        break;
                    }
                    case CREATE: {
                        try {
                            tokens[1].charAt(0);
                        }catch (ArrayIndexOutOfBoundsException e){
                            throw new ParseException("Incorrect amount of tokens for create");
                        }
                        char piece = tokens[1].charAt(0);
                        game.createPiece(piece, Direction.directionFromDegrees(Integer.parseInt(tokens[2])));
                        break;

                    }
                    default:
                        throw new ParseException("Unknown Command Name");
                }
            }

            @Override
            public String getValidForm() {
                String form = "You can: \n - " + formatCommand(CREATE,game.getCurrentPlayer().getUnusedPieceIDs(), d -> Integer.toString(d.getDegrees()));
                form += "\n - " + PASS;
                return form;
            }
        };
    }

    @Override
    public Command moveRotateCommand() {
        return new Command(){

            @Override
            public void parseExecute(String line) throws ParseException, IllegalMoveException {
                String[] tokens = generateTokens(line,1,3);
                String command = tokens[0];

                switch (command){
                    case PASS: {
                        game.passTurnState();
                        break;
                    }
                    case UNDO:{
                        game.undo();
                        break;
                    }
                    case MOVE: {
                        try {
                        char piece = tokens[1].charAt(0);
                        Direction d = Direction.directionFromOtherName(tokens[2]);
                        game.movePiece(piece, d);
                        } catch (ArrayIndexOutOfBoundsException e){
                            throw new ParseException("Incorrect number of tokens for move");
                        }
                        break;
                    }
                    case ROTATE: {
                        try {
                            char piece = tokens[1].charAt(0);
                            Direction d = Direction.directionFromDegrees(Integer.parseInt(tokens[2]));
                            game.rotatePiece(piece, d);
                        } catch (ArrayIndexOutOfBoundsException e){
                            throw new ParseException("Incorrect number of tokens for ROTATE");
                        }
                        break;
                    }
                    default:
                        throw new ParseException("Unknown Command Name");
                }
            }

            @Override
            public String getValidForm() {
                String form = "You can: \n - " + formatCommand(MOVE,game.getCurrentPlayer().getUsedPieceIDs(), d -> d.getOtherName());
                form += "\n - " + formatCommand(ROTATE,game.getCurrentPlayer().getUsedPieceIDs(), d -> Integer.toString(d.getDegrees()));
                form += "\n - " + PASS;
                form += "\n - " + UNDO;
                return form;
            }
        };
    }

    @Override
    public Command processReactionCommand() {
        return new Command() {
            @Override
            public void parseExecute(String line) throws ParseException, IllegalMoveException {
                String[] tokens = generateTokens(line, 1,3);
                String command = tokens[0];

                if (command.equals(UNDO)) {
                    game.undo();
                    return;
                }

                if (tokens.length != 3){
                    throw new ParseException("Invalid Command");
                }

                String react = tokens[1] + " " + tokens[2];

                List<String> possibleReacts = game.getReactionNames().stream()
                        .filter(name -> name.equals(react))
                        .collect(Collectors.toList());

                if (possibleReacts.isEmpty()){
                    throw new ParseException("No match for that reaction");
                }

                game.react(possibleReacts.get(0));
            }

            @Override
            public String getValidForm() {
                String form = "You can: \n - ";
                form += REACT;
                form += String.format("<%s>", String.join("/",game.getReactionNames()));
                form += "\n - " + UNDO;
                return form;
            }
        };
    }

    @Override
    public Command getGameFinished() {
        return new Command() {
            @Override
            public void parseExecute(String line) throws ParseException, IllegalMoveException {
                String[] command = generateTokens(line,1);
                if (!command[0].equals(UNDO)){ throw new ParseException("Invalid Command"); }
                game.undo();
            }

            @Override
            public String getValidForm() {
                return String.format("Player %s won!\n",game.getWinner().getName()) + "You can:\n" + UNDO;
            }
        };
    }
}
