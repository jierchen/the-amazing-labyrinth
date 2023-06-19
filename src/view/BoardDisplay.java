package view;

import model.Board;
import model.Game;
import model.Player;
import model.Treasure;
import util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BoardDisplay extends JPanel{

    public static final int BOARD_SIDE_LENGTH = 600;

    private Board board;

    private TileDisplay[][] tileDisplays = new TileDisplay[Board.NUM_OF_TILES_PER_SIDE][Board.NUM_OF_TILES_PER_SIDE];
    private JLabel[] treasureViews = new JLabel[Treasure.TOTAL_AMOUNT];
    private JLabel[] playerViews = new JLabel[Game.NUM_PLAYERS];

    public BoardDisplay(Board board) {
        this.board = board;

        setupBoardDisplayProperties();
        setupTileDisplays();
        setupPlayerViews();
    }

    private void setupBoardDisplayProperties() {
        setSize(BOARD_SIDE_LENGTH, BOARD_SIDE_LENGTH);
        setLayout(new GridLayout(Board.NUM_OF_TILES_PER_SIDE,Board.NUM_OF_TILES_PER_SIDE));
        setVisible(true);
        setBackground(new Color(150, 220, 220));
    }

    private void setupPlayerViews() {
        Player[] players = board.getPlayers();

        for(int i = 0; i < players.length; i++) {
            Player player = players[i];

            // Create new player image
            playerViews[i] = new JLabel(ImageLoader.getPlayerImage(player.getType()));

            // Properties of playerView
            playerViews[i].setVisible(true);
            playerViews[i].setSize(33,33);
            playerViews[i].setLocation(26 ,29);

            // Add playerView to tile the player is on
            tileDisplays[player.getRow()][player.getCol()].add(playerViews[i], JLayeredPane.DRAG_LAYER);
        }
    }

    private void setupTileDisplays() {
        for(int row = 0; row < Board.NUM_OF_TILES_PER_SIDE; row++){
            for(int col = 0; col < Board.NUM_OF_TILES_PER_SIDE; col++){
                tileDisplays[row][col] = new TileDisplay();
                tileDisplays[row][col].update(board.getTiles()[row][col]);

                add(tileDisplays[row][col]);
            }
        }
    }

    public void update() {
        updateTileViews();
        updatePlayerViews();
    }

    public void updateTileViews() {
        for(int row = 0; row < tileDisplays.length; row++) {
            for(int col = 0; col < tileDisplays.length; col++) {
                tileDisplays[row][col].update(board.getTiles()[row][col]);
            }
        }
    }

    public void updatePlayerViews() {
        for(int i = 0; i < playerViews.length; i++) {
            // Remove playerView from old tileDisplay
            TileDisplay oldTileDisplay = (TileDisplay) playerViews[i].getParent();
            oldTileDisplay.remove(playerViews[i]);
            oldTileDisplay.revalidate();
            oldTileDisplay.repaint();

            // Add playerView to new tileDisplay
            tileDisplays[board.getPlayers()[i].getRow()][board.getPlayers()[i].getCol()].add(playerViews[i], JLayeredPane.DRAG_LAYER);
        }
    }

    public void addActionListenerToAllTiles(ActionListener actionListener) {
        for(TileDisplay[] tileDisplayRow: tileDisplays) {
            for(TileDisplay tileDisplay: tileDisplayRow) {
                tileDisplay.addActionListener(actionListener);
            }
        }
    }

    public TileDisplay[][] getTileDisplays() {
        return tileDisplays;
    }
}
