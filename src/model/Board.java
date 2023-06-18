package model;

import enums.Direction;
import enums.TileType;
import model.tile.BentTile;
import model.tile.StraightTile;
import model.tile.TauTile;
import model.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Board {

    public static final int NUM_OF_TILES_PER_SIDE = 7;

    private static final char[][] STATIONARY_TILES_TYPE_MATRIX = new char[][] {
            {'L',' ','T',' ','T',' ','L'},
            {' ',' ',' ',' ',' ',' ',' '},
            {'T',' ','T',' ','T',' ','T'},
            {' ',' ',' ',' ',' ',' ',' '},
            {'T',' ','T',' ','T',' ','T'},
            {' ',' ',' ',' ',' ',' ',' '},
            {'L',' ','T',' ','T',' ','L'}
    };

    private static final int[][] STATIONARY_TILES_ORIENTATION_MATRIX = new int[][] {
            {'1',' ','2',' ','2',' ','2'},
            {' ',' ',' ',' ',' ',' ',' '},
            {'1',' ','1',' ','2',' ','3'},
            {' ',' ',' ',' ',' ',' ',' '},
            {'1',' ','0',' ','3',' ','3'},
            {' ',' ',' ',' ',' ',' ',' '},
            {'0',' ','0',' ','0',' ','3'},
    };

    /**
     * Helper class to encapsulate {@code TileType} and treasure number pair
     */
    private static class TileTypeAndTreasureNum {
        private TileType type;
        private int treasureNum;

        public TileTypeAndTreasureNum(TileType type, int treasureNum) {
            this.type = type;
            this.treasureNum = treasureNum;
        }

        public TileType getType() {
            return type;
        }

        public int getTreasureNum() {
            return treasureNum;
        }
    }

    // Order is left to right -> top to bottom
    private Tile[][] tiles = new Tile[NUM_OF_TILES_PER_SIDE][NUM_OF_TILES_PER_SIDE];
    private Player[] players;
    private Treasure[] treasures;
    private Tile insertableTile;

    public Board(Player[] players, Treasure[] treasures) {
        this.players = players;
        this.treasures = treasures;

        // Initialize
        setupTiles();
        connectAllTiles();
        addPlayersToTiles();
    }

    private void addPlayersToTiles() {
        for(Player player: players) {
            Tile homeTile = tiles[player.getRow()][player.getCol()];
            homeTile.addPlayerOnTile(player);
        }
    }

    private void connectAllTiles() {
        connectTiles(0, tiles.length - 1, 0, tiles.length - 1);
    }

    private void connectTiles(int rowStart, int rowEnd, int colStart, int colEnd) {
        // Limit the bounds
        rowStart = Math.max(rowStart, 0);
        rowEnd = Math.min(rowEnd, tiles.length - 1);
        colStart = Math.max(colStart, 0);
        colEnd = Math.min(colEnd, tiles.length - 1);

        for(int row = rowStart; row <= rowEnd; row++) {
            for(int col = colStart; col <= colEnd; col++) {

                // Connects to the tile above
                if(row != 0 && tiles[row][col].getOpening(Direction.UP) && tiles[row - 1][col].getOpening(Direction.DOWN)) {
                    tiles[row][col].addAdjacentTile(tiles[row - 1][col]);
                }

                // Connects to the tile to the right
                if(col != 6 && tiles[row][col].getOpening(Direction.RIGHT) && tiles[row][col + 1].getOpening(Direction.LEFT)) {
                    tiles[row][col].addAdjacentTile(tiles[row][col + 1]);
                }

                // Connects to the tile below
                if(row != 6 && tiles[row][col].getOpening(Direction.DOWN) && tiles[row + 1][col].getOpening(Direction.UP)) {
                    tiles[row][col].addAdjacentTile(tiles[row + 1][col]);
                }

                // Connects to the tile to the left
                if(col != 0 && tiles[row][col].getOpening(Direction.LEFT) && tiles[row][col - 1].getOpening(Direction.RIGHT)) {
                    tiles[row][col].addAdjacentTile(tiles[row][col - 1]);
                }
            }
        }
    }

    /* Randomizes initial board tiles and treasures */
    private void setupTiles() {
        final int MAX_SHIFTABLE_TREASURE = 6;
        final int STATIONARY_TILE_AMOUNT = 12;

        // Initial tile data generation
        ArrayList<TileTypeAndTreasureNum> shiftableTilesData = new ArrayList<>();
        int shiftableTreasureCounter = STATIONARY_TILE_AMOUNT;

        for(int i = 0; i < TauTile.TILE_AMOUNT; i++) {
            shiftableTilesData.add(new TileTypeAndTreasureNum(TileType.TAU, shiftableTreasureCounter));
            shiftableTreasureCounter++;
        }

        for(int i = 0; i < BentTile.TILE_AMOUNT; i++) {
            shiftableTilesData.add(new TileTypeAndTreasureNum(TileType.BENT, (i < MAX_SHIFTABLE_TREASURE ? shiftableTreasureCounter : -1)));
            shiftableTreasureCounter++;
        }

        for(int i = 0; i < StraightTile.TILE_AMOUNT; i++) {
            shiftableTilesData.add(new TileTypeAndTreasureNum(TileType.STRAIGHT, -1));
        }

        // Randomize shiftable tiles data
        Collections.shuffle(shiftableTilesData, new Random());

        // Setup board tiles
        int stationaryTreasureCounter = 0;
        int dataCounter = 0;

        for(int row = 0; row < tiles.length; row++) {
            for(int col = 0; col < tiles[row].length; col++) {
                char tileType = STATIONARY_TILES_TYPE_MATRIX[row][col];

                if(tileType != ' ') {
                    // Set-up preset / stationary tiles
                    if(tileType == 'L') {
                        tiles[row][col] = generateTile(row, col, TileType.BENT, -1, true);
                    } else {
                        tiles[row][col] = generateTile(row, col, TileType.TAU, stationaryTreasureCounter, true);
                        stationaryTreasureCounter++;
                    }
                } else {
                    // Setup movable tiles
                    TileTypeAndTreasureNum currentTileData = shiftableTilesData.get(dataCounter);
                    tiles[row][col] = generateTile(row, col, currentTileData.getType(), currentTileData.getTreasureNum(),false);
                    dataCounter++;
                }
            }
        }

        // Setup extraTile
        TileTypeAndTreasureNum extraTileData = shiftableTilesData.get(dataCounter);

        // create extra tile object
        insertableTile = generateTile(-1, -1, extraTileData.getType(), extraTileData.getTreasureNum(), false);
        insertableTile.setInsertable(true);
    }

    /* Generates the appropriate tile object given */
    private Tile generateTile(int row, int col, TileType tileType, int treasureNum, boolean isStationary) {
        Random rand = new Random();

        // Generate orientation
        int orientationValue = isStationary ?
                (STATIONARY_TILES_ORIENTATION_MATRIX[row][col] - '0') :
                (rand.nextInt(4));
        Direction orientation = Direction.from(orientationValue);

        // Generate new tile
        Tile newTile = Tile.create(row, col, orientation, tileType);

        // Add treasure
        if(treasureNum != -1) {
            newTile.setTreasure(treasures[treasureNum]);
        }

        return newTile;
    }

    /* Removes all adjacent tiles for each tile within certain rows and columns */
    private void disconnectTiles(int rowStart, int rowEnd, int colStart, int colEnd) {
        // Limit the bounds
        rowStart = Math.max(rowStart, 0);
        rowEnd = Math.min(rowEnd, tiles.length - 1);
        colStart = Math.max(colStart, 0);
        colEnd = Math.min(colEnd, tiles.length - 1);

        for(int row = rowStart; row <= rowEnd; row++) {
            for(int col = colStart; col <= colEnd; col++) {
                tiles[row][col].removeAdjacentTiles();
            }
        }
    }

    public Tile getInsertableTile() {
        return this.insertableTile;
    }
}
