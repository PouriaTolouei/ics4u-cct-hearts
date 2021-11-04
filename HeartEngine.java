public class HeartEngine {
    // Instance variables
    private Card[] standardDeck;
    private Card[] cardsThrown;
    private Player[] allPlayers;
    private int numPlayers;
    private int numRounds;
    private boolean isHeartBroken;
    private boolean isShootingMoon;
    private Player currPlayer;

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
    /* Calculates and updates the points earned by each Player according to their tricks.
     * @return  - The point earned by a Player based on their trick. */
    // REMOVE THIS COMMENT: Shouldn't this either be void because it only 
    //                      manipulates the Players' points and return nothing,
    //                      OR accept a parameter like (Player currPlayer) and return 
    //                      a point earned by that player.
    public int CalcPoint() {
        return 0;
    }

    // SUGGESTION: I think we should add a DealHand() method

    // SUGGESTION: I think we should add a Shuffle() method

    /* The Game of Hearts require players to pass 3 cards from their initial hand to other players each round.
     * The cards from one player is passed to another player following a specific rule
     * based on the number of players and the number of rounds (Passing rotation).
     * For example, when there are:
     *      - 3 players
     *      - 4 players
     *      - 5 players
     * */
    public void PassHand() {

    }

    /* Indicates whether the Heart has been broken.
     * @return  - true when the Heart has been broken, false otherwise. */
    public boolean GetIsHeartBroken() {
        return false;
    }

    // SUGGESTION: We need SetIsHeartBroken() method

    /* Returns an array consisting of all Player objects.
     * @return  - An array of all Player objects. */
    public Player[] GetAllPlayers() {
        return null;
    }

    /* Returns the current player of the current trick.
     * @return  - A Player object who is currently playing a card to make up a trick */
    public Player GetCurrPlayer() {
        return null;
    }

    /* Replaces current player with a new player.
     * @param   - A new Player object who will be playing a card to make up a trick */
    public void SetCurrPlayer(Player newCurrPlayer) {
        
    }

    /* Switches the current player. */
    public void SwitchPlayer() {

    }

    /* Returns an array of Card objects that has been discarded by Players, which make up a trick.
     * @return  - An array of Card objects that make up a trick. */
    public Card[] GetCardsThrown() {
        return null;
    }

    /* The trick will be given to a player who played a card with highest rank, 
     * and returns the player id who collected the trick.
     * @return  - The player id of a Player who collected the trick */
    public int CollectTrick() {
        return 0;
    }

    /* Converts a card expressed in String to a Card object.
     * @return  - A Card object that corresponds to the specified String representation. */
    public Card ConvertToCard(String card) {
        return null;
    }

}
