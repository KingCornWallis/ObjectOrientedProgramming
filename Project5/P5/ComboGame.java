import java.util.ArrayList;
import java.util.Scanner;


//This file contains the Game class, which holds the Main
//This class runs the game as well as display functions and universal game functions

public class ComboGame {



    final private boolean mancala = false;
    final private boolean backgammon = true;
    //public boolean gameType = false; //False is for Mancala, True is for BackGammon

    final private static boolean Switch = true; //Used for changing turn
    final private static boolean DontSwitch = false; //Used for changing turn
    public int turnCount = 0;

    public int dice1;
    public int dice2;
    private ArrayList<Integer> moves = new ArrayList<Integer>();

    public boolean gameOver = false; //Flag for game ending
    //private boolean turn; //true means P1, false means P2
    public boolean itIsPlayer1sTurn = true; //If true, it is player 1's turn, if not it is player 2's turn

    private String onePlayerName = "P1"; //Default names
    private String twoPlayerName = "P2"; //Default names

    private static Scanner scan = new Scanner(System.in); //Used for user input
    private ComboPlayer player1 = new ComboPlayer();
    private ComboPlayer player2 = new ComboPlayer();
    private ComboBoard b = new ComboBoard();


    public static void main(String[] args) {

        Integer index;
        Integer finalIndex = 0;
        ComboGame game = new ComboGame();
        ComboBoard board;

        game.setGameType(); //Determines the game to be played
        //ComboPlayer player1 = new ComboPlayer();
        //ComboPlayer player2 = new ComboPlayer();

        game.displayIntro(); //Opening informational dialog
        game.namingProcedure(); //Gives names to players
        game.rollDice(); //Determines who goes first

        if (game.itIsPlayer1sTurn) //Player1 is first; creates board oriented to Player1
        {
            board = new ComboBoard(game.player1, game.player2);
            System.out.println(game.onePlayerName + " starts");
        }
        else //Player2 is first; creates a board oriented to Player2
        {
            board = new ComboBoard(game.player2, game.player1);
            System.out.println(game.twoPlayerName + " starts");
        }

        while (!board.gameOver) {
            game.displayBoard();
            //game.moveRedirect();
            if (!game.getGameType()) {
                System.out.println("Enter a position to move: ");
                index = scan.nextInt();


                while (!board.validMove(index, finalIndex, game.moves)) //Wait until user enters a valid move
                {
                    game.displayBoard();
                    System.out.println("Enter a position to move: ");
                    index = scan.nextInt();
                }
                try //This block handles all input exceptions
                {
                    board.movePieces(index, finalIndex, game.moves);
                } catch (Exception e) {
                    System.out.println("Invalid input");
                }
                if (game.itIsPlayer1sTurn) {
                    System.out.println(game.onePlayerName + " moved pieces at index " + index);
                } else
                    System.out.println(game.twoPlayerName + " moved pieces at index " + index);

                if (!board.gameOver) //Creates new boards changing the orientation to the current player's based on turn
                    if (game.itIsPlayer1sTurn && !board.goAgain) {
                        game.turnHandler(Switch);
                        board = new ComboBoard(game.player2, game.player1);
                    } else {
                        if (!game.itIsPlayer1sTurn && !board.goAgain) {
                            game.turnHandler(Switch);
                            board = new ComboBoard(game.player1, game.player2);
                        } else {
                            game.turnHandler(DontSwitch);

                        }
                    }
            }
            else
            {

                game.rollDice();
                System.out.println("Enter start and end indices separated by a space: ");
                index = scan.nextInt();
                finalIndex = scan.nextInt();
                System.out.println(index + " " + finalIndex);
            }
            while (!board.validMove(index, finalIndex, game.moves)) //Wait until user enters a valid move
            {
                game.displayBoard();
                System.out.println("Enter start and end indices separated by a space: ");
                index = scan.nextInt();
                finalIndex = scan.nextInt();
                System.out.println(index + " " + finalIndex);
            }

            try
            {
                board.movePieces(index, finalIndex, game.moves);
            } catch(Exception e)
            {
                System.out.println("Invalid input format");
            }
            if (game.itIsPlayer1sTurn) {
                System.out.println(game.onePlayerName + " moved a piece from " + index + " to " + finalIndex);
            } else
                System.out.println(game.twoPlayerName + " moved a piece from " + index + " to " + finalIndex);

            if (!board.gameOver) //Creates new boards changing the orientation to the current player's based on turn
                if (game.itIsPlayer1sTurn && !board.goAgain) {
                    game.turnHandler(Switch);
                    board = new ComboBoard(game.player2, game.player1);
                } else {
                    if (!game.itIsPlayer1sTurn && !board.goAgain) {
                        game.turnHandler(Switch);
                        board = new ComboBoard(game.player1, game.player2);
                    } else {
                        game.turnHandler(DontSwitch);

                    }
                }
        }

        if (board.determineWinner().equals(game.player1)) {
            System.out.println(game.onePlayerName + " won!");
        } else {
            System.out.println(game.twoPlayerName + " won!");
        }
    }

