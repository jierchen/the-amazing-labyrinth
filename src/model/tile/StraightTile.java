package model.tile;

import enums.Direction;
import enums.TileType;

public class StraightTile extends Tile {

    public static final int TILE_AMOUNT = 12;
    private static final boolean[][] OPENINGS_BY_ORIENTATION = new boolean[][] {
            {true, false, true, false},
            {false, true, false, true},
            {true, false, true, false},
            {false, true, false, true}
    };

    protected StraightTile(int row, int col, Direction orientation) {
        super(row, col, orientation, TileType.STRAIGHT);
    }

    @Override
    protected void updateOpenings() {
        openings = OPENINGS_BY_ORIENTATION[this.getOrientation().getValue()];
    }
}
