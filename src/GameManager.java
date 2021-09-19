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
        players = new Player[NUM_PLAYERS];

        // Setup turnState
        turnState = new TurnState(0, false, false, false);

        // Setup game model
        Game game = new Game(turnState, players);
        game.init();

        // Setup game view
        GameDisplay gameDisplay = new GameDisplay(game);

        // Setup game controller
        GameController gameController = new GameController(turnState, gameDisplay, game);
    }
}