    public void displayIntro() {
        if (!getGameType()) //Mancala
        {
            System.out.println("Welcome to Mancala");
            System.out.println("Player2 is on top moving right to left");
            System.out.println("Player1 is on bottom moving left to right");
        } else//Backgammon
        {
            System.out.println("Welcome to Backgammon");
            System.out.println("Player2 is Black (B), and has a home board in the top right.");
            System.out.println("Player1 is White (W), and has a home board in the bottom right.");
        }
    }

    public void namingProcedure() //Allows users to name players
    {
        System.out.println("Enter a name for Player 1: ");
        onePlayerName = scan.next();
        player1.setName(onePlayerName);

        System.out.println("Enter a name for Player 2: ");
        twoPlayerName = scan.next();
        player2.setName(twoPlayerName);
    }

    public void setGameType(){
        System.out.println("What game would you like to play?");
        System.out.println("Enter M for Mancala or B for Backgammon: ");
        Boolean gameSet = false;

        while (!gameSet) {
            String input = scan.next();
            input = input.toLowerCase();
            switch (input) {
                case "m":
                    b.gameType = mancala;
                    gameSet = true;
                    break;
                case "b":
                    b.gameType = backgammon;
                    gameSet = true;
                    break;
                default:
                    System.out.println("Invalid choice. Enter another choice: ");
            }
        }
    }
    public boolean getGameType(){
        return b.gameType;
    }

    public void rollDice() //Simple dice function for start of game and onwards
    {
        int bonus = 0; //used for doubles in backgammon
        emptyMovesArray();
        System.out.println("Rolling Dice");
        dice1 = (int) (Math.random() * 6 + 1);
        dice2 = (int) (Math.random() * 6 + 1);

        if (turnCount == 0) { //for the start of the game only

            System.out.println(player1.getName() + " rolled a " + dice1 + " , " + player2.getName() + " rolled a " + dice2);

            if (dice1 == dice2) {
                System.out.println("Tie! Rolling again");
                rollDice();
            } else if (dice1 > dice2) {
                itIsPlayer1sTurn = true; //P1 wins
            } else {
                itIsPlayer1sTurn = false; //P2 wins
            }
        } else { //for backgammon turns
            if (dice1 == dice2) {
                if (itIsPlayer1sTurn)
                    System.out.println(player1.getName() + " rolled doubles! That means 4 moves of size " + dice1);
                else
                    System.out.println(player2.getName() + " rolled doubles! That means 4 moves of size " + dice1);
                bonus = dice1;
            } else {
                if (itIsPlayer1sTurn)
                    System.out.println(player1.getName() + " rolled a " + dice1 + " and a " + dice2);
                else
                    System.out.println(player2.getName() + " rolled a " + dice1 + " and a " + dice2);
            }
        }
        moves.add(0, dice1);
        moves.add(1, dice2);
        moves.add(2, bonus);
        moves.add(3, bonus);
        //return moves;
    }
    public void emptyMovesArray() //Resets the contents of the dice value array
    {
        for(int i = 0; i < 4; i++)
        {
            moves.add(i, 0);
        }
    }
    public void moveRedirect()
    {

    }

