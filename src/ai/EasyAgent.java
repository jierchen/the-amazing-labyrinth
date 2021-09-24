package ai;

import algorithm.BreadthFirstSearch;
import commands.MoveCommand;
import model.Board;
import model.Player;
import model.tiles.Tile;

import java.util.Random;

public class EasyAgent extends ComputerAgent {

    private int previousRow = -1;
    private int previousCol = -1;

    // EasyAI's priority scale
    private double[][] weightMatrix = new double[7][7];

    /**
     * Constructor
     *
     * @param board original game board
     * @param player original players
     */
    public EasyAgent(Board board, Player player) {
        super(board, player);
    }

    @Override
    public void evaluateChoices() {
        rotateSimulation();
        slideSimulation();
        moveSimulation();
    }

    /**
     * Calculate the best tile to move to
     */
    private void moveSimulation() {
        Board cloneBoard = getBoard().clone();

        cloneBoard.shiftBoard(getSelectedSlider());

        Tile[][] boardTiles = cloneBoard.getTiles();

        // Get objective tile
        calculateTarget(boardTiles, cloneBoard.getPlayerByColor(getPlayer().getColour()));

        MoveCommand aiMoveCommand = new MoveCommand(cloneBoard, new BreadthFirstSearch());
        aiMoveCommand.setPlayer(cloneBoard.getPlayerByColor(getPlayer().getColour()));

        double highestWeight = MIN_PRIORITY;

        for(int row = 0; row < Board.NUM_OF_TILES_PER_SIDE; row++) {
            aiMoveCommand.setTargetRow(row);

            for(int col = 0; col < Board.NUM_OF_TILES_PER_SIDE; col++) {
                aiMoveCommand.setTargetCol(col);

                // Initialize priority to 0
                getWeightMatrix()[row][col] = 0;

                // Get priority of each tile
                if(aiMoveCommand.isLegal()) {
                    if(row == getTargetRow() && col == getTargetCol()) {
                        getWeightMatrix()[row][col] = MAX_PRIORITY;
                    } else {
                        getWeightMatrix()[row][col] = calcRelativeWeight(row, col);
                    }
                } else {
                    getWeightMatrix()[row][col] = Double.NEGATIVE_INFINITY;
                }

                // Reduce priority to return to old location
                if(previousRow == row && previousCol == col) {
                    getWeightMatrix()[row][col] -= 15;
                }

                // Set highest priority square to move to
                if(getWeightMatrix()[row][col] > highestWeight) {
                    highestWeight = getWeightMatrix()[row][col];

                    setSelectedRow(row);
                    setSelectedCol(col);
                }
            }
        }

        previousRow = getSelectedRow();
        previousCol = getSelectedCol();
    }

    /**
     * Select random slider to use
     */
    private void slideSimulation() {
        Random rand = new Random();

        this.setSelectedSlider(rand.nextInt(12));
    }

    /**
     * Do not rotate insertable tile
     */
    private void rotateSimulation() {
        setSelectedRotateOrientation(getBoard().getInsertableTile().getOrientation());
    }



    // Getters and Setters
    public double[][] getWeightMatrix() {
        return weightMatrix;
    }

    public void setWeightMatrix(double[][] weightMatrix) {
        this.weightMatrix = weightMatrix;
    }
}
