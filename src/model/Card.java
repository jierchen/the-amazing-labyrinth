package model;

public class Card {

    public static final int CARDS_PER_PLAYER = 5;

    private final int treasureNum;

    public Card(int treasureNum) {
        this.treasureNum = treasureNum;
    }

    public boolean matches(Treasure treasure) {
        return this.treasureNum == treasure.getNumber();
    }

    public int getTreasureNum() {
        return treasureNum;
    }
}
