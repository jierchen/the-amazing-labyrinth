package model;

import algorithm.BreadthFirstSearch;
import commands.MoveCommand;
import commands.SlideCommand;
import model.tiles.Tile;
import util.TurnState;

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

    // Turn information
    private TurnState turnState;

    // Action commands
    MoveCommand moveCommand;
    SlideCommand slideCommand;

    /**
     * Constructor
     *
     * @param players players of the game
     */
    public Game(TurnState turnState, Player[] players) {
        this.turnState = turnState;
        this.players = players;
    }

    /**
     * Initializes the game by setting up players
     * and game states of all pieces
     */
    public void init() {
        // Setup pre-game components
        setupPlayers();
        setupCardsAndTreasures();
        distributeCards();

        // Initialize new board
        board = new Board(players, treasures);
        board.init();

        insertableTile = board.getInsertableTile();

        // Initalize commands
        slideCommand = new SlideCommand(getBoard());
        moveCommand = new MoveCommand(getBoard(), new BreadthFirstSearch());
    }

    /**
     * Initializes players for the game
     */
    private void setupPlayers() {
        // Preset player data
        String[] colours =  {"Red", "Blue", "Green", "Purple"};
        int[][] startingPoints = {{0,0}, {6, 0}, {0, 6}, {6, 6}};

        for(int playerNum = 0; playerNum < NUM_PLAYERS; playerNum++) {
            players[playerNum] = new Player(startingPoints[playerNum][0],
                    startingPoints[playerNum][1], colours[playerNum]);
        }
    }

    /**
     * Initializes treasures and treasure cards
     */
    private void setupCardsAndTreasures() {
        // Create treasures and treasure cards
        treasures = new Treasure[Treasure.TREASURE_AMOUNT];
        cards = new Card[Treasure.TREASURE_AMOUNT];

        for(int i = 0; i < Treasure.TREASURE_AMOUNT; i++) {
            treasures[i] = new Treasure(i);
            cards[i] = new Card(treasures[i]);
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
     * Slide insertable tile action for the turn
     *
     * @param slideDirection direction to slide the insertable tile
     * @param slideLine row or column number to slide the insertable tile
     */
    public void slideInsertableTileAction(int slideDirection, int slideLine) {
        slideCommand.setDirection(slideDirection);
        slideCommand.setLine(slideLine);

        if (slideCommand.isLegal()) {
            slideCommand.execute();

            // Retrieve new insertable tile
            this.insertableTile = board.getInsertableTile();

            turnState.setInsertedTile(true);
        }
    }

    /**
     * Move player action for the turn
     *
     * @param targetRow row to move to
     * @param targetCol column to move to
     */
    public void movePlayerAction(int targetRow, int targetCol) {
        moveCommand.setPlayer(players[turnState.getPlayerTurn()]);
        moveCommand.setTargetRow(targetRow);
        moveCommand.setTargetCol(targetCol);

        if (moveCommand.isLegal()) {
            moveCommand.execute();

            turnState.setMoved(true);
        }
    }

    // Setters and Getters
    public Board getBoard() {
        return board;
    }

    public Tile getInsertableTile() {
        return insertableTile;
    }

}
