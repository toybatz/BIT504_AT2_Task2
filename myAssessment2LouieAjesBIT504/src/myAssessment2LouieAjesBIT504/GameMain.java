package myAssessment2LouieAjesBIT504;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener {
    // Constants for game
    // Number of ROWS by COLS cell constants
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String TITLE = "Tic Tac Toe";

    // Constants for dimensions used for drawing
    public static final int CELL_SIZE = 100; // Cell width and height
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS; // Canvas width
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS; // Canvas height
    public static final int CELL_PADDING = CELL_SIZE / 6; // Padding for symbols
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // Symbol size
    public static final int SYMBOL_STROKE_WIDTH = 8; // Stroke width for symbols

    /* Declare game object variables */
    private Board board; // The game board

    // TODO: create the enumeration for the variable below (GameState currentState)
    // HINT all of the states you require are shown in the code within GameMain
    private GameState currentState;

    private Player currentPlayer; // The current player
    private JLabel statusBar; // For displaying game status message

    /** Constructor to set up the UI and game components on the panel */
    public GameMain() {
        // TODO: This JPanel fires a MouseEvent on MouseClicked so add required event listener to 'this'.
        // Add the MouseListener to capture user input
        addMouseListener(this);

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel("         ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);

        // Layout of the panel is in BorderLayout
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        // Account for statusBar height in overall height
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        // TODO: Create a new instance of the game "Board" class. HINT check the variables above for the correct name
        // Initialize the Board object
        board = new Board();

        // TODO: call the method to initialise the game board
        // Initialize the game state and board
        initGame();
    }

    public static void main(String[] args) {
        // Run GUI code in Event Dispatch thread for thread safety
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create a main window to contain the panel
                JFrame frame = new JFrame(TITLE);

                // TODO: create the new GameMain panel and add it to the frame
                // Add the GameMain panel to the frame
                frame.add(new GameMain());

                // TODO: set the default close operation of the frame to exit_on_close
                // Set the close operation
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    /** Custom painting codes on this JPanel */
    public void paintComponent(Graphics g) {
        // Fill background and set colour to white
        super.paintComponent(g);
        setBackground(Color.WHITE);
        // Ask the game board to paint itself
        board.paint(g);

        // Set status bar message
        if (currentState == GameState.Playing) {
            statusBar.setForeground(Color.BLACK);
            if (currentPlayer == Player.Cross) {
                // TODO: use the status bar to display the message "X"'s Turn
                statusBar.setText("X's Turn");
            } else {
                // TODO: use the status bar to display the message "O"'s Turn
                statusBar.setText("O's Turn");
            }
        } else if (currentState == GameState.Draw) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.Cross_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == GameState.Nought_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    /** Initialise the game-board contents and the current status of GameState and Player) */
    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                // All cells empty
                board.cells[row][col].content = Player.Empty;
            }
        }
        // Set the initial game state and player
        currentState = GameState.Playing;
        currentPlayer = Player.Cross;
    }

    /** After each turn check to see if the current player hasWon by putting their symbol in that position,
     * If they have the GameState is set to won for that player
     * If no winner then isDraw is called to see if deadlock, if not GameState stays as PLAYING
     */
    public void updateGame(Player thePlayer, int row, int col) {
        // Check for win after play
        if (board.hasWon(thePlayer, row, col)) {
            // TODO: check which player has won and update the currentstate to the appropriate gamestate for the winner
            currentState = (thePlayer == Player.Cross) ? GameState.Cross_won : GameState.Nought_won;
        } else if (board.isDraw()) {
            // TODO: set the currentstate to the draw gamestate
            currentState = GameState.Draw;
        }
        // Otherwise, no change to current state of playing
    }

    /** Event handler for the mouse click on the JPanel. If selected cell is valid and Empty then current player is added to cell content.
     *  UpdateGame is called which will call the methods to check for winner or Draw. if none then GameState remains playing.
     *  If win or Draw then call is made to method that resets the game board.  Finally a call is made to refresh the canvas so that new symbol appears
     */
    public void mouseClicked(MouseEvent e) {
        // Get the coordinates of where the click event happened
        int mouseX = e.getX();
        int mouseY = e.getY();
        // Get the row and column clicked
        int rowSelected = mouseY / CELL_SIZE;
        int colSelected = mouseX / CELL_SIZE;
        if (currentState == GameState.Playing) {
            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS && board.cells[rowSelected][colSelected].content == Player.Empty) {
                // Move
                board.cells[rowSelected][colSelected].content = currentPlayer;
                // Update currentState
                updateGame(currentPlayer, rowSelected, colSelected);
                // Switch player
                currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross;
            }
        } else {
            // Game over and restart
            initGame();
        }

        // TODO: redraw the graphics on the UI
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Auto-generated, event not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Auto-generated, event not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Auto-generated, event not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Auto-generated, event not used
    }
}
// 11 TODO's
