import controller.GameController;
import model.Game;
import model.Player;
import view.GameDisplay;

public class GameManager {

    public static final int NUM_PLAYERS = 4;

    /**
     * Enscapulation class to hold current turn data to enforce rules
     */
    public class TurnState {
        private int playerTurn;
        private boolean insertedTile;
        private boolean moved;
        private boolean aiTurn;
        private boolean gameEnded = false;

        private TurnState(int playerTurn, boolean insertedTile, boolean moved, boolean aiTurn) {
            this.playerTurn = playerTurn;
            this.insertedTile = insertedTile;
            this.moved = moved;
            this.aiTurn = aiTurn;
        }

        // Getters and Setters
        public void setPlayerTurn(int playerTurn) {
            this.playerTurn = playerTurn;
        }
        public int getPlayerTurn() {
            return this.playerTurn;
        }

        public void setInsertedTile(boolean insertedTile) {
            this.insertedTile = insertedTile;
        }
        public boolean hasInsertedTile() {
            return this.insertedTile;
        }

        public void setMoved(boolean moved) {
            this.moved = moved;
        }
        public boolean hasMoved() {
            return this.moved;
        }

        public void setGameEnded(boolean gameEnded) {
            this.gameEnded = gameEnded;
        }
        public boolean hasGameEnded() {
            return this.gameEnded;
        }
    }

    private GameController gameController;
    private GameDisplay gameDisplay;
    private Game game;

    private Player[] players;

    private TurnState turnState;

    /**
     * Constructor
     */
    public GameManager() {

    }

    /**
     * Initializes Model View Controller and turn state
     */
    public void init() {
        this.players = new Player[NUM_PLAYERS];

        // Setup turnState
        this.turnState = new TurnState(0, false, false, false);

        // Setup game model
        Game game = new Game(this.turnState, this.players);
        game.init();

        // Setup game view
        GameDisplay gameDisplay = new GameDisplay(game);

        // Setup game controller
        GameController gameController = new GameController(gameDisplay, game);
    }
}
