public class Club extends Card {
    
    private static final int CLUB_POINT = 0;

    public Club (int rank) {
        super(rank);
    }

    public int GetPoint() {
        return CLUB_POINT;
    }

    public int GetSuit() {
        return Card.CLUB;
    }
}
