package model;

import model.tiles.Tile;

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
     */
    public void init() {

    }

    /**
     * Initializes the board tiles properties
     */
    private void setupTiles() {

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

    }

    /**
     * Adds players to the tiles they're on
     */
    private void connectPlayersToTiles() {

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
