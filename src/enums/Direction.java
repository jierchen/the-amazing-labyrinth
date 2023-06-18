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

    public static Direction from(int orientationValue) {
        switch(orientationValue) {
            case 0:
                return UP;
            case 1:
                return RIGHT;
            case 2:
                return DOWN;
            default:
                return LEFT;
        }
    }

    public int getValue() {
        return value;
    }
}
