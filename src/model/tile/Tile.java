package model.tile;

import enums.Direction;
import enums.TileType;
import model.Piece;
import model.Player;
import model.Treasure;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile extends Piece {

    // State
    private Direction orientation;
    protected boolean[] openings = new boolean[Direction.NUM_DIRECTIONS];
    private boolean isInsertable = false;
    private final TileType type;

    // External
    private Treasure treasure = null;
    private List<Tile> adjacentTiles = new ArrayList<>();
    private List<Player> playersOnTile = new ArrayList<>();

    protected Tile(int row, int col, Direction orientation, TileType type) {
        super(row, col);
        this.orientation = orientation;
        this.type = type;
        updateOpenings();
    }

    public static Tile create(int row, int col, Direction orientation, TileType type) {
        switch(type) {
            case STRAIGHT:
                return new StraightTile(row, col, orientation);
            case BENT:
                return new BentTile(row, col, orientation);
            default:
                return new TauTile(row, col, orientation);
        }
    }

    protected abstract void updateOpenings();

    /**
     * Rotates tile clockwise
     * @return New orientation of tile
     */
    public Direction rotate() {
        switch(orientation) {
            case UP:
                orientation = Direction.RIGHT;
                break;
            case RIGHT:
                orientation = Direction.DOWN;
                break;
            case DOWN:
                orientation = Direction.LEFT;
                break;
            case LEFT:
                orientation = Direction.UP;
                break;
        }

        updateOpenings();
        return orientation;
    }

    public void addPlayerOnTile(Player player) {
        playersOnTile.add(player);

        if(hasTreasure() && player.getTopOfHand() != null) {
            if(player.getTopOfHand().getTreasureNum() == treasure.getNumber()) {
                player.goToNextCard();

                treasure.setCollected(true);
                treasure = null;
            }
        }
    }

    public void removePlayerOnTile(Player player) {
        playersOnTile.remove(player);
    }

    public void removeAllPlayersOnTile() {
        playersOnTile.clear();
    }

    public boolean hasTreasure() {
        return treasure != null;
    }

    public void addAdjacentTile(Tile tile) {
        adjacentTiles.add(tile);
    }

    public void removeAdjacentTiles() {
        adjacentTiles.clear();
    }

    public boolean getOpening(Direction direction) {
        return openings[direction.getValue()];
    }

    @Override
    public void setRow(int row) {
        super.setRow(row);

        for(Player playerOnTile: playersOnTile) {
            playerOnTile.setRow(row);
        }
    }
    @Override
    public void setCol(int col) {
        super.setCol(col);

        for(Player playerOnTile: playersOnTile) {
            playerOnTile.setCol(col);
        }
    }

    public Direction getOrientation() {
        return orientation;
    }

    public TileType getType() {
        return type;
    }

    public void setInsertable(boolean isInsertable) {
        this.isInsertable = isInsertable;
    }

    public void setTreasure(Treasure treasure) {
        this.treasure = treasure;
    }

    public Treasure getTreasure() {
        return treasure;
    }

    public boolean isInsertable() {
        return isInsertable;
    }

    public List<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }
}
