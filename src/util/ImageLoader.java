package util;

import enums.Direction;
import enums.PlayerType;
import enums.TileType;
import model.Game;
import model.Treasure;

import javax.swing.*;

public class ImageLoader {

    private static String RESOURCE_URL = "resources/game/";

    public static ImageIcon[] PLAYER_IMAGES = new ImageIcon[Game.NUM_PLAYERS];
    public static ImageIcon[] TREASURE_IMAGES = new ImageIcon[Treasure.TOTAL_AMOUNT];
    public static ImageIcon[] SLIDER_IMAGES = new ImageIcon[Direction.NUM_DIRECTIONS];
    public static ImageIcon[] CARD_IMAGES = new ImageIcon[Game.NUM_PLAYERS];

    public static ImageIcon[] STRAIGHT_TILE_IMAGES = new ImageIcon[Direction.NUM_DIRECTIONS];
    public static ImageIcon[] BENT_TILE_IMAGES = new ImageIcon[Direction.NUM_DIRECTIONS];
    public static ImageIcon[] TAU_TILE_IMAGES = new ImageIcon[Direction.NUM_DIRECTIONS];

    static {
        // Player and card images
        for(int i = 0; i < Game.NUM_PLAYERS; i++) {
            PLAYER_IMAGES[i] = new ImageIcon(RESOURCE_URL + "players/player" + i + ".png");
            CARD_IMAGES[i] = new ImageIcon(RESOURCE_URL + "ui/cardback" + i + ".png");
        }

        // Slider and tiles images
        for(int i = 0; i < Direction.NUM_DIRECTIONS; i++) {
            SLIDER_IMAGES[i] = new ImageIcon(RESOURCE_URL + "sliders/slider" + i + ".png");

            STRAIGHT_TILE_IMAGES[i] = new ImageIcon(RESOURCE_URL + "tiles/I" + i + ".png");
            BENT_TILE_IMAGES[i] = new ImageIcon(RESOURCE_URL + "tiles/L" + i + ".png");
            TAU_TILE_IMAGES[i] = new ImageIcon(RESOURCE_URL + "tiles/T" + i + ".png");
        }

        // Treasure images
        for(int i = 0; i < Treasure.TOTAL_AMOUNT; i++) {
            TREASURE_IMAGES[i] = new ImageIcon(RESOURCE_URL + "treasures/" + i + ".png");
        }
    }

    private ImageLoader() {}

    public static ImageIcon getTreasureImage(int treasureNum) {
        return TREASURE_IMAGES[treasureNum];
    }

    public static ImageIcon getTileImage(TileType tileType, Direction orientation) {
        switch(tileType) {
            case STRAIGHT:
                return STRAIGHT_TILE_IMAGES[orientation.getValue()];
            case BENT:
                return BENT_TILE_IMAGES[orientation.getValue()];
            default:
                return TAU_TILE_IMAGES[orientation.getValue()];
        }
    }

    public static ImageIcon getSliderImage(Direction direction) {
        return SLIDER_IMAGES[direction.getValue()];
    }

    public static ImageIcon getPlayerImage(PlayerType playerType) {
        return PLAYER_IMAGES[playerType.getValue()];
    }

    public static ImageIcon getCardImage(PlayerType playerType) {
        return CARD_IMAGES[playerType.getValue()];
    }
}
