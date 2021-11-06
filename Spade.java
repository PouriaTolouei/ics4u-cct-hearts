// Programmed by: Koh Haruki
public class Spade extends Card {
    // The private constants that represent the points the card of Spade worth
    private final int SPADE_POINT = 0; // Any normal card of Spade worth 0 point
    private final int SPADE_Q_POINT = 13; // Queen of Spade worth 13 points

    // The constructor 
    public Spade (int rank) {
        // Calls the superclass's constructor, and 
        // instantiates the card of Spade with specified rank
        super(rank);
    }

    /* Obtains the point of the card.
     * @return  - Returns 13 if the card is a Queen of Spade, 0 otherwise. */
    public int GetPoint() {
        // Checks if the card is Queen. If yes, return 13, otherwise return 0
        if (super.GetRank() == Card.CARD_QUEEN) {
            return SPADE_Q_POINT;
        }
        return SPADE_POINT;
    }

    /* Obtains the integer representation of the card's suit.
     * @return  - Returns 3, one of the global constants in Card class
     *            that represent the suit of Spade. */
    public int GetSuit() {
        return Card.SPADE;
    }
}
