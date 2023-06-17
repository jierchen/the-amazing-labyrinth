package model.tile;

import enums.Direction;
import enums.TileType;

public class BentTile extends Tile {

    // Non-stationary L-tiles amount
    public static final int TILE_AMOUNT = 16;

    // Openings for L-tiles
    private static final boolean[][] OPENINGS_BY_ORIENTATION = new boolean[][] {
            {true, true, false, false},
            {false, true, true, false},
            {false, false, true, true},
            {true, false, false, true}
    };

    public BentTile(int row, int col, Direction orientation) {
        super(row, col, orientation, TileType.BENT);
    }

    @Override
    protected void updateOpenings() {

    }
}
