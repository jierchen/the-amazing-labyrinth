package model;

public class Treasure {

    public static final int TREASURE_AMOUNT = 24;

    private int treasureNum;
    private boolean collected = false;

    /**
     * Constructor
     *
     * @param treasureNum id of this treasure
     */
    public Treasure(int treasureNum) {
        this.treasureNum = treasureNum;
    }

    // Setters and getters
    public int getTreasureNum() {
        return treasureNum;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public boolean isCollected() {
        return collected;
    }

}
