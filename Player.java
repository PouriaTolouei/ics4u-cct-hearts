public class Player {
    // Instance variables
    private Card[] playerCards;
    private Card[] playerTricks;
    private int playerPoints;
    private String playerName;
    private int playerId;
    private Card cardThrown;

    // Constructor 
    // By Pouria
    /* The constructor of the Player class, which represents the person who plays the game.
     * This instantiates all the instance variables except for playerCards and playerTricks arrays 
     * Since they vary depending on the gameplay and number of players in the game (so they get instantiated in HeartEngine).
     * @param playerName    - The name of the player.
     * @param playerId      - A unique id of the player. */
    public Player(String playerName, int playerId) {
        this.playerName = playerName; // Name paramter is assigned.
        this.playerId = playerId; // ID Paramter is assigned.
        this.playerPoints = 0; // Default value is assigned.
        this.cardThrown = null; // Default value is assigned.
    }


     // === Private Helper Methods ===
    
    // By Pouria
    /* Removes a specified card from the player's hand.
     * @param card  - The card to be removed from the player's hand */
    private void removePlayerCard(Card card) {
        Card[] temp = new Card[this.playerCards.length - 1]; // Creates a temporay array with one less card.
        int counter = 0; // Keeps track of the position in the temporary array.

        // Loops through the player's hand (Card array).
        for (int i = 0; i < this.playerCards.length; i++){
            // Checks both the rank and suit to make sure that it doesn't match with the card that needs to be removed.
            if (this.playerCards[i].GetRank() != card.GetRank() && this.playerCards[i].GetSuit() != card.GetSuit()){
                temp[counter] = playerCards[i]; // Copies the card from the player's hand to the temporary array as long as it's not the card that needs to be removed.
                counter++; // Increments the position in the temporary array when a value has been sucesfully copied to the temporary array.
            }
            // Otherwise if it's the card that needs to be removed, it's simply skipped and not added to the temporary array.
        }
        this.playerCards = temp; // The reference of the temporary array is assigned back to player's hand, so now it doesn't have the removed card.
    }

    // By Pouria
    /* Sorts a 1D array of Card objects by their rank in asceding order meaning from 2 (lowest) to Ace (highest).
     * @param cards  - The 1D array of Card objects to be sorted by rank. */
    private void sortByRank(Card[] cards){
        // Loops through the Card object array. 
        for (int i = 1; i < cards.length; i++){
            Card currElement = cards[i]; // Stores the current Card object.
            int j = i - 1; // Sets the position for starting to look for a larger rank (one before the current Card object).
            
            // Loops through all the Card obejcts before the current Card object.
            while (j >= 0 && cards[j].GetRank() > currElement.GetRank()){
                // If a Card object has a larger rank than the current Card object, it's moved one position forward
                cards[j + 1] = cards[j]; 
                j--;
            }
            cards[j + 1] = currElement; // When there are no more Card objects with a higher rank before it, the current Card object is set at that position.
        }
    }

    // === Public Methods ===

    // By Pouria
    /* Obtains the name of the player.
     * @return  - the name of the player. */
    public String GetPlayerName() {
        return this.playerName; // returns the player's name (given at the beiginning of the game).
    }


    // By Pouria
    /* Obtains the id of the player.
     * @return  - the id of the player. */
    public int GetPlayerId() {
        return this.playerId; // return the player's unique id (ranges from 0 to 4 for the 5 potential players).
    }


    // By Pouria
    /* Obtains the player's hand as an array of Card objects.
     * @return  - The player's hand as a 1D array of Card objects. */
    public Card[] GetPlayerCards() {
        return this.playerCards; // Returns the 1D array of Card objects that represent the player's hand.
    }


    // By Pouria
    /* Updates the player's hand to a specified array of Card objects.
     * @param cards - 1D array of Card objects, which will be set to the player's hand */
    public void SetPlayerCards(Card[] cards) {
        this.playerCards = cards; // Passes the refernce of a new array to represent the player's hand.
    }


    // By Pouria
    /* Indicates whether the player has a Card of a specified suit.
     * @param suit  - An integer representation of the suit 
     * @return      - Returns true if the player has the card of a specified suit, false otherwise */
    public boolean HasSuit(int suit) {
        // Loops through the player's hand (Card array).
        for (int i = 0; i < this.playerCards.length; i++){
            // Checks to see if any card has the specified suit and return true and end the loop if so.
            if(this.playerCards[i].GetSuit() == suit){
                return true;
            }
        }
        // Otherwise no card was found with the specified suit.
        return false;
    }


    // By Pouria
    /* Updates the Card that the player throws each trick.
     * @param card  - A player's Card that is to be thrown during trick */
    public void SetCardThrown(Card card) {
        this.cardThrown = card; // Passes the reference of a new card object that the player wants to throw/play.
    }


    // By Haruki
    /* Obtains the Card object that the player has thrown in a trick.
     * @return - The card object that the player has thrown in current trick */
    public Card GetCardThrown() {
        // Returns the Card object that the player has thrown/played in a trick.
        return this.cardThrown;
    }


    // By Haruki
    /* Obtains the player's accumulated points as they play the game.
     * @return  - The player's points. */
    public int GetPlayerPoints() {
        // Returns the player's accumulated point throughout the game
        return this.playerPoints; 
    }


    // By Haruki
    /* Updates the player's points to a specified value.
     * @param point - The player's new point */
    public void SetPlayerPoints(int point) {
        // Updates the player's point to a specified one
        this.playerPoints = point;
    }


    // By Haruki
    /* Obtains the array of Card objects that the player 
     * has collected as tricks each hand (the time it takes to play all cards)
     * @return  - An array of Card objects that contains tricks the player has collected. */
    public Card[] GetPlayerTricks() {
        // Returns the player's tricks, which is 
        // a pile of cards that the player has collected through each hand
        return this.playerTricks;
    }


    // By Haruki
    /* Updates the player's tricks to a specified one.
     * @param cards - 1D array of Card objects that represent a 
     *                new pile of tricks that the player holds. */
    public void SetPlayerTricks(Card[] cards) {
        // Updates the player's tricks to the specified one
        this.playerTricks = cards;
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