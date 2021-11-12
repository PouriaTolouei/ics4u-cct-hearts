public class HeartEngine {
    // Instance variables
    private Card[] standardDeck;
    private Card[] cardsThrown;
    private Player[] allPlayers;
    private int numPlayers;
    private int numRoundHand;
    private boolean isHeartBroken;
    private Player currPlayer;
    private int losingPoint;
    private int leadSuit;

    // Global variable representing the default losing point
    public static final int DEFAULT_LOSING_POINT = 50;

    // Global variable representing the point assigned to other players in the event of "shot the moon"
    public static final int SHOT_THE_MOON_POINT = 26;

    // Global variables representing error codes
    public static final int SHOT_THE_MOON       =  2;
    public static final int SUCCESS             =  1;
    public static final int INVALID_CARD        = -1;
    public static final int HEART_NOT_BROKEN    = -2;

    // Constructor
    // By Pouria
    /* The constructor for the class HeartEngine, which takes care of the backend processes 
     * This instantiates all the instance variables and creates the standard deck of cards.
     * of the game of Hearts.
     * @param numPlayers    - The number of players that play the game.
     * @param playerNames   - An array of players' names 
     * @param losingPoint   - The game ends after a player achieves this point */
    public HeartEngine(int numPlayers, String[] playerNames, int losingPoint) {
        this.standardDeck = new Card[52]; // 52 array blocks for the stadard deck of cards.
        this.cardsThrown = new Card[numPlayers]; // Cards thrown each round is one per player.
        this.allPlayers = new Player[this.numPlayers]; // As many array blocks as the number of players.
        this.numPlayers = numPlayers; // number of players parameter is assigned.
        this.numRoundHand = 1; // Rounds start from 1.
        this.isHeartBroken = false; // Defult value is assigned.
        this.currPlayer = allPlayers[0]; // Starts with player 0 (the first player).
        this.losingPoint = losingPoint; // losing point paramter is assigned.
        this.leadSuit = Card.CLUB; // Defult value is assigned.
        
        // Loops through all the players
        for (int i = 0; i < this.numPlayers; i++){
            allPlayers[i] = new Player(playerNames[i], i); // Assigns a name to each from the player names array parameter.
        }

        createStandardDeck(); // Creates a standard deck of card   
    }

    // == Private Methods ===

    // By Pouria
    /* Creates a standard deck of cards (52 cards with 13 cards from each of the 4 suits)*/
    private void createStandardDeck() {
        // Loops through the 4 suits
        for (int i = 0; i < 4; i++){
            // Loops through the 13 cards
            for (int j = 0; j < 13; j++){
                // Checks the current suit and calls the appropriate constructor and passes the rank (j+2 since the ranks start from 2).
                switch (i){
                    case Card.CLUB:
                        new Club(j+2);
                        break;
                    case Card.DIAMOND:
                        new Diamond(j+2);
                        break;
                    case Card.HEART:
                        new Heart(j+2);
                        break;
                    case Card.SPADE:
                        new Spade(j+2);
                        break;
                }
            }
        }
    }
    
    // By Haruki
    /* Deals appropriate number of Cards (refer to DealPlayerCards() Java Doc) to all Players, 
     * while ensuring specific Cards are removed (also refer to DealPlayerCards() Java Doc).
     * @param illegalCards  - An array of Cards that are not removed before dealing Cards to players. 
     *                        The removed Cards depend on the number of players. */
    private void dealCardsExcept(Card[] illegalCards) {
        int handSize = 0;
        // Updates the size of playerCards array depending on the number of players
        switch(this.numPlayers) {
            case 3: // Each player initially has 17 cards in a 3-player game
                handSize = 17;
                break;
            case 4: // Each player initially has 13 cards in a 4-player game
                handSize = 13;
                break;
            case 5: // Each player initially has 10 cards in a 5-player game
                handSize = 10;
                break;
        }

        // Variables used for dealing hands to Players
        int playerCardsIndex; // Represents the current index of each Player's playerCards
        int startIndex = 0; // The starting index at which LOOP #2 starts
        Card[] newPlayerCards; // An array of Cards, which represent the Cards that are dealt to each Player

        // LOOP #1
        // Iterates through each Player, and deal appropriate number of Cards to each Player
        for (int playerId = 0; playerId < this.allPlayers.length; playerId++) {
            // For each iteration of LOOP #1, reset the playerCardsIndex and newPlayerCards.
            playerCardsIndex = 0;
            newPlayerCards = new Card[handSize]; // Instantiate an array of Card objects with an appropriate length according to numPlayers

            // LOOP #2
            // Parses through every Card inside standardDeck and check each of them against the illegalCards. 
            /* This loop will break out when i is beyond the range of standardDeck.length
             * or when playerCardsIndex is beyond the range of handSize.
             * This ensures each Player receives an appropriate number of Cards */
            for (int i = startIndex; i < this.standardDeck.length && playerCardsIndex < handSize; i++) {
                // If the Card at the i-th position in standardDeck is not illegal, add that Card to newPlayerCards
                if (!this.hasMatchingCard(this.standardDeck[i], illegalCards)) {
                    newPlayerCards[playerCardsIndex] = this.standardDeck[i];
                    // Increments the index so that another Card can be added in the correct position of newPlayerCards
                    playerCardsIndex++; 
                }

                // Increments startIndex to synchronize the value with i
                /* The purpose of startIndex is to keep track of how far the standardDeck has been parsed.
                 * And because i starts from startIndex instead of from 0, this prevents dealing the same 
                 * Card to multiple different players. (e.g. Without startIndex, LOOP #2 would only parse through index 0 to handSize) */
                startIndex++;
            }
            // Updates the Player's Cards to the newPlayerCards, which is essentially dealing Cards to them
            this.allPlayers[playerId].SetPlayerCards(newPlayerCards);
        }
    }

    // By Haruki
    /* Checks if there is an identical Card as the given Card within an array of Cards given.
     * @param cardChecked           - The Card object, which is to be checked against 
     *                                cardsCheckedAgainst for potential match
     * @param cardsCheckedAgainst   - An array of Card objects, which are to be compared 
     *                                against cardChecked to see if there's a match 
     * @return                      - Returns true if there is a match, false otherwise. */
    private boolean hasMatchingCard(Card cardChecked, Card[] cardsCheckedAgainst) {
        // When cardsCheckedAgainst is null or empty, return false as it's impossible to have a match
        if (cardsCheckedAgainst == null || cardsCheckedAgainst.length == 0) {
            return false;
        }
        // Iterates through each Card object inside cardsCheckedAgainst and check if 
        // any of them have the same suit and rank as the cardChecked
        for (int i = 0; i < cardsCheckedAgainst.length; i++) {
            if (cardChecked.equals(cardsCheckedAgainst[i])) {
                // If there is an identical Card as cardChecked inside cardsCheckedAgainst array
                // return true to signify there was a match
                return true;
            }
        }
        return false; // Return false if the above check fails, signifying there was no match
    }


    // === Public Methods ===

    // By Pouria
    /* Calculates and updates the points earned by each Player according to their tricks by the end of each hand.
     * @return  - Returns 1 if normal calculations are applied. 
     *          - Returns 2 if 'shot the moon' calculations are applied (when a player reaches 26 points). */

    public int CalcPoint() {
        int points = 0; // Keeps track of the point being added to each player by the end of each hand.
        int index = 0; // Keeps track of a special index in the event of "shot the moon".

        // Loops through all the players
        for (int i = 0; i < allPlayers.length; i++){
            Card[] playerTricks = allPlayers[i].GetPlayerTricks(); // Stores the player's trick in a new array for easier accessing
            
            // Loops through the current player's tricks
            for (int j = 0; j < playerTricks.length; j++){
                points += playerTricks[i].GetPoint(); // Adds the point of each card in the tricks
            }

            // If the point of that player adds up to 26 for that round, they have "shot the moon",
            // Meaning that player has managed to collect all the heart cards and the queen of spade.
            // So that player gets 0 points (no change) and all the other players get 26 points.
            if (points == SHOT_THE_MOON_POINT){

                // Loops through all the other players except the current player ()
                for (int j = i + 1; j < allPlayers.length - 1 + i; j++){
                    // When i exceeds array length, index uses remainder to reset it to 0 and count from there again.
                    // This is done until the index of the player who initiated 'shot the moon' is reached after one loop (length - 1 + i)/
                    index = j % allPlayers.length; 
                    // The points of the player who 'shot the moon' doesn't change and all other players get 26 points.
                    allPlayers[index].SetPlayerPoints(allPlayers[index].GetPlayerPoints() + SHOT_THE_MOON_POINT); 
                }
                return SHOT_THE_MOON; // Shot the moon value is returned (To be used to display a special message)
            }
            
            // Otherwise, it's normal point calculation, meaning points of the cards in each player's tricks is added to each player's points
            else{
                allPlayers[i].SetPlayerPoints(allPlayers[index].GetPlayerPoints() + points);
            }
        }
        return SUCCESS; // Returns success value, indicating normal calculation.
    }

    // By Pouria
    /* The Game of Hearts require players to pass 3 cards from their initial hand to other players every hand (time takes to play all cards).
     * The cards from one player is passed to another player following a specific rule
     * based on the number of players and the number of hands (Passing rotation).
     * For example, when there are:
     *      - 3 players: Pass the cards to the person on the left on #1 hand, then right on #2 hand, and this continues.
     *      - 4 players: Pass the cards to the person on the left on #1 hand, right on #2 hand, 
     *                   across the player on #3 hand, and no passing on #4 hand. This continues.
     *      - 5 players: Pass the cards to the person on the left on #1 hand, then right on #2 hand, and this continues. 
     * @param passCards  - A 2D array of Cards which includes the three cards that are to be passed from each player. */
    public void PassCards(Card[][] passCards) {

    }

    // By Pouria
    /* Indicates whether the Heart has been broken.
     * @return  - true when the Heart has been broken, false otherwise. */
    public boolean GetIsHeartBroken() {
        return false;
    }

    // By Pouria
    /* Adjusts the boolean value of the isHeartBroken variable, which represents if the Heart has been broken yet.
     * @param isHeartBroken - The boolean status of whether the heart has been broken yet in the game */
    public void SetIsHeartBroken(boolean isHeartBroken) {
        
    }

    // By Pouria
    /* Returns an array consisting of all Player objects.
     * @return  - An array of all Player objects. */
    public Player[] GetAllPlayers() {
        return null;
    }

    // By Pouria
    /* The trick will be given to a player who played a card with highest rank, 
     * and returns the player id who collected the trick.
     * @return  - The player id of a Player who collected the trick.
     *            This player leads the next hand. */
    public int CollectTrick() {
        return 0;
    }

    // By Pouria
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
        // Variables for random index as well as temporary Card object for swapping purpose
        int randIndex;
        Card temp;

        // A Card at each index in standardDeck will be swapped with
        // a Card at random index 
        for (int i = 0; i < this.standardDeck.length; i++) {
            // Generates a random index
            randIndex = (int) (Math.random() * this.standardDeck.length);

            // Swaps the Card object at randIndex with Card object at current i
            temp = this.standardDeck[randIndex];
            this.standardDeck[randIndex] = this.standardDeck[i];
            this.standardDeck[i] = temp;
        }
    }


    // By Haruki
    /* Deals Cards from the shuffled standard deck of card to all players.
     * The Cards are dealt differently depending on the number of players.
     * When there are:
     *      - 3 players: 2 of Diamond is removed, and each player is dealt 17 cards
     *      - 4 players: Each player is dealt 13 cards.
     *      - 5 players: 2 of Diamond and 2 of Club are removed, and each player is dealth 10 cards. */
    public void DealPlayerCards() {
        // Shuffles the standard deck of card before dealing cards to players
        this.Shuffle();

        // The Cards that are removed before dealing. Refer to Java Doc.
        Card[] illegalCards;

        // For different number of players, the behaviour of DealHand() varies 
        switch(this.numPlayers) {
            case 3: // In a 3-player game
                // 2 of Diamond is removed before dealing Cards,
                // and appropriate number of Cards are dealt to each Player.
                illegalCards = new Card[] {this.ConvertToCard("D-2")};
                this.dealCardsExcept(illegalCards);
                break;

            case 4: // In a 4-player game
                // No Cards are removed from the deck before dealing Cards,
                // and appropriate number of Cards are dealt to each Player.
                illegalCards = null;
                this.dealCardsExcept(illegalCards);
                break;

            case 5: // In a 5-player game
                // 2 of Diamond and 2 of Club are removed before dealing Cards,
                // and appropriate number of Cards are dealt to each Player.
                illegalCards = new Card[] {this.ConvertToCard("D-2"), this.ConvertToCard("C-2")};
                this.dealCardsExcept(illegalCards);
                break;
        }
    }
    
    
    // By Haruki
    /* Returns the current player of the current trick.
     * @return  - A Player object who is currently playing a card to make up a trick */
    public Player GetCurrPlayer() {
        return this.currPlayer;
    }


    // By Haruki
    /* Replaces current player with a new player.
     * @param newCurrPlayer - A new Player object who will be playing a card to make up a trick */
    public void SetCurrPlayer(Player newCurrPlayer) {
        this.currPlayer = newCurrPlayer; // Updates the currPlayer with a new one
    }


    // By Haruki
    /* Switches the current player. */
    public void SwitchPlayer() {
        // Switches the current Player to the Player with next playerId
        // Player ID of current player in 4-player game: 0 -> 1 -> 2 -> 3 -> 0, and the cycle continues
        this.SetCurrPlayer(this.allPlayers[(this.GetCurrPlayer().GetPlayerId() + 1) % this.numPlayers]);
    }


    // By Haruki
    /* Returns an array of Card objects that has been discarded by Players, which make up a trick.
     * @return  - An array of Card objects that make up a trick. */
    public Card[] GetCardsThrown() {
        return this.cardsThrown;
    }


    // By Haruki
    /* Returns an array that contains player id(s) of the winner(s).
     * There can be multiple winners, such as when multiple players 
     * have the same and lowest points together when the game ends.
     * @return  - An array of player id(s) of the winner(s).
     *            An empty array is returned to signify no one has won yet
     *            There is no winner when:
     *            - No one has exceeded the losingPoint, and thus the game still continues. */
    public int[] CheckWinner() {
        // Keeps track of the greatest and lowest points among the Players
        int maxPoint = this.allPlayers[0].GetPlayerPoints();
        int minPoint = this.allPlayers[0].GetPlayerPoints();
        int numPlayersWithMinPoint = 1; // Keeps track of the number of Players with same minimum point

        // Iterates through every Player (except 0th Player) 
        for (int playerId = 1; playerId < this.allPlayers.length; playerId++) {
            if (this.allPlayers[playerId].GetPlayerPoints() > maxPoint) {
                // If current maxPoint is smaller than the Player's point of the current iteration,
                // then update the maxPoint
                maxPoint = this.allPlayers[playerId].GetPlayerPoints();
            } else if (this.allPlayers[playerId].GetPlayerPoints() < minPoint) {
                // If current minPoint is larger than the Player's point of the current iteration,
                // then update the minPoint
                minPoint = this.allPlayers[playerId].GetPlayerPoints();
                // Also reset the numPlayersWithMinPoint to indicate there's currently only one player
                // with the same minimum point
                numPlayersWithMinPoint = 1;
            } else if (this.allPlayers[playerId].GetPlayerPoints() == minPoint) {
                // If the minPoint and the Player's point of the current iteration are the same,
                // increment the numPlayersWithMinPoint to indicate that there are multiple 
                // people with the same minimum point.
                numPlayersWithMinPoint++;
            }
        }

        // An array of integer storing playerIds of potential winners
        int[] winnerIds = new int[numPlayersWithMinPoint];
        int pos = 0; // Current position of winnerIds

        if (maxPoint >= this.losingPoint) {
            for (int playerId = 0; playerId < this.allPlayers.length; playerId++) {
                if (this.allPlayers[playerId].GetPlayerPoints() == minPoint) {
                    winnerIds[pos] = playerId;
                }
            }
            // Returns an array consisting of ids of winners
            return winnerIds;
        } else {
            // Returns an empty array to signify no one has won yet
            return new int[0];
        }
    }


    // By Haruki
    /* Adds several Card objects into a specified array of Card objects.
     * @param destArray - The destination array of Card objects where newCards will be added to.
     * @param newCards  - The array of Card objects that are to be added to a specified array.
     * @return          - An array of Card objects that include the original elements as well as
     *                    new elements */
    public Card[] AddCardsToArray(Card[] destArray, Card[] newCards) {
        // If the array of new Card objects is null or empty, it will simply return the destArray
        if (newCards == null || newCards.length == 0) {
            return destArray;
        } else if (destArray == null) {
            // If the destArray is null, instantiate it as an array of Card object with length 0.
            destArray = new Card[0]; // The length of 0 signifies that it is empty
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
