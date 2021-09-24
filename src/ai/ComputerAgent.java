package ai;

import algorithm.BreadthFirstSearch;
import commands.MoveCommand;
import model.Board;
import model.Player;
import model.tiles.Tile;

import java.util.ArrayList;

public abstract class ComputerAgent {

    protected final double MAX_PRIORITY = 100;
    protected final double MIN_PRIORITY = -100;

    private Board board;

    // AI's target tile location for treasure
    private int targetRow = -1;
    private int targetCol = -1;

    // AI's selected moves
    private int selectedSlider = 0;
    private int selectedRow;
    private int selectedCol;
    private int selectedRotateOrientation = 0;

    private Player player;

    /**
     * Constructor
     *
     * @param board original game board
     */
    public ComputerAgent(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    public abstract void evaluateChoices();

    /**
     * Calculate the target
     *
     * @param cloneTiles original board tiles
     */
    protected void calculateTarget(Tile[][] cloneTiles, Player clonePlayer) {
        outerloop: for(int row = 0; row < 7; row++) {
            for(int col = 0; col < 7; col++) {
                if(clonePlayer.getTopOfHand() != null
                        && cloneTiles[row][col].hasTreasure()
                        && clonePlayer.getTopOfHand().getTreasure().getTreasureNum()
                        == cloneTiles[row][col].getTreasure().getTreasureNum()) {
                    setTargetRow(row);
                    setTargetCol(col);
                    break outerloop;
                } else if (clonePlayer.hasCollectedAll()
                        && row == clonePlayer.getHomeRow()
                        && col == clonePlayer.getHomeCol()) {
                    setTargetRow(row);
                    setTargetCol(col);
                    break outerloop;
                }
            }
        }
    }

    /**
     * Calculates the priority weighting of a viable tile based on relative
     * distance to target tile row and column
     *
     * @param row row of the viable tile
     * @param col column of the viable tile
     * @return the weighting of the viable tile to move to
     */
    protected double calcRelativeWeight(int row, int col) {
        double distance = Math.sqrt(Math.pow(row - getTargetRow(), 2)
                + Math.pow(col - getTargetCol(), 2));

        return MAX_PRIORITY - Math.pow(distance, 2);
    }

    // Getters and Setters
    public int getSelectedSlider() {
        return selectedSlider;
    }

    public void setSelectedSlider(int selectedSlider) {
        this.selectedSlider = selectedSlider;
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(int selectedRow) {
        this.selectedRow = selectedRow;
    }

    public int getSelectedCol() {
        return selectedCol;
    }

    public void setSelectedCol(int selectedCol) {
        this.selectedCol = selectedCol;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public void setTargetRow(int targetRow) {
        this.targetRow = targetRow;
    }

    public int getTargetCol() {
        return targetCol;
    }

    public void setTargetCol(int targetCol) {
        this.targetCol = targetCol;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getSelectedRotateOrientation() {
        return selectedRotateOrientation;
    }

    public void setSelectedRotateOrientation(int selectedRotateOrientation) {
        this.selectedRotateOrientation = selectedRotateOrientation;
    }
}
