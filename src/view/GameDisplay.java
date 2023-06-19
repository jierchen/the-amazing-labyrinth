package view;

import enums.Direction;
import enums.PlayerType;
import model.Card;
import model.Game;
import model.Player;
import util.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class GameDisplay extends JFrame {

    // Underlying model
    private final Game game;

    // Views
    private BoardDisplay boardDisplay;
    private TileDisplay insertableTileDisplay;
    private TileSlider[] tileSliders = new TileSlider[12];
    private JLabel[] cardDisplays;
    private JLabel[] cardTreasureImages;

    // Turn information
    private JLabel playerTurn = new JLabel("Player Turn:");
    private JLabel currentPlayerImage;

    public GameDisplay(Game game) {
        this.game = game;

        // Setup
        setupGameDisplayProperties();
        setupBoardDisplay();
        setupInsertableTileDisplay();
        setupTileSliders();
        setupPlayerCards();
        setupPlayerInfo();
        setupCurrentPlayer();

        // Initial update
        updatePlayerCards();
        updateVisualTurn();
    }

    public void update() {
        boardDisplay.update();
        insertableTileDisplay.update(game.getInsertableTile());
        updatePlayerCards();
        updateVisualTurn();
    }

    private void updateVisualTurn() {
        Player currentPlayer = game.getCurrentPlayer();
        currentPlayerImage.setIcon(ImageLoader.getPlayerImage(currentPlayer.getType()));
    }

    private void updatePlayerCards() {
        Player[] players = game.getBoard().getPlayers();

        for(int i = 0; i < cardDisplays.length; i++) {
            // Clear all treasures
            cardTreasureImages[i].setIcon(null);

            // Show current treasure to collect
            if(!players[i].hasWon()) {
                Card card = players[i].getTopOfHand();

                cardTreasureImages[i].setIcon(ImageLoader.getTreasureImage(card.getTreasureNum()));
                cardDisplays[i].revalidate();
                cardDisplays[i].repaint();
            }
        }
    }

    private void setupCurrentPlayer() {
        currentPlayerImage = new JLabel();
        currentPlayerImage.setSize(35, 35);
        currentPlayerImage.setLocation(985, 200);
        add(currentPlayerImage);
    }

    private void setupPlayerInfo() {
        for(int i = 0; i < Game.NUM_PLAYERS; i++) {
            JLabel playerImage = new JLabel(ImageLoader.getPlayerImage(PlayerType.from(i)));
            playerImage.setSize(70, 110);
            playerImage.setLocation(cardDisplays[i].getX() - 50, cardDisplays[i].getY() + 20);
            
            add(playerImage);
        }
    }

    private void setupPlayerCards() {
        cardDisplays = new JLabel[4];
        cardTreasureImages = new JLabel[cardDisplays.length];

        for(int i = 0; i < cardDisplays.length; i++) {
            cardDisplays[i] = new JLabel(ImageLoader.getCardImage(PlayerType.from(i)));
            cardTreasureImages[i] = new JLabel();

            // Properties of empty card
            cardDisplays[i].setSize(96, 150);
            cardDisplays[i].setLocation(900 + 260 * (i / 2), 350 + 200 * (i % 2));

            // Properties of card treasure images
            cardTreasureImages[i].setSize(35, 35);
            cardTreasureImages[i].setLocation(30, 60);

            cardDisplays[i].add(cardTreasureImages[i]);
            add(cardDisplays[i]);
        }
    }

    private void setupTileSliders() {
        int tileLength = TileDisplay.TILE_SIDE_LENGTH;
        int boardLength = BoardDisplay.BOARD_SIDE_LENGTH;

        for(int i = 0; i < tileSliders.length; i++) {
            // order of current slider within its group
            int order = i % 3;

            // Setup top sliders
            if(i >= 0 && i < 3) {
                tileSliders[i] = new TileSlider((2 * order) + 1, Direction.DOWN);
                tileSliders[i].setLocation(100 + (2 * order + 1) * tileLength, 80 - tileLength);
            }
            // Setup right sliders
            else if (i >= 3 && i < 6) {
                tileSliders[i] = new TileSlider((2 * order) + 1, Direction.LEFT);
                tileSliders[i].setLocation(100 + boardLength, 80 + (2 * order + 1) * tileLength);
            }
            // Setup bottom sliders
            else if (i >= 6 && i < 9) {
                tileSliders[i] = new TileSlider((2 * order) + 1, Direction.UP);
                tileSliders[i].setLocation(100 + (2 * order + 1) * tileLength, 80 + boardLength);
            }
            // Setup left sliders
            else {
                tileSliders[i] = new TileSlider((2 * order) + 1, Direction.RIGHT);
                tileSliders[i].setLocation(100 - tileLength, 80 + (2 * order + 1) * tileLength);
            }

            // Add to GUI
            add(tileSliders[i]);
        }
    }

    private void setupInsertableTileDisplay() {
        insertableTileDisplay = new TileDisplay();
        insertableTileDisplay.update(game.getInsertableTile());
        insertableTileDisplay.setSize(TileDisplay.TILE_SIDE_LENGTH, TileDisplay.TILE_SIDE_LENGTH);
        insertableTileDisplay.setLocation(1035, 180);
        add(insertableTileDisplay);
    }

    private void setupBoardDisplay() {
        boardDisplay = new BoardDisplay(game.getBoard());
        boardDisplay.setLocation(100,80);
        add(boardDisplay);
    }

    private void setupGameDisplayProperties() {
        setTitle("The Amazing Labyrinth");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1370, 800);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(210, 210, 150));
    }
}
