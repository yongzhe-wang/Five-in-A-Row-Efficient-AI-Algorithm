import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

// Class that implements the Gomoku Game Logic. The game is entirely based on a linked Structure I made
// called Node. Please first read Node.java before reading this file.

public class GomokuBoard extends JPanel {
    private static final int GRID_SIZE = 19;
    private static final int CELL_SIZE = 30;
    private static final int PIECE_RADIUS = 12;

    // currentPlayer:
    // 1 - Player 1
    // 2 - Player 2

    public int[][] board;
    public Node[][] nodeBoard;
    private final Image backgroundImage;
    private int cursorX, cursorY;
    private int currentPlayer;
    private final Stack<Node[][]> nodeBoardHistory;
    private final MiniMaxAlphaAI AIandrew;
    private boolean isAI;



    public GomokuBoard(boolean isAI) {
        super(new FlowLayout(FlowLayout.CENTER));

        backgroundImage = new ImageIcon("src\\ArtFiles\\board_bg.jpg").getImage();
        this.nodeBoard = new Node[GRID_SIZE][GRID_SIZE];
        this.nodeBoardHistory = new Stack<>();
        this.board = BoardUtils.getSimpleBoard(nodeBoard);
        this.cursorX = GRID_SIZE / 2;
        this.cursorY = GRID_SIZE / 2;
        this.currentPlayer = 1;
        this.isAI = isAI;
        this.AIandrew = new MiniMaxAlphaAI();

        setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));

        // Game allows two type of input, either use mouse click to place stone or WASD to move and
        // ENTER to place the stone.

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        this.setFocusable(true);
        this.requestFocusInWindow();

    }


    // Handle mouse click
    private void handleMouseClick(MouseEvent e) {
        int x = e.getX() / CELL_SIZE;
        int y = e.getY() / CELL_SIZE;

        if (BoardUtils.isValidMove(x, y, board)) {
            placePiece(x, y);
            if (isAI) {
                int[] coord = AIandrew.evaluateMultipleLayers(nodeBoard, 2, 4);
                System.out.println(coord[0] + " " + coord[1]);
                placePiece(coord[0], coord[1]);
            }
            repaint();
        }
    }

    // Handle Key Press
    private void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> cursorY = Math.max(0, cursorY - 1);
            case KeyEvent.VK_S -> cursorY = Math.min(GRID_SIZE - 1, cursorY + 1);
            case KeyEvent.VK_A -> cursorX = Math.max(0, cursorX - 1);
            case KeyEvent.VK_D -> cursorX = Math.min(GRID_SIZE - 1, cursorX + 1);
            case KeyEvent.VK_ENTER -> {
                if (BoardUtils.isValidMove(cursorX, cursorY, board)) {
                    placePiece(cursorX, cursorY);
                    repaint();
                }
            }
        }
        repaint();
    }

    // Implements placing a piece.
    public void placePiece(int x, int y) {
        nodeBoardHistory.push(BoardUtils.copyNodeBoard(nodeBoard));
        // New node for new piece
        Node newNode = new Node(x, y, currentPlayer);
        nodeBoard[y][x] = newNode;
        newNode.updateNode(nodeBoard);
        repaint();

        // Check if win after each move
        if (newNode.checkWin()) {
            repaint();
            if (currentPlayer == 1) {
                JOptionPane.showMessageDialog(this, "Player 1 win!");
            } else {
                JOptionPane.showMessageDialog(this, "Player 1 lose!");
            }
            resetGame();
        }

        // Switch between 1 and 2
        currentPlayer = 3 - currentPlayer;
        board = BoardUtils.getSimpleBoard(nodeBoard);
    }

    // Allows player to take back one move.
    public void takeBackMove() {
        if (!nodeBoardHistory.isEmpty()) {
            nodeBoard = nodeBoardHistory.pop();
            board = BoardUtils.getSimpleBoard(nodeBoard);
            currentPlayer = 3 - currentPlayer;

            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No moves to take back!");
        }
    }

    // Reset the game and clear the board. Start with player 1 (black) again.
    public void resetGame() {
        nodeBoard = new Node[GRID_SIZE][GRID_SIZE];
        board = BoardUtils.getSimpleBoard(nodeBoard);
        currentPlayer = 2;
        repaint();
    }

    // Paint the board
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        requestFocusInWindow();

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }


        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < GRID_SIZE + 1; i++) {
            if (i % 5 == 0) {
                g2d.setStroke(new BasicStroke(3)); // Thick stroke
            } else {
                g2d.setStroke(new BasicStroke(1)); // Thin stroke
            }

            // Draw vertical and horizontal lines
            g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, GRID_SIZE * CELL_SIZE); // Vertical line
            g2d.drawLine(0, i * CELL_SIZE, GRID_SIZE * CELL_SIZE, i * CELL_SIZE); // Horizontal line
        }

        // Create a highlight box for keyboard move.
        switch (currentPlayer) {
            case 1 -> g2d.setColor(Color.RED);
            case 2 -> g2d.setColor(Color.GREEN);
        }
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(cursorX * CELL_SIZE, cursorY * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // Draw pieces
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (board[y][x] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillOval(x * CELL_SIZE + CELL_SIZE / 2 - PIECE_RADIUS,
                            y * CELL_SIZE + CELL_SIZE / 2 - PIECE_RADIUS,
                            PIECE_RADIUS * 2, PIECE_RADIUS * 2);
                } else if (board[y][x] == 2) {
                    g.setColor(Color.WHITE);
                    g.fillOval(x * CELL_SIZE + CELL_SIZE / 2 - PIECE_RADIUS,
                            y * CELL_SIZE + CELL_SIZE / 2 - PIECE_RADIUS,
                            PIECE_RADIUS * 2, PIECE_RADIUS * 2);
                }
            }
        }
    }

}

