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
     * This instantiates all the instance variables, but for playerCards and playerTricks arrays they're set to be empty arrays, 
     * Since they vary depending on the gameplay and number of players in the game (so they get instantiated in HeartEngine methods).
     * @param playerName    - The name of the player.
     * @param playerId      - A unique id of the player. */
    public Player(String playerName, int playerId) {
        this.playerCards = new Card[0]; // Empty array.
        this.playerTricks = new Card[0]; // Empty array.
        this.playerPoints = 0; // Default value is assigned.
        this.playerName = playerName; // Name paramter is assigned.
        this.playerId = playerId; // ID Paramter is assigned.
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
            if (this.playerCards[i].equals(card)){
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

    // By Haruki
    /* It parses through the player's cards, and return an array of Card objects 
     * that only contain the suit specified.
     * @param suit  - The suit of interest represented by an integer. (Refer to Card class's global variables)
     * @return      - An array of Card objects that only contain the Cards of specified suit */
    private Card[] sortBySuit(int suit) {
        // Instantiate "untrimmed" array of Card object that can store up to 13 Cards
        // which is the maximum number of Cards in each suit.
        Card[] cardsOfSuitUntrimmed = new Card[13];

        // currIndex plays a role in placing a matching Card from playerCards to the correct pos of cardsOfSuitUntrimmed
        // currIndex also represents the number of Cards of specified suit
        int currIndex = 0;

        // Iterates through every Card of the playerCards, and add a Card with matching suit to cardsOfSuitUntrimmed
        for (int i = 0; i < this.playerCards.length; i++) {
            if (this.playerCards[i].GetSuit() == suit) {
                cardsOfSuitUntrimmed[currIndex] = this.playerCards[i];
                currIndex++; // Increments so that the next matching Card will be added to the correct position
            }
        }

        // Instantiate the "trimmed" (free of null) array of Card object
        Card[] cardsOfSuitTrimmed = new Card[currIndex];
        // Copies all Cards from the "untrimmed" array to "trimmed" array
        for (int j = 0; j < cardsOfSuitUntrimmed.length; j++) {
            cardsOfSuitTrimmed[j] = cardsOfSuitUntrimmed[j];
        }
        // Returns the trimmed version of the array that only consists of Card objects of specified suit
        return cardsOfSuitTrimmed;
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
     * @return      - Returns true if the player has a card of the specified suit.
     *              - Returns false if the player doesn't have a card of the specified suit.  */
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
    /* Updates the Card that the player throws each trick and removes that from the player's cards(hand).
     * @param card  - A player's Card that is to be thrown during trick. 
     * @ return     - Returns 1 if thrown card was set succesfully.
     *              - Returns -1 if the player doesn't have that card. */
    public int SetCardThrown(Card card) {
        // Loops through the player's hand (Card array).
        for (int i = 0; i < this.playerCards.length; i++){
            // Makes sure that the player has the Card object that they want to throw (checks for both matching suit and rank).
            if (this.playerCards[i].equals(card)){ 
                this.cardThrown = card; // Sets the new Card object that the player wants to throw/play.
                removePlayerCard(card); // Removes the Card object that is about to be thrown from player's cards.
                return HeartEngine.SUCCESS; // Card thrown has been succesfully set.
            }
        }
        // Otherwise the card was not found, so error value is returned.
        return HeartEngine.INVALID_CARD;
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

    
    // By Haruki & Pouria
    /* Sorts player's cards (their hand) by suit as well as the ranks. */
    public void SortPlayerCards() {
        // A temporary 2D array of Card objects.
        // Each row represents each suit, and 13 columns represent the maximum
        // number of cards each suit can have.
        Card[][] card2d = new Card[4][13];
        
        // Obtains cards of each suit from the player's cards
        // and assigns it to each row of the card2d.
        // Each suit is then sorted by the card's rank in an ascending order.
        // The variable i works both as an index as well as integer representation of suits
        // e.g. i = 0 (Card.CLUB), i = 1 (Card. Diamond) etc 
        for (int i = 0; i < 4; i++) {
            card2d[i] = sortBySuit(i);
            sortByRank(card2d[i]);
        }
        
        // Represent the current index of the playerCards
        int count = 0;
        
        // Parses through each cell of the card2d and merges each Card into playerCards
        for (int i = 0; i < card2d.length; i++) {
            for (int j = 0; j < card2d[i].length; j++) {
                // Ensures the Card object is not null
                if (card2d[i][j] != null) {
                    // Adds each Card object in card2d into playerCards
                    this.playerCards[count] = card2d[i][j];
                    count++; // Updates the current index of the playerCards
                } else {
                    // Breaks out of the inner loop when there is a null Object for efficiency purpose.
                    // As this signifies that there are no more Card objects after that index
                    // for the current row/suit
                    break;
                }
            }
        }
    }

}