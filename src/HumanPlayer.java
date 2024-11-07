import java.util.function.Consumer;

public class HumanPlayer implements Player {
    private final char marble;
    private final Consumer<HumanPlayer> moveRequestCallback;

    public HumanPlayer(char marble, Consumer<HumanPlayer> moveRequestCallback) {
        this.marble = marble;
        this.moveRequestCallback = moveRequestCallback;
    }

    @Override
    public char getMarble() {
        return marble;
    }

    @Override
    public void makeMove(GameBoard board) {
        // Notify the GUI that it's time for this player to make a move
        if (moveRequestCallback != null) {
            moveRequestCallback.accept(this);
        }
    }
}
