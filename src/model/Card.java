package model;

public class Card {

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
