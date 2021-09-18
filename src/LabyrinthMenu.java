import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Game menu GUI that allows players to start, choose AIs, and exit game
 */
public class LabyrinthMenu extends JFrame implements ActionListener {

    // Buttons for menu options
    private JButton startButton = new JButton();
    private JButton[] playerTypeButtons = new JButton[4];
    private JButton exitButton = new JButton();

    // Colors indicator for Player type decisions
    private Color[] colors = new Color[4];

    // AI status
    private boolean[] isAI = new boolean[4];

    /** Constructor **/
    public LabyrinthMenu() {
        // Setup menu frame properties
        setTitle("Amazing Labyrinth");
        setSize(1370, 800);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(210, 210, 150));
        setVisible(true);
        setLocationRelativeTo(null);

        // Setup GUI elements
        setup();
    }

    /**
     * Performs menu setup for menu options
     * - Game start button
     * - Change player type buttons
     * - Exit game button
     */
    private void setup() {
        // Setup colors
        colors[0] = new Color(142, 30, 193);
        colors[1] = new Color(51, 153, 255);
        colors[2] = new Color(200, 30, 30);
        colors[3] = new Color(0, 153, 0);

        // Properties of start button
        startButton.setBounds(555, 265, 250, 70);
        startButton.setBorderPainted(false);
        startButton.addActionListener(this);
        startButton.setText("Start");
        startButton.setBackground(new Color(100, 100, 100));
        startButton.setFocusPainted(false);
        this.add(startButton);

        // Setup player button properties
        for (int i = 0; i < playerTypeButtons.length; i++) {

            playerTypeButtons[i] = new JButton();

            // Properties of AIButtons
            playerTypeButtons[i].setBounds(420 + 130 * i, 350, 130, 70);
            playerTypeButtons[i].setBorderPainted(false);
            playerTypeButtons[i].addActionListener(this);
            playerTypeButtons[i].setText("Player");
            playerTypeButtons[i].setBackground(colors[i]);
            playerTypeButtons[i].setFocusPainted(false);

            this.add(playerTypeButtons[i]);
        }

        // Properties of exit button
        exitButton.setBounds(555, 435, 250, 70); // location and size
        exitButton.setBorderPainted(false); // no borders
        exitButton.addActionListener(this); // add actionlistener
        exitButton.setText("Exit");
        exitButton.setBackground(new Color(100, 100, 100));
        exitButton.setFocusPainted(false);
        this.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Start button
        if (e.getSource() == startButton) {

            // create new game instance
            GameManager gameManager = new GameManager();

            this.dispose();
        }

        // Player type buttons
        for (int i = 0; i < playerTypeButtons.length; i++) {

            if (e.getSource() == playerTypeButtons[i]) {

                if (!isAI[i]) {
                    playerTypeButtons[i].setText("AI");
                    isAI[i] = true;
                }

                else if (isAI[i]) {
                    playerTypeButtons[i].setText("Player");
                    isAI[i] = false;
                }
            }

        }

        // Exit button
        if (e.getSource() == exitButton) {
            this.dispose();
        }
    }
}
