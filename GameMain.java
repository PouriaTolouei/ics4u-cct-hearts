import java.util.Scanner;
public class GameMain {
    public static void main(String[] args) {
        // === VARIABLES ===
        // Instances of Classes that handle input/displaying/backend logic
        Scanner input = new Scanner(System.in); // Used for obtaining user inputs
        Displayer disp = new Displayer(); // Used for displaying majority of the information
        HeartEngine engine; // Used for back-end processes and logic

        // Variables used throughout the program
        int option; // User option
        int numPlayers; // Number of players
        String[] playerNames; // An array of player names
        String individualName; // Individual name of a player
        int losingPoint = 0; // The point at which the game ends
        Player currPlayer; // The current player of interest
        String cardStr; // User-input string that represent a Card object (e.g. H-2 -> 2 of Heart)
        Card card; // General Card object
        Card openingCard; // The Card that will make the opening lead (depends on numPlayers)
        String openingCardStr; // The string representation of the openingCard
        int status; // Represents success or error codes
        Card[] cardsThrown; // Represents the tricks; the pile of cards
        int pos; // General representation of an index of an array
        int leadPlayerId; // The id of the player who will lead the trick

        // For the purpose of passing rotation
        Card[][] passingCards;
        
        // Used when announcing winners
        int[] winnerIds;
        Player winner;

        // Used when Players play a Card to make up a trick
        int numCardThrown;
        int numTrickRound;

        // Sentinel values that control the flow of the entire game
        boolean exitGame = false; // Controls whether the game continues
        boolean exitHand = false; // Controls whether each hand continues


        // === TITLE PAGE ===
        System.out.println("========================================");
        System.out.println("|             GAME OF HEART            |");
        System.out.println("========================================");
        System.out.println("PROGAMMED BY: HARUKI and POURIA");
        System.out.println("   COURSE ID: ICS4U");
        System.out.println("         DUE: 2021/11/25");
        System.out.println("\nPRESS ENTER TO PROCEED ...");
        input.nextLine();


        // === GAME RULE EXPLANATION ===
        System.out.println("========================================");
        System.out.println("|               GAME RULE              |");
        System.out.println("========================================");

        // All of the game rules are handled by the Displayer class.
        for (int i = 0; i < Displayer.NUM_SECTIONS; i++) {
            disp.ExplainRule(i);
            // The user can press enter to read the next rule section
            System.out.println("\nPRESS ENTER TO PROCEED ...");
            input.nextLine();
        }


        // === SET UP THE HEART ENGINE ===
        // This while-loop is responsible for prompting users for number of players and their names
        // The while-loop runs indefinitely as long as user inputs invalid number of players
        while (true) {
            System.out.println("========================================");
            System.out.println("|   SET UP, LET US KNOW WHO YOU ARE    |");
            System.out.println("========================================");
            // Prompts the user for the number of players
            System.out.print("How many players are there (3-5)?: ");
            numPlayers = input.nextInt();
            input.nextLine(); // Fixes the scanner

            // Ensures that the user has typed acceptable number of players
            if (3 <= numPlayers && numPlayers <= 5) {
                // Instantiates the String array consisting of players' names
                playerNames = new String[numPlayers];
                // Asks each Player for their name, store them into playerNames array
                for (int i = 0; i < numPlayers; i++) {
                    System.out.print("PLAYER " + i + ": What is your name?: ");
                    individualName = input.nextLine();
                    // Ensures the player names are all in capital. This is for stylistic choice 
                    playerNames[i] = individualName.toUpperCase(); 
                }
                break; // Breaks out of the while-loop
            } else {
                // A warning is shown when the user inputs invalid number of players
                System.out.println("\nWARNING: INVALID NUMBER OF PLAYERS\n");
            }
        }

        // This section is responsible for prompting users for the mode in which they want to play
        // Prompts the user if they would like to play a default or a customized game
        System.out.println(); // Formatting purpose
        System.out.println("Would you like to play a: ");
        System.out.println("1. Default Mode (losing point of 50)");
        System.out.println("2. Custom Mode (customize losing point)");
        
        // This loop is responsible for forcing the user to input either 1 or 2
        while (true) {
            System.out.print("Option: ");
            option = input.nextInt();

            if (option == 1) { // When the user chooses default mode
                // The losingPoint is set to default losingPoint
                losingPoint = HeartEngine.DEFAULT_LOSING_POINT;
                break;
            } else if (option == 2) { // When the user chooses custom mode
                // This while-loop is responsible for ensuring the customized losing point is valid
                while(true) {
                    // Prompts the user for customized losingPoint
                    System.out.print("Enter customized losing point (>0): ");
                    losingPoint = input.nextInt();
    
                    if (losingPoint > 0) {
                        break; // If the losingPoint is positive, break out of the loop
                    } else {
                        // When the user inputs negative losingPoint, warning is shown and loop continues
                        System.out.println("\nWARNING: Invalid Losing Point\n");
                    }
                }
                break;
            } else { // Warning message when a wrong input is chosen
                System.out.println("\nWARNING: INVALID OPTION\n");
            }
        }
        
        // Instantiates the HeartEngine
        engine = new HeartEngine(numPlayers, playerNames, losingPoint);


        // === IDENTIFY THE CARD THAT MAKES AN OPENING LEAD ===
        // The player with 2 of Club make the opening lead.
        // If 2 of Club is removed in 5-player game, the player with 3 of Club makes the opening lead instead.
        if (numPlayers == 5) { // In a 5-player game, opening card is 3 of Club
            openingCard = engine.ConvertToCard("C-3");
            openingCardStr = "C-3";
        } else { // In a 3 or 4 player game, opening card is 2 of Club
            openingCard = engine.ConvertToCard("C-2");
            openingCardStr = "C-2";
        }
        

        // === MAIN GAME LOOP ===
        // This is the main game loop responsible for running the game until someone wins
        while(!exitGame) {
            // Shuffles the standard deck of card and deals the Cards to each Player
            engine.Shuffle();
            engine.DealPlayerCards();


            // === PASSING ROTATION ===
            // Instantiates the 2D array of Cards that is used for passing rotation
            // Each row represent each player, and each column represent each card they want to pass
            passingCards = new Card[numPlayers][3];

            System.out.println("\n==========================================");
            System.out.println("|            PASSING ROTATION            |");
            System.out.println("==========================================");
            System.out.println("Each player has to pick three cards from their hand to be passed to another player.");
            // Prompts each Player for the three cards that they want to pass
            input.nextLine(); // Fixes the scanner

            // This is for the case when it's the 4-player game, and it's the fourth hand,
            // in which case the Cards will not be passed
            if (numPlayers == 4 && engine.GetNumHandRound()%4 == 0) {
                System.out.println("\nIT'S THE FOURTH HAND, SO THERE IS NO PASSING ROTATION!\n");
            } else { // When above check passes, proceed to prompt each Player what cards they want to pass
                for (int playerId = 0; playerId < numPlayers; playerId++) {
                    currPlayer = engine.GetAllPlayers()[playerId]; // Obtains current player
                    pos = 0; // Resets the current position of Card to be passed
                    System.out.printf("\nPLAYER %d (%s), choose three cards you want to pass.\n", playerId, currPlayer.GetPlayerName());
                    
                    // This while loop ensures that each Player passes three Cards
                    while (pos < 3) {
                        disp.DisplayPlayerCards(currPlayer); // Displays the playerCards of each Player
                        
                        // Prompts the user to type the string representation of the Card they want to pass
                        System.out.printf("\n#%d Card (Type it): ", pos + 1);
                        cardStr = input.nextLine().toUpperCase(); // Ensures the SUIT character is capitalized
    
                        // Converts user-input String representation of Card into an actual Card object
                        card = engine.ConvertToCard(cardStr);
    
                        if (!currPlayer.HasCard(card)) { // ERROR HANDLING
                            // A warning message is printed when the player does not have the card they typed or they mis-typed the card
                            System.out.println("\nWARNING: YOU DON'T HAVE THAT CARD or YOU MIS-TYPED YOUR CARD\n");
                        } else {
                            // Removes the specified card from the playerCards
                            currPlayer.RemovePlayerCard(card);
                            
                            // Adds the chosen card to the array
                            passingCards[playerId][pos] = card;
                            // Update the current pos of the Card to the correct index when there is no error
                            pos++;
                        }
                    }
                    // Displays the currPlayer's playerCards last time before switching to the next player
                    disp.DisplayPlayerCards(currPlayer);
                }
            }

            /*
            System.out.println("\nTESTING PASSING CARDS");
            // TESTING PURPOSE: PRINTS EVERY PLAYER'S PASSING CARDS
            for (int playerId = 0; playerId < numPlayers; playerId++) {
                
                System.out.printf("#%d PLAYER: ", playerId);
                disp.DisplayCardThrown(passingCards[playerId]);
                
            }
            System.out.println();
            */
            
            // Cards will be passed as long as the numPlayers is NOT 4 OR numHandRound IS NOT 4
            // This is because PASSING ROTATION is skipped when it's the fourth hand in a 4-player game
            if (numPlayers != 4 || engine.GetNumHandRound() != 4) {
                // The engine passes the Cards among Players according to the number of hand round.
                engine.PassCards(passingCards);
                System.out.println("\nTHE THREE CARDS FROM EACH PLAYER ARE PASSED TO ANOTHER PLAYER.\n");
            }
            

            /*
            System.out.println("\nSHOW EVERYONE'S CARDS");
            // TESTING PURPOSE: PRINT EVERYONE'S CARDS
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("#"+i);
                disp.DisplayPlayerCards(engine.GetAllPlayers()[i]);
            }
            System.out.println();
            */
            


            // === IDENTIFY THE PLAYER WHO MAKES THE OPENING LEAD ===
            // Iterates through every Player until it finds someone with openingCard
            for (int playerId = 0; playerId < numPlayers; playerId++) {
                // The Player with openingCard will be set to currPlayer, and make the opening lead in the hand
                if (engine.GetAllPlayers()[playerId].HasCard(openingCard)) {
                    engine.SetCurrPlayer(engine.GetAllPlayers()[playerId]);
                    break; // Breaks out of the loop immediately once the currPlayer is set, for efficiency reason.
                }
            }


            // === PRINTS OUT EVERY PLAYER'S POINTS EACH HAND ===
            // This is because points are only calculated after each hand
            disp.DisplayPointsTable(engine.GetAllPlayers());

            exitHand = false; // Resets the sentinel value every hand
            // Resets the number of trick rounds every hand (Note: Counts from 1)
            engine.SetNumTrickRound(1);
            
            // === LOOP RUNS UNTIL EVERYONE LOSES ALL OF THEIR HAND ===
            // This loop runs until all the players used all of their hand, and needs to be dealt cards again
            while(!exitHand) {

                numTrickRound = engine.GetNumTrickRound(); // Updates the numTrickRound from the HeartEngine

                numCardThrown = 0; // Resets the numCardPlayed to 0 every trick

                pos = 0; // Resets the position/index of cardsThrown to 0 every trick
                cardsThrown = new Card[numPlayers]; // The pile of Card that one player collect at the end of each trick
                engine.SetCardsThrown(cardsThrown); // Updates the cardsThrown (aka tricks) in HeartEngine 



                // This loops runs until all the players played one of their Cards to make up a trick
                while (numCardThrown < numPlayers) {
                    // Updates and stores the current Player into currPlayer for readability
                    currPlayer = engine.GetCurrPlayer(); 

                    // Displays all the Cards the current player holds
                    disp.DisplayPlayerCards(currPlayer);

                    // When it is the first trick and the first play of a Card, let the Player know what Card has to be played for the opening lead
                    if (numTrickRound == 1 && numCardThrown == 0) { // This section is just a friendly reminder/UI
                        System.out.println("\nREMINDER: The first trick must be led by " + openingCardStr + ".");
                        engine.SetLeadSuit(Card.CLUB); // The opening lead for the 1st trick is always Club (C-2 or C-3);
                    }

                    // This is for a case when the current player does not have Cards other than Hearts but the Heart has not been broken yet
                    // In such a case, the lead is passed to the next person.
                    if (!currPlayer.HasSuit(Card.CLUB) && !currPlayer.HasSuit(Card.DIAMOND) && !currPlayer.HasSuit(Card.SPADE) && !engine.GetIsHeartBroken()) {
                        System.out.println("\nPLAYER %d HAS TO SKIP THIS ROUND BECAUSE HE/SHE ONLY HAS HEARTS.\n");
                        engine.SwitchPlayer();
                    } else {
                        // When the above check passes, begin the trick (the time it takes for everyone to play a card)

                        // Prompts the current player for the Card to play, and convert their String input into a Card object
                        System.out.printf("PLAYER %d (%s), choose a Card to play: ", currPlayer.GetPlayerId(), currPlayer.GetPlayerName());
                        cardStr = input.nextLine();
                        card = engine.ConvertToCard(cardStr);


                        if (card == null) { // Error handling
                            System.out.println("\nWARNING: BAD NOTATION, PLEASE RE-TYPE THE CARD CORRECTLY.\n");
                        } else if (numTrickRound == 1 && numCardThrown == 0 && !card.equals(openingCard)) { // Error handling
                            // When it is the 1st trick and 1st play of a Card, and the lead player does not play the predetermined openingCard,
                            // then they are warned and asked to play a card again
                            System.out.println("\nWARNING: THE FIRST TRICK MUST BE LED BY \"" + openingCardStr + "\".\n");
                        } else { // When the current round is not the 1st trick and the 1st play of a Card of each hand
                            
                            // If it's the first throw/play of the trick, update the lead suit according to the lead player's card
                            // Note that the error handling above makes sure that the leading suit of the first trick is Club
                            if (numCardThrown == 0) { 
                                // If the card is NOT Heart OR the Heart is Broken (meaning Hearts are allowed), then update the lead suit
                                if (card.GetSuit() != Card.HEART || engine.GetIsHeartBroken()) {
                                    engine.SetLeadSuit(card.GetSuit());
                                }
                                // When the card is Heart and the Heart is not broken yet, the PlayCard() below takes care of it
                            }

                            // The engine determines if the player can play/throw the card specified
                            System.out.println("\nTEST: PlayCard() is called");
                            System.out.println("TEST: PLAYER INFO: " + currPlayer.GetPlayerId() + " " + currPlayer.GetPlayerName());
                            System.out.println("TEST: CARD INFO  : " + card.GetSuit());
                            System.out.println("TEST: card.GetSuit() == leadSuit: " + (card.GetSuit() == engine.GetLeadSuit()));
                            status = engine.PlayCard(currPlayer, card);
                            
                            System.out.println(); // Formatting purpose

                            // When the PlayCard() is successful, breaks a heart, or the currPlayer has to skip a trick
                            // The following methods are called (they are methods that are common in those three scenarios)
                            if (status == HeartEngine.SUCCESS || status == HeartEngine.HEART_HAS_BEEN_BROKEN || status == HeartEngine.SKIP_TRICK) {
                                engine.SwitchPlayer(); // Switches the current player
                                currPlayer.SetCardThrown(card); // The currPlayer throws the card
                                cardsThrown[pos] = card; // Add a card to cardsThrown to make up a trick
                                numCardThrown++; // Increments the number of card thrown 
                                pos++; // Increments the index of cardsThrown
                            }

                            // TEST
                            System.out.println("LEAD SUIT  : " + engine.GetLeadSuit());
                            System.out.println("PLAYER INFO: ID " + currPlayer.GetPlayerId() + " " + currPlayer.GetPlayerName());
                            System.out.println("CARD INFO  : " + card.GetSuit());

                            // The main purpose of this switch statement is to control the flow of the program such that appropriate messages are displayed
                            // and appropriate methods are called if necessary.
                            switch(status) {
                                // When the currPlayer throws the card SUCCESSFULLY
                                case HeartEngine.SUCCESS: 
                                    // Prints an appropriate message
                                    System.out.println("----------------------------------------");
                                    System.out.println("|               SUCCESS!               |");
                                    System.out.println("----------------------------------------");
                                    break;

                                // When the currPlayer "breaks the Heart". This is also considered successful.
                                case HeartEngine.HEART_HAS_BEEN_BROKEN:
                                    // Announces that the heart has been broken
                                    System.out.println("----------------------------------------");
                                    System.out.println("|        HEART HAS BEEN BROKEN!        |");
                                    System.out.println("----------------------------------------");
                                    System.out.println("YOU MAY NOW LEAD A TRICK WITH HEARTS.");
                                    engine.SetIsHeartBroken(true); // Sets isHeartBroken to true
                                    break;

                                // When the currPlayer throws an invalid Card (when they don't have it) or mistypes a Card
                                case HeartEngine.INVALID_CARD:
                                    // Let the player know that they either don't have the card or mistyped their card
                                    System.out.println("-------------------------------------------");
                                    System.out.println("| YOU DON'T HAVE THE CARD or BAD NOTATION |");
                                    System.out.println("-------------------------------------------");
                                    System.out.println("ENSURE YOU HAVE THE CARD OR TYPED THE NOTATION CORRECTLY.");
                                    break;

                                // When the currPlayer attemps to throw a Card of Hearts when heart is not broken
                                case HeartEngine.HEART_NOT_BROKEN:
                                    // Let the user know the Heart has not been broken yet
                                    System.out.println("----------------------------------------");
                                    System.out.println("|    HEART HAS NOT BEEN BROKEN YET!    |");
                                    System.out.println("----------------------------------------");
                                    System.out.println("YOU CANNOT PLAY A CARD OF HEART WHEN HEART HAS NOT BEEN BROKEN.");
                                    break;

                                // When the currPlayer can only skip
                                case HeartEngine.SKIP_TRICK:
                                    // Let the player know that the current player has no choice but to skip
                                    // When a player skips, they still have to play a card, but that card will not be
                                    // taken into consideration when it comes to collection of trick
                                    System.out.println("-------------------------------------------");
                                    System.out.printf("| PLAYER %d DON'T HAVE CHOICE BUT TO SKIP |\n", currPlayer.GetPlayerId());
                                    System.out.println("-------------------------------------------");
                                    break;
                                
                                // When the currPlayer attempts to throw a Card not of leading suit
                                // even though currPlayer has Cards in the leading suit
                                case HeartEngine.MUST_FOLLOW_SUIT:
                                    // Remind the player that suits must be followed if possible
                                    System.out.println("----------------------------------------");
                                    System.out.println("|   YOU MUST FOLLOW SUIT IF POSSIBLE!  |");
                                    System.out.println("----------------------------------------");
                                    break;

                                // When the currPlayer makes an illegal move in the first trick
                                case HeartEngine.ILLEGAL_IN_FIRST_TRICK:
                                    // Remind the player that certain moves are illegal in the first trick
                                    System.out.println("----------------------------------------");
                                    System.out.println("|   ILLEGAL MOVE IN THE FIRST TRICK    |");
                                    System.out.println("----------------------------------------");
                                    System.out.println("YOU CANNOT PLAY HEARTS OR QUEEN OF SPADE IN THE FIRST TRICK.");
                                    break;
                            }
                        }


                        // === DISPLAY OF TRICKS (cardThrown) ===
                        // System.out.println("DISPLAY CARD THROWN");
                        disp.DisplayCardThrown(cardsThrown);
                    }
                }

                // === COLLECTION OF TRICKS (cardsThrown) BY A PLAYER ===
                // Updates the cardsThrown (aka tricks) in HeartEngine so that it can be collected via CollectTrick()
                engine.SetCardsThrown(cardsThrown);

                // The Player who played a card with highest rank in the leading suit wins the cardsThrown (aka tricks)
                // The id of the Player who will lead the next trick is stored
                leadPlayerId = engine.CollectTrick();

                // Announces the Player who won the trick
                System.out.println("\n----------------------------------------");
                System.out.printf("| PLAYER %d ( %-8s ) %-14s |\n", leadPlayerId, engine.GetAllPlayers()[leadPlayerId].GetPlayerName(), "WINS THE TRICK");
                System.out.println("----------------------------------------\n");


                // === UPDATES THE currPlayer TO THE PLAYER WHO WON THE TRICK ===
                // This is because the person who won the trick leads the next trick
                engine.SetCurrPlayer(engine.GetAllPlayers()[leadPlayerId]);


                // === INCREMENTS the numTrickRound by 1 ===
                engine.SetNumTrickRound(engine.GetNumTrickRound() + 1); // Increments the numTrickRound by 1


                // === CHECKS IF SOMEONE HAS USED ALL OF THEIR CARDS AWAY ===
                // In such a case, the game proceeds to either next hand or the game end when there are winners or ties
                for (int playerId = 0; playerId < numPlayers; playerId++) {
                    // Checks if someone does not have a card at all
                    if (engine.GetAllPlayers()[playerId].GetPlayerCards().length == 0) {
                        exitHand = true; // Updates the sentinel value to break out of the loop
                    }
                }

            }
            
            // === INCREMENTS the numHandRound by 1 ===
            engine.SetNumHandRound(engine.GetNumHandRound() + 1);


            // === POINT CALCULATION ===
            // Calculates the point for every Player after each Hand, stores its status
            status = engine.CalcPoint();
            
            // An appropriate message is printed when someone "shoots the moon"
            if (status == HeartEngine.SHOT_THE_MOON) {
                System.out.println("--------------------------------------------------------");
                System.out.println("| SOMEONE SHOT THE MOON! +26 POINTS FOR EVERYONE ELSE! |");
                System.out.println("--------------------------------------------------------");
            }

            System.out.println("\nPOINTS HAVE BEEN CALCULATED!\n"); // Let the players know the points have been calculated


            // === CHECK WINNERS ===
            // Obtains the playerIds of the potential winners in an array
            winnerIds = engine.CheckWinner();

            if (winnerIds.length == numPlayers) {
                // If all players are "winners", meaning everyone got the same point while exceeding the losingPoint
                // Then the users are notified that the game tied, and the game ends
                System.out.println("\n====== GAME TIED ======");
                disp.DisplayPointsTable(engine.GetAllPlayers()); // Displays the Players' points
                exitGame = true; // Adjusts the sentinel value to exit the main game loop
            } else if (winnerIds.length != 0) {
                // If there is at least one player who won the game, announce the winner(s) and the game ends
                System.out.println("\n====== CONGRATULATIONS ======");
                disp.DisplayPointsTable(engine.GetAllPlayers()); // Displays the Players' points
                System.out.println(); // For formatting reason
                System.out.println("The following player(s) won the game!");

                // Prints out the information of all winner(s) row by row
                for (int i = 0; i < winnerIds.length; i++) {
                    winner = engine.GetAllPlayers()[winnerIds[i]];
                    System.out.printf("PLAYER %d: %s\n", winner.GetPlayerId(), winner.GetPlayerName());
                }

                exitGame = true; // Adjusts the sentinel value to exit the main game loop
            }
            // If the game does not tie or there is no winner yet, then the next hand begins
        }
        input.close(); // Closes the Scanner after the game ends
    }
}
