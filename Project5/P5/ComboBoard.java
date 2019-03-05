//This file contains the Board class
//This class creates 2 references variables P1 and P2
//P1 is actually the player whose turn it is, and not necessarily PlayerOne
//Everything is oriented to P1, whose first index is 0
//When P2 is used, it's first index is at 5 on their side

import java.util.ArrayList;
import java.util.Scanner;

public class ComboBoard
{
    private ComboPlayer P1, P2; //Instantiations are not needed as the methods are not static
    public boolean goAgain; //Used for free turns

    private static Scanner scan = new Scanner(System.in);

    public boolean gameType = false; //False is for Mancala, True is for BackGammon

    public boolean gameOver = false;
    public boolean turn; //True means P1, false means P2
    public ComboBoard()
    {

    }

    public ComboBoard(ComboPlayer P1, ComboPlayer P2) //Whatever players are passed into this constructor turn into P1 and P2 to be used in the Board Class
    {
        this.P1 = P1;
        this.P2 = P2;
    }
    public void movePieces(int index, int finalIndex, ArrayList moves) //Moves stones across pits on board
    {
        goAgain = false;
        int selectedPieces = P1.getPitCount(index); //Get stones in selected pit

        if (2 < 1) {
            P1.setPitCount(index, 0); //Set selected pit to 0

            for (int i = selectedPieces; i > 0; i--) //Add 1 stone to each successive index until you run out
            {
                index++;

                switch (index) {
                    case 14:
                        index = 0; //larger than board; reset to 0
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5: //Player can only choose indices 0 - 5
                        if (P1.getPitCount(index) == 0 && selectedPieces == 1) //take all stones from players corresponding index if conditions are met
                        {
                            P1.setStoreCount(P1.getStoreCount() + P2.getPitCount(5 - index) + 1);
                            P2.setPitCount(5 - index, 0);
                        } else //Play as normal
                            P1.setPitCount(index, P1.getPitCount(index) + 1);
                        break;
                    case 6: //Handles when stone passes store
                        P1.setStoreCount(P1.getStoreCount() + 1);
                        if (i == 1) //Free turn if last stone lands in store
                            goAgain = true;
                        break;
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12: //Player does not see/use these indices; strictly for game operations
                        P2.setPitCount(index - 7, P2.getPitCount(index - 7) + 1);
                        break;
                    case 13: //Handles when stone passes store
                        P2.setStoreCount(P2.getStoreCount() + 1);
                        break;
                    default:
                        //Fix
                        break;

                }
                selectedPieces--; //Decrement number of stones still moving
            }
            isEmpty(); //Check if player's board just became empty
        }
        else //backgammon
        {

            System.out.println("Backgammon move");

            P1.setPitCount(index, P1.getPitCount(index) - 1);
            P1.setPitCount(finalIndex, P1.getPitCount(finalIndex) + 1);
        }

    }

    public void isEmpty() //Check if a player's board has no stones
    {
        if (P1.getRemainingPieces() == 0 || P2.getRemainingPieces() == 0) {
            if (P1.getRemainingPieces() == 0) {
                takeRemainingPieces(P2);
            } else {
                takeRemainingPieces(P1);
            }
            gameOver = true;
        }
    }

    public void takeRemainingPieces(ComboPlayer loser) //Used for when one player clears their side of the board
    {
        loser.setStoreCount(loser.getStoreCount() + loser.getRemainingPieces());
        loser.removeAllPieces();
    }
    public boolean readyToStore()
    {
        boolean ready = true;
        for(int i = 6; i < 24; i++)
        {
            if (P1.getPitCount(i) > 0 || P1.getBarCount() > 0)
                ready = false;
        }
        return ready;
    }

    public ComboPlayer determineWinner()  //Compares player's score
    {
        if (P1.getStoreCount() > P2.getStoreCount()) {
            return P1;
        } else {
            return P2;
        }
    }
    public boolean validMove(int index, int finalIndex, ArrayList moves) //handles move exceptions
    {
        if (!gameType) {
            if (index > 5 || P1.getPitCount(index) == 0) {
                System.out.println("Invalid index!");
                return false;
            } else
                return true;
        }
        else //Backgammon
        {
            if(index > 23 || P1.getPitCount(index) == 0)
            {
                System.out.println("Invalid index!");
                return false;
            }
            else if(P2.getPitCount(index) > 1)
            {
                System.out.println("Your opponent already has pieces in this spot!");
                return false;
            }
            int move = finalIndex - index;
            boolean validMove = false;
            for (int i = 0; i < 4; i++)
            {
                if(moves.get(i).equals(move))
                {
                   validMove = true;
                   break;
                }
            }



            //else
             return true;
        }
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
                    gameType = false;
                    gameSet = true;
                    break;
                case "b":
                    gameType = true;
                    gameSet = true;
                    break;
                default:
                    System.out.println("Invalid choice. Enter another choice: ");
            }
        }
    }
    public boolean getGameType(){
        return gameType;
    }
}
