public class Heart extends Card {
    private final int HEART_POINT = 1;

    public Heart (int rank) {
        super(rank);
    }

    public int GetPoint() {
        return HEART_POINT;
    }

    public int GetSuit() {
        return Card.HEART;
    }
}
