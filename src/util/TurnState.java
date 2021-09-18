package util;

/**
 * Enscapulation class to hold current turn data to enforce rules
 */
public class TurnState {
    private int playerTurn;
    private boolean insertedTile;
    private boolean moved;
    private boolean aiTurn;
    private boolean gameEnded = false;

    public TurnState(int playerTurn, boolean insertedTile, boolean moved, boolean aiTurn) {
        this.playerTurn = playerTurn;
        this.insertedTile = insertedTile;
        this.moved = moved;
        this.aiTurn = aiTurn;
    }

    // Getters and Setters
    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }
    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setInsertedTile(boolean insertedTile) {
        this.insertedTile = insertedTile;
    }
    public boolean hasInsertedTile() {
        return insertedTile;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
    public boolean hasMoved() {
        return moved;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
    public boolean hasGameEnded() {
        return gameEnded;
    }
}
