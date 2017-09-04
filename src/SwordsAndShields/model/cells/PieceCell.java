package SwordsAndShields.model.cells;


import SwordsAndShields.model.Direction;
import SwordsAndShields.model.TextRep;
import SwordsAndShields.ui.DrawPiece;

import java.awt.*;

/**
 * Representation of a piece on the board
 */
public class PieceCell extends BoardCell{

    private final Sides sides;
    private final Character id;

    /**
     * Create a piece
     * @param id    character representation of the piece
     * @param sides items that appear on the sides of the piece
     */
    public PieceCell(char id, Sides sides) {
        this.id = id;
        this.sides = sides;
    }

    @Override
    public char[][] textRep() {
        char[][] rep = initialTextRep();
        rep[1][1] = id;

        rep[0][1] = getTextRep(sides.north,Direction.NORTH);
        rep[1][2] = getTextRep(sides.east,Direction.EAST);
        rep[2][1] = getTextRep(sides.south,Direction.SOUTH);
        rep[1][0] = getTextRep(sides.west,Direction.WEST);

        return rep;
    }

    private char getTextRep(SideType t, Direction d){
        return t.textRep(d);
    }

    /** All the different permutation of sword and shield a piece can have
     *
     * Naming order follows that in handout
     * North, South, East, West
     */

    public static Sides[] Permutations =
        {
            new Sides(SideType.SWORD, SideType.SHIELD, SideType.SWORD, SideType.SWORD),
            new Sides(SideType.SWORD,SideType.EMPTY,SideType.SWORD,SideType.SWORD),
            new Sides(SideType.SHIELD,SideType.SHIELD,SideType.SHIELD,SideType.SHIELD),
            new Sides(SideType.SWORD,SideType.EMPTY,SideType.SHIELD,SideType.EMPTY),

            new Sides(SideType.EMPTY,SideType.EMPTY,SideType.EMPTY,SideType.EMPTY),
            new Sides(SideType.SWORD,SideType.SHIELD,SideType.SHIELD,SideType.SWORD),
            new Sides(SideType.SWORD,SideType.SWORD,SideType.SWORD,SideType.SWORD),
            new Sides(SideType.SWORD,SideType.EMPTY,SideType.SHIELD,SideType.SHIELD),
            new Sides(SideType.EMPTY,SideType.SHIELD,SideType.EMPTY,SideType.EMPTY),

            new Sides(SideType.SWORD,SideType.SHIELD,SideType.SWORD,SideType.SHIELD),
            new Sides(SideType.SWORD,SideType.SHIELD,SideType.EMPTY,SideType.SWORD),
            new Sides(SideType.SWORD,SideType.EMPTY,SideType.EMPTY,SideType.EMPTY),
            new Sides(SideType.SWORD,SideType.SHIELD,SideType.SHIELD,SideType.EMPTY),
            new Sides(SideType.EMPTY,SideType.SHIELD,SideType.SHIELD,SideType.EMPTY),

            new Sides(SideType.SWORD,SideType.EMPTY,SideType.SWORD,SideType.SHIELD),
            new Sides(SideType.SWORD,SideType.EMPTY,SideType.SHIELD,SideType.SWORD),
            new Sides(SideType.SWORD,SideType.EMPTY,SideType.EMPTY,SideType.SHIELD),
            new Sides(SideType.SWORD,SideType.SHIELD,SideType.EMPTY,SideType.SHIELD),
            new Sides(SideType.EMPTY,SideType.SHIELD,SideType.EMPTY,SideType.SHIELD),

            new Sides(SideType.SWORD,SideType.EMPTY,SideType.SWORD,SideType.EMPTY),
            new Sides(SideType.SWORD,SideType.EMPTY,SideType.EMPTY,SideType.SWORD),
            new Sides(SideType.SWORD,SideType.SHIELD,SideType.EMPTY,SideType.EMPTY),
            new Sides(SideType.SWORD,SideType.SHIELD,SideType.SHIELD,SideType.SHIELD),
            new Sides(SideType.EMPTY,SideType.SHIELD,SideType.SHIELD,SideType.SHIELD)
    };

