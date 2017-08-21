package SwordsAndShields.model;

/**
 * Exception thrown when the user attempts to make a move taht does not follow game rules
 */
public class IllegalMoveException extends Exception {

    public IllegalMoveException(String m){super(m);}
}
