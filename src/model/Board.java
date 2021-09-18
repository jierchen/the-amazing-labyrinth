package model;

import model.tiles.ITile;
import model.tiles.LTile;
import model.tiles.TTile;
import model.tiles.Tile;

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
     * Helper class to encapsulate tile {@code type} and {@code treasureNum} pair
     */
    private static class TypeAndTreasureNum {

        private char type;
        private int treasureNum;

        public TypeAndTreasureNum (char type, int treasureNum) {
            this.type = type;
            this.treasureNum = treasureNum;
        }

        // getters
        public char getType() {
            return this.type;
        }

        public int getTreasureNum() {
            return this.treasureNum;
        }

        public String toString(){
            return String.valueOf(type);
        }
    }

    // Board tiles
    // Order is left to right -> top to bottom
    private Tile[][] tiles = new Tile[NUM_OF_TILES_PER_SIDE][NUM_OF_TILES_PER_SIDE];

    // Player pieces
    private Player[] players;

    // Treasures
    private Treasure[] treasures;

    /**
     * Constructor
     *
     * @param players players of the game
     * @param treasures treasures of the game
     */
    public Board(Player[] players, Treasure[] treasures) {

    }

    /**
     * Initializes the board by setting up the board tiles
     *
     * @return the leftover insertable tile
     */
    public Tile init() {

        Tile insertableTile = setupTiles();
        connectTiles(0, this.tiles.length - 1, 0, this.tiles.length - 1);
        connectPlayersToTiles();

        return insertableTile;
    }

    /**
     * Initializes the board tiles properties
     *
     * @return returns the extra, insertable board tile
     */
    private Tile setupTiles() {
        final int MAX_SHIFTABLE_TREASURE = 6;
        final int STATIONARY_TILE_AMOUNT = 12;

        // Initial tile data generation
        ArrayList<TypeAndTreasureNum> shiftableTilesData = new ArrayList<TypeAndTreasureNum>();
        int shiftableTreasureCounter = STATIONARY_TILE_AMOUNT;

        for(int i = 0; i < TTile.TILE_AMOUNT; i++) {
            shiftableTilesData.add(new TypeAndTreasureNum('T', (i < MAX_SHIFTABLE_TREASURE ? shiftableTreasureCounter : -1)));
            shiftableTreasureCounter++;
        }

        for(int i = 0; i < LTile.TILE_AMOUNT; i++) {
            shiftableTilesData.add(new TypeAndTreasureNum('L', (i < MAX_SHIFTABLE_TREASURE ? shiftableTreasureCounter : -1)));
            shiftableTreasureCounter++;
        }

        for(int i = 0; i < ITile.TILE_AMOUNT; i++) {
            shiftableTilesData.add(new TypeAndTreasureNum('I', -1));
        }

        // Randomize shiftable tiles data
        Collections.shuffle(shiftableTilesData, new Random());
        System.out.println(shiftableTilesData);

        // Setup board tiles
        int stationaryTreasureCounter = 0;
        int dataCounter = 0;

        for(int row = 0; row < this.tiles.length; row++) {
            for(int col = 0; col < this.tiles[row].length; col++) {

                char tileType = STATIONARY_TILES_TYPE_MATRIX[row][col];

                // set-up preset/stationary tiles
                if(tileType != ' ') {

                    // Stationary L-tiles have no treasures
                    if(tileType == 'L') {
                        this.tiles[row][col] = generateTile(row, col, tileType, -1, true);
                    }
                    else {
                        this.tiles[row][col] = generateTile(row, col, tileType, stationaryTreasureCounter, true);
                        stationaryTreasureCounter++;
                    }
                }
                // setup movable tiles
                else {
                    TypeAndTreasureNum currentTileData = shiftableTilesData.get(dataCounter);

                    this.tiles[row][col] = generateTile(row, col, currentTileData.getType(), currentTileData.getTreasureNum(),false);
                    dataCounter++;
                }
            }
        }

        // Setup extraTile
        TypeAndTreasureNum extraTileData = shiftableTilesData.get(dataCounter);

        // create extra tile object
        Tile insertableTile = generateTile(-1, -1, extraTileData.getType(), extraTileData.getTreasureNum(), false);
        insertableTile.setInsertable(true);

        return insertableTile;

    }

    /**
     * Generates the appropriate tile object given
     * tile properties
     *
     * @param row row of the tile
     * @param col column of the tile
     * @param tileType type of the tile
     * @param treasureNum treasure id the tile contains
     * @param isStationary if the tile is stationary
     * @return the appropriate {@code Tile}
     */
    private Tile generateTile(int row, int col, char tileType, int treasureNum, boolean isStationary) {
        Tile newTile;
        int orientation;
        Random rand = new Random();

        // generate orientation
        if(isStationary) {
            orientation = (STATIONARY_TILES_ORIENTATION_MATRIX[row][col] - '0');
        } else {
            orientation = (rand.nextInt(4));
        }

        // generate new tile object
        if(tileType == 'T') {
            newTile = new TTile(row, col,  orientation);
        } else if (tileType == 'L') {
            newTile = new LTile(row, col,  orientation);
        } else {
            newTile = new ITile(row, col, orientation);
        }

        //add treasure
        if(treasureNum != -1) {
            newTile.setTreasure(this.treasures[treasureNum]);
        }

        return newTile;
    }

    /**
     * Connects each tile, within certain rows and columns to its neighbouring tiles
     *
     * @param rowStart beginning of row constraint
     * @param rowEnd end of row constraint
     * @param colStart beginning of column constraint
     * @param colEnd end of column constraint
     */
    private void connectTiles(int rowStart, int rowEnd, int colStart, int colEnd) {
        // Limit the bounds
        rowStart = Math.max(rowStart, 0);
        rowEnd = Math.min(rowEnd, this.tiles.length - 1);
        colStart = Math.max(colStart, 0);
        colEnd = Math.min(colEnd, this.tiles.length - 1);

        for(int row = rowStart; row <= rowEnd; row++) {
            for(int col = colStart; col <= colEnd; col++) {

                // Connects to the tile above
                if(row != 0 && this.tiles[row][col].getOpening(0) && this.tiles[row - 1][col].getOpening(2)) {
                    this.tiles[row][col].addAdjTile(this.tiles[row - 1][col]);
                }

                // Connects to the tile to the right
                if(col != 6 && this.tiles[row][col].getOpening(1) && this.tiles[row][col + 1].getOpening(3)) {
                    this.tiles[row][col].addAdjTile(this.tiles[row][col + 1]);
                }

                // Connects to the tile below
                if(row != 6 && this.tiles[row][col].getOpening(2) && this.tiles[row + 1][col].getOpening(0)) {
                    this.tiles[row][col].addAdjTile(this.tiles[row + 1][col]);
                }

                // Connects to the tile to the left
                if(col != 0 && this.tiles[row][col].getOpening(3) && this.tiles[row][col - 1].getOpening(1)) {
                    this.tiles[row][col].addAdjTile(this.tiles[row][col - 1]);
                }
            }
        }
    }

    /**
     * Adds players to the tiles they're on
     */
    private void connectPlayersToTiles() {
        for(Player player: players) {
            Tile homeTile = this.tiles[player.getRow()][player.getCol()];

            player.setHomeTile(homeTile);
            homeTile.addPlayerOnTile(player);
        }
    }

    /**
     * Removes all adjacent tiles for each tile within certain rows and columns
     *
     * @param rowStart beginning of row constraint
     * @param rowEnd end of row constraint
     * @param colStart beginning of column constraint
     * @param colEnd end of column constraint
     */
    private void disconnectTiles(int rowStart, int rowEnd, int colStart, int colEnd) {

    }

    /**
     * Re-adds any players pushed off the board tiles to the other side
     *
     * @param row opposite tile row
     * @param col opposite tile column
     */
    private void reAddPlayers(int row, int col) {

    }

    /**
     * Moves a player to a tile
     *
     * @param player the player to be moved
     * @param row the desired row to move to
     * @param col the desired column to move to
     */
    public void movePlayer(Player player, int row, int col) {

    }

    /**
     * Shifts a row of tiles to the left
     * - Inserts the insertable tile from the right
     * - The pushed out tile from the left becomes the new insertable tile
     *
     * @param row the row of tiles to shift
     */
    public void shiftRowLeft(int row) {

    }

    /**
     * Shifts a row of tiles to the right
     * - Inserts the insertable tile from the left
     * - The pushed out tile from the right becomes the new insertable tile
     *
     * @param row the row of tiles to shift
     */
    public void shiftRowRight(int row) {

    }

    /**
     * Shifts a column of tiles down
     * - Inserts the insertable tile from the top
     * - The pushed out tile from the bottom becomes the new insertable tile
     *
     * @param col the column of tiles to shift
     */
    public void shiftColDown(int col) {

    }

    /**
     * Shifts a column of tiles up
     * - Inserts the insertable tile from the bottom
     * - The pushed out tile from the top becomes the new insertable tile
     *
     * @param col the column of tiles to shift
     */
    public void shiftColUp(int col) {

    }

    /**
     * Converts a board tile to become the insertable tile
     *
     * @param tile tile to be converted
     */
    private void becomeInsertableTile(Tile tile) {

    }

    /**
     * Converts the extra tile to a board tile
     *
     * @param row row of its new location
     * @param col col of its new location
     */
    private void becomeBoardTile(int row, int col) {

    }

    // Getters and Setters
    public void setTiles(Tile[][] tiles){
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    public Treasure[] getTreasures() {
        return this.treasures;
    }
}
