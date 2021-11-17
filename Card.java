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

    // By Pouria
    /* Converts the Card object into its String version for displaying purposes (eg. 2 of hearts --> "H-2"). */
    public String toString() {
        String stringCard = null; // Stores the string version of the Card object 

        
        // Checks for the suit of each Card object to store the proper suit.
        switch (this.GetSuit()){
            case Card.CLUB:
                stringCard = "C-";
                break;
            case Card.DIAMOND:
                stringCard = "D-";
                break;
            case Card.HEART:
                stringCard = "H-";
                break;
            case Card.SPADE:
                stringCard = "S-";
                break;
        }
        // Checks for the rank ofeach Card object to store the proper rank.
        switch (this.GetRank()){
            // Non-numbered ranks get converted into their names before getting stored.
            case Card.CARD_JACK:
                stringCard += "J ";
                break;
            case Card.CARD_QUEEN:
                stringCard += "Q ";
                break;
            case Card.CARD_KING:
                stringCard += "K ";
                break;
            case Card.CARD_ACE:
                stringCard += "A ";
                break;
            // Numbered ranks are directly stored.
            default:
                stringCard += this.GetRank();
        }
        
        return stringCard; // Returns the String version of the Card object.
    }

    public abstract int GetPoint();

    public abstract int GetSuit();
}
