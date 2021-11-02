public abstract class Card {
    // === INSTANCE VARIABLE ===
    private int rank;

    // === GLOBAL VARIABLES ===
    public static final int CARD_ACE    = 14;

    // These are probably unnecessary
    public static final int CARD_KING   = 13;
    public static final int CARD_QUEEN  = 12;
    public static final int CARD_JACK   = 11;
    public static final int CARD_TEN    = 10;
    public static final int CARD_NINE   = 9;
    public static final int CARD_EIGHT  = 8;
    public static final int CARD_SEVEN  = 7;
    public static final int CARD_SIX    = 6;
    public static final int CARD_FIVE   = 5;
    public static final int CARD_FOUR   = 4;
    public static final int CARD_THREE  = 3;
    public static final int CARD_TWO    = 2;

    // Constants that represent each suit
    public static final int CLUB        = 0;
    public static final int DIAMOND     = 1;
    public static final int HEART       = 2;
    public static final int SPADE       = 3;
    
    // Constructor for the Card object
    public Card(int rank) {
        this.rank = rank;
    }

    public int GetRank() {
        return this.rank;
    }

    public abstract int GetPoint();

    public abstract int GetSuit();
}
