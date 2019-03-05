import java.util.ArrayList;

//This file contains the Player class
//This class assigns half of the board to a player and handles relevant operations

public class Player
{
    private ArrayList<Integer> playerPits = new ArrayList<>(); //The array of stones for a player
    private int playerStore; //The player's score
    private int defaultStones = 4; //Amount of stones to be filled in pits at start of game
    //String playerName;

    Player() //Default constructor that fills half of a board with stones
    {
        //this.playerName = name;
        for (int i = 0; i < 6; i++)
            playerPits.add(defaultStones);
    }

    public ArrayList<Integer> getPlayerPits() //A player's half of the board
    {
        return playerPits;
    }

    public int getPitCount(int index) //Number of stones in a selected pit
    {
        return playerPits.get(index);
    }

    public void setPitCount(int index, int stones) //Set number of stones in a selected pit
    {
       playerPits.set(index, stones);
    }

    public int getStoreCount() //Get the player's score
    {
        return playerStore;
    }

    public void setStoreCount(int stones) //Set the player's score
    {
        playerStore = stones;
    }

    public int getRemainingStones() //Determine stones still left on player's half of the board
    {
        int remainder = 0;
        for(int i = 0; i < 6; i++)
        {
            remainder += playerPits.get(i);
        }
        return remainder;
    }

    public void removeAllStones() //Remove all stones on board for a selected player
    {
        for(int i = 0; i < 6; i++)
        {
            this.setPitCount(i, 0);
        }
    }

    /*public String getName()
    {
        return playerName;
    }*/
}
