import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.function.Consumer;

public class PentagoGUI {
    private JFrame frame;
    private Pentago game;
    private JButton[][] buttons;
    private JButton rotateButton;
    private int selectedQuadrant;
    private boolean rotateClockwise;

    public PentagoGUI() {
        String gameMode = getUserGameModeSelection();
        Consumer<HumanPlayer> moveRequestCallback = this::handleHumanPlayerMove;
        game = new Pentago(moveRequestCallback, gameMode);
        frame = new JFrame("Pentago");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(6, 6));
        frame.add(boardPanel, BorderLayout.CENTER);

        buttons = new JButton[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 24));
                button.addActionListener(new ButtonListener(i, j));
                boardPanel.add(button);
                buttons[i][j] = button;
            }
        }

        rotateButton = new JButton("Rotate");
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt user to select quadrant and direction after placing a marble
                SwingUtilities.invokeLater(() -> {
                    selectedQuadrant = getUserQuadrantSelection();
                    rotateClockwise = getUserRotationDirection();
                    game.rotateQuadrant(selectedQuadrant, rotateClockwise);
                    updateBoard();
                    game.switchPlayer();
                });
            }
        });
        frame.add(rotateButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void handleHumanPlayerMove(HumanPlayer player) {
        // Enable all buttons for the human player to make a move
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (game.getMarble(i, j) == '.') {
                    buttons[i][j].setEnabled(true);
                }
            }
        }
    }

    private void updateBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                char marble = game.getMarble(i, j);
                buttons[i][j].setText(String.valueOf(marble));
                buttons[i][j].setEnabled(marble == '.');
            }
        }
    }

    private int getUserQuadrantSelection() {
        Object[] options = {"Upper Left", "Upper Right", "Lower Left", "Lower Right"};
        int n = JOptionPane.showOptionDialog(frame,
                "Choose a quadrant to rotate:",
                "Select Quadrant",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        return n + 1;
    }

    private boolean getUserRotationDirection() {
        Object[] options = {"Clockwise", "Counter-Clockwise"};
        int n = JOptionPane.showOptionDialog(frame,
                "Choose the rotation direction:",
                "Select Rotation Direction",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        return n == 0;
    }

    private class ButtonListener implements ActionListener {
        private int row;
        private int col;

        public ButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!game.placeMarble(row, col)) {
                JOptionPane.showMessageDialog(frame, "Invalid move, try again.");
                return;
            }

            JButton button = (JButton) e.getSource();
            button.setText(String.valueOf(game.getCurrentPlayerMarble()));
            button.setEnabled(false);

            char winner = game.checkWinner();
            if (winner != '.') {
                JOptionPane.showMessageDialog(frame, "Player " + winner + " wins!");
                System.exit(0);
            }

            // Prompt user to select quadrant and direction for rotation
            selectedQuadrant = getUserQuadrantSelection();
            rotateClockwise = getUserRotationDirection();
            game.rotateQuadrant(selectedQuadrant, rotateClockwise);
            updateBoard();
            game.switchPlayer();
        }
    }

    private String getUserGameModeSelection() {
        Object[] options = {"Player vs. Player", "Player vs. AI"};
        int n = JOptionPane.showOptionDialog(null,
                "Choose a game mode:",
                "Select Game Mode",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        return options[n].toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PentagoGUI pentagoGUI = new PentagoGUI();
            }
        });

    }
}
