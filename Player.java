public class Player {
    // Instance variables
    private Card[] playerCards;
    private Card[] playerTricks;
    private int playerPoints;
    private String playerName;
    private int playerId;
    private Card cardThrown;

    // Constructor 
    /* The constructor of the Player class, which represents the person who plays the game.
     * @param playerName    - The name of the player.
     * @param playerId      - A unique id of the player. */
    public Player(String playerName, int playerId) {

    }


    // Private helper method
    /* Removes a specified card from the player's hand.
     * @param card  - The card to be removed from the player's hand */
    private void removePlayerCard(Card card) {
        
    }


    // Public methods
    /* Obtains the name of the player.
     * @return  - the name of the player. */
    public String GetPlayerName() {
        return null;
    }


    /* Obtains the id of the player.
     * @return  - the id of the player. */
    public int GetPlayerId() {
        return 0;
    }


    /* Obtains the player's hand as an array of Card objects.
     * @return  - The player's hand as a 1D array of Card objects. */
    public Card[] GetPlayerCards() {
        return null;
    }


    /* Updates the player's hand to a specified array of Card objects.
     * @param cards - 1D array of Card objects, which will be set to the player's hand */
    public void SetPlayerCards(Card[] cards) {

    }


    /* Obtains the player's accumulated points as they play the game.
     * @return  - The player's points. */
    public int GetPlayerPoints() {
        return 0;
    }


    /* Updates the player's points to a specified value.
     * @param point - The player's new point */
    public void SetPlayerPoints(int point) {

    }


    /* Obtains the array of Card objects that the player 
     * has collected as tricks each hand (the time it takes to play all cards)
     * @return  - An array of Card objects that contains tricks the player has collected. */
    public Card[] GetPlayerTricks() {
        return null;
    }


    /* Updates the player's tricks to a specified one.
     * @param cards - 1D array of Card objects that represent a 
     *                new pile of tricks that the player holds. */
    public void SetPlayerTricks(Card[] cards) {

    }

    /* Obtains the Card object that the player has thrown in a trick.
     * @return - The card object that the player has thrown in current trick */
    public Card GetCardThrown() {
        return null;
    }


    /* Updates the Card that the player throws each trick.
     * @param card  - A player's Card that is to be thrown during trick
     * @return      - Returns 1 if the player has the Card specified.
     *                Returns -1 if the player does not have the Card specified. */
    public int SetCardThrown(Card card) {
        return 0;
    }

    /* Adds several Card objects into a specified array of Card objects.
     * @param newCards  - The array of Card objects that are to be added to a specified array
     * @param array     - The array of Card objects where newCards will be added to. */
    public void AddCardsToArray(Card[] newCards, Card[] array) {
        
    }

}