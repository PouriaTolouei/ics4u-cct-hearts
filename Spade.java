public class Spade extends Card {
    private final int SPADE_POINT = 0;
    private final int SPADE_Q_POINT = 13;

    public Spade (int rank) {
        super(rank);
    }

    public int GetPoint() {
        if (super.GetRank() == Card.CARD_QUEEN) {
            return SPADE_Q_POINT;
        }
        return SPADE_POINT;
    }

    public int GetSuit() {
        return Card.SPADE;
    }
}
