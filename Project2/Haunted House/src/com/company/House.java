package com.company;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class House {
    public ArrayList<String> rooms = new ArrayList<>();             //Stores all of the rooms, all subsequent arrays are based on this index
    public ArrayList<String> description = new ArrayList<>();       //Contains the default description
    public ArrayList<String> shortDescription = new ArrayList<>();  //Contains the shortened description
    public ArrayList<String> specialDescription = new ArrayList<>();//Contains the description altered by specialAction
    public ArrayList<Boolean> isLocked = new ArrayList<>();         //Says whether or not the selected room is locked
    public ArrayList<String> lockedDirection = new ArrayList<>();   //Says which direction, if any, is locked
    public ArrayList<String> upDirection = new ArrayList<>();       //Says where up will go to based on currentRoom
    public ArrayList<String> downDirection = new ArrayList<>();     //Says where down will go to based on currentRoom
    public ArrayList<String> northDirection = new ArrayList<>();    //Says where north will go to based on currentRoom
    public ArrayList<String> southDirection = new ArrayList<>();    //Says where south will go to based on currentRoom
    public ArrayList<String> eastDirection = new ArrayList<>();     //Says where east will go to based on currentRoom
    public ArrayList<String> westDirection = new ArrayList<>();     //Says where west will go to based on currentRoom
    public ArrayList<String> useItem = new ArrayList<>();           //Stores the usable item in currentRoom
    public ArrayList<Boolean> usedItem = new ArrayList<>();         //Says if an item has been used in currentRoom
    public ArrayList<String> takeItem = new ArrayList<>();          //Stores the acquirable item in currentRoom
    public ArrayList<Boolean> takenItem = new ArrayList<>();        //Says if an item has been taken in currentRoom
    public ArrayList<Boolean> visitState = new ArrayList<>();       //Says if currentRoom has been entered or not
    public ArrayList<String> specialAction = new ArrayList<>();     //Contains the action for the current Room
    public ArrayList<Boolean> actionState = new ArrayList<>();      //Says if a special action has been performed in currentRoom
    public ArrayList<String> itemDialogue = new ArrayList<>();      //Contains the message that will appear if an item is used
    public ArrayList<String> actionDialogue = new ArrayList<>();    //Contains the message that will appear if specialAction is performed

    public ArrayList<String> itemList = new ArrayList<>();          //Stores all of the items in the game
    public ArrayList<String> itemDescriptionList = new ArrayList<>();
    public ArrayList<String> inventory = new ArrayList<>();         //Says which items the player has
    public String[] itemHolder = new String[2];                     //used for use and take commands

    public boolean gameOver = false;
    int i = 0;
    private String currentRoom;
    //private String targetRoom;
    public int firstRoomIndex = 0;
    public int currentRoomIndex = 0;
    //public int targetRoomIndex = 1;
    public int lastRoomIndex = 10;
    //public House()
    //{}

    public void loadGame() throws FileNotFoundException {
        loadRoomsArray();
        loadRoomDependentArrays();
        loadItemsArrays();

    }

    public void startGame() throws FileNotFoundException {
        File newGame = new File("NewGame.txt");
        Scanner ScanFile = new Scanner(newGame);
        while (ScanFile.hasNextLine()) {
            String line = ScanFile.nextLine();
            System.out.println(line);
        }
        setDefaultRoom();
    }

    private void loadItemsArrays() throws FileNotFoundException{
        File itemsList = new File("theItemList.txt");
        Scanner List = new Scanner(itemsList);
        while (List.hasNextLine()) {
            String line = List.nextLine();
            itemList.add(line);
            inventory.add("");
            //System.out.println(line);
        }
        File descriptionsList = new File("itemDescriptions.txt");
        Scanner descriptions = new Scanner(descriptionsList);
        while (descriptions.hasNextLine()) {
            String line = descriptions.nextLine();
            itemDescriptionList.add(line);
            //System.out.println(line);
        }
    }

    private void loadRoomsArray() throws FileNotFoundException {
        File roomList = new File("theRoomList.txt");
        Scanner List = new Scanner(roomList);
        while (List.hasNextLine()) {
            String line = List.nextLine();
            rooms.add(line);
            //System.out.println(line);
        }
        List.close();
        //for (int j = 0; j < rooms.size(); j++) {
        //    System.out.println(rooms.get(j));
        //}
    }

    private void loadRoomDependentArrays() throws FileNotFoundException {
        for (int j = 0; j < rooms.size(); j++) {
            String roomHolder = rooms.get(j) + ".txt";
            File roomFiles = new File(roomHolder);
            Scanner Files = new Scanner(roomFiles);

            while (Files.hasNextLine()) {
                String line = Files.nextLine();
                int index = line.indexOf(":");
                if (line.startsWith("description")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    description.add(useLine);
                    actionState.add(Boolean.FALSE);
                    visitState.add(Boolean.FALSE); //random place to put this here
                    usedItem.add(Boolean.FALSE);
                    takenItem.add(Boolean.FALSE);
                }
                if (line.startsWith("shortDescription")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    shortDescription.add(useLine);
                }
                if (line.startsWith("specialDescription")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    specialDescription.add(useLine);
                }
                if (line.startsWith("lockedDirection")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    lockedDirection.add(useLine);
                    if (useLine.contains("null"))
                        isLocked.add(Boolean.FALSE);
                    else
                        isLocked.add(Boolean.TRUE);
                }
                if (line.startsWith("up")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    //if (!useLine.toLowerCase().contains("null"))
                    upDirection.add(useLine);
                }
                if (line.startsWith("down")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    downDirection.add(useLine);
                }
                if (line.startsWith("north")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    northDirection.add(useLine);
                }
                if (line.startsWith("south")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    southDirection.add(useLine);
                }
                if (line.startsWith("east")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    eastDirection.add(useLine);
                }
                if (line.startsWith("west")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    westDirection.add(useLine);
                }
                if (line.startsWith("takeItem")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    takeItem.add(useLine);
                }
                if (line.startsWith("useItem")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    useItem.add(useLine);
                }
                if (line.startsWith("specialAction")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    specialAction.add(useLine);
                    actionState.add(Boolean.FALSE);
                }
                if (line.startsWith("itemDialogue")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    itemDialogue.add(useLine);
                }
                if (line.startsWith("actionDialogue")) {
                    String useLine = line.substring(index + 2, line.trim().length());
                    actionDialogue.add(useLine);
                }
                if (line.contains("startRoom")) {
                    firstRoomIndex = j;
                }
                if (line.contains("finishRoom")) {
                    lastRoomIndex = j;
                }

            }
            Files.close();
            //System.out.println(description.get(j));
        }

    }
    public void RoomProcedure()
    {
        //System.out.println(visitState.get(currentRoomIndex));
        if (!visitState.get(currentRoomIndex))
        {
            System.out.println(description.get(currentRoomIndex));
            visitState.set(currentRoomIndex, true);
        }
        else {
            if (actionState.get(currentRoomIndex) || usedItem.get(currentRoomIndex))
                System.out.println(specialDescription.get(currentRoomIndex));
            else
                System.out.println(shortDescription.get(currentRoomIndex));
        }
    }
    public void itemHandler(String[] command) {
        if (command[0].equals("take")) {
            if (takeItem.get(currentRoomIndex).equals(command[1]) && !inventory.contains(command[1])) {
                System.out.println("You got the " + takeItem.get(currentRoomIndex));
                takenItem.set(currentRoomIndex, true);
                inventory.add(takeItem.get(currentRoomIndex));
            }
            else if (takeItem.get(currentRoomIndex).equals("null"))
                errorHandler(2, command[1]);
            else if (command[1].equals(takeItem.get(currentRoomIndex)) && takenItem.get(currentRoomIndex))
                errorHandler(3, command[1]);
            else if (takenItem.get(currentRoomIndex))
                errorHandler(4, command[1]);
            else
                errorHandler(8, command[1]);
        }
        if (command[0].equals("use")) {
            if (!usedItem.get(currentRoomIndex) && inventory.contains((command[1])) && useItem.get(currentRoomIndex).equals(command[1])) {
                System.out.println("You used the " + useItem.get(currentRoomIndex));
                System.out.println(itemDialogue.get(currentRoomIndex));
                usedItem.set(currentRoomIndex, true);
                isLocked.set(currentRoomIndex, false);

                if(currentRoomIndex == lastRoomIndex)
                    gameOver = true;

                RoomProcedure();
            }
            else if (usedItem.get(currentRoomIndex) && inventory.contains(command[1]))
                errorHandler(5, command[1]);
            else if ((!command[1].equals(useItem.get(currentRoomIndex)) || useItem.get(currentRoomIndex).equals("null")) && inventory.contains(command[1]))
                errorHandler(6, command[1]);
            else if (!inventory.contains(command[1]))
                errorHandler(7, command[1]);
            else
                errorHandler(8, command[1]);
        }
        if (command[0].equals("examine")) {
            if (inventory.contains(command[1])) {
                System.out.println(itemDescriptionList.get(itemList.indexOf(command[1])));
            }
            else if (itemList.contains(command[1]))
                errorHandler(7, command[1]);
            else
                errorHandler(8, command[1]);
        }
        if (command[0].equals("drop")) {
            if (inventory.contains(command[1])) {
                inventory.remove(inventory.indexOf(command[1]));
                System.out.println("You dropped the " + command[1] + ". It has returned to its original room");
                takenItem.set(takeItem.indexOf(command[1]), false);
            }
            else if (!inventory.contains(command[1]) && itemList.contains(command[1]))
                errorHandler(7, command[1]);

            else
                errorHandler(8, command[1]);

        }
    }
    private void setDefaultRoom()
    {
        currentRoom = rooms.get(firstRoomIndex);
        RoomProcedure();
    }

    public void moveRooms(String direction) {
        currentRoomIndex = rooms.indexOf(currentRoom);
        if (direction.equals("up")) {
            if (upDirection.get(currentRoomIndex).equals("null"))
                errorHandler(1, direction);
            else if (lockedDirection.get(currentRoomIndex).equals(direction))// && isLocked.get(currentRoomIndex))
                errorHandler(0, direction);
            else {
                currentRoomIndex = rooms.indexOf(upDirection.get(currentRoomIndex));
                //System.out.println(currentRoomIndex);
                currentRoom = rooms.get(currentRoomIndex);

                RoomProcedure();
                //setCurrentRoom(currentRoom);
            }
        }

        if (direction.equals("down")) {
            if (downDirection.get(currentRoomIndex).equals("null"))
                errorHandler(1, direction);
            else if (lockedDirection.get(currentRoomIndex).equals(direction) && isLocked.get(currentRoomIndex))
                errorHandler(0, direction);
            else {
                currentRoomIndex = rooms.indexOf(downDirection.get(currentRoomIndex));
                //System.out.println(currentRoomIndex);
                currentRoom = rooms.get(currentRoomIndex);
                RoomProcedure();
                //setCurrentRoom(currentRoom);
            }
        }
        if (direction.equals("north")) {
            if (northDirection.get(currentRoomIndex).equals("null"))
                errorHandler(1, direction);
            else if (lockedDirection.get(currentRoomIndex).equals(direction) && isLocked.get(currentRoomIndex))
                errorHandler(0, direction);
            else {
                currentRoomIndex = rooms.indexOf(northDirection.get(currentRoomIndex));
                //System.out.println(currentRoomIndex);
                currentRoom = rooms.get(currentRoomIndex);
                RoomProcedure();
                //setCurrentRoom(currentRoom);
            }
        }
        if (direction.equals("south")) {
            if (southDirection.get(currentRoomIndex).equals("null"))
                errorHandler(1, direction);
            else if (lockedDirection.get(currentRoomIndex).equals(direction) && isLocked.get(currentRoomIndex))
                errorHandler(0, direction);
            else {
                currentRoomIndex = rooms.indexOf(southDirection.get(currentRoomIndex));
                //System.out.println(currentRoomIndex);
                currentRoom = rooms.get(currentRoomIndex);
                RoomProcedure();
                //setCurrentRoom(currentRoom);
            }
        }
        if (direction.equals("east")) {
            if (eastDirection.get(currentRoomIndex).equals("null"))
                errorHandler(1, direction);
            else if (lockedDirection.get(currentRoomIndex).equals(direction) && isLocked.get(currentRoomIndex))
                errorHandler(0, direction);
            else {
                currentRoomIndex = rooms.indexOf(eastDirection.get(currentRoomIndex));
                //System.out.println(currentRoomIndex);
                currentRoom = rooms.get(currentRoomIndex);
                RoomProcedure();
                //setCurrentRoom(currentRoom);
            }
        }
        if (direction.equals("west")) {
            if (westDirection.get(currentRoomIndex).equals("null"))
                errorHandler(1, direction);
            else if (lockedDirection.get(currentRoomIndex).equals(direction) && isLocked.get(currentRoomIndex))
                errorHandler(0, direction);
            else {
                currentRoomIndex = rooms.indexOf(westDirection.get(currentRoomIndex));
                //System.out.println(currentRoomIndex);
                currentRoom = rooms.get(currentRoomIndex);
                RoomProcedure();
                //setCurrentRoom(currentRoom);
            }
        }

    }
    private void errorHandler(int errorNumber, String input)
    {
        switch(errorNumber){
            case(0):
                System.out.println("You can't go " + input + " yet");
                break;
            case(1):
                System.out.println("There is nothing in the " + input + " direction");
                break;
            case(2):
                System.out.println("There is no " + input + " in this room");
                break;
            case(3):
                System.out.println("The " + input + " is not here anymore");
                break;
            case(4):
                System.out.println("You already have the " + input);
                break;
            case(5):
                System.out.println("You already used the " + input);
                break;
            case(6):
                System.out.println("You can't use the " + input + " here");
                break;
            case(7):
                System.out.println("You don't have the " + input + " anymore");
                break;
            case(8):
                System.out.println(input + " does not exist");
                break;
        }
    }
    //public void setCurrentRoom(String currentRoom)
    //{

    //}
}
