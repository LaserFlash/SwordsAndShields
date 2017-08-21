package SwordsAndShields.controller;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.IllegalMoveException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The command that the user is trying to perform
 */
public abstract class Command {

    /**
     * Generate a string that nicely displays the expected format of the given command
     * @param command       user input for the command name
     * @param pieces        potential pieces that could be used
     * @param directionName how to create the direction name
     * @return
     */
    protected static String formatCommand(String command, Collection<Character> pieces, Function<Direction, String> directionName){
        /* Sort the ids of the pieces for display */
        List<String> ids = pieces.stream()
                .map(Object::toString)
                .sorted()
                .collect(Collectors.toList());

        /* Get the relevant direction name */
        List<String> directions = Arrays.stream(Direction.values())
                .map(directionName)
                .collect(Collectors.toList());

        /*
          Suitably format the string for display
          Format: command <id/id/id/..> <direction/direction/direction/direction>
         */
        String s = String.format(
                "%s <%s> <%s>",
                command,
                String.join("/",ids),
                String.join("/", directions)
        );

        return s;
    }

    /**
     *  Create an array of tokens to represent the give line of text
     * @param line      text to generate from
     * @param range     The possible number of tokens
     * @return
     */
    protected static String[] generateTokens(String line, Integer... range) throws ParseException{
        String[] tokens = Arrays.stream(line.split(" "))
                .map(String::trim)
                .filter(t -> t.length() > 0)
                .toArray(String[]::new);

        if (!Arrays.asList(range).contains(tokens.length)){
            throw new ParseException("Incorrect number of tokens");
        }
        return tokens;
    }

    /**
     *
     * @param line user entered text
     */
    public abstract void parseExecute(String line) throws ParseException, IllegalMoveException;

    /**
     * gets a string to represent how commands should have been entered
     * @return
     */
    public abstract String getValidForm();
}
