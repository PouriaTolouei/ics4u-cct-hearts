public class Displayer {
    // Global Constants representing the section id of the game rules.
    public static final int SECTION_OBJECTIVE       = 0;
    public static final int SECTION_SETUP           = 1;
    public static final int SECTION_TERMS           = 2;
    public static final int SECTION_PASSING         = 3; 
    public static final int SECTION_GAME_PROCEDURE  = 4;
    public static final int SECTION_POINT           = 5;
    public static final int SECTION_INVALID         = -100; // id when user inputs invalid option

    // Empty constructor
    public Displayer() {
    }

    // === Private Helper Methods ===
    
    // By Pouria
    /* Displays the 1D array of Card objects that is passed to it (a suit of player's hand or the cards thrown).
    * @oaram cards         - The 1D array of Card objects that will be displayed. */
    public void displayCards(Card[]cards){

    }

    // === Public Methods ===
    
    // By Haruki
    /* Explains the section of the rule of the game of Hearts that is specified by the section id. 
     * @param sectionId    - The id assigned to each section of game rule. */
    public void ExplainRule(int sectionId) {
        switch(sectionId) {
            case SECTION_OBJECTIVE: // The general overview and objective of the game
                System.out.println("===== OBJECTIVE =====");
                System.out.println("The objective of the game is to get as little points as possible, as the player with the lowest score wins the game.");
                System.out.println("The game ends when one player hits the predetermined score or higher (The default score is 50).");
                System.out.println("The game can be played by 3 to 5 players.");
                break;

            case SECTION_SETUP: // The explanation of how cards are dealt to players to set up the game
                System.out.println("===== SET UP =====");
                System.out.println("A standard deck of card is shuffled and dealt to players.");
                System.out.println("In a 4-player game, each player is dealt 13 cards.");
                System.out.println("In a 3-player game, each player is dealt 17 cards from a deck of card without 2 of Diamond.");
                System.out.println("In a 5-player game, each player is dealth 10 cards from a deck of card without 2 of Diamond and 2 of Club.");
                break;

            case SECTION_TERMS: // The explanation of the terminologies used in the game
                System.out.println("===== TERMINOLOGY =====");
                System.out.printf("%15s | %s\n", "Hand", "The time it takes to play all the cards that each player holds. Hand also refers to the cards the player holds. ");
                System.out.printf("%15s | %s\n", "Trick", "One round of play where each each player plays one card and make up a trick.");
                System.out.printf("%15s | %s\n", "Game", "Each game begins by dealing cards to each player and ends whenever someone reaches 100 or customized points.");
                System.out.printf("%15s | %s\n", "\"Heart is broken\"", "A heart is broken when someone discards a Heart when another suit is led because s/he does not have cards of the suits led.");
                System.out.printf("%15s | %s\n", "\"Shot the moon\"", "It is when a player takes all 13 Hearts and a Spade of Queen in one hand.");
                break;

            case SECTION_PASSING: // The explanation of the passing rotation, which occurs every hand
                System.out.println("===== PASSING ROTATION =====");
                System.out.println("After looking at your hand, each player picks three cards from their hand, and passes them to another player.");
                System.out.println("In a 4-player game, pass the cards to the person on the left on the 1st hand, right on the 2nd hand, across the player on 3rd hand, and no passing on 4th hand. This cycle continues.");
                System.out.println("In a 3-player or 5-player game, pass the cards to the person on the left on 1st hand, then right on 2nd hand, and this cycle continues.");
                break;

            case SECTION_GAME_PROCEDURE: // This section explains how the game proceeds
                System.out.println("===== GAME PROCEDURE =====");
                System.out.println("1. The player who has the 2 of Club after the deal makes the opening lead.");
                System.out.println("   If 2 of Club has been removed, it is the 3 of Club that makes the opening lead instead.");
                System.out.println("2. The play proceeds in the ascending order (by player ID) from the led player.");
                System.out.println("3. Each player MUST follow suit if possible. If a player's hand does not contain the suit led,");
                System.out.println("   the card of any other suit may be played.");
                System.out.println("   i. However, if a player has no Clubs when the first trick is led,");
                System.out.println("      a Heart or the Queen of Spade may not be played.");
                System.out.println("4. The highest card of the suit led wins the trick, and the winner of");
                System.out.println("   that trick takes all the cards from that trick.");
                System.out.println("5. The winner of the last trick leads the next trick.");
                System.out.println("   i. A player may not lead a Heart or Queen of Spade until \"Heart has been broken\"");
                System.out.println("   ii.If the player to lead only has Hearts and Heart has not been broken, then they pass the lead to the next person.");
                System.out.println("6. The play continues until all the tricks have been taken, then points");
                System.out.println("   are scored for each player based on the cards from each of the tricks they have won.");
                System.out.println("7. If no player exceeds the maximum amount of points, then the cards are shuffled and dealt again.");
                System.out.println("8. The player with lowest score wins the game once someone exceeds the maximum amount of points.");
                break;

            case SECTION_POINT: // This section explains the point system of the game.
                System.out.println("===== POINT SYSTEM =====");
                System.out.printf("%20s | %10s\n", "Suit", "Point");
                System.out.printf("%20s | %10s\n", "Heart", "1 point");
                System.out.printf("%20s | %10s\n", "Queen of Spade", "13 points");
                System.out.printf("%20s | %10s\n", "Spade", "0 point");
                System.out.printf("%20s | %10s\n", "Club", "0 point");
                System.out.printf("%20s | %10s\n", "Diamond", "0 point");
                break;

            default: // ERROR HANDLING, when invalid option is chosen, warning is displayed
                System.out.println("WARNING: INVALID SECTION ID");

        }
    }


    // By Haruki
    /* Displays the hand (the cards they have) of a specified player.
     * @param player    - A Player whose hand will be displayed. */
    public void DisplayHand(Player player) {

    }


    // By Pouria
    /* Displays the thrown cards that make up a trick in the order it was thrown.
     * @param cardsThrown - An array of Card objects which are thrown by Player each round. */
    public void DisplayCardThrown(Card[] cardsThrown) {

    }


    // By Pouria
    /* Displays the points of all players in a formatted way.
     * @param allPlayers - An array of all Player objects, whose points will be displayed. */
    public void DisplayPointsTable(Player[] allPlayers) {

    }
}
