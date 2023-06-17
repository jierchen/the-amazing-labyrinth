package model;

public class Treasure {
    public static final int TOTAL_AMOUNT = 24;

    // State
    private final int number;
    private boolean isCollected = false;

    public Treasure(int number) {
        this.number = number;
    }

    // Getters and Setters
    public int getNumber() {
        return number;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        this.isCollected = collected;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Treasure) {
            Treasure comparedTreasure = (Treasure) obj;
            return this.number == comparedTreasure.getNumber();
        }

        return false;
    }
}
