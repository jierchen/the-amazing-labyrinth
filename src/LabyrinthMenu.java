import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LabyrinthMenu extends JFrame implements ActionListener {

    // Buttons for menu options
    private JButton startButton = new JButton();
    private JButton[] playerTypeOption = new JButton[4];
    private JButton exitButton = new JButton();

    // Colors indicator for Player type decisions
    private Color[] colors = new Color[4];

    // AI status
    private boolean[] isAI = new boolean[4];

    /** Constructor **/
    public LabyrinthMenu() {

    }

    /**
     * Performs menu setup for menu options
     * - Game start button
     * - Change player type buttons
     * - Exit game button
     */
    private void setup() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
