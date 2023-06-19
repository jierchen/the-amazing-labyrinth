package commands;

import enums.Direction;
import model.Board;
import model.Game;

import java.util.ArrayList;
import java.util.List;

public class SlideCommand implements Command {

    private static final ArrayList<Integer> SHIFTABLE_LINES = new ArrayList<>(List.of(1, 3, 5));

    private Game game;
    private final Direction direction;
    private final int line;

    public SlideCommand(Game game, Direction direction, int line) {
        this.game = game;
        this.direction = direction;
        this.line = line;
    }

    @Override
    public boolean isLegal() {
        return SHIFTABLE_LINES.contains(line);
    }

    @Override
    public void execute() {
        Board board = game.getBoard();

        switch (direction) {
            case UP:
                board.shiftColUp(line);
                break;

            case RIGHT:
                board.shiftRowRight(line);
                break;

            case DOWN:
                board.shiftColDown(line);
                break;

            case LEFT:
                board.shiftRowLeft(line);
                break;
        }
    }
}
