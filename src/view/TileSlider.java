package view;

import javax.swing.*;

public class TileSlider extends JButton {

    // Direction of the slider direction
    // 0 - Up
    // 1 - Right
    // 2 - Down
    // 3 - Left
    private int direction;

    // row or column line the slider is responsible for
    private int lineResponsible;

    private boolean disabled = false;

    /**
     * Constructor
     *
     * @param lineResponsible the line in the direction
     * @param direction direction the slider is facing
     */
    public TileSlider(int lineResponsible, int direction) {
        this.direction = direction;
        this.lineResponsible = lineResponsible;

        // Properties
        setIcon(new ImageIcon(this.getClass().getResource("/resources/game/sliders/slider" + this.direction + ".png")));// Properties
        setSize(BoardDisplay.TILES_SIDE_LENGTH, BoardDisplay.TILES_SIDE_LENGTH);
        setVisible(true);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    /**
     * Toggles {@code disabled} boolean
     */
    public void toggleDisabled() {
        disabled = !disabled;
    }

    // Getters
    public int getLineResponsible() {
        return lineResponsible;
    }
    public boolean isDisabled() {
        return disabled;
    }
    public int getDirection() {
        return direction;
    }

}