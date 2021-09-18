package model;

import model.tiles.Tile;

import java.util.Random;
import java.util.Stack;

public class Game {

    public static final Integer NUM_PLAYERS = 4;

    // Game board
    private Board board;

    // Game cards to distribute to players
    private Card[] cards;

    // Treasures on the board
    private Treasure[] treasures;

    // Extra insertable tile
    private Tile insertableTile;

    // Players of the game
    private Player[] players;

    /**
     * Constructor
     *
     * @param players players of the game
     */
    public Game(Player[] players) {
        this.players = players;
    }

    /**
     * Initializes the game by setting up players
     * and game states of all pieces
     */
    public void init() {
        // Setup pre-game components
        this.setupCardsAndTreasures();
        this.distributeCards();

        // Initialize new board
        this.board = new Board(this.players, this.treasures);
        this.insertableTile = this.board.init();
    }

    /**
     * Initializes players for the game
     */
    private void setupPlayers() {
        // Preset player data
        String[] colours =  {"Red", "Blue", "Green", "Purple"};
        int[][] startingPoints = {{0,0}, {6, 0}, {0, 6}, {6, 6}};

        for(int playerNum = 0; playerNum < NUM_PLAYERS; playerNum++) {
            this.players[playerNum] = new Player(startingPoints[playerNum][0],
                    startingPoints[playerNum][1], colours[playerNum]);
        }
    }

    /**
     * Initializes treasures and treasure cards
     */
    private void setupCardsAndTreasures() {
        // Create treasures and treasure cards
        this.treasures = new Treasure[Treasure.TREASURE_AMOUNT];
        this.cards = new Card[Treasure.TREASURE_AMOUNT];

        for(int i = 0; i < Treasure.TREASURE_AMOUNT; i++) {
            this.treasures[i] = new Treasure(i);
            this.cards[i] = new Card(this.treasures[i]);
        }
    }

    /**
     * Shuffles and distributes 4 cards from the pool to each player
     */
    private void distributeCards() {
        Card[] shuffledCards = new Card[cards.length];

        System.arraycopy(cards, 0, shuffledCards, 0, cards.length);

        Random rand = new Random();

        // Fisher-Yates shuffle
        for(int i = shuffledCards.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);

            // swap
            Card temp = shuffledCards[index];
            shuffledCards[index] = shuffledCards[i];
            shuffledCards[i] = temp;
        }

        int cardsPerPlayer = 5;
        int cardCounter = 0;

        for(Player player: players) {
            Stack<Card> newHand = new Stack<Card>();

            for(int j = 0; j < cardsPerPlayer; j++) {
                newHand.push(shuffledCards[cardCounter]);
                cardCounter++;
            }

            player.setHand(newHand);
        }
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
