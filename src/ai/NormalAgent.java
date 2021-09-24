package ai;

import algorithm.BreadthFirstSearch;
import commands.MoveCommand;
import model.Board;
import model.Player;
import model.tiles.Tile;

import java.util.ArrayList;
import java.util.Random;

public class NormalAgent extends ComputerAgent {

    /**
     * Constructor
     *
     * @param board original game board
     * @param player original players
     */
    public NormalAgent(Board board, Player player) {
        super(board, player);
    }

    @Override
    public void evaluateChoices() {
        System.out.println("Evaluating Choices");
        final int NUM_OF_ORIENTATIONS = 4;
        final int NUM_OF_SLIDERS = 12;
        final int NUM_OF_TILES_PER_SIDE = 7;

        double highestWeight = MIN_PRIORITY;

        // All possible insertable tile rotations
        rotateLoop: for(int rotateAmount = 0; rotateAmount < NUM_OF_ORIENTATIONS; rotateAmount++) {
            System.out.println(getPlayer().getColour());

            // All possibles tile inserts
            sliderLoop: for(int sliderNumber = 0; sliderNumber < NUM_OF_SLIDERS; sliderNumber++) {

                // Create clone to simulate moves
                Board cloneBoard = getBoard().clone();
                Tile[][] cloneTiles = cloneBoard.getTiles();

                for(int i = 0; i < rotateAmount; i++) {
                    cloneBoard.getInsertableTile().rotate();
                }

                cloneBoard.shiftBoard(sliderNumber);

                // Get objective tile
                calculateTarget(cloneTiles, cloneBoard.getPlayerByColor(getPlayer().getColour()));
                System.out.println("Slider Number:" + sliderNumber);
                System.out.println(getTargetRow());
                System.out.println(getTargetCol());
                if(getTargetCol() == -1 && getTargetCol() == -1) {
                    continue sliderLoop;
                }

                // Move encapsulation
                MoveCommand aiMoveCommand = new MoveCommand(cloneBoard, new BreadthFirstSearch());
                aiMoveCommand.setPlayer(cloneBoard.getPlayerByColor(getPlayer().getColour()));

                // All possible row moves
                rowLoop: for(int row = 0; row < NUM_OF_TILES_PER_SIDE; row++) {
                    aiMoveCommand.setTargetRow(row);

                    // All possible column moves
                    colLoop: for(int col = 0; col < NUM_OF_TILES_PER_SIDE; col++) {
                        aiMoveCommand.setTargetCol(col);

                        // Analyze move
                        double weight;
                        if(aiMoveCommand.isLegal()) {
                            System.out.println("Legal move: " + row + " " + col);
                            weight = calcRelativeWeight(row, col);
                        } else {
                            continue colLoop;
                        }

                        // Choose the highest weight move
                        if(weight > highestWeight) {
                            setSelectedRotateOrientation(cloneBoard.getInsertableTile().getOrientation());
                            setSelectedSlider(sliderNumber);
                            setSelectedRow(row);
                            setSelectedCol(col);

                            if(weight == MAX_PRIORITY) {
                                break rotateLoop;
                            }
                        }
                    }
                }
            }
        }

        System.out.println();
        System.out.println("Decisions: ");
        System.out.println(getSelectedRotateOrientation());
        System.out.println(getSelectedSlider());
        System.out.println(getSelectedRow());
        System.out.println(getSelectedCol());
    }
}
