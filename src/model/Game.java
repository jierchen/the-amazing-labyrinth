package model;

import model.tiles.Tile;

public class Game {

    public static final Integer NUM_PLAYERS = 4;

    // Game board
    private Board board;

    // Treasures on the board
    private Treasure[] treasures;

    // Extra insertable tile
    private Tile insertableTile;

    /**
     * Constructor
     *
     * Creates and sets up a new game state
     *
     * @param isComputerPlayer array representing which players are computer players
     */
    public Game(boolean[] isComputerPlayer) {

    }

    /**
     * Initializes the game by setting up players and game states of all pieces
     */
    public void init() {

    }

    /**
     * Initializes players for the game
     */
    private void setupPlayers() {

    }

    /**
     * Initializes treasures and treasure cards
     */
    private void setupCardsAndTreasures() {

    }

    /**
     * Shuffles and distributes 4 cards from the pool to each player
     */
    private void distributeCards() {

    }

    /**
     *
     *
     * @param slideDirection direction to slide the insertable tile
     * @param slideLine row or column number to slide the insertable tile
     */
    public void slideInsertableTile(int slideDirection, int slideLine) {

    }

    // Setters and Getters
    public Board getBoard() {
        return this.board;
    }

    public Tile getInsertableTile() {
        return this.insertableTile;
    }

}
