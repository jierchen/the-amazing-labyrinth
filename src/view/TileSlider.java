package view;

import enums.Direction;
import util.ImageLoader;

import javax.swing.*;

public class TileSlider extends JButton {

    private final Direction direction;
    private final int line;
    private boolean disabled = false;

    public TileSlider(int line, Direction direction) {
        this.direction = direction;
        this.line = line;

        setTileSliderProperties();
    }

    private void setTileSliderProperties() {
        setIcon(ImageLoader.getSliderImage(this.direction));
        setSize(TileDisplay.TILE_SIDE_LENGTH, TileDisplay.TILE_SIDE_LENGTH);
        setVisible(true);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    public Direction getDirection() {
        return direction;
    }

    public int getLine() {
        return line;
    }
}
