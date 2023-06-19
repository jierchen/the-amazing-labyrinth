package commands;

import enums.Direction;
import model.Board;
import model.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SlideCommand implements Command {

    private static final Logger logger = Logger.getLogger(SlideCommand.class.getName());

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
                logger.info(String.format("Shifted column %d %s", line, Direction.UP));
                break;

            case RIGHT:
                board.shiftRowRight(line);
                logger.info(String.format("Shifted row %d %s", line, Direction.RIGHT));
                break;

            case DOWN:
                board.shiftColDown(line);
                logger.info(String.format("Shifted column %d %s", line, Direction.DOWN));
                break;

            case LEFT:
                board.shiftRowLeft(line);
                logger.info(String.format("Shifted row %d %s", line, Direction.LEFT));
                break;
        }
    }
}
