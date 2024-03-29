package view;

import model.Board;
import model.tile.Tile;
import util.ImageLoader;

import javax.swing.*;
import java.awt.event.ActionListener;

public class TileDisplay extends JLayeredPane {

    public static final int TILE_SIDE_LENGTH = BoardDisplay.BOARD_SIDE_LENGTH / Board.NUM_OF_TILES_PER_SIDE;
    private final JButton tileImage = new JButton();
    private final JLabel treasureImage = new JLabel();

    public TileDisplay() {
        setTileDisplayProperties();
        setTileImageProperties();
        setTreasureImageProperties();
    }

    private void setTreasureImageProperties() {
        treasureImage.setSize(35,35);
        treasureImage.setLocation(25,27);
        add(treasureImage, JLayeredPane.POPUP_LAYER);
    }

    private void setTileImageProperties() {
        tileImage.setSize(TILE_SIDE_LENGTH, TILE_SIDE_LENGTH);
        tileImage.setLocation(0, 0);
        add(tileImage, JLayeredPane.DEFAULT_LAYER);
    }

    private void setTileDisplayProperties() {
        setVisible(true);
        setLayout(null);
    }

    /**
     * Updates tile image and treasure image based on given {@code Tile} model
     * @param tile Tile to get information from.
     */
    public void update(Tile tile) {
        tileImage.setIcon(ImageLoader.getTileImage(tile.getType(), tile.getOrientation()));

        if(tile.hasTreasure()) {
            treasureImage.setIcon(ImageLoader.getTreasureImage(tile.getTreasure().getNumber()));
        } else {
            treasureImage.setIcon(null);
        }
    }

    public void addActionListener(ActionListener actionListener) {
        tileImage.addActionListener(actionListener);
    }

    public JButton getTileImage() {
        return this.tileImage;
    }
}
