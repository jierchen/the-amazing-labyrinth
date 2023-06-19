package model;

import commands.MoveCommand;
import commands.SlideCommand;
import enums.Direction;
import enums.PlayerType;
import helper.BreadthFirstSearch;
import model.tile.Tile;

import java.util.Random;

public class Game {

    public static final int NUM_PLAYERS = 4;

    private Board board;
    private Card[] cards;
    private Treasure[] treasures;
    private Player[] players = new Player[NUM_PLAYERS];
    private Turn turn;
    private boolean gameEnded = false;

    // Commands
    private MoveCommand moveCommand;
    private SlideCommand slideCommand;

    public Game() {
        // Setup pre-game components
        setupPlayers();
        setupCardsAndTreasures();
        distributeCards();

        // Initialize new board
        board = new Board(players, treasures);
        turn = new Turn(PlayerType.PURPLE);
    }

    /* Initializes players for the game */
    private void setupPlayers() {
        // Preset player data
        int[][] startingPoints = {{0,0}, {6, 0}, {0, 6}, {6, 6}};

        for(int playerNum = 0; playerNum < NUM_PLAYERS; playerNum++) {
            players[playerNum] = new Player(startingPoints[playerNum][0],
                    startingPoints[playerNum][1], PlayerType.from(playerNum));
        }
    }

    /* Initializes treasures and treasure cards */
    private void setupCardsAndTreasures() {
        treasures = new Treasure[Treasure.TOTAL_AMOUNT];
        cards = new Card[Treasure.TOTAL_AMOUNT];

        for(int treasureIndex = 0; treasureIndex < Treasure.TOTAL_AMOUNT; treasureIndex++) {
            treasures[treasureIndex] = new Treasure(treasureIndex);
            cards[treasureIndex] = new Card(treasureIndex);
        }
    }

    /* Shuffles and distributes 4 treasures from the pool to each player */
    private void distributeCards() {
        Card[] shuffledCards = new Card[cards.length];
        System.arraycopy(cards, 0, shuffledCards, 0, cards.length);

        Random rand = new Random();

        // Fisher-Yates shuffle
        for(int cardIndex = shuffledCards.length - 1; cardIndex > 0; cardIndex--) {
            int index = rand.nextInt(cardIndex + 1);

            Card tempCard = shuffledCards[index];
            shuffledCards[index] = shuffledCards[cardIndex];
            shuffledCards[cardIndex] = tempCard;
        }

        int cardCounter = 0;

        for(Player player: players) {
            for(int handIndex = 0; handIndex < Card.CARDS_PER_PLAYER; handIndex++) {
                player.addToHand(shuffledCards[cardCounter]);
                cardCounter++;
            }
        }
    }

    public void slideInsertableTileAction(Direction direction, int line) {
        slideCommand = new SlideCommand(this, direction, line);
        if(slideCommand.isLegal()) {
            slideCommand.execute();
            turn.inserted();
        }
    }

    public void movePlayerAction(int row, int col) {
        moveCommand = new MoveCommand(this, new BreadthFirstSearch(this.getBoard().getTiles()), row, col);
        if(moveCommand.isLegal()) {
            moveCommand.execute();
            turn.moved();
            turn.nextPlayer();
        }
    }

    public Tile getInsertableTile() {
        return this.board.getInsertableTile();
    }

    public Player getCurrentPlayer() {
        return players[turn.getCurrentPlayerType().getValue()];
    }

    public Board getBoard() {
        return this.board;
    }

    public boolean hasGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public Turn getTurn() {
        return turn;
    }
}
