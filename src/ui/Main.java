/**
 * Main: The main class
 */
package ui;

import java.util.Scanner;
import model.MLController;

public class Main {

    public Scanner reader;
    public MLController controller;

    /**
     * @param args
     * @throws CloneNotSupportedException
     */
    public static void main(String[] args) throws CloneNotSupportedException {

        Main mercadoLibre = new Main();
        mercadoLibre.hello();
        mercadoLibre.mainMenu();
        mercadoLibre.bye();
        
    }

    public Main() {
        reader = new Scanner(System.in);
        controller = new MLController();
    }

    /**
     * Description: Print a message of welcome
     */
    public void hello() {
        System.out.println("\nWelcome to Mercado Libre...");
    }

    /**
     * Description: Print a message of goodbye
     */
    public void bye() {
        System.out.println("\n Exiting of the program... Thanks for use me  :)");
    }

    /**
     * Description: Save the list of Orders and Products
     */
    public void closeProgram(){
        controller.closeProgram();
    }

    /**
     * Description: Allows select the option of the main menu
     * 
     * @throws CloneNotSupportedException
     */
    public void mainMenu() throws CloneNotSupportedException {

        int optionMenu = 0;
        boolean exit = false;
        do {
            System.out.println(
                    "\n----------\nMain Menu\n---------- Choose a option:\n 0) Exit of program\n 1) Play"
                            + "\n 2) See Rankig"
                            + "\n-------------------");
            optionMenu = validateIntegerOption();

            switch (optionMenu) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    //play();
                    break;
                case 2:
                    //System.out.println(seeRanking());
                    break;
                default:
                    System.out.println("------------------\nValue incorrect!");
                    break;
            }

        } while (exit == false);
    }


    /**
     * validateIntegerOption: This method checks if a number is an integer
     * 
     * @return option - int: Returns the entered number if it is an integer or
     *         returns -1 if it is not an integer
     */
    public int validateIntegerOption() {
        int option = 0;

        if (reader.hasNextInt()) {
            option = reader.nextInt();
        } else {
            reader.nextLine();
            option = -1;
        }

        return option;
    }


}
