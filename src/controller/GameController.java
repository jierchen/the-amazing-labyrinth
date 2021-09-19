package controller;

import model.Board;
import model.Game;
import model.Player;
import util.TurnState;
import view.BoardDisplay;
import view.GameDisplay;
import view.TileDisplay;
import view.TileSlider;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {

    private GameDisplay gameDisplay;
    private Game game;

    private Player[] players;

    private BoardDisplay boardDisplay;
    private Board board;

    private TurnState turnState;

    /**
     * Controller
     *
     *  @param turnState turn information
     * @param players
     * @param gameDisplay game view
     * @param game game model
     */
    public GameController(TurnState turnState, Player[] players, GameDisplay gameDisplay, Game game) {
        this.turnState = turnState;
        this.players = players;
        this.gameDisplay = gameDisplay;
        this.game = game;

        boardDisplay = gameDisplay.getBoardDisplay();
        board = game.getBoard();

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

        // Add move listener to tileDisplays in
        boardDisplay.addMoveListeners(new MoveListener());
    }

    public void endGame() {
        turnState.setGameEnded(true);
    }

    /**
     * Prepares turn state for next turn
     */
    public void nextTurn() {
        turnState.setPlayerTurn((turnState.getPlayerTurn() + 1) % 4);
        turnState.setInsertedTile(false);
        turnState.setMoved(false);
    }

    /**
     * User input to slide the insertable tile and shift the board
     */
    public class SlideListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!turnState.hasGameEnded() && !turnState.hasInsertedTile() && !turnState.hasMoved()) {
                TileSlider slider = (TileSlider) e.getSource();

                game.slideInsertableTileAction(slider.getDirection(), slider.getLineResponsible());

                boardDisplay.updateBoard();
                gameDisplay.getInsertableTileDisplay().update(game.getInsertableTile());
                boardDisplay.updatePlayerViews();

                // Update turnState
                turnState.setInsertedTile(true);
            }
        }
    }

    /**
     * User input to rotate the insertable tile
     */
    public class RotateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!turnState.hasGameEnded() && !turnState.hasInsertedTile()) {
                // Rotate extra tile
                board.getInsertableTile().rotate();

                // Update extraTileDisplay
                gameDisplay.getInsertableTileDisplay().update(board.getInsertableTile());

            }
        }
    }

    /**
     * User input to move the player on the board
     */
    public class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!turnState.hasGameEnded() && turnState.hasInsertedTile() && !turnState.hasMoved()) {
                TileDisplay[][] tileDisplays = boardDisplay.getTileDisplays();

                // Scroll through all Tile JButtons
                for (int row = 0; row < tileDisplays.length; row++) {
                    for (int col = 0; col < tileDisplays.length; col++) {
                        if (e.getSource() == tileDisplays[row][col].getTileImage()) {
                            game.movePlayerAction(row, col);

                            boardDisplay.updateBoard();
                            boardDisplay.updatePlayerViews();
                        }
                    }
                }

                // Go to next turn
                turnState.setMoved(true);
                nextTurn();
            }
        }
    }
}
