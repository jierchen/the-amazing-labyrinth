package commands;

import model.Board;
import model.Player;
import model.tiles.Tile;

public class MoveCommand implements Command {

    public Board board;
    public Tile[][] tiles;
    public Player player;
    public int targetRow;
    public int targetCol;

    public MoveCommand(Board board) {
        this.board = board;
        this.tiles = board.getTiles();
    }

    @Override
    public void execute() {
        board.movePlayer(player, targetRow, targetCol);
    }

    /**
     * Determine if player can move to target location by using graph search
     * to find target tile
     *
     * @return if tile can be reached
     */
    @Override
    public boolean isLegal() {
        return false;
    }

    // Getters and Setters
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
}
