package enums;

public enum PlayerType {
    PURPLE(0),
    BLUE(1),
    RED(2),
    GREEN(3);

    private int value;

    PlayerType(int value) {
        this.value = value;
    }

    public static PlayerType from(int playerValue) {
        for(PlayerType playerType : PlayerType.values()) {
            if(playerType.value == playerValue) {
                return playerType;
            }
        }
        throw new RuntimeException("Could not find PlayerType Enum with value " + playerValue);
    }

    public int getValue() {
        return value;
    }
}
