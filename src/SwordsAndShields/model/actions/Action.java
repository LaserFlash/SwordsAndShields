package SwordsAndShields.model.actions;

/**
 * Interface to provide an Action template
 * Actions are step taken to move pieces
 */
public interface Action{

    /**
     * The steps taken to move the piece and other affected pieces on the board
     */
    void performAction();

    /**
     * The steps taken to undo the changes made by perform action.
     */
    void undoAction();

}
