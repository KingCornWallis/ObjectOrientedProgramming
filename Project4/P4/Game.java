import java.util.Scanner;

//This file contains the Game class, which holds the Main
//This class runs the game as well as display functions and universal game functions

public class Game {

    final private static boolean Switch = true; //Used for changing turn
    final private static boolean DontSwitch = false; //Used for changing turn

    public boolean gameOver = false; //Flag for game ending
    private boolean turn; //true means P1, false means P2

    private String onePlayerName = "P1"; //Default names
    private String twoPlayerName = "P2"; //Default names

    private static Scanner scan = new Scanner(System.in); //Used for user input
    private Player player1 = new Player();
    private Player player2 = new Player();


    public static void main(String[] args) {

        Integer index;
        Game game = new Game();
        Board board;

        game.displayIntro(); //Opening informational dialog
        game.namingProcedure(); //Gives names to players
        game.rollDice(); //Determines who goes first

        if (game.turn) //Player1 is first; creates board oriented to Player1
        {
            board = new Board(game.player1, game.player2);
            System.out.println(game.onePlayerName + " starts");
        }
        else //Player2 is first; creates a board oriented to Player2
        {
            board = new Board(game.player2, game.player1);
            System.out.println(game.twoPlayerName + " starts");
        }

        while (!board.gameOver) {

            game.displayBoard();
            System.out.println("Enter a position to move: ");
            index = scan.nextInt();
            while (!board.validMove(index)) //Wait until user enters a valid move
            {
                game.displayBoard();
                System.out.println("Enter a position to move: ");
                index = scan.nextInt();
            }
            try //This block handles all input exceptions
            {
                board.moveStones(index);
            }
            catch (Exception e)
            {
                System.out.println("Invalid input");
            }
            if (game.turn) {
                System.out.println(game.onePlayerName + " moved stones at index " + index);
            } else
                System.out.println(game.twoPlayerName + " moved stones at index " + index);

            if (!board.gameOver) //Creates new boards changing the orientation to the current player's based on turn
                if (game.turn && !board.goAgain) {
                    game.turnHandler(Switch);
                    board = new Board(game.player2, game.player1);
                } else {
                    if (!game.turn && !board.goAgain) {
                        game.turnHandler(Switch);
                        board = new Board(game.player1, game.player2);
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

    public void namingProcedure() //Allows users to name players
    {
        System.out.println("Enter a name for Player 1: ");
        onePlayerName = scan.next();
        //player1 = new Player(tname);
        System.out.println("Enter a name for Player 2: ");
        twoPlayerName = scan.next();
        //player2 = new Player(tname);
    }

    public void rollDice() //Simple dice function for start of game
    {
        System.out.println("Rolling Dice");
        int dice1 = (int) (Math.random() * 6 + 1);
        int dice2 = (int) (Math.random() * 6 + 1);
        System.out.println(onePlayerName + " rolled " + dice1 + " " + twoPlayerName + " rolled a " + dice2);

        if (dice1 == dice2) {
            System.out.println("Tie! Rolling again");
            rollDice();
        } else if (dice1 > dice2)
            turn = true; //P1 wins
        else
            turn = false; //P2 wins
    }

    public void turnHandler(boolean doIt) //Can change turns and display whose turn it is, or just display the turn
    {
        if (turn) {
            if (doIt) {
                turn = false;
                System.out.println("It is " + twoPlayerName + "'s turn"); //P2 turn
            } else
                System.out.println("It is " + onePlayerName + "'s turn"); //P1 turn
        } else {
            if (doIt) {
                turn = true;
                System.out.println("It is " + onePlayerName + "'s turn"); //P1 turn
            } else {
                System.out.println("It is " + twoPlayerName + "'s turn"); //P2 turn
            }

        }
    }

    public void displayIntro() {
        System.out.println("Welcome to Mancala");
        System.out.println("Player2 is on top moving right to left");
        System.out.println("Player1 is on bottom moving left to right");
    }

    public void displayBoard() {
        System.out.println("---------------------------------------");
        System.out.println(twoPlayerName + "'s Side");
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
            if (player2.getPitCount(i) < 10) {
                System.out.print(player1.getPitCount(i) + "  ");
            } else {
                System.out.println(player1.getPitCount(i) + " ");
            }
        }
        System.out.println("  Pits 0 -> 5");
        System.out.println(onePlayerName + "'s Side");
        System.out.println("---------------------------------------");
    }
}
