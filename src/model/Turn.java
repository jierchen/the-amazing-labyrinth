package model;

import enums.Action;
import enums.PlayerType;

public class Turn {

    private PlayerType currentPlayer;
    private Action action = Action.INSERT;

    public Turn(PlayerType playerType) {
        this.currentPlayer = playerType;
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

    public void moved() {
        action = Action.INSERT;
    }

    public Action getAction() {
        return action;
    }

    public PlayerType getCurrentPlayerType() {
        return currentPlayer;
    }
}
