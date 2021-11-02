public class HeartEngine {
    // Instance variables
    private Card[] standardDeck;
    private Card[] cardsThrown;
    private Player[] allPlayers;
    private int numPlayers;
    private int numRounds;
    private boolean isHeartBroken;
    private boolean isShootingMoon;

    // Global variables representing error codes
    public static final int SUCCESS = 0;
    public static final int INVALID_CARD = 0;
    public static final int HEART_NOT_BROKEN = 0;

    // Constructor
    /* 
     *
     * */
    public HeartEngine(int numPlayers, String[] playerNames) {

    }


    // Public methods
    /*
     *
     * */
    public int CalcPoint() {
        return 0;
    }

    /*
     *
     * */
    public void PassHand() {

    }

    /*
     *
     * */
    public boolean GetIsHeartBroken() {
        return false;
    }

    /*
     *
     * */
    public Player[] GetAllPlayers() {
        return null;
    }

    /*
     *
     * */
    public Card[] GetCardsThrown() {
        return null;
    }

    /*
     *
     * */
    public int CollectTrick() {
        return 0;
    }

    /*
     *
     * */
    public Card ConvertToCard(String card) {
        return null;
    }

}
