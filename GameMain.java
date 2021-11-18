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
        int endingPoint = 0; // The point at which the game ends
        Player currPlayer; // The current player of interest
        String cardStr; // User-input string that represent a Card object (e.g. H-2 -> 2 of Heart)
        Card card; // General Card object
        Card openingCard; // The Card that will make the opening lead (depends on numPlayers)
        int status = 0; // Represents success or error codes
        int pos; // General representation of an index of an array
        int leadPlayerId; // The id of the player who will lead the trick

        // For the purpose of passing rotation
        Card[][] passingCards;
        
        // Used when announcing winners
        int[] winnerIds;
        Player winner;

        // Sentinel values that control the flow of the entire game
        boolean exitGame = false; // Controls whether the game continues
        boolean exitHand = false; // Controls whether each hand continues
        boolean exitTrick = false; // Controls whether each trick continues


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
            disp.ExplainRule(i); // Rules are explained from Section #0 up to Section #NUM_SECTIONS
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

        
        System.out.println(); // Formatting purpose
        
        
        // This loop is responsible for forcing the user to input either 1 or 2
        while (true) {
            // This section is responsible for prompting users for the mode in which they want to play
            // Prompts the user if they would like to play a default or a customized game
            System.out.println("Would you like to play a: ");
            System.out.println("1. Default Mode (ending point of 50)");
            System.out.println("2. Custom Mode (customize ending point)");
            System.out.print("Option: ");
            option = input.nextInt();
            input.nextLine(); // Fixes the scanner

            if (option == 1) { // When the user chooses default mode
                // The endingPoint is set to default endingPoint
                endingPoint = HeartEngine.DEFAULT_ENDING_POINT;
                break;
            } else if (option == 2) { // When the user chooses custom mode
                // This while-loop is responsible for ensuring the customized ending point is valid
                while(true) {
                    // Prompts the user for customized endingPoint
                    System.out.print("Enter customized ending point (>0): ");
                    endingPoint = input.nextInt();
                    input.nextLine(); // Fixes the scanner
    
                    if (endingPoint > 0) {
                        break; // If the endingPoint is positive, break out of the loop
                    } else {
                        // When the user inputs negative endingPoint, warning is shown and loop continues
                        System.out.println("\nWARNING: Invalid Ending Point\n");
                    }
                }
                break;
            } else { // Warning message when a wrong input is chosen
                System.out.println("\nWARNING: INVALID OPTION\n");
            }
        }
        
        // Instantiates the HeartEngine
        engine = new HeartEngine(numPlayers, playerNames, endingPoint);


        // === IDENTIFY THE CARD THAT MAKES AN OPENING LEAD ===
        // The player with 2 of Club make the opening lead.
        // If 2 of Club is removed in 5-player game, the player with 3 of Club makes the opening lead instead.
        openingCard = engine.GetOpeningCard(); // openingCard has already been set in the constructor according to numPlayers

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
            // input.nextLine(); // Fixes the scanner

            // This is for the case when it's the 4-player game, and it's the fourth hand,
            // in which case the Cards will not be passed
            if (numPlayers == 4 && engine.GetNumHandRound()%4 == 0) {
                System.out.println("\nIT'S THE FOURTH HAND, SO THERE IS NO PASSING ROTATION!\n");
            } else { // When above check passes, proceed to prompt each Player what cards they want to pass
                for (int playerId = 0; playerId < numPlayers; playerId++) {
                    currPlayer = engine.GetAllPlayers()[playerId]; // Obtains player of current iteration
                    pos = 0; // Resets the current position of Card to be passed
                    System.out.printf("\nPLAYER %d (%s), choose three cards you want to pass.\n", playerId, currPlayer.GetPlayerName());
                    
                    // This while loop ensures that each Player passes three Cards
                    while (pos < 3) {
                        disp.DisplayPlayerCards(currPlayer); // Displays the playerCards of each Player
                        
                        // Prompts the user to type the string representation of the Card they want to pass
                        System.out.printf("\n#%d Card (Type it): ", pos + 1);
                        cardStr = input.nextLine(); 
    
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

            
            // Cards will be passed as long as the numPlayers is NOT 4 OR numHandRound IS NOT 4
            // This is because PASSING ROTATION is skipped when it's the fourth hand in a 4-player game
            if (numPlayers != 4 || engine.GetNumHandRound() != 4) {
                // The engine passes the Cards among Players according to the number of hand round.
                engine.PassCards(passingCards);
                System.out.println("\nTHE THREE CARDS FROM EACH PLAYER ARE PASSED TO ANOTHER PLAYER.\n");
            }
            
            
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
            engine.SetIsHeartBroken(false); // Reset the "heart has been broken" status to false every hand
            // Resets the number of trick rounds every hand (Note: Counts from 1)
            engine.SetNumTrickRound(1);
            
            // === LOOP RUNS UNTIL EVERYONE LOSES ALL OF THEIR HAND ===
            // This loop runs until all the players used all of their hand, and needs to be dealt cards again
            while(!exitHand) {

                engine.SetNumCardsThrown(0); // Resets the numCardsThrown to 0 every trick

                pos = 0; // Resets the position/index of cardsThrown to 0 every trick
                engine.SetCardsThrown(new Card[numPlayers]); // Updates the cardsThrown (aka tricks) in HeartEngine to an empty array

                exitTrick = false; // Resets the sentinel value every trick

                // This loops runs until all the players played one of their Cards to make up a trick
                while (!exitTrick) {
                    // Updates and stores the current Player into currPlayer for readability
                    currPlayer = engine.GetCurrPlayer(); 

                    disp.DisplayRounds(engine.GetNumHandRound(), engine.GetNumTrickRound());

                    // Displays all the Cards the current player holds
                    disp.DisplayPlayerCards(currPlayer);

                    // When it is the first trick and the first play of a Card, let the Player know what Card has to be played for the opening lead
                    if (engine.GetNumTrickRound() == 1 && engine.GetNumCardsThrown() == 0) { // This section is just a friendly reminder/UI
                        System.out.println("\nREMINDER: The first trick must be led by " + openingCard.toString() + ".");
                    }

                    // Displays current leading suit if it isn't the first throw/play in a trick
                    // This is because the leading suit is decided by the first throw/play in a trick, thus make no sense to display it.
                    if (engine.GetNumCardsThrown() != 0) {
                        System.out.println("LEADING SUIT: " + Card.SUITS_NAME[engine.GetLeadSuit()]);
                    }

                    // Displays if the Heart has been broken yet in a formatted way
                    System.out.println("HEART HAS BEEN BROKEN: " + (engine.GetIsHeartBroken() + "").toUpperCase());
                    

                    // Prompts the current player for the Card to play, and convert their String input into a Card object
                    System.out.println(); // formatting purpose
                    System.out.printf("PLAYER %d (%s), choose a Card to play: ", currPlayer.GetPlayerId(), currPlayer.GetPlayerName());
                    cardStr = input.nextLine();
                    card = engine.ConvertToCard(cardStr);

                    // The engine determines if the player can play/throw the card specified
                    status = engine.PlayCard(currPlayer, card);

                    System.out.println(); // Formatting purpose


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
                        
                        // When the currPlayer does not play the openingCard in for the first throw of a Card in the first trick
                        case HeartEngine.MUST_PLAY_OPENING_CARD:
                            // Remind the player that the first trick must be led by openingCard
                            System.out.println("----------------------------------------");
                            System.out.printf("|  THE FIRST TRICK MUST BE LED BY %3s  |\n", openingCard.toString());
                            System.out.println("----------------------------------------");
                            break;

                        // When the currPlayer have no choice but to give the lead to the next player
                        case HeartEngine.GIVE_LEAD_TO_NEXT:
                            System.out.println("--------------------------------------------");
                            System.out.printf("| PLAYER %d HAS TO PASS LEAD TO NEXT PLAYER |\n", currPlayer.GetPlayerId());
                            System.out.println("--------------------------------------------");
                            // engine.SetCardsThrown(null); // Sets the cardsThrown to null to indicate that no Cards are thrown this trick
                            break;
                    }

                    // When the PlayCard() is successful, breaks a heart, the currPlayer has to skip a trick in the first trick, or the currPlayer has to give lead to the next Player
                    // The following methods are called (they are methods that are common in those three scenarios)
                    if (status == HeartEngine.SUCCESS || status == HeartEngine.HEART_HAS_BEEN_BROKEN || status == HeartEngine.SKIP_TRICK || status == HeartEngine.GIVE_LEAD_TO_NEXT) {
                        // Only when the player is not skipping (meaning only when success or breaking a heart), the player throws that card
                        if (status == HeartEngine.SUCCESS || status == HeartEngine.HEART_HAS_BEEN_BROKEN) {
                            currPlayer.SetCardThrown(card); // The currPlayer throws the card
                            engine.GetCardsThrown()[pos] = card; // Add a card to cardsThrown to make up a trick
                        }

                        engine.SwitchPlayer(); // Switches the current player for next iteration
                        
                        
                        // In a case where currPlayer has to skip playing a Card in the first trick, set their card to null to indicate that they are skipping
                        if (status == HeartEngine.SKIP_TRICK) {
                            card = null;
                        }
                        
                        
                        // Increments the number of card thrown. This still occurs even if the player skips.
                        engine.SetNumCardsThrown(engine.GetNumCardsThrown() + 1); 
                        pos++; // Increments the index of cardsThrown
                    }


                    // === DISPLAY OF TRICKS (cardThrown) ===
                    disp.DisplayCardThrown(engine.GetCardsThrown());
                    

                    // Adjusts the sentinel value to break out of a trick when everyone has thrown/played a card (numCardsThrown >= numPlayers)
                    // OR when currPlayer has to give the lead to the next Player according to the status
                    if (engine.GetNumCardsThrown() >= numPlayers || status == HeartEngine.GIVE_LEAD_TO_NEXT) {
                        exitTrick = true;
                    }

                }

                // If status is not GIVE_LEAD_TO_NEXT, proceed to collect tricks and announce the trick winner
                if (status != HeartEngine.GIVE_LEAD_TO_NEXT) {
                    // === COLLECTION OF TRICKS (cardsThrown) BY A PLAYER ===
                    // The Player who played a card with highest rank in the leading suit wins the cardsThrown (aka tricks)
                    // The id of the Player who will lead the next trick is stored
                    leadPlayerId = engine.CollectTrick();


                    // === UPDATES THE currPlayer TO THE PLAYER WHO WON THE TRICK ===
                    // This is because the person who won the trick leads the next trick
                    engine.SetCurrPlayer(engine.GetAllPlayers()[leadPlayerId]);


                    // === ANNOUNCEMENT OF TRICK WINNER ===
                    disp.AnnounceTrickWinner(engine.GetCurrPlayer());
                    

                    // === INCREMENTS the numTrickRound by 1 ===
                    engine.SetNumTrickRound(engine.GetNumTrickRound() + 1); // Increments the numTrickRound by 1

                }  
                // If the status is GIVE_LEAD_TO_NEXT, then the game simply proceeds with the next player
                

                // === CHECKS IF SOMEONE HAS USED ALL OF THEIR CARDS AWAY ===
                // In such a case, the game proceeds to either next hand or the game ends when there are winners or ties
                exitHand = engine.HasHandEnded(); // Updates the sentinel value based on if someone has used all of their cards away

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

            // === PRINTS OUT EVERY PLAYER'S POINTS ===
            // This is the second time the player's points are displayed in one hand
            // This is so that Players can check their points more frequently
            disp.DisplayPointsTable(engine.GetAllPlayers());

            System.out.println("\nPOINTS HAVE BEEN CALCULATED!\n"); // Let the players know the points have been calculated


            // === RESET PLAYER'S TRICKS EVERY HAND ===
            // Iterate through every player and reset their playerTricks
            for (int playerId = 0; playerId < numPlayers; playerId++) {
                engine.GetAllPlayers()[playerId].SetPlayerTricks(new Card[0]);
            }


            // === CHECK WINNERS ===
            // Obtains the playerIds of the potential winners in an array
            winnerIds = engine.CheckWinner();

            if (winnerIds.length == numPlayers) {
                // If all players are "winners", meaning everyone got the same point while exceeding the endingPoint
                // Then the users are notified that the game tied, and the game ends
                System.out.println();
                System.out.println("----------------------------------------");
                System.out.println("|               GAME TIED!             |");
                System.out.println("----------------------------------------");
                disp.DisplayPointsTable(engine.GetAllPlayers()); // Displays the Players' points
                exitGame = true; // Adjusts the sentinel value to exit the main game loop
            } else if (winnerIds.length != 0) {
                // If there is at least one player who won the game, announce the winner(s) and the game ends
                System.out.println();
                System.out.println("----------------------------------------");
                System.out.println("|            CONGRATULATIONS!          |");
                System.out.println("----------------------------------------");
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
