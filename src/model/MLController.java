package model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import exceptions.*;

public class MLController {
    private Searcher<String> stringSearcher;
    private Searcher<Double> doubleSearcher;
    private Searcher<Integer> integerSearcher;
    private Searcher<Date> dateSearcher;
    private MercadoLibre mercadoLibre;
    private ManagerPersistence managerPersistence;

    public MLController() {
        stringSearcher = new Searcher<>();
        doubleSearcher = new Searcher<>();
        integerSearcher = new Searcher<>();
        dateSearcher = new Searcher<>();
        mercadoLibre = MercadoLibre.getInstance();
        managerPersistence = new ManagerPersistence();
        openProgram();
    }

    /**
     * Description: Load the list of Orders and Products of data
     */
    public void openProgram() {
        mercadoLibre.setOrders(managerPersistence.loadOrders());
        mercadoLibre.setProducts(managerPersistence.loadProducts());
    }

    /**
     * Description: Save the list of Orders and Products
     */
    public void closeProgram() {
        managerPersistence.saveOrders(mercadoLibre.getOrders());
        managerPersistence.saveProducts(mercadoLibre.getProducts());
    }

    /**
     * The function adds a new product to a marketplace and checks for duplicate
     * names and invalid
     * amounts.
     * 
     * @param name        The name of the product being added (as a String).
     * @param description A String that describes the product being added.
     * @param price       The price of the product being added, represented as a
     *                    double data type.
     * @param amount      The amount parameter refers to the quantity of the product
     *                    being added to the
     *                    inventory. It should be a non-negative integer value.
     * @param category    The category parameter is an integer that represents the
     *                    category of the product
     *                    being added. The specific categories and their
     *                    corresponding integer values are likely defined
     *                    elsewhere in the code.
     * @return The method is returning a String message indicating whether the
     *         product was added
     *         correctly or not. If the product was added correctly, the message
     *         will be "Product Added
     *         Correctly". If there is another product with the same name, the
     *         message will be "There is
     *         another product with the same name, please write another name for the
     *         product". If the amount is
     *         invalid, the message will be "Please write a
     */
    public String addProduct(String name, String description, double price, int amount, int category)
            throws ObjectWithSameName, ObjectWithInvalidAmount {
        Product product = new Product(name.toLowerCase(), description, price, amount, category);
        if (amount < 0) {
            throw new ObjectWithInvalidAmount("Please write a valid amount (amount must be grater than or equal to 0)");
        }
        if (searchProductPosByName(name.toLowerCase()) == -1) {
            mercadoLibre.addProduct(product);
            return "Product Added Correctly ";
        } else {
            throw new ObjectWithSameName(
                    "There is another product with the same name, please write another name for the product");
        }
    }

    /**
     * This function adds a new order with the given buyer name to the list of
     * orders in the
     * MercadoLibre system.
     * 
     * @param nameBuyer The name of the buyer who placed the order. This parameter
     *                  is used to create a
     *                  new Order object and add it to the list of orders in the
     *                  MercadoLibre system.
     * @throws ParseException
     */
    public void addOrder(String nameBuyer) throws ParseException {
        mercadoLibre.getOrders().add(new Order(nameBuyer));
    }

    /**
     * This Java function edits the amount of a product in a marketplace and throws
     * an exception if the
     * new amount is invalid.
     * 
     * @param name      A String representing the name of the product to be edited.
     * @param newAmount an integer representing the new amount of a product to be
     *                  edited.
     * @return The method returns a String message indicating whether the quantity
     *         of a product has
     *         been changed successfully or if the product was not found. If the new
     *         amount is invalid, it
     *         throws an ObjectWithInvalidAmount exception.
     */
    public String editAmountProduct(String name, int newAmount) throws ObjectWithInvalidAmount {
        int pos = searchProductPosByName(name);
        if (newAmount < 0) {
            throw new ObjectWithInvalidAmount("Please write a valid amount (amount must be grater than or equal to 0)");
        }
        if (searchProductPosByName(name) == -1) {
            return "Product not found";
        } else {
            mercadoLibre.getProducts().get(pos).setAmount(newAmount);
            return "The quantity of the product has been changed successfully";

        }

    }

