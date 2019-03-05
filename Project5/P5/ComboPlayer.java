import java.util.ArrayList;

//This file contains the Player class
//This class assigns half of the board to a player and handles relevant operations

public class ComboPlayer
{
    private ArrayList<Integer> playerPits = new ArrayList<>(); //The array of stones for a player
    private int playerStore; //The player's score
    private int playerBar;
    private int defaultPieces = 4; //Amount of stones to be filled in pits at start of game
    private String playerName;
    ComboBoard b = new ComboBoard();

    ComboPlayer() //Default constructor that fills half of a board with stones
    {
        if (b.getGameType()) //Mancala, indices 0 - 5
        {
            for (int i = 0; i < 6; i++)
                playerPits.add(defaultPieces);
        } else //Backgammon, indices 0 - 23
        {
            for (int i = 0; i < 24; i++) {
                playerPits.add(0);
            }
            playerPits.set(5, 5);
            playerPits.set(7, 3);
            playerPits.set(12, 5);
            playerPits.set(23, 2);
        }
    }

    public ArrayList<Integer> getPlayerPits() //A player's half of the board
    {
        return playerPits;
    }

    public int getPitCount(int index) //Number of stones in a selected pit
    {
        return playerPits.get(index);
    }

    public void setPitCount(int index, int pieces) //Set number of stones in a selected pit
    {
        playerPits.set(index, pieces);
    }
    public int getBarCount()
    {
        return playerBar;
    }

    public void setBarCount(int sign)
    {
        if(sign > 0)
            playerBar++;
        if(sign < 0)
            playerBar--;
    }

    public int getStoreCount() //Get the player's score
    {
        return playerStore;
    }

    public void setStoreCount(int pieces) //Set the player's score
    {
        playerStore = pieces;
    }

    public int getRemainingPieces() //Determine stones still left on player's half of the board
    {
        int remainder = 0;
        if (!b.getGameType()) //Mancala
        {

            for (int i = 0; i < 6; i++) {
                remainder += playerPits.get(i);
            }
        } else //Backgammon
        {
            for (int j = 0; j < 24; j++) {
                remainder += playerPits.get(j);
            }
        }
        return remainder;
    }
    public void removeAllPieces() //Remove all stones on board for a selected player
    {
        for(int i = 0; i < 6; i++)
        {
            this.setPitCount(i, 0);
        }
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
