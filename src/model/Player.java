package model;

import ai.ComputerAgent;
import model.tiles.Tile;

import java.util.Iterator;
import java.util.Stack;

public class Player extends Piece {

    private String colour;
    private Stack<Card> hand;

    // Home tile information
    private int homeRow;
    private int homeCol;
    private boolean returnedHome = false;

    // AI Agent
    private ComputerAgent computerAgent;

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

    public Player clone() {
        Player clonePlayer = new Player(getRow(), getCol(), colour);

        clonePlayer.setHomeRow(homeRow);
        clonePlayer.setHomeCol(homeCol);
        clonePlayer.setReturnedHome(returnedHome);

        // Deep copy stack
        Stack<Card> cloneHand = new Stack<Card>();
        Iterator stackIterator = hand.iterator();

        while(stackIterator.hasNext()) {
            Card currentCard = (Card) stackIterator.next();
            Card cloneCard = new Card(currentCard.getTreasure().clone());

            cloneHand.push(cloneCard);
        }
        clonePlayer.setHand(cloneHand);

        return clonePlayer;
    }

    public int colourToTurn() {
        switch(colour) {
            case "Purple":
                return 0;

            case "Blue":
                return 1;

            case "Red":
                return 2;

            default:
                return 3;
        }
    }

    // Getters and setters
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

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

    public void setComputerAgent(ComputerAgent computerAgent) {
        this.computerAgent = computerAgent;
    }

    public ComputerAgent getComputerAgent() {
        return this.computerAgent;
    }

    public int getHomeRow() {
        return homeRow;
    }

    public void setHomeRow(int homeRow) {
        this.homeRow = homeRow;
    }

    public int getHomeCol() {
        return homeCol;
    }

    public void setHomeCol(int homeCol) {
        this.homeCol = homeCol;
    }
}
