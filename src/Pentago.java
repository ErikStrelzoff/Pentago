import java.util.function.Consumer;
public class Pentago {
    private static final int GRID_SIZE = 6;
    private static final int QUADRANT_SIZE = GRID_SIZE / 2;
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    private GameBoard board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Consumer<HumanPlayer> moveRequestCallback;

    public Pentago(Consumer<HumanPlayer> moveRequestCallback, String gameMode) {
        board = new GameBoard();
        this.moveRequestCallback = moveRequestCallback;
        initializePlayers(gameMode);
    }

    private void initializePlayers(String gameMode) {
        PlayerFactory playerFactory = new PlayerFactory(moveRequestCallback);
        player1 = playerFactory.createPlayer(PLAYER_X, gameMode);
        player2 = playerFactory.createPlayer(PLAYER_O, gameMode);
        currentPlayer = player1;
    }

    /**
     * Rotates the specified quadrant of the game board.
     * @param quadrant The quadrant to rotate.
     * @param clockwise Whether to rotate clockwise or not.
     */
    public void rotateQuadrant(int quadrant, boolean clockwise) {
        board.rotateQuadrant(quadrant, clockwise);
    }

    /**
     * Places a marble on the game board.
     * @param row The row to place the marble.
     * @param col The column to place the marble.
     * @return Whether the marble was successfully placed.
     */
    public boolean placeMarble(int row, int col) {
        return board.placeMarble(row, col, currentPlayer.getMarble());
    }

    /**
     * Checks for a winner on the game board.
     * @return The marble of the winner, or '.' if there is no winner.
     */
    public char checkWinner() {
        return board.checkWinner();
    }


    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        System.out.println("Pentago.switchPlayer called.");
        System.out.println("New Current Player: " + currentPlayer);
        System.out.println("Player1: " + player1);
        System.out.println("Player2: " + player2);
    }

    public char getCurrentPlayerMarble() {
        return currentPlayer.getMarble();
    }

    public char getMarble(int row, int col) {
        return board.getMarble(row, col);
    }
}
