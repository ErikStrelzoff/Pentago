/**
 * This class represents the game board for the game of Pentago.
 * It provides methods for placing marbles, rotating quadrants, and checking for a win.
 */
public class GameBoard {
    // Other methods remain unchanged...

    /**
     * Checks if there is a line of 5 marbles in a row starting from the given position in the given direction.
     * @param startRow the starting row
     * @param startCol the starting column
     * @param deltaRow the row direction (-1, 0, or 1)
     * @param deltaCol the column direction (-1, 0, or 1)
     * @param playerMarble the marble to check for
     * @return true if there is a line of 5 marbles, false otherwise
     */
    private boolean checkLineForWin(int startRow, int startCol, int deltaRow, int deltaCol, char playerMarble) {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            int row = startRow + i * deltaRow;
            int col = startCol + i * deltaCol;
            if (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE || board[row][col] != playerMarble) {
                return false;
            }
            count++;
        }
        return count == 5;
    }
    private char[][] board;
    public static final int QUADRANT_SIZE = 3;
    public static final int GRID_SIZE = 6;

    public GameBoard() {
        board = new char[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                board[i][j] = '.';
            }
        }
    }

    public char getMarble(int row, int col) {
        return board[row][col];
    }

    public boolean placeMarble(int row, int col, char marble) {
        if (board[row][col] == '.') {
            board[row][col] = marble;
            return true;
        }
        return false;
    }
    public char checkWinner() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col] != '.' && (
                    checkLineForWin(row, col, 0, 1, board[row][col]) ||
                    checkLineForWin(row, col, 1, 0, board[row][col]) ||
                    checkLineForWin(row, col, 1, 1, board[row][col]) ||
                    checkLineForWin(row, col, 1, -1, board[row][col]))) {
                    return board[row][col];
                }
            }
        }
        return '.';
    }

    /**
     * Rotates a quadrant of the board clockwise or counterclockwise.
     * @param quadrant the quadrant to rotate (1-4)
     * @param clockwise true to rotate clockwise, false to rotate counterclockwise
     */
    public void rotateQuadrant(int quadrant, boolean clockwise) {
        int startRow = (quadrant <= 2) ? 0 : QUADRANT_SIZE;
        int startCol = (quadrant == 1 || quadrant == 3) ? 0 : QUADRANT_SIZE;
        char[][] temp = copyQuadrantToTempArray(startRow, startCol);
        rotateTempArrayAndCopyBack(temp, startRow, startCol, clockwise);
    }

    /**
     * Copies a quadrant of the board to a temporary array.
     * @param startRow the starting row of the quadrant
     * @param startCol the starting column of the quadrant
     * @return the temporary array
     */
    public char[][] copyQuadrantToTempArray(int startRow, int startCol) {
        char[][] temp = new char[QUADRANT_SIZE][QUADRANT_SIZE];
        for (int row = 0; row < QUADRANT_SIZE; row++) {
            for (int col = 0; col < QUADRANT_SIZE; col++) {
                temp[row][col] = board[startRow + row][startCol + col];
            }
        }
        return temp;
    }

    /**
     * Rotates a temporary array clockwise or counterclockwise and copies it back to the board.
     * @param temp the temporary array
     * @param startRow the starting row of the quadrant on the board
     * @param startCol the starting column of the quadrant on the board
     * @param clockwise true to rotate clockwise, false to rotate counterclockwise
     */
    public void rotateTempArrayAndCopyBack(char[][] temp, int startRow, int startCol, boolean clockwise) {
        if (clockwise) {
            for (int row = 0; row < QUADRANT_SIZE; row++) {
                for (int col = 0; col < QUADRANT_SIZE; col++) {
                    board[startRow + col][startCol + QUADRANT_SIZE - row - 1] = temp[row][col];
                }
            }
        } else {
            for (int row = 0; row < QUADRANT_SIZE; row++) {
                for (int col = 0; col < QUADRANT_SIZE; col++) {
                    board[startRow + QUADRANT_SIZE - col - 1][startCol + row] = temp[row][col];
                }
            }
        }
    }

    // Existing methods from the GameBoard class...

    public boolean checkWinningMove(int row, int col, char playerMarble) {
        // Temporarily place the marble to check for a win
        char originalMarble = board[row][col];
        if (originalMarble != '.') {
            return false; // Position is already occupied, cannot place marble
        }
        board[row][col] = playerMarble; // Temporarily place the marble

        // Check horizontal, vertical, and both diagonal directions
        boolean won = (checkLineForWin(row, col - 4, 0, 1, playerMarble) ||
                       checkLineForWin(row - 4, col, 1, 0, playerMarble) ||
                       checkLineForWin(row - 4, col - 4, 1, 1, playerMarble) ||
                       checkLineForWin(row - 4, col + 4, 1, -1, playerMarble));

        // Remove the temporarily placed marble
        board[row][col] = '.';

        return won;
    }

    /**
     * Removes a marble from the board at the specified location.
     * This is useful for undoing a move after checking for a win.
     * @param row the row of the marble
     * @param col the column of the marble
     * @throws IllegalArgumentException if the position is already empty
     */
    public void removeMarble(int row, int col) {
        if (board[row][col] != '.') {
            board[row][col] = '.';
        } else {
            throw new IllegalArgumentException("Position is already empty");
        }
    }
    // Other methods remain unchanged...
}
