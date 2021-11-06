// Programmed by: Pouria Tolouei
public class Club extends Card {
    
    // Private static constant for how many points each card carries.
    private final int CLUB_POINT = 0; // Any card from the club suit is worth 0 points.

    // Constructor
    public Club (int rank) {
        super(rank); // Calls the superclass (Card) constructor, and passes the card rank to initiate the club card there.
    }

    /* Gets the point of the card.
     * @return  - Returns 0 (all club cards are worth 0 points). */
    public int GetPoint() {
        return CLUB_POINT;
    }

    /* Gets the suit of the card.
     * @return  - Returns 0 (global constant for the club suite). */
    public int GetSuit() {
        return Card.CLUB;
    }
}
