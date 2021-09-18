package model.tiles;

import model.Piece;
import model.Player;
import model.Treasure;

import java.util.ArrayList;

public abstract class Tile extends Piece {

    // Orientation of the tile
    // 0 - 0 degree
    // 1 - 90 degrees
    // 2 - 180 degrees
    // 3 - 270 degrees
    private int orientation;

    // Openings of the tile
    // 0 - Up
    // 1 - Right
    // 2 - Down
    // 3 - Left
    protected boolean[] openings = new boolean[4];

    // Determines if tile is the insertable tile
    private boolean insertable = false;

    private Treasure treasure = null;
    private ArrayList<Tile> adjTiles = new ArrayList<Tile>();
    private ArrayList<Player> playersOnTile = new ArrayList<Player>();

    // Tile type
    // - I
    // - L
    // - T
    private char type;

    /**
     * Constructor
     *
     * @param row row position on the board
     * @param col column position on the board
     * @param orientation orientation of the tile
     */
    public Tile(int row, int col, int orientation) {
        super(row, col);
        this.orientation = orientation;
    }

    /**
     * Removes current treasure on tile
     */
    public void removeTreasure() {

    }

    /**
     * Determines if this tile contains a treasure
     *
     * @return if the treasure is not null
     */
    public boolean hasTreasure() {

    }

    /**
     * Connects a tile to {@code adjTiles}
     *
     * @param tile model.Tile to be connected
     */
    public void addAdjTile(Tile tile) {

    }

    /**
     * Removes all connected tiles in {@code adjTiles}
     */
    public void removeAdjTiles() {

    }

    /**
     * Adds a player to list of players on the tile
     *
     * @param player model.Player to be added
     */
    public void addPlayerOnTile(Player player) {

    }

    /**
     * Removes a player from list of players on the tile
     *
     * @param player model.Player to be removed
     */
    public void removePlayerOnTile(Player player) {

    }

    /**
     * Clears all players from the list of players on the tile
     */
    public void removeAllPlayersOnTile() {
        playersOnTile.clear();
    }

    /**
     * Rotates current tile by 90 degrees (increment orientation by 1)
     * and returns orientation to 0 when rotating at 270 degrees
     */
    public void rotate() {

    }

    /**
     * Sets tile openings based on tile type and orientation
     */
    public abstract void updateOpenings();

    // Setters and getters
    public ArrayList<Tile> getAdjTiles() {
        return adjTiles;
    }

    public ArrayList<Player> getPlayersOnTile() {
        return this.playersOnTile;
    }

    @Override
    public void setRow(int row) {
        super.setRow(row);
    }
    @Override
    public void setCol(int col) {
        super.setCol(col);
    }

    public boolean[] getOpenings() {
        return this.openings;
    }

    public boolean getOpening(int dir){
        return(openings[dir]);
    }

    public void setTreasure(Treasure treasure) {
        this.treasure = treasure;
    }

    public Treasure getTreasure() {
        return this.treasure;
    }

    public void setType(char type) {
        this.type = type;
    }

    public char getType() {
        return this.type;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setInsertable(boolean extra) {
        this.insertable = extra;
    }

    public boolean isInsertable() {
        return this.insertable;
    }

}
