// Programmed by: Koh Haruki
public class Heart extends Card {
    // The private constants that represent the points the card of Heart worth
    private final int HEART_POINT = 1;

    // The constructor 
    public Heart (int rank) {
        // Calls the superclass's constructor, and 
        // instantiates the card of Heart with specified rank
        super(rank);
    }

    /* Obtains the point of the card.
     * @return  - Returns 1, the worth of a card of Heart. */
    public int GetPoint() {
        return HEART_POINT;
    }

    /* Obtains the integer representation of the card's suit.
     * @return  - Returns 2, one of the global constants in Card class
     *            that represent the suit of Heart. */
    public int GetSuit() {
        return Card.HEART;
    }
}