    public void turnHandler(boolean doIt) //Can change turns and display whose turn it is, or just display the turn
    {
        if (itIsPlayer1sTurn) {
            if (doIt) {
                itIsPlayer1sTurn = false;
                System.out.println("It is " + player2.getName() + "'s turn"); //P2 turn
            } else
                System.out.println("It is " + player1.getName() + "'s turn"); //P1 turn
        } else {
            if (doIt) {
                itIsPlayer1sTurn = true;
                System.out.println("It is " + player1.getName() + "'s turn"); //P1 turn
            } else {
                System.out.println("It is " + player2.getName() + "'s turn"); //P2 turn
            }
        }
        turnCount++;
    }

    public void displayBoard() {

        if(!getGameType()) { //Mancala
            System.out.println("---------------------------------------");
            System.out.println(player2.getName() + "'s Side");
            for (int i = 5; i > -1; i--) {
                if (player2.getPitCount(i) < 10) {
                    System.out.print(player2.getPitCount(i) + "  ");
                } else {
                    System.out.print(player2.getPitCount(i) + " ");
                }
            }
            System.out.println("  Pits 5 <- 0");
            System.out.println(player2.getStoreCount() + "              " + player1.getStoreCount());
            for (int i = 0; i < 6; i++) {
                if (player1.getPitCount(i) < 10) {
                    System.out.print(player1.getPitCount(i) + "  ");
                } else {
                    System.out.println(player1.getPitCount(i) + " ");
                }
            }
            System.out.println("  Pits 0 -> 5");
            System.out.println(player1.getName() + "'s Side");
            System.out.println("---------------------------------------");
        }
        else {
            System.out.println("---------------------------------------");
            System.out.println(player2.getName() + "'s Side (B): " + player2.getStoreCount());
            boolean newline = false; //true is W, false is B
            ArrayList<Integer> boardValues = new ArrayList<>();
            ArrayList<Integer> pieceColor = new ArrayList<>(); //0 W, 1 B, 2 Empty
            for (int i = 0; i < 24; i++) {
                boardValues.add(0);
                pieceColor.add(2);
            }
            for (int i = 0; i < 24; i++) {
                if (i < 12) {
                    if (player1.getPitCount(i) > 0) //white
                    {
                        boardValues.set(23 - i, player1.getPitCount(i));
                        pieceColor.set(23 - i, 0);
                    }
                    if (player2.getPitCount(i) > 0) //black
                    {
                        boardValues.set(11 - i, player2.getPitCount(i));
                        pieceColor.set(11 - i, 1);
                    }

                }
                if (i > 11) {
                    if (player1.getPitCount(i) > 0) //white
                    {
                        boardValues.set(i - 12, player1.getPitCount(i));
                        pieceColor.set(i - 12, 0);
                    }
                    if (player2.getPitCount(i) > 0) //black
                    {
                        boardValues.set(i, player2.getPitCount(i));
                        pieceColor.set(i, 1);
                    }
                }
            }

            for (int j = 0; j < 24; j++) {

                if(j > 11 && !newline) {
                    System.out.println();
                    newline = true;
                }
                if (pieceColor.get(j) == 0) {
                    System.out.print(" W" + boardValues.get(j) + "  ");
                }
                else if (pieceColor.get(j) == 1) {
                    System.out.print(" B" + boardValues.get(j) + "  ");
                }
                else
                    System.out.print("  " + 0 + "  ");
            }

            System.out.println();
            System.out.println(player1.getName() + "'s Side (W): " + player1.getStoreCount());
            System.out.println("---------------------------------------");
        }
    }
}
