/**
 * Main: The main class
 */
package ui;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import exceptions.ObjectDoesntExists;
import exceptions.ObjectOutOfStock;
import exceptions.ObjectWithInvalidAmount;
import exceptions.ObjectWithSameName;
import model.MLController;

public class Main {

    public Scanner reader;
    public MLController controller;

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        Main mercadoLibre = new Main();
        mercadoLibre.hello();
        mercadoLibre.mainMenu();
        mercadoLibre.closeProgram();
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
        System.out.println("\n Exiting of the program (saving data)... Thanks for use me  :)");
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
     * @throws ParseException
     *
     */
    public void mainMenu() throws ParseException {
        int optionMenu = 0;
        boolean exit = false;
        do {
            System.out.println(
                    "\n----------\nMain Menu\n---------- Choose a option:\n 0) Exit of program\n 1) Add Product" +
                            "\n 2) Add Order" +
                            "\n 3) Edit product" +
                            "\n 4) Show all products" +
                            "\n 5) Show all orders" +
                            "\n 6) Search Order" +
                            "\n 7) Search Product" +
                            "\n-------------------");
            optionMenu = validateIntegerOption();

            switch (optionMenu) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    addProduct(); // Finish
                    break;
                case 2:
                    addOrder(); // Finish
                    break;
                case 3:
                    editAmountProduct(); // Finish
                    break;
                case 4:
                    System.out.println(controller.showProducts());
                    break;
                case 5:
                    System.out.println(controller.showOrders());
                    break;
                case 6:
                    searchOrderMenu();
                    break;
                case 7:
                    searchProductMenu(false);
                    break;
                default:
                    System.out.println("------------------\nValue incorrect!");
                    break;
            }
        } while (exit == false);
    }

    /**
     * Description: Allows select the option of the search Product
     *
     */
    public void searchProductMenu(boolean onePiece) {
        if (controller.productsIsEmpty()) {
            System.out.println("There aren't products registered for search");
            return;
        }
        int optionMenu = 0;

        boolean exit = false;
        do {
            System.out.println(
                    "----------\nSearch Product Menu\n---------- Choose a option:\n 0) Exit of Menu" +
                            "\n 1) Search by name" +
                            "\n 2) Search by price" +
                            "\n 3) Search by category" +
                            "\n 4) Search by number of purchases" +
                            "\n 5) Search by number of Available quantity" +
                            "\n-------------------");
            optionMenu = validateIntegerOption();

            switch (optionMenu) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    searchProductByName(); //Finish
                    if (onePiece) {
                        return;
                    }
                    break;
                case 2:
                    searchProductByPrice(); // Numeric Finish
                    if (onePiece) {
                        return;
                    }
                    break;
                case 3:
                    searchProductByCategory(); // Category Finish
                    if (onePiece) {
                        return;
                    }
                    break;
                case 4:
                    searchProductBySells(); // Numeric Finish
                    if (onePiece) {
                        return;
                    }
                    break;
                case 5:
                    searchProductByAmount(); // Numeric Finish
                    if (onePiece) {
                        return;
                    }
                    break;
                default:
                    System.out.println("------------------\nValue incorrect!");
                    break;
            }
        } while (exit == false);
    }

    /**
     * Description: Allows select the option of the search Order
     *
     */
    public void searchOrderMenu() {
        if (controller.orderIsEmpty()) {
            System.out.println("There aren't orders registered for search");
            return;
        }
        int optionMenu = 0;

        boolean exit = false;
        do {
            System.out.println(
                    "----------\nSearch Order Menu\n---------- Choose a option:\n 0) Exit of Menu" +
                            "\n 1) Search by buyer name" +
                            "\n 2) Search by price" +
                            "\n 3) Search by date" +
                            "\n-------------------");
            optionMenu = validateIntegerOption();
            System.out.println("\n");

            switch (optionMenu) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    searchOrderByBuyerName(); // Finish
                    break;
                case 2:
                    searchOrderByTotalPrice(); // Numeric Finish
                    break;
                case 3:
                    searchOrderByDate(); // Date Finish
                    break;
                default:
                    System.out.println("------------------\nValue incorrect!");
                    break;
            }
        } while (exit == false);
    }

    
    /**
     * This function prompts the user to input information about a product and then adds it to a list
     * of products, handling any errors that may occur.
     */
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
        } catch (ObjectWithInvalidAmount e) {

            System.out.println("\n" + e.getMessage() + "\n");

        }
    }

    
    /**
     * This function adds an order to the system, allowing the user to select products and their
     * respective amounts to be added to the order.
     */
    public void addOrder() throws ParseException {
        if (controller.productsIsEmpty()) {
            System.out.println("You can't register a Order, because there aren't products");
            return;
        }
        System.out.println(
                "The offer will now be registered, the registration date will be automatically saved by the program.");
        int finish = 0;
        reader.nextLine();
        System.out.println("\nWrite Buyer's name");
        String nameBuyer = reader.nextLine();
        controller.addOrder(nameBuyer);
        while (finish == 0) {
            searchProductMenu(true);
            System.out.println(
                    "Please select one of the products, if there are no product that match your search type -1");
            int product = reader.nextInt();
            if (product == -1) {
                System.out.println("If you want to add another product write 0, otherwise write 1");
                finish = validateIntegerOption();
                continue;
            }
            System.out.println("Please write the amount of products");
            int amount = validateIntegerOption();
            try {
                System.out.println(controller.addProductToOrder(amount, product));
                System.out.println("If you want to add another product write 0, otherwise write 1");
                finish = validateIntegerOption();
            } catch (ObjectOutOfStock e) {
                System.out.println(e.getMessage());

            } catch (ObjectWithInvalidAmount e) {
                System.out.println(e.getMessage());

            } catch (ObjectDoesntExists e) {
                System.out.println(e.getMessage());
            }

        }
        controller.verifyOrder();
    }

    
    /**
     * This function allows the user to edit the amount of a product in a system by selecting the
     * product and entering a new amount.
     */
    public void editAmountProduct() {
        searchProductMenu(true);
        System.out.println(
                "Please select one of the products, if there are no product that match your search, type -1");
        int product = reader.nextInt();
        if (product == -1) {
            return;
        }
        while (product < 1 || product > controller.productsLimit()) {
            System.out.println("Write a valid number");
            product = reader.nextInt();
        }
        try {
            System.out.println("Write the new amount for the product");
            int newAmount = validateIntegerOption();
            System.out.println(controller.editAmountProduct(product, newAmount));
        } catch (ObjectWithInvalidAmount e) {
            System.out.println(e.getMessage());
            editAmountProduct();
        }

    }

    
    /**
     * The function prompts the user to select whether they want to see a list from minimum to maximum
     * and returns a boolean value indicating the user's choice.
     * 
     * @return A boolean value indicating whether the user wants to see the list from minimum to
     * maximum (true) or not (false).
     */
    public boolean minToMax() {
        System.out.println("If you wanna see the list from minimum to maximum select 1, otherwise select 2");
        int minOrMax = validateIntegerOption();
        while (minOrMax > 2 || minOrMax < 1) {
            System.out.println("Write a valid option");
            minOrMax = validateIntegerOption();
        }
        boolean minToMax = false;
        if (minOrMax == 1) {
            minToMax = true;
        }
        return minToMax;
    }

    
    /**
     * This function searches for a product by name, with options for exact match or range search.
     */
    public void searchProductByName() {
        int option = prefixOrSuffix();
        if(option==3){
            reader.nextLine();
            System.out.println("Write the Product name");
            String s= reader.nextLine();
            System.out.println(controller.searchExactProductByName(s.toLowerCase(), s.toLowerCase(), true));
            return;
        }
        String[] minAndMax = rangeMinAndMaxString(true);
        if (option==2) {
            while (minAndMax[0].length() != minAndMax[1].length()) {
                if (minAndMax[0].length() != minAndMax[1].length()) {
                    System.out.println("\nThe suffix must be the same size");
                    minAndMax = rangeMinAndMaxString(false);
                }
            }

        }
        boolean minToMax = minToMax();

        System.out.println(controller.searchProductByName(minAndMax[0].toLowerCase(), minAndMax[1].toLowerCase(), minToMax, true));

    }

    
    /**
     * This function searches for products by price, either within a range or for an exact price.
     */
    public void searchProductByPrice() {
        boolean option = rangeOrExact();
        if (option) {
            double[] minAndMax = rangeMinAndMax();
            boolean minToMax = minToMax();
            System.out.println(controller.searchProductsByPrice(minAndMax[0], minAndMax[1], minToMax));
        } else {
            System.out.println("Write the price to search");
            double minAndMax = reader.nextDouble();
            System.out.println(controller.searchProductsByPrice(minAndMax, minAndMax, true));
        }
    }

   
    /**
     * This function searches for products based on their sales, either within a range or an exact
     * number.
     */
    public void searchProductBySells() {
        boolean option = rangeOrExact();
        if (option) {
            double[] minAndMax = rangeMinAndMax();
            boolean minToMax = minToMax();
            System.out.println(controller.searchProductBySells((int) minAndMax[0], (int) minAndMax[1], minToMax));
        } else {
            System.out.println("Write the number of purchases to search");
            int minAndMax = reader.nextInt();
            System.out.println(controller.searchProductBySells(minAndMax, minAndMax, true));
        }
    }

    
    /**
     * This function searches for products based on their stock amount, either within a range or an
     * exact amount.
     */
    public void searchProductByAmount() {
        boolean option = rangeOrExact();
        if (option) {
            double[] minAndMax = rangeMinAndMax();
            boolean minToMax = minToMax();
            System.out.println(controller.searchProductByStock((int) minAndMax[0], (int) minAndMax[1], minToMax));
        } else {
            System.out.println("Write the number of purchases to search");
            int minAndMax = reader.nextInt();
            System.out.println(controller.searchProductByStock(minAndMax, minAndMax, true));
        }
    }

    
    /**
     * This Java function searches for a product by category and prints the results.
     */
    public void searchProductByCategory() {
        int minAndMax = selectCategory();
        System.out.println(controller.searchProductByCategory(minAndMax, minAndMax, true));
    }

    
    /**
     * This Java function searches for orders by buyer name, either exact match or within a specified
     * range.
     */
    public void searchOrderByBuyerName() {
        int option = prefixOrSuffix();
        if(option==3){
            reader.nextLine();
            System.out.println("Write the Buyer name");
            String s= reader.nextLine();
            System.out.println(controller.searchExactOrderByBuyerName(s.toLowerCase(), s.toLowerCase(), true));
            return;
        }
        String[] minAndMax = rangeMinAndMaxString(true);
        if (option==2) {
            while (minAndMax[0].length() != minAndMax[1].length()) {
                if (minAndMax[0].length() != minAndMax[1].length()) {
                    System.out.println("\nThe suffix must be the same size");
                    minAndMax = rangeMinAndMaxString(false);
                }
            }

        }
        boolean minToMax = minToMax();

        System.out.println(controller.searchOrderByBuyerName(minAndMax[0].toLowerCase(), minAndMax[1].toLowerCase(), minToMax, true));

    }

    
    /**
     * This Java function searches for orders by total price based on user input.
     */
    public void searchOrderByTotalPrice() {
        boolean option = rangeOrExact();
        if (option) {
            double[] minAndMax = rangeMinAndMax();
            boolean minToMax = minToMax();
            System.out.println(controller.searchOrderByTotalPrice(minAndMax[0], minAndMax[1], minToMax));
        } else {
            System.out.println("Write the number of purchases to search");
            double minAndMax = reader.nextDouble();
            System.out.println(controller.searchOrderByTotalPrice(minAndMax, minAndMax, true));
        }
    }

    
    /**
     * This Java function searches for orders by date, either within a range or for an exact date.
     */
    public void searchOrderByDate() {
        boolean option = rangeOrExact();
        boolean validDate = false;
        reader.nextLine();
        if (option) {
            Date[] minAndMax = rangeMinAndMaxDate();
            boolean minToMax = minToMax();
            System.out.println(controller.searchOrderByDate(minAndMax[0], minAndMax[1], minToMax));
        } else {

            while (!validDate) {
                System.out.println("Enter the date to search (Format of date: dd/MM/yyyy): ");
                String dateString = reader.nextLine();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    Date minAndMax = format.parse(dateString);
                    validDate = true;
                    System.out.println(controller.searchOrderByDate(minAndMax, minAndMax, true));
                } catch (ParseException e) {
                    System.out.println("Please write a valid date");
                }
            }
        }
    }

     
    /**
     * This function displays a menu for selecting a type of search (prefix, suffix, or exact
     * coincidence) and validates the user's input.
     * 
     * @return The method is returning an integer value, which is the option selected by the user for
     * the type of search (prefix, suffix, or exact coincidence).
     */
    public int prefixOrSuffix() {
        int optionMenu = 0;
        boolean exit = false;
        System.out.println(
                "\n----------\nType of search\n---------- Choose a option:\n 1) Prefix Search" +
                        "\n 2) Suffix Search " +
                        "\n 3) Exact Coincidence"+
                        "\n-------------------");
        do {
            optionMenu = validateIntegerOption();
            if (optionMenu < 1 || optionMenu > 3) {
                System.out.println("This isn't a valid option, please select another one: ");
            } else {
                exit = true;
            }
        } while (!exit);
        return optionMenu;
    }

    
    /**
     * This function prompts the user to select a product from a list and validates their input.
     * 
     * @return The method is returning an integer value, which is the user's selected product.
     */
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

    
    /**
     * This function prompts the user to select a category type and returns the selected category.
     * 
     * @return The method is returning an integer value, which is the selected type of category.
     */
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
     * This function prompts the user to choose between a range search and an exact search and returns
     * a boolean value based on the user's choice.
     * 
     * @return A boolean value is being returned, which is true if the user selects "Range Search" and
     * false if the user selects "Exact Search".
     */
    public boolean rangeOrExact() {
        int optionMenu = 0;
        boolean exit = false;
        System.out.println(
                "\n----------\nType of search\n---------- Choose a option:\n 1) Range Search" +
                        "\n 2) Exact Search " +
                        "\n-------------------");
        do {
            optionMenu = validateIntegerOption();
            if (optionMenu < 1 || optionMenu > 2) {
                System.out.println("This isn't a valid option, please select another one: ");
            } else {
                exit = true;
            }
        } while (!exit);
        if (optionMenu == 1) {
            return true;
        }
        return false;
    }

    
    /**
     * This function prompts the user to enter a range minimum and maximum, ensuring that the maximum
     * is greater than or equal to the minimum, and returns an array containing these values.
     * 
     * @return An array of two doubles, where the first element is the range minimum and the second
     * element is the range maximum.
     */
    public double[] rangeMinAndMax() {
        double[] minAndMax = new double[2];

        System.out.println("Enter the range minimum to search: ");
        minAndMax[0] = reader.nextDouble();
        minAndMax[1] = -1;

        System.out.println("Enter the range maximum to search: ");
        while (minAndMax[1] < minAndMax[0]) {
            minAndMax[1] = reader.nextDouble();
            if (minAndMax[1] < minAndMax[0]) {
                System.out.println("The range maximum must be bigger than the range minimum: ");
            }
        }

        return minAndMax;
    }

    
    /**
     * This function prompts the user to enter a range of dates and returns an array containing the
     * minimum and maximum dates.
     * 
     * @return The method is returning an array of Date objects, which contains the minimum and maximum
     * dates entered by the user.
     */
    public Date[] rangeMinAndMaxDate() {
        Date[] minAndMax = new Date[2];
        boolean validDate = false;
        boolean validDate2 = false;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        while (!validDate) {
            System.out.println("Enter the range minimum to search (Format of date: dd/MM/yyyy): ");
            String dateString = reader.nextLine();
            System.out.println("Enter the range maximum to search (Format of date: dd/MM/yyyy): ");
            String dateString2 = reader.nextLine();

            try {
                minAndMax[0] = format.parse(dateString);
                minAndMax[1] = format.parse(dateString2);
                validDate = true;

                while (minAndMax[1].compareTo(minAndMax[0]) < 0 && !validDate2) {
                    try {
                        System.out.println("The range maximum must be bigger than the range minimum: ");
                        System.out.println("Enter the range maximum to search (Format of date: dd/MM/yyyy): ");
                        dateString = reader.nextLine();
                        minAndMax[1] = format.parse(dateString);
                        validDate2 = true;

                    } catch (ParseException e) {
                        System.out.println("Please write a valid date");
                    }

                }

            } catch (ParseException e) {
                System.out.println("Please write a valid date");
            }
        }

        return minAndMax;
    }

    
    /**
     * This function prompts the user to enter a range minimum and maximum and returns them as a string
     * array.
     * 
     * @param recharge A boolean variable that indicates whether the method is being called after a
     * recharge or not. If it is true, it means that the method is being called after a recharge and
     * the user will be prompted to enter the range minimum to search. If it is false, the user will
     * not be prompted to enter
     * @return An array of two strings, where the first string is the range minimum and the second
     * string is the range maximum.
     */
    public String[] rangeMinAndMaxString(boolean recharge) {
        String[] minAndMax = new String[2];
        if (recharge) {
            reader.nextLine();
            System.out.println("Enter the range minimum to search: ");
        } else {
            System.out.println("Enter the range minimum to search: ");
        }
        minAndMax[0] = reader.nextLine().toLowerCase();
        minAndMax[1] = "";

        System.out.println("Enter the range maximum to search: ");
        while (minAndMax[1].compareTo(minAndMax[0]) < 0) {
            minAndMax[1] = reader.nextLine().toLowerCase();
            if (minAndMax[1].compareTo(minAndMax[0]) < 0) {
                System.out.println("The range maximum must be bigger than the range minimum: ");
            }
        }

        return minAndMax;
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