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
    public static final int SUCCESS             =  1;
    public static final int INVALID_CARD        = -1;
    public static final int HEART_NOT_BROKEN    = -2;

    // Constructor
    /* The constructor for the class HeartEngine, which takes care of the backend processes 
     * of the game of Hearts.
     * @param numPlayers    - The number of players that play the game.
     * @param playerNames   - An array of players' names */
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


    /* Shuffles the standard deck of card in a random order. */
    public void Shuffle() {

    }


    /* Deals Cards from the standard deck of card to all players.
     * The Cards are dealt differently depending on the number of players.
     * When there are:
     *      - 3 players: 2 of Diamond is removed, and each player is dealt 17 cards
     *      - 4 players: Each player is dealt 13 cards.
     *      - 5 players: 2 of Diamond and 2 of Club are removed, and each player is dealth 10 cards. */
    public void DealHand() {

    }


    /* The Game of Hearts require players to pass 3 cards from their initial hand to other players every hand (time takes to play all cards).
     * The cards from one player is passed to another player following a specific rule
     * based on the number of players and the number of hands (Passing rotation).
     * For example, when there are:
     *      - 3 players: Pass the cards to the person on the left on #1 hand, then right on #2 hand, and this continues.
     *      - 4 players: Pass the cards to the person on the left on #1 hand, right on #2 hand, 
     *                   across the player on #3 hand, and no passing on #4 hand. This continues.
     *      - 5 players: Pass the cards to the person on the left on #1 hand, then right on #2 hand, and this continues. */
    public void PassHand() {

    }


    /* Indicates whether the Heart has been broken.
     * @return  - true when the Heart has been broken, false otherwise. */
    public boolean GetIsHeartBroken() {
        return false;
    }


    /* Adjusts the boolean value of the isHeartBroken variable, which represents if the Heart has been broken yet.
     * @param isHeartBroken - The boolean status of whether the heart has been broken yet in the game */
    public void SetIsHeartBroken(boolean isHeartBroken) {
        
    }


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
     * @param newCurrPlayer - A new Player object who will be playing a card to make up a trick */
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
     * @param card  - String representation of a card.
     * @return      - A Card object that corresponds to the specified String representation. 
     *                Returns null if there is no such Card. */
    public Card ConvertToCard(String card) {
        return null;
    }

}
