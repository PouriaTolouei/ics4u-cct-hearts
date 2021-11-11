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
    private int losingPoint;
    private int leadSuit;

    // Global variable representing the default losing point
    public static final int DEFAULT_LOSING_POINT = 50;

    // Global variables representing error codes
    public static final int SUCCESS             =  1;
    public static final int INVALID_CARD        = -1;
    public static final int HEART_NOT_BROKEN    = -2;
    public static final int SHOT_THE_MOON       = -3;

    // Constructor
    /* The constructor for the class HeartEngine, which takes care of the backend processes 
     * of the game of Hearts.
     * @param numPlayers    - The number of players that play the game.
     * @param playerNames   - An array of players' names 
     * @param losingPoint   - The game ends after a player achieves this point */
    public HeartEngine(int numPlayers, String[] playerNames, int losingPoint) {

    }

    // == Private Methods ===



    // === Public Methods ===

    /* Calculates and updates the points earned by each Player according to their tricks.
     * @return  - The point earned by a Player based on their trick. */
    // REMOVE THIS COMMENT: Shouldn't this either be void because it only 
    //                      manipulates the Players' points and return nothing,
    //                      OR accept a parameter like (Player currPlayer) and return 
    //                      a point earned by that player.
    public int CalcPoint() {
        return 0;
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


    /* The trick will be given to a player who played a card with highest rank, 
     * and returns the player id who collected the trick.
     * @return  - The player id of a Player who collected the trick.
     *            This player leads the next hand. */
    public int CollectTrick() {
        return 0;
    }


    /* Converts a card expressed in String to a Card object.
     * @param card  - String representation of a card. 
     *                The suit is represented by C (clubs), D (diamonds), H (hearts), and S (spades).
     *                The rank is represented by numbers from 1 to 13 (Ace to King)
     *                (e.g. C-A => Ace of Club, H-2 => 2 of Heart, S-Q => Queen of Spade)
     * @return      - A Card object that corresponds to the specified String representation. 
     *                Returns null if there is no such Card. */
    public Card ConvertToCard(String card) {
        return null;
    }

    // By Pouria
    /* 
     * */
    public int GetLeadSuit() {
        return 0;
    }

    
    // By Haruki
    /* Updates the leading suit of the current trick to the specified one.
     * @param suitId    - The numerical id of a suit, which is declared in Card class. */
    public void SetLeadSuit(int suitId) {
        this.leadSuit = suitId;
    }


    // By Haruki
    /* Shuffles the standard deck of card in a random order. */
    public void Shuffle() {

    }


    // By Haruki
    /* Deals Cards from the standard deck of card to all players.
     * The Cards are dealt differently depending on the number of players.
     * When there are:
     *      - 3 players: 2 of Diamond is removed, and each player is dealt 17 cards
     *      - 4 players: Each player is dealt 13 cards.
     *      - 5 players: 2 of Diamond and 2 of Club are removed, and each player is dealth 10 cards. */
    public void DealHand() {

    }
    
    
    // By Haruki
    /* Returns the current player of the current trick.
     * @return  - A Player object who is currently playing a card to make up a trick */
    public Player GetCurrPlayer() {
        return null;
    }


    // By Haruki
    /* Replaces current player with a new player.
     * @param newCurrPlayer - A new Player object who will be playing a card to make up a trick */
    public void SetCurrPlayer(Player newCurrPlayer) {
        
    }


    // By Haruki
    /* Switches the current player. */
    public void SwitchPlayer() {

    }


    // By Haruki
    /* Returns an array of Card objects that has been discarded by Players, which make up a trick.
     * @return  - An array of Card objects that make up a trick. */
    public Card[] GetCardsThrown() {
        return null;
    }


    // By Haruki
    /* Returns an array that contains player ids of the winner(s).
     * @return  - An array of player ids of the winner(s) */
    public int[] CheckWinner() {
        return null;
    }


    // By Haruki
    /* Adds several Card objects into a specified array of Card objects.
     * @param destArray - The destination array of Card objects where newCards will be added to.
     * @param newCards  - The array of Card objects that are to be added to a specified array.
     * @return          - An array of Card objects that include the original elements as well as
     *                    new elements */
    public Card[] AddCardsToArray(Card[] destArray, Card[] newCards) {
        // If the array of new Card objects is null or empty, it will simply return the 
        if (newCards == null || newCards.length == 0) {
            return destArray;
        }

        // If the newCards is not null or empty, create a new array with the length 
        // that is larger than the destination array's length by the length of newCards
        Card[] updatedArray = new Card[destArray.length + newCards.length];

        // Copy all of the elements in the destination array to the updatedArray
        for (int i = 0; i < destArray.length; i++) {
            updatedArray[i] = destArray[i];
        }

        // Add all of the elements in the newCards at the end of the updatedArray
        for (int i = 0; i < newCards.length; i++) {
            updatedArray[destArray.length + i] = newCards[i];
        }

        return updatedArray; // Return the array with newly added elements
    }

}