    /**
     * Rotate the piece to to the given direction
     * @param d
     */
    public void rotate(Direction d){
        for (int i = 0; i < d.ordinal(); i++){
            sides.rotate();
        }
    }

    /**
     * Items that can appear on sides of the piece
     */
    public enum SideType{
        SWORD{
            public char textRep(Direction dir){
                if (dir == Direction.NORTH || dir == Direction.SOUTH) { return '|';}
                return '-';
            }
        },
        SHIELD{
            public char textRep(Direction dir){
                return '#';
            }
        },
        EMPTY{
            public char textRep(Direction dir){
                return TextRep.BLANK_TEXT;
            }
        };

        /**
         * Get the character representation of the item
         * Can depend on the direction facing
         * @param d direction facing
         * @return
         */
        public abstract char textRep(Direction d);
    }

    @Override
    public void draw(Graphics g, int x, int y, Color bg) {
        g.setColor(bg);
        g.fillRect(x,y, DrawPiece.size,DrawPiece.size);
        g.setColor(Color.black);

        switch (getSide(Direction.NORTH)){
            case SHIELD:
                g.fillRect(x,y,DrawPiece.shieldHeight,DrawPiece.itemWidth);
                break;
            case SWORD:
                g.fillRect(x + DrawPiece.size/2 - DrawPiece.itemWidth/2,y,DrawPiece.itemWidth,DrawPiece.swordHeight);
                break;
        }

        switch (getSide(Direction.EAST)){
            case SHIELD:
                g.fillRect(x + DrawPiece.size - DrawPiece.itemWidth, y, DrawPiece.itemWidth, DrawPiece.shieldHeight);
                break;
            case SWORD:
                g.fillRect(x + DrawPiece.size/2, y + DrawPiece.size/2 - DrawPiece.itemWidth/2 , DrawPiece.swordHeight, DrawPiece.itemWidth);
                break;
        }

        switch (getSide(Direction.SOUTH)){
            case SHIELD:
                g.fillRect(x, y + DrawPiece.size - DrawPiece.itemWidth, DrawPiece.shieldHeight, DrawPiece.itemWidth);
                break;
            case SWORD:
                g.fillRect(x + DrawPiece.size/2 - DrawPiece.itemWidth/2, y + DrawPiece.size/2, DrawPiece.itemWidth, DrawPiece.swordHeight);
                break;
        }

        switch (getSide(Direction.WEST)){
            case SHIELD:
                g.fillRect(x, y, DrawPiece.itemWidth, DrawPiece.shieldHeight);
                break;
            case SWORD:
                g.fillRect(x , y + DrawPiece.size/2 - DrawPiece.itemWidth/2, DrawPiece.swordHeight, DrawPiece.itemWidth);
                break;
        }
    }

    /**
     * Find and get the item on the given side of the piece
     * @param direction
     * @return
     */
    public PieceCell.SideType getSide(Direction direction){
        switch (direction){
            case WEST:
                return sides.west;
            case EAST:
                return sides.east;
            case SOUTH:
                return sides.south;
            case NORTH:
                return sides.north;
        }
        return SideType.EMPTY;
    }

    @Override
    public char getID(){
        return id;
    }

    /**
     * Holding object for the 4 sides of the shapes
     */
    public static class Sides{
        private SideType north;
        private SideType east;
        private SideType south;
        private SideType west;

        public Sides(SideType n, SideType e, SideType s, SideType w){
            north = n;
            south = s;
            east = e;
            west = w;
        }

        /**
         * Rotate the piece 90 degrees clockwise
         */
        private void rotate() {
            Sides tmp = clone();

            north = tmp.west;
            east = tmp.north;
            south = tmp.east;
            west = tmp.south;
        }

        /**
         * Clone method to copy the sides
         */
        public Sides clone(){
            return new Sides(north,east,south,west);
        }
    }

    public PieceCell clone() {return new PieceCell(id,sides.clone());}

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()){
            return false;
        }
        PieceCell p = (PieceCell) o;
        if (id != p.getID()){
            return false;
        }
        return true;
    }
}
