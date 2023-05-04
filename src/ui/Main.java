/**
 * Main: The main class
 */
package ui;

import java.util.Scanner;

import exceptions.ObjectOutOfStock;
import exceptions.ObjectWithSameName;
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
                    "\n----------\nMain Menu\n---------- Choose a option:\n 0) Exit of program\n 1) Add Product" +
                            "\n 2) Add Order" +
                            "\n-------------------");
            optionMenu = validateIntegerOption();

            switch (optionMenu) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    addProduct();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    System.out.println(controller.showOrders());
                    break;
                default:
                    System.out.println("------------------\nValue incorrect!");
                    break;
            }
        } while (exit == false);
    }

    public void addProduct() {
        reader.nextLine();
        System.out.println("\nWrite the product name");
        String name = reader.nextLine();
        System.out.println("Write the product description ");
        String description = reader.nextLine();
        System.out.println("Write the product price");
        double price = reader.nextDouble();
        System.out.println("Write the amount of product");
        int amount = reader.nextInt();
        System.out.println("Write the category of product");
        int category = selectCategory();

        try {
            System.out.println("\n" + controller.addProduct(name, description, price, amount, category));
        } catch (ObjectWithSameName e) {
            System.out.println("\n" + e.getMessage() + "\n");
            addProduct();
        }
    }

    public void addOrder() {
        int finish = 0;
        reader.nextLine();
        System.out.println("\nWrite Buyer's name");
        String nameBuyer = reader.nextLine();
        controller.addOrder(nameBuyer);
        while (finish == 0) {
            int product = selectProduct();
            System.out.println("Write the Amount of this product");
            int amount = validateIntegerOption();
            try {
                System.out.println(controller.addProductToOrder(amount, product));
                System.out.println("If u wanna add another product write 0, if not write 1");
                finish = validateIntegerOption();
            } catch (ObjectOutOfStock e) {
                System.out.println("\n" + e.getMessage());
            }
        }

    }

    public int selectProduct() {
        System.out.println(controller.showProducts());
        System.out.println("Select one product");
        int o = validateIntegerOption();
        while (o > controller.getProductsSize() || o < 1) {
            System.out.println("Select a Valid product");
            System.out.println("Select one product");
            System.out.println(controller.showProducts());
            o = validateIntegerOption();
        }
        return o;
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
