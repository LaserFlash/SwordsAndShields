package SwordsAndShields.model.cells;


/**
 * PlayerCell is a type of BoardCell
 * This is a cell that is the Player.
 */
public class PlayerCell extends BoardCell {


    private final Token token;

    public PlayerCell(Token t){
        this.token = t;
    }


    @Override
    /**
     * Generate the text representation of the piece
     */
    public char[][] textRep() {
        char[][] rep = initialTextRep();
        for (int r = 0; r < rep.length; r++) {
            for (int c = 0; c < rep[r].length; c++) {
                rep[r][c] = '+';
            }
        }
        rep[1][1] = representation();
        return rep;
    }

    private char representation(){
        return token.getNumber();
    }

    public String getName(){
        StringBuilder b = new StringBuilder();
        b.append(token);
        b.append("(" + token.getNumber()+ ")");
        return b.toString();
    }

    public char getID(){
        return token.getNumber();
    }


    public enum Token{
        YELLOW{
            @Override
            public char getNumber() {
                return '1';
            }
        },
        GREEN {
            @Override
            public char getNumber() {
                return '0';
            }
        };


        public abstract char getNumber();
    }
}
