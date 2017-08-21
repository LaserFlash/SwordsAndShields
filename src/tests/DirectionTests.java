package tests;

import SwordsAndShields.model.Direction;
import SwordsAndShields.model.IllegalMoveException;
import org.junit.Test;


import static org.junit.Assert.*;


public class DirectionTests {

    @Test
    public void getDegrees_Correctness(){
        assertEquals(0,Direction.NORTH.getDegrees());
        assertEquals(90,Direction.EAST.getDegrees());
        assertEquals(180,Direction.SOUTH.getDegrees());
        assertEquals(270,Direction.WEST.getDegrees());
    }

    @Test
    public void getOtherName_Correctness(){
        assertEquals("up", Direction.NORTH.getOtherName());
        assertEquals("right", Direction.EAST.getOtherName());
        assertEquals("down", Direction.SOUTH.getOtherName());
        assertEquals("left", Direction.WEST.getOtherName());
    }

    @Test
    public void directionFromDegrees_Correctness(){
        assertEquals(Direction.NORTH, Direction.directionFromDegrees(0));
        assertEquals(Direction.EAST, Direction.directionFromDegrees(90));
        assertEquals(Direction.SOUTH, Direction.directionFromDegrees(180));
        assertEquals(Direction.WEST, Direction.directionFromDegrees(270));
        assertEquals(Direction.NORTH,Direction.directionFromDegrees(80));
    }

    @Test
    public void directionFromOtherName_Correctness() {
        try {
            assertEquals(Direction.NORTH, Direction.directionFromOtherName("up"));
            assertEquals(Direction.EAST, Direction.directionFromOtherName("right"));
            assertEquals(Direction.SOUTH, Direction.directionFromOtherName("down"));
            assertEquals(Direction.WEST, Direction.directionFromOtherName("left"));
        } catch (IllegalMoveException e) { fail(); }
    }

    @Test
    public void directionUndo_Correctness(){
        assertEquals(Direction.NORTH,Direction.undoDirection(Direction.NORTH)); //no rotation actually occurs
        assertEquals(Direction.WEST,Direction.undoDirection(Direction.EAST));
        assertEquals(Direction.EAST,Direction.undoDirection(Direction.WEST));
        assertEquals(Direction.SOUTH,Direction.undoDirection(Direction.SOUTH)); //Same amount of rotation
    }

}
