package view;

import model.Game;
import model.Player;
import model.Treasure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameDisplay extends JFrame {

    // Underlying game model
    private Game game;

    // The extra tile
    private TileDisplay insertableTileDisplay;

    // The board
    private BoardDisplay boardDisplay;

    // Tile slider arrows
    private TileSlider[] tileSliders = new TileSlider[12];

    // Visual representation of the cards the players hold
    private JLabel[] cardDisplays;
    private JLabel[] cardTreasureImages;

    // Game information GUI
    private JLabel playerTurn = new JLabel("Player Turn:");

    /**
     * Constructor
     *
     * @param game game model
     */
    public GameDisplay(Game game) {
        this.game = game;

        // Properties of view.GameDisplay
        setTitle("The Amazing Labyrinth");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1370, 800);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(150, 220, 220));

        // GUI initialization
        boardDisplay = new BoardDisplay(game.getBoard());
        insertableTileDisplay = new TileDisplay();
        insertableTileDisplay.update(game.getInsertableTile());
        setupTileSliders();
        setupCardGUIs();
        setupGameInfo();

        // Properties of board GUI
        boardDisplay.setLocation(100,80);

        // Properties of insertable tile GUI
        insertableTileDisplay.setSize(BoardDisplay.TILES_SIDE_LENGTH, BoardDisplay.TILES_SIDE_LENGTH);
        insertableTileDisplay.setLocation(1035, 180);

        // add components to view.GameDisplay
        add(boardDisplay);
        add(insertableTileDisplay);
    }

    /**
     * Setup player and treasure graphical information for the game UI
     */
    private void setupGameInfo() {
        for(int i = 0; i < 4; i++) {
            JLabel playerImage = new JLabel(new ImageIcon(getClass().getResource(
                    "../resources/game/players/player" + i + ".png")));

            playerImage.setSize(70, 110);
            playerImage.setLocation(cardDisplays[i].getX() - 50, cardDisplays[i].getY() + 20);

            add(playerImage);
        }
    }

    /**
     * Setup and display cards with treasures for each player
     */
    private void setupCardGUIs() {
        cardDisplays = new JLabel[4];
        cardTreasureImages = new JLabel[cardDisplays.length];

        for(int i = 0; i < cardDisplays.length; i++) {
            cardDisplays[i] = new JLabel(new ImageIcon(getClass().getResource("../resources/game/ui/cardback" + i + ".png")));
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

        updateCardDisplay();
    }

    /**
     * Update cards to show next treasure to collect
     */
    public void updateCardDisplay() {
        Player[] players = game.getBoard().getPlayers();

        for(int i = 0; i < cardDisplays.length; i++) {

            // Clear all treasures
            cardTreasureImages[i].setIcon(null);

            if(!players[i].hasCollectedAll()) {
                Treasure cardTreasure = players[i].getTopOfHand().getTreasure();

                cardTreasureImages[i].setIcon(new ImageIcon(getClass().getResource(
                        "../resources/game/treasures/" + cardTreasure.getTreasureNum() + ".png")));


                cardDisplays[i].revalidate();
                cardDisplays[i].repaint();
            }
        }
    }

    /**
     * Setup graphical tile sliders to indicate slide directions
     */
    private void setupTileSliders() {
        int tileLength = BoardDisplay.TILES_SIDE_LENGTH;
        int boardLength = BoardDisplay.BOARD_SIDE_LENGTH;

        for(int i = 0; i < tileSliders.length; i++) {
            // order of current slider within its group
            int order = i % 3;

            // Setup top sliders
            if(i >= 0 && i < 3) {
                tileSliders[i] = new TileSlider((2 * order) + 1, 2);
                tileSliders[i].setLocation(100 + (2 * order + 1) * tileLength, 80 - tileLength);
            }
            // Setup right sliders
            else if (i >= 3 && i < 6) {
                tileSliders[i] = new TileSlider((2 * order) + 1, 3);
                tileSliders[i].setLocation(100 + boardLength, 80 + (2 * order + 1) * tileLength);
            }
            // Setup bottom sliders
            else if (i >= 6 && i < 9) {
                tileSliders[i] = new TileSlider((2 * order) + 1, 0);
                tileSliders[i].setLocation(100 + (2 * order + 1) * tileLength, 80 + boardLength);
            }
            // Setup left sliders
            else {
                tileSliders[i] = new TileSlider((2 * order) + 1, 1);
                tileSliders[i].setLocation(100 - tileLength, 80 + (2 * order + 1) * tileLength);
            }

            // Add to GUI
            add(tileSliders[i]);
        }

    }

    /**
     * Add ability to listen to user input for each slider
     *
     * @param sliderListener user input listener for tile sliding
     */
    public void addSlideListeners(ActionListener sliderListener) {
        for(TileSlider tileSlider: tileSliders) {
            tileSlider.addActionListener(sliderListener);
        }
    }

    // Getters
    public Game getGame() {
        return game;
    }

    public BoardDisplay getBoardDisplay() {
        return boardDisplay;
    }

    public TileDisplay getInsertableTileDisplay() {
        return insertableTileDisplay;
    }

    public TileSlider[] getTileSliders() {
        return tileSliders;
    }

}
