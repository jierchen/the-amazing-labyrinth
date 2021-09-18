package model;

public abstract class Piece {

    // row and column positions
    private int row;
    private int col;

    /**
     * Constructor
     *
     * @param row row position on the board
     * @param col column position on the board
     */
    public Piece(int row, int col) {
        // set positions
        this.row = row;
        this.col = col;
    }

    // Getters and setters
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }

}
