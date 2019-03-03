package com.company;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {


        //ArrayList<String> items = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        House house = new House();
        house.loadGame();
        house.startGame();


        while (!house.gameOver) {
            //house.RoomProcedure();
            System.out.print("> ");
            String text = input.nextLine().toLowerCase();


            switch (text) {
                case "go up":
                case "up":
                case "u":
                    house.moveRooms("up");
                    break;
                case "go down":
                case "down":
                case "d":
                    house.moveRooms("down");
                    break;
                case "go north":
                case "north":
                case "n":
                    house.moveRooms("north");
                    break;
                case "go south":
                case "south":
                case "s":
                    house.moveRooms("south");
                    break;
                case "go east":
                case "east":
                case "e":
                    house.moveRooms("east");
                    break;
                case "go west":
                case "west":
                case "w":
                    house.moveRooms("west");
                    break;
                case "help": {
                    File HelpFile = new File("Help.txt");
                    Scanner Help = new Scanner(HelpFile);
                    while (Help.hasNextLine()) {
                        String line = Help.nextLine();
                        System.out.println(line);
                    }
                    Help.close();
                    break;
                }
                case "inventory":
                    for (int i = 0; i < house.inventory.size(); i++) {
                       System.out.println(house.inventory.get(i));
                   }
                   break;
                case "description":
                    System.out.println(house.description.get(house.currentRoomIndex));
                    break;
                default: {
                    if (text.contains("use") || text.contains("take") || text.contains("examine") || text.contains("drop")) {
                        house.itemHolder = text.split(" ");
                        house.itemHandler(house.itemHolder);
                    }
                    else
                        System.out.println("Command not found; try 'help'");
                    break;

                }
            }
        }

    }
}

