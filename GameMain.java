import java.util.Scanner;
public class GameMain {
    public static void main(String[] args) {
        // === VARIABLES ===
        // Instances of Classes that handle input/displaying/backend logic
        Scanner input = new Scanner(System.in); // Used for obtaining user inputs
        Displayer disp = new Displayer(); // Used for displaying majority of the information
        HeartEngine engine; // Used for back-end processes and logic

        // Variables used throughout the program
        int option;
        int numPlayers;
        String[] playerNames;
        String individualName;
        int losingPoint = 0;
        Player currPlayer;
        String cardStr;   
        Card card;

        // For the purpose of passing rotation
        Card[][] passingCards;
        
        // Used when announcing winners
        int[] winnerIds;
        Player winner;

        // Sentinel values that control the flow of the entire game
        boolean exitGame = false;
        boolean exitHand = false;
        boolean exitTrick = false;


        // === TITLE PAGE ===
        System.out.println("======================================");
        System.out.println("|            GAME OF HEART           |");
        System.out.println("======================================");
        System.out.println("PROGAMMED BY: HARUKI and POURIA");
        System.out.println("   COURSE ID: ICS4U");
        System.out.println("         DUE: 2021/11/25");
        System.out.println("\nPRESS ENTER TO PROCEED ...");
        input.nextLine();

        // === GAME RULE EXPLANATION ===
        System.out.println("======================================");
        System.out.println("|              GAME RULE             |");
        System.out.println("======================================");

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
            System.out.println("======================================");
            System.out.println("|  SET UP, LET US KNOW WHO YOU ARE   |");
            System.out.println("======================================");
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
                    playerNames[i] = individualName;
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
        System.out.print("Option: ");
        option = input.nextInt();

        if (option == 1) { // When the user chooses default mode
            // The losingPoint is set to default losingPoint
            losingPoint = HeartEngine.DEFAULT_LOSING_POINT;
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
        }
        
        // Instantiates the HeartEngine
        engine = new HeartEngine(numPlayers, playerNames, losingPoint);
        
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
            int pos; // The current position of Card

            System.out.println("======================================");
            System.out.println("|          PASSING ROTATION          |");
            System.out.println("======================================");
            System.out.println("Each player has to pick three cards from their hand to be passed to another player.");
            // Prompts each Player for the three cards that they want to pass
            for (int playerId = 0; playerId < numPlayers; playerId++) {
                // Displays the playerCards of each Player
                currPlayer = engine.GetAllPlayers()[playerId];
                disp.DisplayPlayerCards(currPlayer);
                pos = 0; // Resets the current position of Card to be passed
                System.out.printf("PLAYER %d, choose three cards you want to pass.\n", playerId);
                while (pos < 3) {
                    input.nextLine(); // Fixes the scanner
                    // Promps the user to type the string representation of the Card they want to pass
                    System.out.printf("#%d Card (Type it): ", pos + 1);
                    cardStr = input.nextLine();

                    /*
                    card = engine.ConvertToCard(cardStr);
                    if (!currPlayer.HasCard(card)) { // ERROR HANDLING
                        // An appropriate message is printed when the player does not have the card they typed
                        // Or they mis-typed the card
                        System.out.println("\nWARNING: YOU DON'T HAVE THAT CARD or YOU MIS-TYPED YOUR CARD\n");
                    } else {
                        // Adds the chosen card to the array
                        passingCards[playerId][pos] = card;
                        // Update the current pos of the Card to the correct index when there is no error
                        pos++;
                    }
                    */

                }
            }

            // This loop runs until all the players used all of their hand, and needs to be dealt cards again
            while(!exitHand) {
                
                // This loops runs until all the players played one of their Cards to make up a trick
                while(!exitTrick) {

                }
            }

            // === POINT CALCULATION ===
            // Calculates the point for every Player after each Hand
            engine.CalcPoint();

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
