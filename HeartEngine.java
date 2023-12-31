public class HeartEngine {
    // Instance variables
    private Card[] standardDeck; // The standard deck of 52 Cards without Jokers
    private Card[] cardsThrown; // The array of Cards thrown by each Player during a trick (this makes up a trick)
    private Player[] allPlayers; // The array of all Players
    private int numPlayers; // The number of Players
    private int numHandRound; // The number of rounds a hand (the time it takes to play all Cards) occurred
    private int numTrickRound; // The number of rounds a trick (the time it takes for each player to play a card) occurred during a hand
    private int numCardsThrown; // The number of Cards thrown by players during a trick
    private boolean isHeartBroken; // The status of whether the Heart has been broken
    private Player currPlayer; // The current player who plays a card
    private int endingPoint; // The game ends when at least one Player reach or exceeds this point
    private int leadSuit; // The leading suit of a trick
    private Card openingCard; // The Card that must be played to lead the very first trick of each hand

    // Global variable representing the default ending point
    public static final int DEFAULT_ENDING_POINT    = 50;

    // Global variable representing the point assigned to other players in the event of "shot the moon"
    public static final int SHOT_THE_MOON_POINT     = 26;

    // Global variables representing error codes
    public static final int HEART_HAS_BEEN_BROKEN   =  3;
    public static final int SHOT_THE_MOON           =  2;
    public static final int SUCCESS                 =  1;
    public static final int INVALID_CARD            = -1;
    public static final int HEART_NOT_BROKEN        = -2;
    public static final int SKIP_TRICK              = -3;
    public static final int MUST_FOLLOW_SUIT        = -4;
    public static final int ILLEGAL_IN_FIRST_TRICK  = -5;
    public static final int MUST_PLAY_OPENING_CARD  = -6;
    public static final int GIVE_LEAD_TO_NEXT       = -7;


    // Constructor
    // By Pouria
    /* The constructor for the class HeartEngine, which takes care of the backend processes 
     * This instantiates all the instance variables and creates the standard deck of cards.
     * of the game of Hearts.
     * @param numPlayers    - The number of players that play the game.
     * @param playerNames   - An array of players' names 
     * @param endingPoint   - The game ends after a player achieves this point */
    public HeartEngine(int numPlayers, String[] playerNames, int endingPoint) {
        this.standardDeck = new Card[52]; // 52 array blocks for the stadard deck of cards.
        this.cardsThrown = new Card[numPlayers]; // Cards thrown each round is one per player.
        this.allPlayers = new Player[numPlayers]; // As many array blocks as the number of players.
        this.numPlayers = numPlayers; // number of players parameter is assigned.
        this.numHandRound = 1; // Rounds start from 1.
        this.numTrickRound = 1; // Rounds start from 1.
        this.numCardsThrown = 0; // Default value is assigned
        this.isHeartBroken = false; // Defult value is assigned.
        this.currPlayer = allPlayers[0]; // Starts with player 0 (the first player).
        this.endingPoint = endingPoint; // ending point paramter is assigned.
        this.leadSuit = Card.CLUB; // Defult value is assigned.
        
        // Loops through all the players
        for (int i = 0; i < this.numPlayers; i++){
            allPlayers[i] = new Player(playerNames[i], i); // Assigns a name to each from the player names array parameter.
        }

        // Assigns the opening card (for beignning a hand round) to be 3 of Club if there are 5 players.
        if (numPlayers == 5) {
            this.openingCard = ConvertToCard("C-3");
        } 
        // Otherise the opening card should be 2 of Club/
        else {
            this.openingCard = ConvertToCard("C-2");
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
                // Checks the current suit, calls the appropriate constructor, and passes the 13 ranks one by one (j+2 since the ranks start from 2).
                // Each card object is passed to a block in the standard deck Card array (j+(13*i), so that after each suit, it moves on to the next 13 cards).
                switch (i){
                    case Card.CLUB:
                        standardDeck[j+(13*i)] = new Club(j+2);
                        break;
                    case Card.DIAMOND:
                        standardDeck[j+(13*i)] = new Diamond(j+2);
                        break;
                    case Card.HEART:
                        standardDeck[j+(13*i)] = new Heart(j+2);
                        break;
                    case Card.SPADE:
                        standardDeck[j+(13*i)] = new Spade(j+2);
                        break;
                }
            }
        }
    }
    

    // By Pouria
    /* Passes the 3 cards among players in the given direction.
     * For example:
     * - cardsPasser(1, 0, passCards) would pass the 3 cards from each player to the person on the left.
     * - cardsPasser(0, 1 passCards) would pass the 3 cards from each player to the person on the right.
     * - cardsPasser(2, 0 passCards) or (0, 2 passCards) would pass the 3 cards from each player to the person across (for even number of players).
     * @param leftPass   - How many positions to the left should the 3 cards be passed.
     * @param rightPass  - How many positions to the right should the 3 cards be passed.
     * @param passCard   - A 2D array of Cards which includes all the sets of three cards that are to be passed from each player. */
    private void cardsPasser(int leftPass, int rightPass, Card[][] passCards){
        // Loops through all the players.
        for (int i = 0; i < numPlayers; i++){
            Card[] playerCards = allPlayers[(i + leftPass) % numPlayers].GetPlayerCards(); // Passes the Card array of the reciever player into a new array for easier access.
            
            // For this method, either leftPass or RightPass will be 0 (only of them has a value at a time): 
            // For passing to the left, the reciever is moved up by some positions, so that the 3 cards get passed up by some positions (clockwise)
            // For passing to the right, the sender is moved up by some positions, so  that the 3 cards get passed down by some positions (counter-clockwise).
            // ** Remainder is used for the index so that position of [array length] would return back to player 0.
            allPlayers[(i + leftPass) % numPlayers].SetPlayerCards(AddCardsToArray(playerCards, passCards[(i + rightPass) % numPlayers]));
        }
    }
    

    // By Pouria
    /* Finds the first player in the order of the array who threw a lead suit card.
     * @return   - Returns the ID of that player who has the first lead suit card.*/
    private int findFirstThrewLeadSuit(){
        int firstIndex = 0; // Dummy value (Stores the id of the first player who has a lead suit card)
        
        // Loops through all of the players
        for (int i = 0; i < numPlayers; i++){
            Card cardThrown = allPlayers[i].GetCardThrown(); // Stores the current player's thrown card into a new variable for easier access.
            
            // Makes sure the the player has thrown a card, otherwise the player gets skipped.
            if (cardThrown != null){
                // Finds the first player who threw a card with a lead suit
                // Stores the id of that player and breaks the loop 
                if (cardThrown.GetSuit() == leadSuit){
                    firstIndex = i;
                    break;
                }
            }
        }
        return firstIndex; // Return the id of that player.
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
            /* This loop will break out when i is beyond the range of standardDeck.length or when playerCardsIndex is beyond the range of handSize.
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
        // Loops through all the players
        for (int i = 0; i < numPlayers; i++){
            int points = 0; // Keeps track of the point being added to each player by the end of each hand, and also resets it.
            int index = 0; // Keeps track of a special index in the event of "shot the moon", and also resets it.
            
            Card[] playerTricks = allPlayers[i].GetPlayerTricks(); // Stores the player's trick in a new array for easier accessing
            
            // Loops through the current player's tricks
            for (int j = 0; j < playerTricks.length; j++){
                // Makes sure that current Card in player's trick is not null, otherwise it gets skipped.
                if (playerTricks[j] != null){
                    points += playerTricks[j].GetPoint(); // Adds the point of each card in the tricks
                }
            }

            // If the point of that player adds up to 26 for that round, they have "shot the moon",
            // Meaning that player has managed to collect all the heart cards and the queen of spade.
            // So that player gets 0 points (no change) and all the other players get 26 points.
            if (points == SHOT_THE_MOON_POINT){

                // Loops through all the other players except the current player ()
                for (int k = i + 1; k < numPlayers + i; k++){
                    // When i exceeds array length, index uses remainder to reset it to 0 and count from there again.
                    // This is done until the index of the player who initiated 'shot the moon' is reached after one loop (numPlayers + i)/
                    index = k % numPlayers; 
                    // The points of the player who 'shot the moon' doesn't change and all other players get 26 points.
                    allPlayers[index].SetPlayerPoints(allPlayers[index].GetPlayerPoints() + SHOT_THE_MOON_POINT); 
                }
                return SHOT_THE_MOON; // Shot the moon value is returned (To be used to display a special message)
            }
            
            // Otherwise, it's normal point calculation, meaning points of the cards in each player's tricks is added to each player's points
            else{
                allPlayers[i].SetPlayerPoints(allPlayers[i].GetPlayerPoints() + points);
            }
        }
        return SUCCESS; // Returns success value, indicating normal calculation.
    }


    // By Pouria
    /* The Game of Hearts require players to pass 3 cards from their initial hand to other players every hand (time takes to play all cards).
     * The cards from one player is passed to another player following a specific rule
     * based on the number of players and the number of hand round(Passing rotation).
     * For example, when there are:
     *      - 3 players: Pass the cards to the person on the left on #1 hand, then right on #2 hand, and this continues.
     *      - 4 players: Pass the cards to the person on the left on #1 hand, right on #2 hand, 
     *                   across the player on #3 hand, and no passing on #4 hand. This continues.
     *      - 5 players: Pass the cards to the person on the left on #1 hand, then right on #2 hand, and this continues. 
     * @param passCards  - A 2D array of Cards which includes all the sets of three cards that are to be passed from each player. 
     *                   - For example passCards[0] would be an array of the three cards that player 0 is passing. */
    public void PassCards(Card[][] passCards) {
        // Checks to see how many players are in the game
        switch (numPlayers){
            // For 3 players and 5 players, the passing rule is essentially the same
            case 3:
            case 5:
                // Checks whether it's the #1 hand scenario (0) or #2 hand scenario (1) 
                // And uses remainder to simplify to these two scenarios when it goes beyond #2.
                switch ((numHandRound - 1) % 2){
                    case 0:
                        cardsPasser(1, 0, passCards); // Each player passes their 3 cards to the person on the left.
                        break;
                    case 1:
                        cardsPasser(0, 1, passCards); // Each player passes their 3 cards to the person on the right.
                        break; 
                }
                break;
            case 4:
                 // Checks whether it's the #1 hand scenario (0), #2 hand scenario (1), #3 hand scenario (2), or #4 hand scenario (nothing happens)
                 // And uses remainder to simplify to these four scenarios when it goes beyond #4.
                switch ((numHandRound - 1) % 4){
                    case 0:
                        cardsPasser(1, 0, passCards); // Each player passes their 3 cards to the person on the left.
                        break;
                    case 1:
                        cardsPasser(0, 1, passCards); // Each player passes their 3 cards to the person on the right.
                        break;
                    case 2:
                        cardsPasser(0, 2, passCards); // Each player passes their 3 cards to the person across.
                        break;
                }
                break;
        }
    }


    // By Pouria
    /* The trick will be given to a player who played a lead suit card with the highest rank, 
     * and returns the player id who collected the trick.
     * @return  - The player id of a Player who collected the trick.
     *            This player leads the next hand. */
    public int CollectTrick() {
        // Starts the comparison by setting the current highest rank and index, 
        // Based on the first player in the array who threw a lead suit card.
        int currMaxIndex = findFirstThrewLeadSuit(); // It keeps track of the id of the player who threw the highest lead suit rank.
        Card cardThrown = allPlayers[currMaxIndex].GetCardThrown(); // Stores the first player with a suit's thrown card into a new variable for easier access.
        int currMaxRank = cardThrown.GetRank(); // It keeps track of the highest lead suit rank thrown.

        // Loops through the players but only starting from the first player in the array who threw a lead suit card
        // Since the other players before who did not throw a lead suit card can never collect the trick.
        for (int i = currMaxIndex; i < numPlayers; i++){
            cardThrown = allPlayers[i].GetCardThrown(); // Stores the current player's thrown card into a variable for easier access.
            
            // Makes sure the the player has thrown a card, otherwise the player gets skipped.
            if (cardThrown != null){
                // Checks to see if the current player threw a lead suit because without it, even the highest rank wouldn't collect the trick.
                if (cardThrown.GetSuit() == leadSuit){
                    // Checks to see if the current player's lead suit card has a higher rank than the current highest rank.
                    // If so, it becomes the current highest rank and that player would be the one closest to collecting the trick.
                    if (cardThrown.GetRank() > currMaxRank){
                        currMaxIndex = i;
                        currMaxRank = cardThrown.GetRank();
                        
                    }
                }
            }
        }

        // The player who had the highest ranking lead suit card collects the trick.
        allPlayers[currMaxIndex].SetPlayerTricks(AddCardsToArray(allPlayers[currMaxIndex].GetPlayerTricks(), cardsThrown));

        return currMaxIndex; // The id of the player who had the highest ranking lead suit card will be returned.
    }


    // By Pouria
    /* Converts a card expressed in String to a Card object.
     * @param cardStr - String representation of a card. 
     *                  The suit is represented by C (clubs), D (diamonds), H (hearts), and S (spades).
     *                  The rank is represented by numbers from 1 to 13 (Ace to King)
     *                  (e.g. C-A => Ace of Club, H-2 => 2 of Heart, S-Q => Queen of Spade)
     * @return        - A Card object that corresponds to the specified String representation. 
     *                  Returns null if there is no such Card. */
    public Card ConvertToCard(String cardStr) {
        int rank; // Stores the rank later on for instantiating 
        
        String[] cardComponents = cardStr.toUpperCase().split("-"); // Makes the card string all capital to handle lower case and then split it by "-" to get the suit and the rank.
        
        // Incorrect card format checking
        
        // Check whether the format is not followed (when split doesn't result in two seperate Strings for the suit and the rank) and returns null.
        if (cardComponents.length != 2){
            return null;
        }
        
        // Rank checking 

        // Checks whether it's a valid rank letter (J, Q, K, and A) and assigns the correct integer value.
        if(cardComponents[1].equals("J")){
            rank = Card.CARD_JACK;
        }
        else if (cardComponents[1].equals("Q")){
            rank = Card.CARD_QUEEN;
        }
        else if (cardComponents[1].equals("K")){
            rank = Card.CARD_KING;
        }
        else if (cardComponents[1].equals("A")){
            rank = Card.CARD_ACE;
        }
        // Checks whether it's a rank number between 2 and 10 (inclusive) and then converts it to be stored as integer.
        // ** It must be checked as a String because parsing them for checking would break the code if the user inputs wrong letter rank.
        else if (cardComponents[1].equals("2") || cardComponents[1].equals("3") || cardComponents[1].equals("4") || cardComponents[1].equals("5") || cardComponents[1].equals("6") || cardComponents[1].equals("7") || cardComponents[1].equals("8") || cardComponents[1].equals("9") || cardComponents[1].equals("10")){
            rank = Integer.parseInt(cardComponents[1]);
        }
        // Otherwise it is not a valid rank, so null is returned as it cannot be used to create a valid Card object.
        else{
            return null;
        }

        // Suit checking

        // Checks whether it's a valid Suit letter (C, D, H, and S) and calls the appropriate constructor to pass in the rank parameter.
        if(cardComponents[0].equals("C")){
            return new Club(rank);
        }
        else if (cardComponents[0].equals("D")){
            return new Diamond(rank);
        }
        else if (cardComponents[0].equals("H")){
            return new Heart(rank);
        }
        else if (cardComponents[0].equals("S")){
            return new Spade(rank);
        }
        // Otherwise it is not a valid suit, so null is returned as it cannot be used to create a valid Card object.
        else{
            return null;
        }
    }


    // By Haruki
    /* Switches the current player. */
    public void SwitchPlayer() {
        // Switches the current Player to the Player with next playerId
        // Player ID of current player in 4-player game: 0 -> 1 -> 2 -> 3 -> 0, and the cycle continues
        this.SetCurrPlayer(this.allPlayers[(this.GetCurrPlayer().GetPlayerId() + 1) % this.numPlayers]);
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
    /* Deals Cards from the standard deck of card to all players.
     * The Cards are dealt differently depending on the number of players.
     * When there are:
     *      - 3 players: 2 of Diamond is removed, and each player is dealt 17 cards
     *      - 4 players: Each player is dealt 13 cards.
     *      - 5 players: 2 of Diamond and 2 of Club are removed, and each player is dealth 10 cards. */
    public void DealPlayerCards() {

        // The Cards that are removed before dealing. Refer to Java Doc.
        Card[] illegalCards;

        // For different number of players, the behaviour of DealPlayerCards() varies 
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


    /* The specified Player ATTEMPTS to play/throw a specified Card. (Meaning, this method does not modify the player's playerCards.)
     * This method returns error or success codes according to several conditions listed below.
     * This method is also responsible for updating the leadSuit according to the game rule.
     * @param player    - The Player who will throw the card.
     * @param card      - The Card that the player wants to play/throw for a trick.
     * @return          - Returns 1 if the player successfully throws the card.
     *                    Returns 3 if the player "breaks the heart" and successfully throws the card.
     *                    Returns -1 if the player does not have the card or when the card is null.
     *                    Returns -2 if the player tries to throw a Heart when heart is not broken. 
     *                    Returns -3 if the player has no choice but to skip the first trick 
     *                    Returns -4 if the player throws a Card that is not in lead suit even if they have Cards in lead suit 
     *                    Returns -5 if the player throws a Card of Hearts of Queen of Spade in the first trick, which is illegal. 
     *                    Returns -6 if the player throws a Card other than openingCard in the first throw of the first trick 
     *                    Returns -7 if the player has no choice but to give the lead to the next person */
    public int PlayCard(Player player, Card card) {
        
        // The following if-else statements check for potential errors
        // === PREREQUISITE CHECK ===
        // Checks if the card the player wants to throw/play is valid in the first place
        if (!player.HasCard(card)) { // #1 Check
            // If the player does not have the card they want to throw or the card is null, then -1 is returned
            return INVALID_CARD;
        }

        // === UPDATING LEAD SUIT ===
        // If it is the first throw/play in a trick AND ( the card thrown is NOT heart OR the Heart is broken )
        if (this.numCardsThrown == 0 && (card.GetSuit() != Card.HEART || this.isHeartBroken)) {
            this.SetLeadSuit(card.GetSuit()); // Update the lead suit before proceeding to conduct below checks
        }
        
        // === EDGE CASES CHECK ===
        // This boolean indicates whether the player has Card in suits other than Heart
        boolean hasSuitOtherThanHeart = (player.HasSuit(Card.CLUB) || player.HasSuit(Card.DIAMOND) || player.HasSuit(Card.SPADE));
        // This is for the edge cases involving the first trick of a hand
        if (this.numTrickRound == 1) {
            if (!hasSuitOtherThanHeart) { // #1 Check
                // This is when the player only has Cards in Hearts in the first trick
                // Such a player can only skip (returns -3). This is because playing Hearts is illegal in the first trick.
                return SKIP_TRICK;

            } else if (player.HasCard(this.ConvertToCard("S-Q"))) { // #2 Check
                // This is for an extremely rare edge case where the player only has Cards of Hearts and Queen of Spade in the first trick
                // In such a case, the player can only skip as card of Hearts of Queen of Spade are illegal to play in first trick
    
                // Firstly, temporarily remove Queen of Spade from the player's playerCards
                player.RemovePlayerCard(this.ConvertToCard("S-Q"));
    
                // Re-evalute hasSuitOtherThanHeart without Queen of Spade
                // If true, that means the player has some Cards that they can play for the first trick
                // If false, that means the player only has Hearts and Queen of Spade, both of which are illegal to play in first trick 
                hasSuitOtherThanHeart = (player.HasSuit(Card.CLUB) || player.HasSuit(Card.DIAMOND) || player.HasSuit(Card.SPADE));
    
                // Add the Queen of Spade back to the player's playerCards
                player.SetPlayerCards(this.AddCardsToArray(player.GetPlayerCards(), new Card[] {this.ConvertToCard("S-Q")}));
    
                // If the player does not have Cards in suits that is not Hearts when Queen of Spade is temporarily removed from their playerCard
                // Then the player can only skip in the first trick; return -3. Otherwise, proceed for next checks.
                if (!hasSuitOtherThanHeart) {
                    return SKIP_TRICK;
                }

                // Re-evaluate hasSuitOtherThanHeart with Queen of Spade for later use
                hasSuitOtherThanHeart = (player.HasSuit(Card.CLUB) || player.HasSuit(Card.DIAMOND) || player.HasSuit(Card.SPADE));
            }
        }

        // This if-else statements cannot be combined with the above if-else statement, 
        // this is to ensure #3 will still be checked even if #2 check passes
        if (this.numTrickRound == 1 && (card.GetSuit() == Card.HEART || card.equals(this.ConvertToCard("S-Q")))) { // #3 Check
            // Note: The #2 Check above ensures that the player has some legal cards to play in the first trick
            // It is illegal to throw a card of Hearts OR Queen of Spade IN the first trick
            // Thus, if the player throws such Cards, -5 is returned
            return ILLEGAL_IN_FIRST_TRICK;
        } else if (this.numCardsThrown == 0 && !hasSuitOtherThanHeart && !this.isHeartBroken) { // #4
            // If it is the first play/throw of a trick AND the player does NOT have cards other than Hearts AND Heart has not been broken
            // Returns -7 to signify that the lead must be passed to the next player
            return GIVE_LEAD_TO_NEXT;
        } else if (card.GetSuit() != this.leadSuit && player.HasSuit(this.leadSuit) && this.numCardsThrown != 0) { // #5 Check
            // If the card is not in lead suit while the player has Cards in lead suit 
            // and it is not the first throw of a trick, -4 is returned
            return MUST_FOLLOW_SUIT;

        } else if (this.GetNumCardsThrown() == 0 && this.GetNumTrickRound() == 1 && !card.equals(this.openingCard)) { // #6 Check
            // If it is the first throw of a Card in the first trick of a hand AND the player does not play the openingCard
            return MUST_PLAY_OPENING_CARD; // -6 is returned to indicate that the player must play the openingCard
        }

        
        // === "HEART HAS BEEN BROKEN" MECHANISM ===
        // This if-else statement deal with "Heart has been broken" mechanism
        if (card.GetSuit() == Card.HEART && !this.isHeartBroken) {
            if (this.numCardsThrown == 0) { // #7 Check
                // If the card is Heart AND the heart is NOT broken AND it is the first throw of a trick
                // A heart cannot be broken so -2 is returned
                return HEART_NOT_BROKEN;
            } else if (!player.HasSuit(this.leadSuit) && this.numCardsThrown != 0) { // #8 Check
                // If the card is Heart AND the heart is NOT broken 
                // AND the player has no Cards in lead suit AND it is not the first throw/play in a trick
                // then 3 is returned to indicate the heart has been broken
                return HEART_HAS_BEEN_BROKEN;
    
            } else { // #9 Check
                // If the card is Heart AND Heart is NOT broken, then -2 is returned
                return HEART_NOT_BROKEN;
            } 
        }
        
        // === AFTER ALL CASES ARE CHECKED AGAINST ===
        // The several checks above ensures that the specified player can successfully play/throw the card specified
        return SUCCESS;
    }


    // By Pouria
    /* States whether the hand round has ended or not.
     * @return - Returns true if any of the players has no more cards to play.
     *         - Returns false if all players have at least one card to play. */
    public boolean HasHandEnded() {
        // Loops through all of the players
        for (int i = 0; i < numPlayers; i++) {
            // Checks to see if any player has no cards and true is returned.
            if (GetAllPlayers()[i].GetPlayerCards().length == 0) {
                return true; 
            }
        }
        // Otherwise false is returned (when all players have atleast one card).
        return false;
    }


    // By Haruki
    /* Returns an array that contains player id(s) of the winner(s).
     * There can be multiple winners, such as when multiple players 
     * have the same and lowest points together when the game ends.
     * @return  - An array of player id(s) of the winner(s).
     *            An empty array is returned to signify no one has won yet
     *            There is no winner when:
     *            - No one has exceeded the endingPoint, and thus the game still continues. */
    public int[] CheckWinner() {
        // Keeps track of the greatest and lowest points among the Players
        int maxPoint = this.allPlayers[0].GetPlayerPoints();
        int minPoint = this.allPlayers[0].GetPlayerPoints();
        int numPlayersWithMinPoint = 1; // Keeps track of the number of Players with same minimum point

        // Iterates through every Player (except 0th Player) 
        for (int playerId = 1; playerId < this.allPlayers.length; playerId++) {
            if (this.allPlayers[playerId].GetPlayerPoints() > maxPoint) {
                // If current maxPoint is smaller than the Player's point of the current iteration, then update the maxPoint
                maxPoint = this.allPlayers[playerId].GetPlayerPoints();
            } else if (this.allPlayers[playerId].GetPlayerPoints() < minPoint) {
                // If current minPoint is larger than the Player's point of the current iteration, then update the minPoint
                minPoint = this.allPlayers[playerId].GetPlayerPoints();
                // Also reset the numPlayersWithMinPoint to indicate there's currently only one player with the same minimum point
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

        // If the max point is greater than or equal to the endingPoint 
        // that indicates the game has ended and there needs to be winner(s)
        if (maxPoint >= this.endingPoint) {
            // Parses through each Player's points and store their playerIds into an array if they have the lowest score
            for (int playerId = 0; playerId < this.allPlayers.length; playerId++) {
                if (this.allPlayers[playerId].GetPlayerPoints() == minPoint) {
                    winnerIds[pos] = playerId;
                    pos++; // Increments the current position of winnerIds array
                }
            }
            // Returns an array consisting of ids of winners
            return winnerIds;
        } else {
            // Returns an empty array to signify no one has won yet
            return new int[0];
        }
    }


    // === Getters and Setters

    // By Haruki
    /* Returns an array of Card objects that has been discarded by Players, which make up a trick.
     * @return  - An array of Card objects that make up a trick. */
    public Card[] GetCardsThrown() {
        return this.cardsThrown; // Returns the cardsThrown
    }


    // By Haruki
    /* Updates the cardsThrown (aka tricks) to the one specified.
     * @param cardsThrown   - An array that consists of Cards that the Player played during a trick*/
    public void SetCardsThrown(Card[] cardsThrown) {
        this.cardsThrown = cardsThrown; // Updates the cardsThrown
    }


    // By Pouria
    /* Returns an array consisting of all Player objects.
     * @return  - An array of all Player objects. */
    public Player[] GetAllPlayers() {
        return this.allPlayers; // Returns the array of all player objects.
    }

    
    // By Pouria 
    /* Obtains the current hand round number.
     * @return - Returns the hand round number. */
    public int GetNumHandRound() {
        return this.numHandRound; // Return the integer which represents the hand round number.
    }


    // By Haruki
    /* Updates the hand round number to the specified one (when players play all the cards in their hands).
     * @param numHandRound  -  The integer that represents the hand round number. */
    public void SetNumHandRound(int numHandRound) {
        this.numHandRound = numHandRound; // Updates the numHandRound
    }
  

    // By Pouria
    /* Obtains the current trick round number.
     * @return - Returns the trick round number. */
    public int GetNumTrickRound() {
        return this.numTrickRound; // Return the integer which represents the trick round number.
    }


    // By Haruki
    /* Updates the trick round number to the specified one (when players play one card to make up a trick).
     * @param numTrickRound  -  The integer that represents the trick round number. */
    public void SetNumTrickRound(int numTrickRound) {
        this.numTrickRound = numTrickRound; // Updates the numTrickRound
    }

    
    // By Pouria
    /* Obtains the number of Cards thrown during a trick so far.
     * @return - Returns the number of cards thrown by Players so far during a trick */
    public int GetNumCardsThrown() {
        return this.numCardsThrown; // Returns the number of Cards thrown by Players during a trick
    }


    // By Haruki
    /* Updates the number of Cards thrown during a trick to the specified value.
     * @rapam numCardsThrown    - The number of cards thrown by Players during a trick */
    public void SetNumCardsThrown(int numCardsThrown) {
        this.numCardsThrown = numCardsThrown; // Updates the numCardsThrown to the specified one
    }

    
    // By Pouria
    /* Indicates whether the Heart has been broken.
     * @return  - true when the Heart has been broken, false otherwise. */
    public boolean GetIsHeartBroken() {
        return this.isHeartBroken; // Returns the latest boolean status of whether heart is broken or not.
    }


    // By Pouria
    /* Adjusts the boolean value of the isHeartBroken variable, which represents if the Heart has been broken yet.
     * @param isHeartBroken - The boolean status of whether the heart has been broken yet in the game */
    public void SetIsHeartBroken(boolean isHeartBroken) {
        this.isHeartBroken = isHeartBroken; // Updates the boolean status of whether heart is broken or not.
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


    // By Pouria
    /* Obtains the lead suit for the current trick.
     * @return  - Returns 0 if it is Club
     *          - Returns 1 if it is Diamond
     *          - Returns 2 if it is Heart   
     *          - Returns 3 if it is Spade */
    public int GetLeadSuit() {
        return this.leadSuit; // Returns the integer representation of the lead suit.
    }


    // By Haruki
    /* Updates the leading suit of the current trick to the specified one.
     * @param suitId       - The numerical id of a suit, which is declared in Card class. */
    public void SetLeadSuit(int suitId) {
        this.leadSuit = suitId; // Updates the lead suit
    }


    // By Pouria
    /* Obtain the opening card needed for starting a hand round (either 2 or 3 of clubs).
     * @return - Returns the opening card that must be used for beiginning all the hand rounds*/
    public Card GetOpeningCard() {
        return this.openingCard; // Returns the Card object that represents the opening card.
    }

}
