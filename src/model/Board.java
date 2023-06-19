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

        for(int tauIndex = 0; tauIndex < TauTile.TILE_AMOUNT; tauIndex++) {
            shiftableTilesData.add(new TileTypeAndTreasureNum(TileType.TAU, shiftableTreasureCounter));
            shiftableTreasureCounter++;
        }

        for(int bentIndex = 0; bentIndex < BentTile.TILE_AMOUNT; bentIndex++) {
            shiftableTilesData.add(new TileTypeAndTreasureNum(TileType.BENT, (bentIndex < MAX_SHIFTABLE_TREASURE ? shiftableTreasureCounter : -1)));
            shiftableTreasureCounter++;
        }

        for(int straightIndex = 0; straightIndex < StraightTile.TILE_AMOUNT; straightIndex++) {
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

    /* Re-adds any players pushed off the board tiles to the other side */
    private void reAddPlayers(int row, int col) {
        for(Player player: players) {
            if(player.getRow() == -1 && player.getCol() == -1) {
                player.setRow(row);
                player.setCol(col);
                tiles[row][col].addPlayerOnTile(player);
            }
        }
    }

    /**
     * Moves a player to a position.
     * @param player {@code Player} to move
     * @param row Destination row
     * @param col Destination column
     */
    public void movePlayer(Player player, int row, int col) {
        // Remove player from its current tile
        tiles[player.getRow()][player.getCol()].removePlayerOnTile(player);

        // Move to new tile
        player.setRow(row);
        player.setCol(col);
        tiles[row][col].addPlayerOnTile(player);
    }

    /*
     * Shifts a row of tiles to the left
     * - Inserts the insertable tile from the right
     * - The pushed out tile from the left becomes the new insertable tile
     */
    public void shiftRowLeft(int row) {
        // Hold new extra tile
        Tile newExtraTile = tiles[row][0];
        becomeInsertableTile(newExtraTile);

        // Shift row
        for (int col = 0; col < tiles[row].length - 1; col++) {
            tiles[row][col] = tiles[row][col + 1];
            tiles[row][col].setCol(col);
        }

        // Insert previous extra tile to the end of row
        becomeBoardTile(row, tiles.length - 1);

        // Reconnect tile nodes
        disconnectTiles(row - 1, row + 1, 0, tiles.length - 1);
        connectTiles(row - 1, row + 1, 0, tiles.length - 1);

        reAddPlayers(row, tiles.length - 1);
        insertableTile = newExtraTile;
    }

    /*
     * Shifts a row of tiles to the right
     * - Inserts the insertable tile from the left
     * - The pushed out tile from the right becomes the new insertable tile
     */
    public void shiftRowRight(int row) {
        // Hold new extra tile
        Tile newExtraTile = tiles[row][tiles.length - 1];
        becomeInsertableTile(newExtraTile);

        // Shift row
        for (int col = tiles[row].length - 1; col > 0; col--) {
            tiles[row][col] = tiles[row][col - 1];
            tiles[row][col].setCol(col);
        }

        // Insert previous extra tile to the start of row
        becomeBoardTile(row, 0);

        // Reconnect tile nodes
        disconnectTiles(row - 1, row + 1, 0, tiles.length - 1);
        connectTiles(row - 1, row + 1, 0, tiles.length - 1);

        reAddPlayers(row, 0);
        insertableTile = newExtraTile;
    }

    /*
     * Shifts a column of tiles down
     * - Inserts the insertable tile from the top
     * - The pushed out tile from the bottom becomes the new insertable tile
     */
    public void shiftColDown(int col) {
        // Hold new extra tile
        Tile newExtraTile = tiles[tiles.length - 1][col];
        becomeInsertableTile(newExtraTile);

        // Shift column
        for (int row = tiles.length - 1; row > 0; row--) {
            tiles[row][col] = tiles[row - 1][col];
            tiles[row][col].setRow(row);
        }

        // Insert previous extra tile to the start of column
        becomeBoardTile(0, col);

        // Reconnect tile nodes
        disconnectTiles(0, tiles.length - 1, col - 1, col + 1);
        connectTiles(0, tiles.length - 1, col - 1, col + 1);

        reAddPlayers(0, col);
        insertableTile = newExtraTile;
    }

    /*
     * Shifts a column of tiles up
     * - Inserts the insertable tile from the bottom
     * - The pushed out tile from the top becomes the new insertable tile
     */
    public void shiftColUp(int col) {
        // Hold new extra tile
        Tile newExtraTile = tiles[0][col];
        becomeInsertableTile(newExtraTile);

        // Shift column
        for (int row = 0; row < tiles.length - 1; row++) {
            tiles[row][col] = tiles[row + 1][col];
            tiles[row][col].setRow(row);
        }

        // Insert previous extra tile to the end of column
        becomeBoardTile(tiles.length - 1, col);

        // Reconnect tile nodes
        disconnectTiles(0, tiles.length - 1, col - 1, col + 1);
        connectTiles(0, tiles.length - 1, col - 1, col + 1);

        reAddPlayers(tiles.length - 1, col);
        insertableTile = newExtraTile;
    }

    /* Converts a board tile to become the insertable tile */
    private void becomeInsertableTile(Tile tile) {
        tile.setRow(-1);
        tile.setCol(-1);
        tile.setInsertable(true);
        tile.removeAllPlayersOnTile();
        tile.removeAdjacentTiles();
    }

    /* Converts the extra tile to a board tile */
    private void becomeBoardTile(int row, int col) {
        tiles[row][col] = insertableTile;
        tiles[row][col].setRow(row);
        tiles[row][col].setCol(col);
        tiles[row][col].setInsertable(false);
    }

    public Tile getInsertableTile() {
        return this.insertableTile;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
