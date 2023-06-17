package enums;

public enum Direction {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3);

    public static final int NUM_DIRECTIONS = 4;

    private int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
