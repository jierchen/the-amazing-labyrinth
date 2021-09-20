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
        slideSimulation();
        moveSimulation();
    }

    private void moveSimulation() {
        Tile[][] boardTiles = getBoard().getTiles();

        // Get objective tile
        calculateTarget(boardTiles);

        MoveCommand aiMoveCommand = new MoveCommand(getBoard(), new BreadthFirstSearch());
        aiMoveCommand.setPlayer(getPlayer());

        double highestWeight = -100;

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
                    getWeightMatrix()[row][col] = MIN_PRIORITY;
                }

                // Reduce priority to return to old location
                if(previousRow == row && previousCol == col) {
                    getWeightMatrix()[row][col] -= 15;
                }

                // Set highest prority square to move to
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

    private void calculateTarget(Tile[][] boardTiles) {
        outerloop: for(int row = 0; row < 7; row++) {
            for(int col = 0; col < 7; col++) {
                if(getPlayer().getTopOfHand() != null
                        && boardTiles[row][col].hasTreasure()
                        && getPlayer().getTopOfHand().getTreasure() == boardTiles[row][col].getTreasure()) {
                    setTargetRow(row);
                    setTargetCol(col);
                    break outerloop;
                } else if (getPlayer().hasCollectedAll() && boardTiles[row][col] == getPlayer().getHomeTile()) {
                    setTargetRow(row);
                    setTargetCol(col);
                    break outerloop;
                }
            }
        }
    }

    private double calcRelativeWeight(int row, int col) {
        double distance = Math.sqrt(Math.pow(row - getTargetRow(), 2)
                            + Math.pow(col - getTargetCol(), 2));

        return MAX_PRIORITY - Math.pow(distance, 2);
    }

    /**
     * Select random slider
     */
    private void slideSimulation() {
        Random rand = new Random();

        this.setSelectedSlider(rand.nextInt(12));
    }
}
