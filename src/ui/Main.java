/**
 * Main: The main class
 */
package ui;

import java.text.ParseException;
import java.util.Scanner;

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
            System.out.println("\n");

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
                    editAmountProduct();
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
                    searchProductMenu();
                    break;
                default:
                    System.out.println("------------------\nValue incorrect!");
                    break;
            }
        } while (exit == false);
    }


    /**
     * This function prompts the user to input information about a product and then adds it to a
     * controller object, handling any exceptions that may occur.
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
     * This function adds an order by prompting the user to input the buyer's name, product name, and
     * amount, and then adding the product to the order until the user chooses to finish.
     * @throws ParseException
     */
    public void addOrder() throws ParseException {
        System.out.println(
                "The offer will now be registered, the registration date will be automatically saved by the program.");
        int finish = 0;
        reader.nextLine();
        System.out.println("\nWrite Buyer's name");
        String nameBuyer = reader.nextLine();
        controller.addOrder(nameBuyer);
        while (finish == 0) {
            System.out.println("Writte the name of the product");
            String product = reader.nextLine();
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

     /**
     * This function prompts the user to enter a product name and a new amount for the product, and
     * then calls a controller method to edit the amount of the product, handling any exceptions that
     * may occur.
     */
    public void editAmountProduct() {
        reader.nextLine();
        System.out.println("\nWrite the product name");
        String name = reader.nextLine();
        System.out.println("Write the new amount for the product");
        int newAmount = reader.nextInt();
        try {
            System.out.println("\n" + controller.editAmountProduct(name, newAmount));

        } catch (ObjectWithInvalidAmount e) {
            
            System.out.println("\n" + e.getMessage() + "\n");

        }

    }

    public void searchProductByPrice(){
        boolean option = rangeOrExact();
        if(option){
            double[] minAndMax = rangeMinAndMax();
            System.out.println("If you wanna see the list from minimum to maximum select 1, otherwise select 2");
            int minOrMax= reader.nextInt();
            while (minOrMax>2 || minOrMax<1) {
                System.out.println("Write a valid option");
                minOrMax = reader.nextInt();
            }
            boolean minToMax= false;
            if(minOrMax==1){
                minToMax=true;
            }
            System.out.println(controller.searchProductsByPrice(minAndMax[0], minAndMax[1], minToMax));
        }else{
            System.out.println("Write the price to search");
            double minAndMax = reader.nextDouble();
            System.out.println(controller.searchProductsByPrice(minAndMax, minAndMax, true));
        }
    }  

    public void searchProductBySells(){
        boolean option = rangeOrExact();
        if(option){
            int[] minAndMax = rangeMinAndMaxInt();
            System.out.println("If you wanna see from minimum to maximum select 1, otherwise select 2");
            int minOrMax= reader.nextInt();
            while (minOrMax>2 || minOrMax<1) {
                System.out.println("Write a valid option");
                minOrMax = reader.nextInt();
            }
            boolean minToMax= false;
            if(minOrMax==1){
                minToMax=true;
            }
            System.out.println(controller.searchProductBySells(minAndMax[0], minAndMax[1], minToMax));
        }else{
            System.out.println("Write the number of purchases to search");
            int minAndMax = reader.nextInt();
            System.out.println(controller.searchProductBySells(minAndMax, minAndMax, true));
        }
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
     * This Java function prompts the user to select a category type and returns the selected category.
     * 
     * @return The method is returning an integer value which represents the selected category.
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

    /**
     * Description: Allows select the option of the search Product
     *
     */
    public void searchProductMenu() {
        int optionMenu = 0;
        
        boolean exit = false;
        do {
            System.out.println(
                    "\n----------\nSearch Product Menu\n---------- Choose a option:\n 0) Exit of Menu\n 1) Search by name"
                            +
                            "\n 2) Search by price" +
                            "\n 3) Search by category" +
                            "\n 4) Search by number of purchases" +
                            "\n-------------------");
            optionMenu = validateIntegerOption();
            System.out.println("\n");
            
            switch (optionMenu) {
                case 0:
                    exit = true;
                    break;
                case 1:
    
                    break;
                case 2:
                    searchProductByPrice();
                    break;
                case 3:
                    //
                    break;
                case 4:
                    searchProductBySells();
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
        int optionMenu=0;
        
        boolean exit = false;
        do {
            System.out.println(
                    "----------\nSearch Product Menu\n---------- Choose a option:\n 0) Exit of Menu" + 
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
                    //
                    break;
                case 2:
                    //
                    break;
                case 3:
                    //
                    break;
                default:
                    System.out.println("------------------\nValue incorrect!");
                    break;
            }
        } while (exit == false);
    }


    /**
     * This function prompts the user to choose between a range search or an exact search and returns a
     * boolean value based on the user's choice.
     * 
     * @return A boolean value indicating whether the user chose a range search (true) or an exact
     * search (false).
     */
    public boolean rangeOrExact() {
        int optionMenu = 0;
        boolean exit = false;
        System.out.println(
                "\n----------\nSearch Product Menu\n---------- Choose a option:\n 1) Range Search" +
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
        if(optionMenu==1){
            return true;
        }
        return false;
    }

    /**
     * The function prompts the user to enter a range minimum and maximum, ensuring that the maximum is
     * greater than or equal to the minimum, and returns an array containing these values.
     * 
     * @return An array of two doubles, where the first element is the range minimum and the second
     * element is the range maximum.
     */
    public double[] rangeMinAndMax(){
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
     * The function prompts the user to enter a range minimum and maximum, ensuring that the maximum is
     * greater than or equal to the minimum, and returns an array containing these values.
     * 
     * @return An array of two doubles, where the first element is the range minimum and the second
     * element is the range maximum.
     */
    public int[] rangeMinAndMaxInt(){
        int[] minAndMax = new int[2];

        System.out.println("Enter the range minimum to search: ");
        minAndMax[0] = reader.nextInt();
        minAndMax[1] = -1;

        System.out.println("Enter the range maximum to search: ");
        while (minAndMax[1] < minAndMax[0]) {
            minAndMax[1] = reader.nextInt();
            if (minAndMax[1] < minAndMax[0]) {
                System.out.println("The range maximum must be bigger than the range minimum: ");
            }
        }

        return minAndMax;
    }


    /**
     * import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;



        
        System.out.println("Insert the date (formato dd/MM/yyyy): ");
        String fechaString = scanner.nextLine();

        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;

        try {
            // Utilizar el objeto SimpleDateFormat para convertir la cadena de entrada a un objeto Date
            fecha = dateFormat.parse(fechaString);
        } catch (ParseException e) {
            System.out.println("The date entered is not in the correct format");
            return;
        }

        // Imprimir la fecha obtenida
        System.out.println("La fecha ingresada es: " + fecha);
    }
}

     */

    
    
}