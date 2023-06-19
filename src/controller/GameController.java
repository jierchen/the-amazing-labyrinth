package controller;

import enums.Action;
import model.Board;
import model.Game;
import model.Turn;
import view.BoardDisplay;
import view.GameDisplay;
import view.TileDisplay;
import view.TileSlider;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {

    private final GameDisplay gameDisplay;
    private final Game game;
    private final BoardDisplay boardDisplay;
    private final Board board;

    // Game data
    private boolean[] isBot;
    private Turn turn;

    // Timer to start bot turn
    private final Timer botTimer;

    public GameController(GameDisplay gameDisplay, Game game) {
        this.gameDisplay = gameDisplay;
        this.game = game;
        this.boardDisplay = gameDisplay.getBoardDisplay();
        this.board = game.getBoard();
        this.turn = game.getTurn();

        this.botTimer = new Timer(2000, new AgentListener());
        this.botTimer.setRepeats(false);

        setupListeners();
    }

    private void setupListeners() {
        // Add slider listener to game sliders
        gameDisplay.addActionListenerToSliders(new SlideListener());

        // Add rotate listener to insertable tile display
        gameDisplay.getInsertableTileDisplay().addActionListener(new RotateListener());

        // Add move listener to board tile displays
        boardDisplay.addActionListenerToAllTiles(new MoveListener());
    }

    public class AgentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!isBot[turn.getCurrentPlayerType().getValue()]) {
                // TODO
            }
        }
    }

    public class SlideListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!game.hasGameEnded()) {
                if(turn.getAction() == Action.INSERT) {
                    TileSlider slider = (TileSlider) e.getSource();

                    // Update model
                    game.slideInsertableTileAction(slider.getDirection(), slider.getLine());

                    // Update view
                    gameDisplay.update();
                }
            }
        }
    }

    public class RotateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!game.hasGameEnded()) {
                if(turn.getAction() == Action.INSERT) {
                    // Rotate extra tile
                    board.getInsertableTile().rotate();

                    // Update insertable tile view
                    gameDisplay.getInsertableTileDisplay().update(board.getInsertableTile());
                }
            }
        }
    }

    public class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!game.hasGameEnded()) {
                if(turn.getAction() == Action.MOVE) {
                    TileDisplay[][] tileDisplays = boardDisplay.getTileDisplays();

                    // Iterate through all Tile buttons
                    for(int row = 0; row < tileDisplays.length; row++) {
                        for(int col = 0; col < tileDisplays.length; col++) {
                            if(e.getSource() == tileDisplays[row][col].getTileImage()) {
                                game.movePlayerAction(row, col);
                            }
                        }
                    }

                    // Successful move
                    if(turn.getAction() == Action.INSERT) {
                        gameDisplay.update();
                    }
                }
            }
        }
    }
}
