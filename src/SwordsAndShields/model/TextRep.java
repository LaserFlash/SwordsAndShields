package SwordsAndShields.model;

/**
 * Created by brynt on 10/08/2017.
 */
public interface TextRep {
    char BLANK_DIVIDE = '.';
    char BLANK_TEXT = ' ';


    static char[][] initialTextRep(int height, int width) { return initialTextRep(height,width,BLANK_DIVIDE);}

    static char[][] initialTextRep(int height, int width, char fill) {
        char[][] rep = new char[height][width];

        for (int r = 0; r < height; r++){
            char[] row = rep[r];
            for (int c = 0; c < width; c++){
                row[c] = fill;
            }
        }
        return rep;
    }

    /**
     * Given the representation covert it to a string for easy printing
     * @param rep
     * @return
     */
    static String convertToString(char[][] rep){
        StringBuilder s = new StringBuilder();
        for (char[] row : rep){
            for (char c : row){
                s.append(c);
                s.append("  ");
            }
            s.append('\n');
        }
        return s.toString();
    }

    /**
     * Put a representation inside another, useful for building up a board of different cells
     * @param insert
     * @param rep
     * @param row
     * @param col
     */
    static void insertRep(char[][] insert, char[][] rep, int row, int col){
        for (int r = 0; r < insert.length; r++) {
            for (int c = 0; c < insert[0].length; c++) {
                rep[row + r][col + c] = insert[r][c];
            }
        }
    }

    /**Create a text representation of the object
     *
     * @return the 2d character array representing the object
     */
    char[][] textRep();
}
