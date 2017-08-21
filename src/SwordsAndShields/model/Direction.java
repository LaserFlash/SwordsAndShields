package SwordsAndShields.model;

import java.util.Arrays;

/**
 * Cardinal directions for indicating side of a cell or direction of movement
 */
public enum Direction {
    NORTH("up"),
    EAST("right"),
    SOUTH("down"),
    WEST("left");

    private final String otherName;

    Direction(String name){
        this.otherName = name;
    }

    /**
     * Rotation amount for each direction
     * @return direction in degrees
     */
    public int getDegrees(){
        return ordinal() * 90;
    }

    /**
     * Movement based name for the direction
     * @return
     */
    public String getOtherName(){
        return otherName;
    }

    /**
     * Get the cardinal direction from degrees
     * defaults to North if incorrect cardinal direction
     * @param d degrees
     * @return cardinal direction
     */
    public static Direction directionFromDegrees(int d){
        if (d == 0) return NORTH;
        if (d == 90) return EAST;
        if (d == 180) return SOUTH;
        if (d == 270) return WEST;
        return NORTH;
    }

    /**
     * Given movement based name get cardinal direction
     * @param name
     * @return cardinal direction
     * @throws IllegalMoveException
     */
    public static Direction directionFromOtherName(String name) throws IllegalMoveException {
        return Arrays.stream(values())
                .filter(d -> d.getOtherName().endsWith(name))
                .findAny()
                .orElseThrow(() ->new IllegalMoveException("That is not a direction: " + name));

    }

    /**
     * Get the directional opposite
     * @param d
     * @return
     */
    public static Direction opposite(Direction d) {
        switch (d){
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
        }
        return NORTH;
    }


    /**
     * Calculate the direction of rotation required to undo
     * @param direction
     * @return direction corresponding to rotation required to reverse
     */
    public static Direction undoDirection(Direction direction){
        switch (direction){
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            default:
                return NORTH;
        }
    }


}
