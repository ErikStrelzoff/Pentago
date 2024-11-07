import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GameBoardTest {
    private GameBoard board;

    @Before
    public void setUp() {
        board = new GameBoard();
    }

    // Existing tests...

    @Test
    public void testWinnerHorizontal() {
        for (int i = 0; i < GameBoard.GRID_SIZE; i++) {
            setUp(); // Reset the board for each test case
            for (int j = 0; j < GameBoard.GRID_SIZE; j++) {
                board.placeMarble(i, j, 'X');
            }
            assertEquals('X', board.checkWinner());
        }
    }

    @Test
    public void testWinnerVertical() {
        for (int i = 0; i < GameBoard.GRID_SIZE; i++) {
            setUp(); // Reset the board for each test case
            for (int j = 0; j < GameBoard.GRID_SIZE; j++) {
                board.placeMarble(j, i, 'O');
            }
            assertEquals('O', board.checkWinner());
        }
    }

    @Test
    public void testWinnerDiagonal() {
        setUp(); // Reset the board
        for (int i = 0; i < GameBoard.GRID_SIZE; i++) {
            board.placeMarble(i, i, 'X');
        }
        assertEquals('X', board.checkWinner());

        setUp(); // Reset the board
        for (int i = 0; i < GameBoard.GRID_SIZE; i++) {
            board.placeMarble(i, GameBoard.GRID_SIZE - 1 - i, 'O');
        }
        assertEquals('O', board.checkWinner());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testPlaceMarbleOutOfBounds() {
        board.placeMarble(-1, -1, 'X');
        board.placeMarble(GameBoard.GRID_SIZE, GameBoard.GRID_SIZE, 'X');
    }

    @Test
    public void testInitialBoardHasNoWinner() {
        assertEquals('.', board.checkWinner());
    }

    @Test
    public void testInvalidMove() {
        board.placeMarble(0, 0, 'X');
        assertFalse(board.placeMarble(0, 0, 'O'));
    }
    @Test
    public void testInitialBoardIsEmpty() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertEquals('.', board.getMarble(i, j));
            }
        }
    }

    @Test
    public void testPlaceMarble() {
        assertTrue(board.placeMarble(0, 0, 'X'));
        assertEquals('X', board.getMarble(0, 0));
        assertFalse(board.placeMarble(0, 0, 'O')); // Should not overwrite
    }

    @Test
    public void testRotateQuadrantClockwise() {
        board.placeMarble(1, 0, 'X');
        board.rotateQuadrant(1, true);
        assertEquals('X', board.getMarble(0, 1));
    }

    @Test
    public void testRotateQuadrantCounterClockwise() {
        board.placeMarble(0, 1, 'X');
        board.rotateQuadrant(1, false);
        assertEquals('X', board.getMarble(1, 0));
    }
    @Test
    public void testCheckWinningMoveHorizontal() {
        for (int i = 0; i < GameBoard.GRID_SIZE - 4; i++) {
            setUp(); // Reset the board for each test case
            for (int j = 0; j < 4; j++) {
                board.placeMarble(i, j, 'X');
            }
            assertTrue(board.checkWinningMove(i, 4, 'X'));
        }
    }

    @Test
    public void testCheckWinningMoveVertical() {
        for (int i = 0; i < GameBoard.GRID_SIZE - 4; i++) {
            setUp(); // Reset the board for each test case
            for (int j = 0; j < 4; j++) {
                board.placeMarble(j, i, 'O');
            }
            assertTrue(board.checkWinningMove(4, i, 'O'));
        }
    }

    @Test
    public void testCheckWinningMoveDiagonalTopLeftToBottomRight() {
        setUp(); // Reset the board
        for (int i = 0; i < 4; i++) {
            board.placeMarble(i, i, 'X');
        }
        assertTrue(board.checkWinningMove(4, 4, 'X'));
    }

    @Test
    public void testCheckWinningMoveDiagonalTopRightToBottomLeft() {
        setUp(); // Reset the board
        for (int i = 0; i < 4; i++) {
            board.placeMarble(i, GameBoard.GRID_SIZE - 1 - i, 'O');
        }
        assertTrue(board.checkWinningMove(4, GameBoard.GRID_SIZE - 5, 'O'));
    }

    @Test
    public void testCheckWinningMoveNoWin() {
        setUp(); // Reset the board
        for (int i = 0; i < 3; i++) {
            board.placeMarble(i, i, 'X');
        }
        assertFalse(board.checkWinningMove(4, 4, 'X'));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveMarbleFromEmptyPosition() {
        board.removeMarble(0, 0);
    }

    @Test
    public void testCopyQuadrantToTempArray() {
        board.placeMarble(0, 0, 'X');
        char[][] temp = board.copyQuadrantToTempArray(0, 0);
        assertEquals('X', temp[0][0]);
    }

    @Test
    public void testRotateTempArrayAndCopyBackClockwise() {
        char[][] temp = new char[GameBoard.QUADRANT_SIZE][GameBoard.QUADRANT_SIZE];
        temp[0][0] = 'X';
        board.rotateTempArrayAndCopyBack(temp, 0, 0, true);
        assertEquals('X', board.getMarble(0, GameBoard.QUADRANT_SIZE - 1));
    }

    @Test
    public void testRotateTempArrayAndCopyBackCounterClockwise() {
        char[][] temp = new char[GameBoard.QUADRANT_SIZE][GameBoard.QUADRANT_SIZE];
        temp[0][0] = 'X';
        board.rotateTempArrayAndCopyBack(temp, 0, 0, false);
        assertEquals('X', board.getMarble(GameBoard.QUADRANT_SIZE - 1, 0));
    }
}