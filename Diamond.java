// Programmed by: Pouria Tolouei
public class Diamond extends Card {
    
    // Private constant for how many points each card carries.
    private final int DIAMOND_POINT = 0; // Any card from the diamond suit is worth 0 points.

    // Constructor
    public Diamond (int rank) {
        super(rank); // Calls the superclass (Card) constructor, and passes the card rank to initiate the diamond card there.
    }

    /* Gets the point of the card.
     * @return  - Returns 0 (all diamond cards are worth 0 points). */
    public int GetPoint() {
        return DIAMOND_POINT;
    }

    /* Gets the suit of the card.
     * @return  - Returns 1 (global constant for the diamond suite). */
    public int GetSuit() {
        return Card.DIAMOND;
    }
}
