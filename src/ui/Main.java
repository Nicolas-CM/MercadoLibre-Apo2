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
    public void closeProgram() {
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
                    "\n----------\nMain Menu\n---------- Choose a option:\n 0) Exit of program\n 1) Play" +
                            "\n 2) See Rankig" +
                            "\n-------------------");
            optionMenu = validateIntegerOption();

            switch (optionMenu) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    // play();
                    break;
                case 2:
                    // System.out.println(seeRanking());
                    break;
                default:
                    System.out.println("------------------\nValue incorrect!");
                    break;
            }
        } while (exit == false);
    }

    public void addProduct() {
        reader.nextLine();
        System.out.println("Write the product name");
        String name = reader.nextLine();
        System.out.println("Write the product description ");
        String description = reader.nextLine();
        System.out.println("Write the product price");
        double price = reader.nextDouble();
        System.out.println("Write the amount of product");
        int amount = reader.nextInt();
        System.out.println("Write the category of product");
        int category = selectCategory();

        System.out.println(controller.addProduct(name, description, price, amount, category));
    }

    public void addOrder() {
        reader.nextLine();
        System.out.println("Write the product name");
        String name = reader.nextLine();
        System.out.println("Write the product description ");
        String description = reader.nextLine();
        System.out.println("Write the product price");
        double price = reader.nextDouble();
        System.out.println("Write the amount of product");
        int amount = reader.nextInt();
        System.out.println("Write the category of product");
        int category = selectCategory();

        System.out.println(controller.addOrder(name, null, category));
    }

    public int selectCategory() {
        boolean exit = false;
        int typeCategory = 0;
        System.out.println(controller.toStringCategory());
        do {
            System.out.println("Enter the number of type of category: ");
            typeCategory = reader.nextInt();
            if (typeCategory < 0 || typeCategory > 7) {
                System.out.println(
                        "There isn't this type of category, please enter another: ");
            } else {
                exit = true;
            }
        } while (!exit);
        return typeCategory;
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
