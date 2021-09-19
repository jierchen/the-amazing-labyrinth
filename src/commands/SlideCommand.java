package commands;

import model.Board;

import java.util.ArrayList;
import java.util.List;

public class SlideCommand implements Command {

    // The only rows and columns that are shiftable
    private static final ArrayList<Integer> SHIFTABLE_LINES = new ArrayList<Integer>(List.of(1, 3, 5));

    private Board board;
    private int direction = -1;
    private int line = -1;

    /**
     * Constructor
     *
     * @param board board model
     */
    public SlideCommand(Board board) {
        this.board = board;
    }

    /**
     * Shifts the board for the appropriate direction and line
     */
    @Override
    public void execute() {
        switch (direction) {
            case 0 :
                board.shiftColUp(line);
                break;

            case 1:
                board.shiftRowRight(line);
                break;

            case 2:
                board.shiftColDown(line);
                break;

            case 3:
                board.shiftRowLeft(line);
                break;

            default:
        }
    }

    /**
     * Determines if the board line is shiftable
     *
     * @return if slider is usable
     */
    @Override
    public boolean isLegal() {
        return SHIFTABLE_LINES.contains(line);
    }

    // Getters and Setters
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }
}