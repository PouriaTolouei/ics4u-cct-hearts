public class Diamond extends Card {
    private static final int DIAMOND_POINT = 0;

    public Diamond (int rank) {
        super(rank);
    }

    public int GetPoint() {
        return DIAMOND_POINT;
    }

    public int GetSuit() {
        return Card.DIAMOND;
    }
}
