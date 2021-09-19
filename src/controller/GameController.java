package controller;

import model.Board;
import model.Game;
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

    private BoardDisplay boardDisplay;
    private Board board;

    private TurnState turnState;

    public GameController(TurnState turnState, GameDisplay gameDisplay, Game game) {
        this.turnState = turnState;
        this.gameDisplay = gameDisplay;
        this.game = game;

        boardDisplay = gameDisplay.getBoardDisplay();
        board = game.getBoard();

        init();
    }

    public void init() {
        // Add slider listeners
        gameDisplay.addSlideListeners(new SlideListener());

        // Add rotate listener to extraTileDisplay
        gameDisplay.getInsertableTileDisplay().addRotateListener(new RotateListener());

        // Add move listener to tileDisplays in
        boardDisplay.addMoveListeners(new MoveListener());
    }

    public class SlideListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TileSlider slider = (TileSlider) e.getSource();

            game.slideInsertableTileAction(slider.getOrientation(), slider.getLineResponsible());

            boardDisplay.updateBoard();
            gameDisplay.getInsertableTileDisplay().update(game.getInsertableTile());
            boardDisplay.updatePlayerViews();
        }
    }

    public class RotateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Rotate extra tile
            board.getInsertableTile().rotate();

            // Update extraTileDisplay
            gameDisplay.getInsertableTileDisplay().update(board.getInsertableTile());
        }
    }

    public class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TileDisplay[][] tileDisplays = boardDisplay.getTileDisplays();

            // Scroll through all Tile JButtons
            for(int row = 0; row < tileDisplays.length; row++) {
                for(int col = 0; col < tileDisplays.length; col++) {
                    if(e.getSource() == tileDisplays[row][col].getTileImage()) {
                        game.movePlayerAction(row, col);

                        boardDisplay.updateBoard();
                        boardDisplay.updatePlayerViews();
                    }
                }
            }

        }
    }
}
