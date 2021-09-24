import ai.ComputerAgent;
import ai.EasyAgent;
import ai.NormalAgent;
import controller.GameController;
import model.Game;
import model.Player;
import util.TurnState;
import view.GameDisplay;

public class GameManager {

    public static final int NUM_PLAYERS = 4;

    private GameController gameController;
    private GameDisplay gameDisplay;
    private Game game;

    private Player[] players;
    private boolean[] isAI;

    private TurnState turnState;

    /**
     * Constructor
     */
    public GameManager(boolean[] isAI) {
        this.isAI = isAI;
        players = new Player[NUM_PLAYERS];
    }

    /**
     * Initializes Model View Controller and turn state
     */
    public void init() {
        // Setup turnState
        turnState = new TurnState(0, false, false, isAI[0]);

        // Setup game model
        game = new Game(turnState, players);
        game.init();
        System.out.println(game.getBoard());

        players = game.getBoard().getPlayers();
        setupAI();

        // Setup game view
        gameDisplay = new GameDisplay(game);

        // Setup game controller
        gameController = new GameController(turnState, players, isAI, gameDisplay, game);
    }

    /**
     * Initializes players for the game
     */
    private void setupAI() {
        // Preset player data
        for(int playerNum = 0; playerNum < NUM_PLAYERS; playerNum++) {

            // Player is an AI
            if(isAI[playerNum]) {
                ComputerAgent computerAgent = new NormalAgent(game.getBoard(), players[playerNum]);
                players[playerNum].setComputerAgent(computerAgent);
            }
        }
    }
}