    /**
     * This Java function adds a specified amount of a product to an order, checking
     * if it is in stock
     * and throwing an exception if it is not.
     * 
     * @param amount  The amount of the product to be added to the order.
     * @param product The name of the product to be added to the order.
     * @return If the product is added to the order correctly, the method returns
     *         the string "Product
     *         Added to the Order correctly". If the product is out of stock, the
     *         method throws an
     *         ObjectOutOfStock exception. If the product is not found in the list
     *         of products, the method
     *         returns an empty string.
     */
    public String addProductToOrder(int amount, String product) throws ObjectOutOfStock {

        int pos = searchProductPosByName(product.toLowerCase());
        if (pos != -1) {
            if (mercadoLibre.getProducts().get(pos).getAmount() >= amount) {
                mercadoLibre.getProducts().get(pos)
                        .setAmount(mercadoLibre.getProducts().get(pos).getAmount() - amount);
                mercadoLibre.getProducts().get(pos)
                        .setNumberPurchases(mercadoLibre.getProducts().get(pos).getNumberPurchases() + amount);
                mercadoLibre.getOrders().get(0)
                        .addCouple(new CoupleOrderAmount(amount, mercadoLibre.getProducts().get(pos)));
                return "Product Added to the Order correctly";
            } else {
                throw new ObjectOutOfStock("The product is Out of Stock, select another one");
            }
        } else {
            return "no se encontro";

        }

    }

    /**
     * The function searches for a product by name in a sorted list of products
     * using binary search.
     * 
     * @param product The name of the product that needs to be searched in the list
     *                of products.
     * @return The method is returning an integer value, which represents the index
     *         of the product in
     *         the sorted list of products that matches the given product name. If
     *         the product is not found, it
     *         will return a negative value.
     */
    public int searchProductPosByName(String product) {
        mercadoLibre.getProducts().sort(Comparator.comparing(Product::getName));
        String[] arr = new String[mercadoLibre.getProducts().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getProducts().get(i).getName();
        }
        return stringSearcher.binarySearch(arr, product.toLowerCase());

    }

