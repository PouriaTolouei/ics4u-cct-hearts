public class Displayer {
    // Global Constants representing the section id of the game rules.
    public static final int SECTION_OBJECTIVE       = 0;
    public static final int SECTION_SETUP           = 1;
    public static final int SECTION_TERMS           = 2;
    public static final int SECTION_PASSING         = 3; 
    public static final int SECTION_GAME_PROCEDURE  = 4;
    public static final int SECTION_POINT           = 5;
    public static final int SECTION_NOTATION        = 6;
    public static final int NUM_SECTIONS            = 7;
    public static final int SECTION_INVALID         = -100; // id when user inputs invalid option

    // Empty constructor
    public Displayer() {
    }

    // === Private Helper Methods ===
    
    // By Pouria
    /* Displays the 1D array of Card objects that is passed to it (a suit of player's hand or the cards thrown).
    * @oaram cards         - The 1D array of Card objects that will be displayed. */
    private void displayCards(Card[]cards){
        for (int i = 0; i < cards.length; i++){
            // Checks for the suit of each Card object to display the proper suit.
            switch (cards[i].GetSuit()){
                case Card.CLUB:
                    System.out.print("C-");
                    break;
                case Card.DIAMOND:
                    System.out.print("D-");
                    break;
                case Card.HEART:
                    System.out.print("H-");
                    break;
                case Card.SPADE:
                    System.out.print("S-");
                    break;
            }
            // Checks for the rank of each Card object to display the proper rank.
            switch (cards[i].GetRank()){
                // Non-numbered ranks get converted into their names before getting displayed.
                case Card.CARD_JACK:
                    System.out.print("J ");
                    break;
                case Card.CARD_QUEEN:
                    System.out.print("Q ");
                    break;
                case Card.CARD_KING:
                    System.out.print("K ");
                    break;
                case Card.CARD_ACE:
                    System.out.print("A ");
                    break;
                // Numbered ranks are directly displayed.
                default:
                    System.out.print(cards[i].GetRank() + " ");
            }
        }
        System.out.print("\n"); // Goes to the next line so that future displaying are seperate.
    }

    // By Haruki
    /* Separates an array of Card objects by suit, and return a 2D array where each row corresponds to each suit.
     * @param cards - An array of Card objects to be separated by suit
     * @return      - A 2d array of Card objects where each row only contain Cards of each suit.
     *                0th row represents Card.CLUB
     *                1st row represents Card.DIAMOND
     *                2nd row represents Card.HEART
     *                3rd row represents Card.SPADE     */
    private Card[][] separateBySuit(Card[] cards) {
        // The temporary "untrimmed" 2D Card array, which may contain null
        Card[][] card2dUntrimmed = new Card[4][13];
        // The "trimmed" 2D Card array, which will not contain null
        Card[][] card2dTrimmed = new Card[4][];

        // Represent the current index of each row/suit of card2d
        // The position of the integer in counter correspond with the suit ID.
        // e.g. counter[0] represent current index for the 0th row of card2d, which 
        //      only contains Cards of Card.CLUB, which is represented as 0 numerically.
        // Each integer will also represent the number of Cards in a certain suit as well.
        // e.g. if counter[0] == 2, that would mean there are 2 Cards of Club (Card.CLUB = 0)
        int[] counter = new int[4];

        // Represents the suit and the index of the 2d array, respectively
        int suit;
        int index;

        // Parses through each Card in cards
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                suit = cards[i].GetSuit();
                index = counter[suit];
                // As long as the current Card object is not null
                // Add that Card object to card2d according to their suit and current index
                card2dUntrimmed[suit][index] = cards[i];
                counter[suit]++;
            } else {
                // For efficiency purpose, when there is a null object
                // this will break out of the for-loop, as null object indicates there
                // are no more Card objects after that
                break;
            }
        }

        // Represent each row/suit of Cards
        Card[] cardRow;

        for (int suitId = 0; suitId < counter.length; suitId++) {
            // Instantiates a cardRow with correct number of Cards for each suit
            cardRow = new Card[counter[suitId]];
            for (int j = 0; j < counter[suitId]; j++) {
                cardRow[j] = card2dUntrimmed[suitId][j];
            }
            card2dTrimmed[suitId] = cardRow;
        }

        return card2dTrimmed; // Returns the "trimmed" 2d Array without any null
    }

    // === Public Methods ===
    
    // By Haruki
    /* Explains the section of the rule of the game of Hearts that is specified by the section id. 
     * @param sectionId    - The id assigned to each section of game rule. */
    public void ExplainRule(int sectionId) {
        switch(sectionId) {
            case SECTION_OBJECTIVE: // The general overview and objective of the game
                System.out.println("====== OBJECTIVE ======");
                System.out.println("- The objective of the game is to get AS LITTLE POINTS AS POSSIBLE, as the player with the LOWEST SCORE WINS the game.");
                System.out.println("- The GAME ENDS WHEN ONE PLAYER EXCEEDS 50 POINTS (Default) or customized points");
                System.out.println("- The game can be played by 3 TO 5 PLAYERS.");
                break;

            case SECTION_SETUP: // The explanation of how cards are dealt to players to set up the game
                System.out.println("====== SET UP ======");
                System.out.println("- A standard deck of card is shuffled and dealt to players.");
                System.out.println("- In a 4-PLAYER game, each player is DEALT 13 CARDS.");
                System.out.println("- In a 3-PLAYER game, each player is DEALT 17 CARDS from a deck of card without 2 of Diamond.");
                System.out.println("- In a 5-PLAYER game, each player is DEALT 10 CARDS from a deck of card without 2 of Diamond and 2 of Club.");
                break;

            case SECTION_TERMS: // The explanation of the terminologies used in the game
                System.out.println("====== TERMINOLOGY ======");
                System.out.printf("%20s | %s\n", "Hand", "The time it takes to play all the cards that each player holds. Hand also refers to the cards the player holds. ");
                System.out.printf("%20s | %s\n", "Trick", "One round of play where each each player plays one card and make up a trick.");
                System.out.printf("%20s | %s\n", "Game", "Each game begins by dealing cards to each player and ends whenever someone reaches 100 or customized points.");
                System.out.printf("%20s | %s\n", "\"Heart is broken\"", "A heart is broken when someone discards a Heart when another suit is led because s/he does not have cards of the suits led.");
                System.out.printf("%20s | %s\n", "\"Shot the moon\"", "It is when a player takes all 13 Hearts and a Spade of Queen in one hand.");
                break;

            case SECTION_PASSING: // The explanation of the passing rotation, which occurs every hand
                System.out.println("====== PASSING ROTATION ======");
                System.out.println("- After looking at your hand, EACH PLAYER PICKS THREE CARDS from their hand, and PASSES THEM to another player.");
                System.out.println("- In a 4-player game, pass the cards to the person on the left on the 1st hand, right on the 2nd hand, across the player on 3rd hand, and no passing on 4th hand. This cycle continues.");
                System.out.println("- In a 3-player or 5-player game, pass the cards to the person on the left on 1st hand, then right on 2nd hand, and this cycle continues.");
                break;

            case SECTION_GAME_PROCEDURE: // This section explains how the game proceeds
                System.out.println("====== GAME PROCEDURE ======");
                System.out.println("1. The player who has the 2 OF CLUB after the deal makes the OPENING LEAD.");
                System.out.println("   If 2 of Club has been removed, it is the 3 of Club that makes the opening lead instead.");
                System.out.println("2. The play proceeds in the ASCENDING ORDER (by player ID) from the led player.");
                System.out.println("3. Each player MUST FOLLOW SUIT if possible. If a player's hand does not contain the suit led,");
                System.out.println("   the card of any other suit may be played.");
                System.out.println("   i. However, if a player has no Clubs when the first trick is led,");
                System.out.println("      a Heart or the Queen of Spade may not be played.");
                System.out.println("4. The HIGHEST CARD OF THE SUIT LED WINS THE TRICK, and the winner of");
                System.out.println("   that trick takes all the cards from that trick.");
                System.out.println("5. The WINNER OF THE LAST TRICK LEADS THE NEXT TRICK");
                System.out.println("   i. A player MAY NOT LEAD A HEART OR QUEEN OF SPADE until \"HEART HAS BEEN BROKEN\"");
                System.out.println("   ii.If the player to lead only has Hearts and Heart has not been broken, then they pass the lead to the next person.");
                System.out.println("6. The play CONTINUES UNTILL ALL THE TRICKS HAVE BEEN TAKEN, then points");
                System.out.println("   are scored for each player based on the cards from each of the tricks they have won.");
                System.out.println("7. If no player exceeds the maximum amount of points, then the CARDS ARE SHUFFLED AND DEALT AGAIN.");
                System.out.println("8. The player with LOWEST SCORE WINS THE GAME once someone exceeds the maximum amount of points.");
                break;

            case SECTION_POINT: // This section explains the point system of the game.
                System.out.println("====== POINT SYSTEM ======");
                System.out.printf("%20s | %10s\n", "Suit", "Point");
                System.out.println("---------------------------------");
                System.out.printf("%20s | %10s\n", "Heart", "1 point");
                System.out.printf("%20s | %10s\n", "Queen of Spade", "13 points");
                System.out.printf("%20s | %10s\n", "Spade", "0 point");
                System.out.printf("%20s | %10s\n", "Club", "0 point");
                System.out.printf("%20s | %10s\n", "Diamond", "0 point");
                break;
            
            case SECTION_NOTATION: // This section explains the notation used in the game.
                System.out.println("====== NOTATIONS ======");
                System.out.println("Suits: C (Club), D (Diamond), H (Heart), and S(Spade)");
                System.out.println("Ranks: A (Ace), 2, 3, ..., 10, J (Jack), Q (Queen), K (King)");
                System.out.println("   Ex: C-2 (2 of Club), H-A (Ace of Heart), S-Q (Queen of Spade)");
                break;

            default: // ERROR HANDLING, when invalid option is chosen, warning is displayed
                System.out.println("WARNING: INVALID SECTION ID");

        }
    }


    // By Haruki
    /* Displays current number of hand rounds and trick rounds in a formatted way.
     * @param numHandRound  - The number of hand rounds.
     * @param numTrickRound - The number of trick rounds. */
    public void DisplayRounds(int numHandRound, int numTrickRound) {
        // Prints out the numHandRound and numTrickRound in a formatted way
        System.out.println("======================");
        System.out.printf("HAND: %2d | TRICK: %2d\n", numHandRound, numTrickRound);
        System.out.println("======================");
    }


    // By Haruki
    /* Displays the hand (their playerCards) of a specified player 
     * in an ascending order within each suit.
     * @param player    - A Player whose hand will be displayed. */
    public void DisplayPlayerCards(Player player) {
        // Sorts the player's cards by their suit and rank
        player.SortPlayerCards();

        // The names of suit with corresponding index and suitId
        // e.g. suitNames[0] = "CLUB", Card.CLUB = 0;
        String[] suitNames = {"CLUB", "DIAMOND", "HEART", "SPADE"};

        // Obtains playerCards and organize them by their suit into 2D array
        Card[][] groupedCards = separateBySuit(player.GetPlayerCards());

        // Each row of groupedCards contains cards of a certain suit,
        // and each suit are printed row by row, in the order of Clubs, Diamonds, Hearts, and Spades.
        for (int suit = 0; suit < groupedCards.length; suit++) {
            System.out.printf("%7s: ", suitNames[suit]); // Adds the suit name in the beginning 
            displayCards(groupedCards[suit]); // Prints out all the Cards of the specified suit
        }
    }


    // By Pouria
    /* Displays the thrown cards that make up a trick in the order it was thrown.
     * @param cardsThrown - An array of Card objects which are thrown by Player each round. */
    public void DisplayCardThrown(Card[] cardsThrown) {
        displayCards(cardsThrown);
    }


    // By Pouria
    /* Displays the latest points of all players in a formatted way.
     * @param allPlayers - An array of all Player objects, whose points will be displayed. */
    public void DisplayPointsTable(Player[] allPlayers) {
        // Displays the top border (based on the number of players).
        for (int i = 0; i < allPlayers.length; i++){
            System.out.print("===========");
        }
        System.out.print("\n");
        System.out.print("|"); // Displays the left border.
        // Displays the title row (players' IDs)(based on the number of players).
        for (int i = 0; i < allPlayers.length; i++){
            System.out.printf(" Player %1d | ", allPlayers[i].GetPlayerId());
        }
        System.out.print("\n");
        System.out.print("|"); //Displays the left border.
         // Displays the row for players' latest points (based on the number of players).
        for (int i = 0; i < allPlayers.length; i++){
            System.out.printf(" %8d | ", allPlayers[i].GetPlayerPoints());
        }
        System.out.print("\n");
        // Displays the bottom border (based on the number of players).
        for (int i = 0; i < allPlayers.length; i++){
            System.out.print("===========");
        }
    }
}
