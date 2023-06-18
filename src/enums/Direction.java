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

    public static Direction from(int directionValue) {
        for(Direction direction : Direction.values()) {
            if(direction.getValue() == directionValue) {
                return direction;
            }
        }

        throw new RuntimeException("Could not find Direction Enum with value " + directionValue);
    }

    public int getValue() {
        return value;
    }
}
