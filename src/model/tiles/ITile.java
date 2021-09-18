package model.tiles;

public class ITile extends Tile {

    // Non-stationary I-tiles amount
    public static final int TILE_AMOUNT = 12;

    // Openings for I-tiles
    public static final boolean[][] OPENINGS_BY_ORIENTATION = new boolean[][] {
            {true, false, true, false},
            {false, true, false, true},
            {true, false, true, false},
            {false, true, false, true}
    };

    /**
     * Creates new I-tile based on row position, column position, and orientation
     *
     * @param row row position on the board
     * @param col column position on the board
     * @param orientation orientation of the tile
     */
    public ITile(int row, int col, int orientation) {
        super(row, col, orientation);

        setType('I');
        updateOpenings();
    }


    /**
     * Sets openings for I-tiles based on orientation
     */
    @Override
    public void updateOpenings() {
        this.openings = OPENINGS_BY_ORIENTATION[this.getOrientation()];
    }

}