    public String searchProducByName(String min, String max, boolean minToMax) {
        Collections.sort(mercadoLibre.getProducts(), new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return String.valueOf(p1.getName()).compareTo(String.valueOf(p2.getName()));
            }
        });
        String[] arr = new String[mercadoLibre.getProducts().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getProducts().get(i).getName();
        }
        ArrayList<Integer> position = stringSearcher.binarySearchByRange(arr, min, max);

        return printInRange(position, minToMax, true);
    }

    /**
     * This function searches for products within a price range and returns them in
     * either ascending or
     * descending order based on their price.
     * 
     * @param max      The maximum price of the products to be searched.
     * @param min      The minimum price value to search for products.
     * @param minToMax A boolean value that determines whether the products should
     *                 be sorted from
     *                 minimum to maximum price (true) or from maximum to minimum
     *                 price (false).
     * @return The method is returning a String that contains the information of the
     *         products that meet
     *         the price range criteria, sorted either from lowest to highest price
     *         or from highest to lowest
     *         price, depending on the value of the boolean parameter "minToMax".
     */
    public String searchProductsByPrice(Double min, Double max, boolean minToMax) {
        mercadoLibre.getProducts().sort(Comparator.comparingDouble(Product::getPrice));
        Double[] arr = new Double[mercadoLibre.getProducts().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getProducts().get(i).getPrice();
        }
        ArrayList<Integer> position = doubleSearcher.binarySearchByRange(arr, min, max);

        return printInRange(position, minToMax, true);
    }

    /**
     * The function searches for products within a specified stock range and returns
     * them in either
     * ascending or descending order based on the boolean input.
     * 
     * @param max      The maximum amount of stock that the user wants to search
     *                 for.
     * @param min      The minimum amount of stock that a product should have to be
     *                 included in the search.
     * @param minToMax A boolean value that determines whether the search results
     *                 should be sorted from
     *                 minimum to maximum or from maximum to minimum. If it is true,
     *                 the results will be sorted from
     *                 minimum to maximum, and if it is false, the results will be
     *                 sorted from maximum to minimum.
     * @return The method is returning a String that contains the information of the
     *         products that have
     *         a stock amount within the specified range (min and max). The order of
     *         the products in the
     *         returned String depends on the value of the boolean parameter
     *         minToMax. If it is true, the
     *         products are sorted from the one with the lowest stock amount to the
     *         one with the highest stock
     *         amount. If it is false, the
     */
    public String searchProductByStock(int min, int max, boolean minToMax) {// Amount
        mercadoLibre.getProducts().sort(Comparator.comparingInt(Product::getAmount));
        Integer[] arr = new Integer[mercadoLibre.getProducts().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getProducts().get(i).getAmount();
        }
        ArrayList<Integer> position = integerSearcher.binarySearchByRange(arr, min, max);

        return printInRange(position, minToMax, true);
    }

    /**
     * This Java function searches for products within a specified range of number
     * of purchases and
     * returns them in either ascending or descending order based on a boolean
     * parameter.
     * 
     * @param max      The maximum number of purchases a product can have to be
     *                 included in the search
     *                 results.
     * @param min      The minimum number of purchases a product must have to be
     *                 included in the search.
     * @param minToMax A boolean value that determines whether the search results
     *                 should be sorted from
     *                 minimum to maximum (true) or from maximum to minimum (false).
     * @return The method is returning a String that contains the information of the
     *         products that have
     *         a number of purchases within a certain range, sorted either from
     *         lowest to highest or from
     *         highest to lowest, depending on the value of the boolean parameter
     *         "minToMax".
     */
    public String searchProductBySells(int min, int max, boolean minToMax) {// NumberPurchases
        mercadoLibre.getProducts().sort(Comparator.comparingInt(Product::getNumberPurchases));
        Integer[] arr = new Integer[mercadoLibre.getProducts().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getProducts().get(i).getNumberPurchases();
        }
        ArrayList<Integer> position = integerSearcher.binarySearchByRange(arr, min, max);

        return printInRange(position, minToMax, true);
    }

    /**
     * This function searches for products within a specified category range and
     * returns a string
     * message with the matching products.
     * 
     * @param min      The minimum value of the range to search for products by
     *                 category.
     * @param max      The maximum value of the range to search for products by
     *                 category.
     * @param minToMax A boolean value that determines whether the search should be
     *                 performed from the
     *                 minimum value to the maximum value (true) or from the maximum
     *                 value to the minimum value
     *                 (false).
     * @return The method is returning a String that contains a message with the
     *         products that match
     *         the given category range. If there are no products that match the
     *         range, the method returns a
     *         message indicating that there are no elements on this range.
     */
    public String searchProductByCategory(Integer min, Integer max, boolean minToMax) {
        mercadoLibre.getProducts().sort(Comparator.comparingInt(Product::getCategoryPos));
        Integer[] arr = new Integer[mercadoLibre.getProducts().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getProducts().get(i).getCategory().ordinal();
        }
        ArrayList<Integer> position = integerSearcher.binarySearchByRange(arr, min, max);

        return printInRange(position, minToMax, true);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * The function sorts a list of orders by price, searches for orders within a
     * given price range,
     * and returns a string representation of the orders in either ascending or
     * descending order based
     * on a boolean parameter.
     * 
     * @param max      The maximum price limit for the orders to be searched.
     * @param min      The minimum price value to search for in the list of orders.
     * @param minToMax A boolean value that determines whether the search results
     *                 should be sorted from
     *                 minimum to maximum price (true) or from maximum to minimum
     *                 price (false).
     * @return The method is returning a String that contains the orders within a
     *         given price range,
     *         sorted either from lowest to highest or from highest to lowest,
     *         depending on the value of the
     *         boolean parameter "minToMax".
     */
    public String searchOrderByTotalPrice(Double min, Double max, boolean minToMax) {
        mercadoLibre.getOrders().sort(Comparator.comparingDouble(Order::getPrice));
        Double[] arr = new Double[mercadoLibre.getOrders().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getOrders().get(i).getPrice();
        }
        ArrayList<Integer> position = doubleSearcher.binarySearchByRange(arr, min, max);

        return printInRange(position, minToMax, false);
    }

    /**
     * The function sorts a list of orders by date, performs a binary search on the
     * list based on a
     * given range, and returns a string representation of the orders in either
     * ascending or descending
     * order.
     * 
     * @param max      The maximum value to search for in the list of orders, based
     *                 on the date in
     *                 milliseconds.
     * @param min      The minimum value for the date range to search for orders.
     * @param minToMax A boolean value that determines whether the search results
     *                 should be sorted from
     *                 minimum to maximum date or from maximum to minimum date. If
     *                 it is true, the results will be
     *                 sorted from minimum to maximum date, otherwise, they will be
     *                 sorted from maximum to minimum
     *                 date.
     * @return The method is returning a String that contains information about the
     *         orders within a
     *         certain date range, sorted either from the earliest to the latest
     *         date or from the latest to the
     *         earliest date, depending on the value of the boolean parameter
     *         "minToMax".
     */
    public String searchOrderByDate(Date min, Date max, boolean minToMax) {
        Collections.sort(mercadoLibre.getOrders(), new Comparator<Order>() {
            @Override
            public int compare(Order p1, Order p2) {
                return p1.getDateFormatDate().compareTo(p2.getDateFormatDate());
            }
        });
        Date[] arr = new Date[mercadoLibre.getOrders().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getOrders().get(i).getDateFormatDate();
        }
        ArrayList<Integer> position = dateSearcher.binarySearchByRange(arr, min, max);

        return printInRange(position, minToMax, false);
    }

    /**
     * This function searches for orders by buyer name within a given range and returns the results in
     * a specified order.
     * 
     * @param min The minimum value to search for in the list of buyer names.
     * @param max The maximum value for the range of buyer names to search for.
     * @param minToMax A boolean value that determines whether the search results should be sorted in
     * ascending order (true) or descending order (false).
     * @return The method is returning a string that contains the orders within the range of buyer
     * names specified by the "min" and "max" parameters, sorted in ascending or descending order based
     * on the value of the "minToMax" parameter.
     */
    public String searchOrderByBuyerName(String min, String max, boolean minToMax) {
        Collections.sort(mercadoLibre.getOrders(), new Comparator<Order>() {
            @Override
            public int compare(Order p1, Order p2) {
                return String.valueOf(p1.getNameBuyer()).compareTo(String.valueOf(p2.getNameBuyer()));
            }
        });
        String[] arr = new String[mercadoLibre.getOrders().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getOrders().get(i).getNameBuyer();
        }
        ArrayList<Integer> position = stringSearcher.binarySearchByRange(arr, min, max);

        return printInRange(position, minToMax, false);
    }

    /**
     * The function prints a range of elements from an ArrayList either in ascending or descending
     * order and either as products or orders.
     * 
     * @param position An ArrayList of integers representing the positions of elements to be printed.
     * @param minToMax A boolean value indicating whether the elements should be printed in ascending
     * order (true) or descending order (false).
     * @param isProduct isProduct is a boolean variable that determines whether the method should print
     * products or orders. If isProduct is true, the method will print products, and if it is false,
     * the method will print orders.
     * @return The method is returning a String that contains a message with the elements within a
     * given range of an ArrayList, either in ascending or descending order, and either as products or
     * orders, depending on the parameters passed to the method.
     */
    public String printInRange(ArrayList<Integer> position, boolean minToMax, boolean isProduct) {
        StringBuilder msj = new StringBuilder();
        if (position == null || position.isEmpty()) {
            return "\nThere are not elements on this range";
        }
        int sumIndex = 1;
        int init = 0;
        int limit = position.size();
        if (!minToMax) {
            sumIndex = -1;
            init = position.size() - 1;
            limit = -1;
        }
        if (isProduct) {
            for (int index = init; index != limit; index += sumIndex) {
                msj.append("\n").append((position.get(index) + 1)).append(") ")
                        .append(mercadoLibre.getProducts().get(position.get(index)).toString());
            }
        } else {
            for (int index = init; index != limit; index += sumIndex) {
                msj.append("\n").append((position.get(index) + 1)).append(") ")
                        .append(mercadoLibre.getOrders().get(position.get(index)).toString());
            }
        }

        return msj.toString();
    }

    /**
     * The function displays a list of products or a message indicating that the
     * list is empty.
     * 
     * @return The method `showProducts()` returns a string that contains either the
     *         list of products
     *         or a message indicating that the list is empty.
     */
    public String showProducts() {
        StringBuilder msj = new StringBuilder("PRODUCTS LIST");
        if (!mercadoLibre.getProducts().isEmpty()) {
            for (int i = 0; i < mercadoLibre.getProducts().size(); i++) {
                msj.append("\n").append((i + 1)).append(") ").append(mercadoLibre.getProducts().get(i).toString());
            }
            return msj.toString();
        } else {
            return "The products list is Empty";
        }
    }

    /**
     * The function displays a list of orders or a message indicating that the list
     * is empty.
     * 
     * @return The method is returning a String that represents either the list of
     *         orders or a message
     *         indicating that the list is empty.
     */
    public String showOrders() {
        StringBuilder msj = new StringBuilder("ORDERS LIST");
        if (!mercadoLibre.getOrders().isEmpty()) {
            for (int i = 0; i < mercadoLibre.getOrders().size(); i++) {
                msj.append("\n").append((i + 1)).append(") ").append(mercadoLibre.getOrders().get(i).toString());
            }
            return msj.toString();
        } else {
            return "The orders list is Empty";
        }

    }

    /**
     * This function returns the size of the list of products in a MercadoLibre
     * object.
     * 
     * @return The method `getProductsSize()` is returning the size of the list of
     *         products in the
     *         `mercadoLibre` object.
     */
    public int getProductsSize() {
        return mercadoLibre.getProducts().size();
    }

    /**
     * This function returns a string representation of all the categories in an
     * enum.
     * 
     * @return The method `toStringCategory()` is returning a string that contains
     *         the index and name
     *         of each category in the `Category` enum, separated by a new line
     *         character.
     */
    public String toStringCategory() {
        Category[] categories = Category.values();
        StringBuilder msj = new StringBuilder();
        for (int i = 0; i < categories.length; i++) {
            msj.append("\n ").append(i).append(") ").append(categories[i]);
        }
        return msj.toString();
    }

    /**
     * @return MercadoLibre return the mercadoLibre
     */
    public MercadoLibre getMercadoLibre() {
        return mercadoLibre;
    }

    /**
     * This function checks if the orders list in a marketplace object is empty and
     * returns a boolean
     * value accordingly.
     * 
     * @return A boolean value indicating whether the orders list in the
     *         mercadoLibre object is empty
     *         or not. If the list is empty, the method returns true, otherwise it
     *         returns false.
     */
    public boolean orderIsEmpty() {
        return (mercadoLibre.getOrders().isEmpty());
    }

    /**
     * This function checks if the list of products in a marketplace object is empty
     * and returns a
     * boolean value accordingly.
     * 
     * @return A boolean value indicating whether the list of products in the
     *         "mercadoLibre" object is
     *         empty or not. If the list is empty, the method will return "true",
     *         otherwise it will return
     *         "false".
     */
    public boolean productsIsEmpty() {
        return (mercadoLibre.getProducts().isEmpty());
    }

}