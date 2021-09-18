package model;

import model.tiles.Tile;

import java.util.Stack;

public class Player extends Piece {

    private String colour;
    private Stack<Card> hand;

    // Home tile information
    private Tile homeTile;
    private boolean returnedHome = false;

    /**
     * Constructor
     *
     * @param row starting row on the board
     * @param col starting column on the board
     * @param colour player colour identifier
     */
    public Player(int row, int col, String colour) {
        super(row, col);

        this.colour = colour;
    }

    /**
     * Draws the next card for player
     */
    public void goToNextCard() {
        if(hand.size() != 0) {
            // remove obtained treasure
            hand.pop();
        }
    }

    /**
     * sets the top card of hand to this new card
     * @param card The card to be sent to the top of the stack
     */
    public void addToHand(Card card){
        hand.push(card);
    }

    /**
     * Gets the top card from the hand stack
     *
     * @return the top card from hand or null if hand is empty
     */
    public Card getTopOfHand() {
        if(hand.size() > 0) {
            return hand.peek();
        }

        return null;
    }

    /**
     * Checks if the player has won by checking if player still has cards left
     * @return if the player has won
     */
    public boolean hasCollectedAll() {
        return this.hand.empty();
    }

    // Getters and setters
    public void setHand(Stack<Card> hand) {
        this.hand = hand;
    }

    public Stack<Card> getHand() {
        return hand;
    }

    public void setReturnedHome(boolean returnedHome) {
        this.returnedHome = returnedHome;
    }

    public boolean hasReturnedHome() {
        return returnedHome;
    }

    public void setHomeTile(Tile homeTile) {
        this.homeTile = homeTile;
    }

    public Tile getHomeTile() {
        return homeTile;
    }
}
