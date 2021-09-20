package ai;

import algorithm.BreadthFirstSearch;
import commands.MoveCommand;
import model.Board;
import model.Player;
import model.tiles.Tile;

public abstract class ComputerAgent {

    protected final double MAX_PRIORITY = 100;
    protected final double MIN_PRIORITY = -100;

    private Board board;

    // AI's target tile location for treasure
    private int targetRow;
    private int targetCol;

    // AI's selected moves
    private int selectedSlider = 0;
    private int selectedRow;
    private int selectedCol;

    // AI's priority scale
    private double[][] weightMatrix = new double[7][7];

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

    public double[][] getWeightMatrix() {
        return weightMatrix;
    }

    public void setWeightMatrix(double[][] weightMatrix) {
        this.weightMatrix = weightMatrix;
    }

}
