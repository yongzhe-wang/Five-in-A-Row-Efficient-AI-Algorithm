import javax.swing.*;
import java.awt.*;

// This is the main class that controls the GUI of the Gomoku game.
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String playerName;

    public MainFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels
        showInstructionPopup();
        JPanel startScreen = new StartScreen(this);
        mainPanel.add(startScreen, "StartScreen");
        mainPanel.add(createGameScreen(false), "TwoPlayerGame");
        mainPanel.add(createGameScreen(true), "AIGame");

        // Add main panel
        add(mainPanel);

        // Frame settings
        setTitle("Gomoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(30*19+10, 810);
        setLocationRelativeTo(null);
        setVisible(true);

        switchTo("StartScreen");
    }

    // Create a new dialog for instructions
    private void showInstructionPopup() {
        JDialog instructionDialog = new JDialog(this, "Welcome to Gomoku!", true);
        instructionDialog.setLayout(new BorderLayout());
        instructionDialog.setSize(500, 250);
        instructionDialog.setLocationRelativeTo(this);

        // Instruction message
        JLabel instructions = new JLabel("<html><h2>  Welcome to Gomoku!</h2>" +
                "<p>  Instructions:</p>" +
                "<ul>" +
                "<li>Take turns to place your pieces on the grid.</li>" +
                "<li>First player to align 5 pieces in a row, column, or diagonal wins!</li>" +
                "</ul></html>");
        instructions.setHorizontalAlignment(SwingConstants.CENTER);
        instructionDialog.add(instructions, BorderLayout.CENTER);

        // Input for player's name
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel nameLabel = new JLabel("Enter your name: ");
        JTextField nameInput = new JTextField(20);
        inputPanel.add(nameLabel);
        inputPanel.add(nameInput);

        // Add "OK" button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            playerName = nameInput.getText().trim();
            if (!playerName.isEmpty()) {
                instructionDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(instructionDialog, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(okButton);

        // Add panels to the dialog
        instructionDialog.add(inputPanel, BorderLayout.NORTH);
        instructionDialog.add(bottomPanel, BorderLayout.SOUTH);

        // Show the dialog
        instructionDialog.setVisible(true);
    }

    // This creates the main game function, returns a JPanel. Mode is to control ai game (True) or human game (False).
    private JPanel createGameScreen(boolean mode) {
        JPanel gameScreen = new JPanel(new BorderLayout());

        // Use different name for ai game and 2 human player game
        String playerTwoName = "Player 2";
        if (mode) {
            playerTwoName = "Andrew 2.0";
        }
        if (playerName.isEmpty()) {
            playerName = "Player 1";
        }

        // Top right panel for Picture, Name, and Take Back Button
        JPanel toprightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel topleftPanel = new JPanel(new FlowLayout());
        createPanels(toprightPanel, new Dimension(800, 100), playerName, createImageIcon("src\\ArtFiles\\Cat_Avatar.jpg"));

        JButton takeBackButton = new JButton("Take Back Move");
        takeBackButton.setFocusable(false);
        takeBackButton.setFont(new Font("Arial", Font.BOLD, 12));
        takeBackButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        takeBackButton.setPreferredSize(new Dimension(150, 30));
        topleftPanel.add(takeBackButton);

        // Center Panel (Game Board)
        GomokuBoard board = new GomokuBoard(mode);
        takeBackButton.addActionListener(e -> board.takeBackMove());

        // Combine top components
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(800, 100));
        topPanel.add(topleftPanel, BorderLayout.EAST);
        topPanel.add(toprightPanel, BorderLayout.WEST);

        // Bottom Panel
        JPanel bottomRightPanel = new JPanel(new FlowLayout());
        JPanel bottomLeftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomLeftPanel.setPreferredSize(new Dimension(800, 50));
        if (mode) {
            ImageIcon ava = createImageIcon("src\\ArtFiles\\AI_Avatar.jpg");
            createPanels(bottomLeftPanel, new Dimension(800, 100), playerTwoName, ava);
        } else {
            ImageIcon ava = createImageIcon("src\\ArtFiles\\Dog_Avatar.jpg");
            createPanels(bottomLeftPanel, new Dimension(800, 100), playerTwoName, ava);
        }
        // Return to main menu Button
        JButton returnButton = new JButton("Return to Main");
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        returnButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        returnButton.setPreferredSize(new Dimension(150, 30));
        bottomRightPanel.add(returnButton);

        // Combine bottom panels
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(800, 100));
        bottomPanel.add(bottomRightPanel, BorderLayout.WEST);
        bottomPanel.add(bottomLeftPanel, BorderLayout.EAST);

        // Add components to the game screen
        gameScreen.add(topPanel, BorderLayout.NORTH);
        gameScreen.add(board, BorderLayout.CENTER);
        gameScreen.add(bottomPanel, BorderLayout.SOUTH);

        returnButton.addActionListener(e -> {
            switchTo("StartScreen");
            board.resetGame();
        });

        return gameScreen;
    }

    // Function to load the image.
    private ImageIcon createImageIcon(String path) {
        Image pictureIcon = new ImageIcon(path).getImage();
        Image scaledImage = pictureIcon.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    //Create panel associated with an image and a string.
    private void createPanels(JPanel panel, Dimension d, String name, ImageIcon scaledIcon) {

        panel.setPreferredSize(d);

        JLabel pictureLabel = new JLabel(scaledIcon);
        pictureLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pictureLabel.setPreferredSize(new Dimension(70, 70));

        JLabel nameLabel = new JLabel(name);

        panel.add(pictureLabel);
        panel.add(nameLabel);

    }

    // Switch between panels
    public void switchTo(String screen) {
        cardLayout.show(mainPanel, screen);
    }


    public static void main(String[] args) {
        new MainFrame();
    }
}
