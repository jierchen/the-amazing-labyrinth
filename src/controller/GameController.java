package controller;

import ai.ComputerAgent;
import model.Board;
import model.Game;
import model.Player;
import util.TurnState;
import view.BoardDisplay;
import view.GameDisplay;
import view.TileDisplay;
import view.TileSlider;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {

    // Views and models
    private GameDisplay gameDisplay;
    private Game game;

    private BoardDisplay boardDisplay;
    private Board board;

    // Game data
    private Player[] players;
    private boolean[] isAI;
    private TurnState turnState;

    // Timer to start AI turn
    private Timer aiTimer = new Timer(2000, new AgentListener());

    /**
     * Controller
     *
     * @param turnState turn information
     * @param players players of the game
     * @param isAI which players are AIs
     * @param gameDisplay game view
     * @param game game model
     */
    public GameController(TurnState turnState, Player[] players, boolean[] isAI, GameDisplay gameDisplay, Game game) {
        this.turnState = turnState;
        this.players = players;
        this.isAI = isAI;
        this.gameDisplay = gameDisplay;
        this.game = game;

        boardDisplay = gameDisplay.getBoardDisplay();
        board = game.getBoard();

        aiTimer.setRepeats(false);

        init();
    }

    /**
     * Connects the GUI to the ActionListeners
     */
    public void init() {
        // Add slider listeners
        gameDisplay.addSlideListeners(new SlideListener());

        // Add rotate listener to extraTileDisplay
        gameDisplay.getInsertableTileDisplay().addRotateListener(new RotateListener());

        // Add move listener to tileDisplays
        boardDisplay.addMoveListeners(new MoveListener());

        if(isAI[turnState.getPlayerTurn()]) {
            turnState.setAiTurn(true);
            aiTimer.start();
        }
    }

    /**
     * Set game to ended in turn state
     */
    public void endGame() {
        turnState.setGameEnded(true);
    }

    /**
     * Prepares turn state for next turn
     */
    public void nextTurn() {

        if(checkGameHasWinner()) {
            endGame();
        }

        turnState.setPlayerTurn((turnState.getPlayerTurn() + 1) % 4);
        turnState.setInsertedTile(false);
        turnState.setMoved(false);

        // Update view
        gameDisplay.updateVisualTurn();

        if(isAI[turnState.getPlayerTurn()]) {
            turnState.setAiTurn(true);
            aiTimer.start();
        }
    }

    /**
     * Check if a player won the game by collecting
     * all their treasures and return home to base
     *
     * @return if a player has won the game
     */
    private boolean checkGameHasWinner() {
        for(Player player: players) {
            if(player.hasReturnedHome()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Timer input for AI to handle its turn
     */
    public class AgentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!turnState.hasGameEnded() && turnState.isAiTurn()) {

                ComputerAgent computerAgent = players[turnState.getPlayerTurn()].getComputerAgent();

                if(computerAgent != null) {
                    computerAgent.evaluateChoices();

                    while(game.getInsertableTile().getOrientation() != computerAgent.getSelectedRotateOrientation()) {
                        gameDisplay.getInsertableTileDisplay().getTileImage().doClick(100);
                    }
                    System.out.println("Rotated");

                    gameDisplay.getTileSliders()[computerAgent.getSelectedSlider()].doClick();

                    System.out.println("Inserted");

                    boardDisplay.getTileDisplays()[computerAgent.getSelectedRow()][computerAgent.getSelectedCol()]
                            .getTileImage().doClick();

                    System.out.println("Moved");
                }
            }
        }
    }

    /**
     * User input to slide the insertable tile and shift the board
     */
    public class SlideListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!turnState.hasGameEnded()) {
                if(!turnState.hasInsertedTile() && !turnState.hasMoved()) {
                    TileSlider slider = (TileSlider) e.getSource();

                    // Update model
                    game.slideInsertableTileAction(slider.getDirection(), slider.getLineResponsible());

                    // Update view
                    gameDisplay.update();
                }
            }
        }
    }

    /**
     * User input to rotate the insertable tile
     */
    public class RotateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!turnState.hasGameEnded()) {
                if(!turnState.hasInsertedTile() ) {
                    // Rotate extra tile
                    board.getInsertableTile().rotate();

                    // Update insertable tile view
                    gameDisplay.getInsertableTileDisplay().update(board.getInsertableTile());
                }
            }
        }
    }

    /**
     * User input to move the player on the board
     */
    public class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!turnState.hasGameEnded()) {
                if (turnState.hasInsertedTile()) {
                    TileDisplay[][] tileDisplays = boardDisplay.getTileDisplays();

                    // Scroll through all Tile JButtons
                    for (int row = 0; row < tileDisplays.length; row++) {
                        for (int col = 0; col < tileDisplays.length; col++) {
                            if (e.getSource() == tileDisplays[row][col].getTileImage()) {
                                game.movePlayerAction(row, col);
                            }
                        }
                    }

                    // Update view
                    gameDisplay.update();

                }

                // Go to next turn if successful move
                if (turnState.hasMoved()) {
                    nextTurn();
                }
            }
        }
    }
}
