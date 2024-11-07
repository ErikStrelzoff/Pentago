import java.util.function.Consumer;

public class PlayerFactory {
    private final Consumer<HumanPlayer> moveRequestCallback;

    public PlayerFactory(Consumer<HumanPlayer> moveRequestCallback) {
        this.moveRequestCallback = moveRequestCallback;
    }

    public Player createPlayer(char marble, String gameMode) {
        if (gameMode.equals("Player vs. Player")) {
            return new HumanPlayer(marble, moveRequestCallback);
        } else if (gameMode.equals("Player vs. AI")) {
            if (marble == 'X') {
                return new HumanPlayer(marble, moveRequestCallback);
            } else {
                return new AIGreedyPlayer(marble);
            }
        } else {
            throw new IllegalArgumentException("Invalid game mode: " + gameMode);
        }
    }
}
