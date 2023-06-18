package model;

import enums.Action;
import enums.PlayerType;

public class Turn {

    private PlayerType currentPlayer;
    private Action action;

    public Turn(PlayerType playerType) {
        this.currentPlayer = playerType;
    }

    public PlayerType getPlayerType() {
        return currentPlayer;
    }

    public void nextPlayer() {
        switch(currentPlayer) {
            case PURPLE:
                currentPlayer = PlayerType.BLUE;
                break;
            case BLUE:
                currentPlayer = PlayerType.RED;
                break;
            case RED:
                currentPlayer = PlayerType.GREEN;
                break;
            default:
                currentPlayer = PlayerType.PURPLE;
        }

        action = Action.INSERT;
    }

    public void inserted() {
        action = Action.MOVE;
    }
}
