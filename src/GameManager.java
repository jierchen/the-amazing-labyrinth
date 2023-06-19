import controller.GameController;
import model.Game;
import view.GameDisplay;

public class GameManager {

    private final GameController gameController;
    private final GameDisplay gameDisplay;
    private final Game game;

    public GameManager() {
        game = new Game();
        gameDisplay = new GameDisplay(game);
        gameController = new GameController(gameDisplay, game);
    }
}
