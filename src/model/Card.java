package model;

public class Card {

    // Treasure to collect for this card
    private Treasure treasure;

    /**
     * Constructor
     *
     * @param treasure indicated treasure to collect
     */
    public Card(Treasure treasure) {
        this.treasure = treasure;
    }

    // Setters and getters
    public Treasure getTreasure() {
        return treasure;
    }

}
