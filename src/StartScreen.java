import javax.swing.*;
import java.awt.*;

// Simple class to create the start screen page.
public class StartScreen extends JPanel {

    private Image backgroundImage;

    public StartScreen(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        backgroundImage = new ImageIcon("src\\ArtFiles\\start_bg.jpg").getImage();

        // Title Label
        JLabel titleLabel = new JLabel("GOMOKU", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        add(titleLabel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);

        // Buttons
        JButton twoPlayerButton = createStyledButton("  Two Players  ");
        JButton aiButton = createStyledButton("  Play with AI  ");
        JButton ai2Button = createStyledButton("  AI against AI  ");

        // Add action listeners
        twoPlayerButton.addActionListener(e -> mainFrame.switchTo("TwoPlayerGame"));
        aiButton.addActionListener(e -> mainFrame.switchTo("AIGame"));

        // Add buttons to the panel with spacing
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(Box.createVerticalStrut(50));
        buttonsPanel.add(twoPlayerButton);
        buttonsPanel.add(Box.createVerticalStrut(50));
        buttonsPanel.add(aiButton);
        add(buttonsPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 25));
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image, scaling it to fit the panel
        g.drawImage(backgroundImage, 0, 0, 30*19+10, 810, this);
    }
}
