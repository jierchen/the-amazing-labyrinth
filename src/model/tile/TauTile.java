package model.tile;

import enums.Direction;
import enums.TileType;

public class TauTile extends Tile {

    // Non-stationary T-tiles amount
    public static final int TILE_AMOUNT = 6;

    // Openings for T-tiles
    private final static boolean[][] OPENINGS_BY_ORIENTATION = new boolean[][] {
            {true, true, false, true},
            {true, true, true, false},
            {false, true, true, true},
            {true, false, true, true}
    };

    protected TauTile(int row, int col, Direction orientation) {
        super(row, col, orientation, TileType.TAU);
    }

    @Override
    protected void updateOpenings() {
        openings = OPENINGS_BY_ORIENTATION[this.getOrientation().getValue()];
    }
}
