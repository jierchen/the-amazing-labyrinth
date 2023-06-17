package model;

import enums.PlayerType;

import java.util.Stack;

public class Player extends Piece {

    private final PlayerType type;
    private final Stack<Treasure> hand = new Stack<>();

    private final int homeRow;
    private final int homeCol;

    public Player(int row, int col, PlayerType type) {
        super(row, col);
        this.homeRow = row;
        this.homeCol = col;
        this.type = type;
    }
    
    public void goToNextCard() {
        if(!hand.empty()) {
            hand.pop();
        }
    }

    public Treasure getTopOfHand() {
        if(!hand.empty()) {
            return hand.peek();
        }

        return null;
    }

    public void addToHand(Treasure treasure){
        hand.push(treasure);
    }

    public boolean hasWon() {
        return this.hand.empty() && this.getRow() == homeRow && this.getCol() == homeCol;
    }

    public PlayerType getType() {
        return type;
    }

    public Stack<Treasure> getHand() {
        return hand;
    }
}
