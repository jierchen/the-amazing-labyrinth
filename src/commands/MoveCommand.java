package commands;

import helper.GraphSearch;
import model.Board;
import model.Game;
import model.Player;

import java.util.logging.Logger;

public class MoveCommand implements Command {

    private static final Logger logger = Logger.getLogger(MoveCommand.class.getName());

    private Game game;
    private Player player;
    private final int targetRow;
    private final int targetCol;
    private GraphSearch graphSearch;

    public MoveCommand(Game game, GraphSearch graphSearch, int targetRow, int targetCol) {
        this.game = game;
        this.graphSearch = graphSearch;
        this.targetRow = targetRow;
        this.targetCol = targetCol;
        this.player = game.getCurrentPlayer();
    }

    @Override
    public boolean isLegal() {
        return graphSearch.reachable(player.getRow(), player.getCol(), targetRow, targetCol);
    }

    @Override
    public void execute() {
        game.getBoard().movePlayer(player, targetRow, targetCol);

        logger.info(String.format("Player %s moved from (%d, %d) to (%d, %d)",
                player.getType(), player.getRow(), player.getCol(), targetRow, targetCol));
    }
}
