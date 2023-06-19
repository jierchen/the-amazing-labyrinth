import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LabyrinthMenu extends JFrame implements ActionListener {

    private final JButton startButton = new JButton();
    private final JButton[] playerTypeButtons = new JButton[4];
    private final JButton exitButton = new JButton();
    private final Color[] colors = new Color[4];
    private final boolean[] isBot = new boolean[4];

    public LabyrinthMenu() {
        setupLabyrinthMenuProperties();
        setupStartButton();
        setupColors();
        setupPlayerButtons();
        setupExitButton();
    }

    private void setupLabyrinthMenuProperties() {
        setTitle("Amazing Labyrinth");
        setSize(1370, 800);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(210, 210, 150));
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void setupStartButton() {
        startButton.setBounds(555, 265, 250, 70);
        startButton.setBorderPainted(false);
        startButton.addActionListener(this);
        startButton.setText("Start");
        startButton.setBackground(new Color(100, 100, 100));
        startButton.setFocusPainted(false);
        add(startButton);
    }

    private void setupColors() {
        colors[0] = new Color(142, 30, 193);
        colors[1] = new Color(51, 153, 255);
        colors[2] = new Color(200, 30, 30);
        colors[3] = new Color(0, 153, 0);
    }

    private void setupPlayerButtons() {
        for(int i = 0; i < playerTypeButtons.length; i++) {
            playerTypeButtons[i] = new JButton();

            playerTypeButtons[i].setBounds(420 + 130 * i, 350, 130, 70);
            playerTypeButtons[i].setBorderPainted(false);
            playerTypeButtons[i].addActionListener(this);
            playerTypeButtons[i].setText("Player");
            playerTypeButtons[i].setBackground(colors[i]);
            playerTypeButtons[i].setFocusPainted(false);

            add(playerTypeButtons[i]);
        }
    }

    private void setupExitButton() {
        exitButton.setBounds(555, 435, 250, 70);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(this);
        exitButton.setText("Exit");
        exitButton.setBackground(new Color(100, 100, 100));
        exitButton.setFocusPainted(false);
        add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Start button
        if(e.getSource() == startButton) {
            GameManager gameManager = new GameManager();
            this.setVisible(false);
        }

        // Player type buttons
        for(int i = 0; i < playerTypeButtons.length; i++) {
            if(e.getSource() == playerTypeButtons[i]) {
                if(!isBot[i]) {
                    playerTypeButtons[i].setText("AI");
                    isBot[i] = true;
                } else {
                    playerTypeButtons[i].setText("Player");
                    isBot[i] = false;
                }
            }
        }

        // Exit button
        if(e.getSource() == exitButton) {
            this.dispose();
        }
    }
}
