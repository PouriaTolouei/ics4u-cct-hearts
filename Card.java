public abstract class Card {
    // === INSTANCE VARIABLE ===
    private int rank;

    // === GLOBAL VARIABLES ===
    // Constants representing the rank
    public static final int CARD_ACE    = 14;
    public static final int CARD_KING   = 13;
    public static final int CARD_QUEEN  = 12;
    public static final int CARD_JACK   = 11;

    // Constants that represent each suit
    public static final int CLUB        = 0;
    public static final int DIAMOND     = 1;
    public static final int HEART       = 2;
    public static final int SPADE       = 3;
    
    // By Pouria
    // Constructor for the Card object (Assigns the rank of the card)
    public Card(int rank) {
        this.rank = rank;
    }

    // By Haruki
    /* Obtains the rank of the Card, ranging from 2 to 14, where 14 represents Ace.
     * @return  - The rank of the card.*/
    public int GetRank() {
        return this.rank;
    }

    // By Haruki & Pouria
    /* Indicates whether this Card has the same suit and rank as the card that is compared against.
     * @param card  - The Card to be checked against */
    public boolean equals(Card card) {
        // Returns true if their rank and suit are the same, false otherwise. 
        if (this.GetRank() == card.GetRank() && this.GetSuit() == card.GetSuit()) {
            return true;
        }
        return false;
    }

    public abstract int GetPoint();

    public abstract int GetSuit();
}
