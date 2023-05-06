package model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import exceptions.*;

public class MLController {
    private Searcher<String> stringSearcher;
    private Searcher<Double> doubleSearcher;
    private Searcher<Integer> integerSearcher;

    private MercadoLibre mercadoLibre;
    private ManagerPersistence managerPersistence;

    public MLController() {
        stringSearcher = new Searcher<>();
        doubleSearcher = new Searcher<>();
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
                mercadoLibre.getOrders().get(pos)
                        .addCouple(new CoupleOrderAmount(amount, mercadoLibre.getProducts().get(pos)));
                return "Product Added to the Order correctly";
            } else {
                throw new ObjectOutOfStock("The product is Out of Stock, select another one");
            }
        }
        return "";
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
        Collections.sort(mercadoLibre.getProducts(), new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.valueOf(p1.getPrice()).compareTo(Double.valueOf(p2.getPrice()));
            }
        });
        Double[] arr = new Double[mercadoLibre.getProducts().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getProducts().get(i).getPrice();
        }

        String msj = "";

        ArrayList<Integer> position = doubleSearcher.binarySearchByRange(arr, min, max);
        if(position == null || position.isEmpty()){
            return "There are not elements on this range";
        }
        if (minToMax) {
            for (int index = 0; index < position.size(); index++) {
                msj += "\n" + position.get(index) + ") "
                        + mercadoLibre.getProducts().get(position.get(index)).toString();
            }
        } else {
            for (int index = position.size()-1; index >= 0; index--) {
                msj += "\n" + position.get(index) + ") "
                        + mercadoLibre.getProducts().get(position.get(index)).toString();
            }
        }
        return msj;
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
    public String searchProductByStock(int max, int min, boolean minToMax) {
        Collections.sort(mercadoLibre.getProducts(), new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Integer.valueOf(p1.getAmount()).compareTo(Integer.valueOf(p2.getAmount()));
            }
        });
        Integer[] arr = new Integer[mercadoLibre.getProducts().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getProducts().get(i).getAmount();
        }

        String msj = "";

        ArrayList<Integer> position = integerSearcher.binarySearchByRange(arr, min, max);
        if (minToMax) {
            for (int index = 0; index < position.size(); index++) {
                msj += "\n" + position.get(index) + ") "
                        + mercadoLibre.getProducts().get(position.get(index)).toString();
            }
        } else {
            for (int index = position.size()-1; index >= 0; index--) {
                msj += "\n" + position.get(index) + ") "
                        + mercadoLibre.getProducts().get(position.get(index)).toString();
            }
        }
        return msj;
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
    public String searchProductBySells(int max, int min, boolean minToMax) {
        Collections.sort(mercadoLibre.getProducts(), new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Integer.valueOf(p1.getNumberPurchases()).compareTo(Integer.valueOf(p2.getNumberPurchases()));
            }
        });
        Integer[] arr = new Integer[mercadoLibre.getProducts().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getProducts().get(i).getNumberPurchases();
        }

        String msj = "";

        ArrayList<Integer> position = integerSearcher.binarySearchByRange(arr, min, max);
        if (minToMax) {
            for (int index = 0; index < position.size(); index++) {
                msj += "\n" + position.get(index) + ") "
                        + mercadoLibre.getProducts().get(position.get(index)).toString();
            }
        } else {
            for (int index = position.size(); index >= 0; index--) {
                msj += "\n" + position.get(index) + ") "
                        + mercadoLibre.getProducts().get(position.get(index)).toString();
            }
        }
        return msj;
    }

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
    public String searchOrderByPrice(Double max, Double min, boolean minToMax) {
        Collections.sort(mercadoLibre.getOrders(), new Comparator<Order>() {
            @Override
            public int compare(Order p1, Order p2) {
                return Double.valueOf(p1.getPrice()).compareTo(Double.valueOf(p2.getPrice()));
            }
        });
        Double[] arr = new Double[mercadoLibre.getOrders().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getOrders().get(i).getPrice();
        }

        String msj = "";

        ArrayList<Integer> position = doubleSearcher.binarySearchByRange(arr, min, max);
        if (minToMax) {
            for (int index = 0; index < position.size(); index++) {
                msj += "\n" + position.get(index) + ") " + mercadoLibre.getOrders().get(position.get(index)).toString();
            }
        } else {
            for (int index = position.size(); index >= 0; index--) {
                msj += "\n" + position.get(index) + ") " + mercadoLibre.getOrders().get(position.get(index)).toString();
            }
        }
        return msj;
    }

    /**
     * The function sorts a list of orders by date, performs a binary search on the list based on a
     * given range, and returns a string representation of the orders in either ascending or descending
     * order.
     * 
     * @param max The maximum value to search for in the list of orders, based on the date in
     * milliseconds.
     * @param min The minimum value for the date range to search for orders.
     * @param minToMax A boolean value that determines whether the search results should be sorted from
     * minimum to maximum date or from maximum to minimum date. If it is true, the results will be
     * sorted from minimum to maximum date, otherwise, they will be sorted from maximum to minimum
     * date.
     * @return The method is returning a String that contains information about the orders within a
     * certain date range, sorted either from the earliest to the latest date or from the latest to the
     * earliest date, depending on the value of the boolean parameter "minToMax".
     */
    public String searchOrderByDate(Double max, Double min, boolean minToMax) {
        Collections.sort(mercadoLibre.getOrders(), new Comparator<Order>() {
            @Override
            public int compare(Order p1, Order p2) {
                return Double.valueOf(p1.getDateFormatDate().getTime()).compareTo(Double.valueOf(p2.getDateFormatDate().getTime()));
            }
        });
        Double[] arr = new Double[mercadoLibre.getOrders().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (double) mercadoLibre.getOrders().get(i).getDateFormatDate().getTime();
        }

        String msj = "";

        ArrayList<Integer> position = doubleSearcher.binarySearchByRange(arr, min, max);
        if (minToMax) {
            for (int index = 0; index < position.size(); index++) {
                msj += "\n" + position.get(index) + ") " + mercadoLibre.getOrders().get(position.get(index)).toString();
            }
        } else {
            for (int index = position.size(); index >= 0; index--) {
                msj += "\n" + position.get(index) + ") " + mercadoLibre.getOrders().get(position.get(index)).toString();
            }
        }
        return msj;
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
        Collections.sort(mercadoLibre.getProducts(), new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        String[] arr = new String[mercadoLibre.getProducts().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getProducts().get(i).getName();
        }
        return stringSearcher.binarySearch(arr, product.toLowerCase());

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
        String msj = "PRODUCTS LIST";
        if (!mercadoLibre.getProducts().isEmpty()) {
            for (int i = 0; i < mercadoLibre.getProducts().size(); i++) {
                msj += "\n" + (i + 1) + ") " + mercadoLibre.getProducts().get(i).toString() + "\n";
            }
            return msj;
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
        String msj = "ORDERS LIST";
        if (!mercadoLibre.getOrders().isEmpty()) {
            for (int i = 0; i < mercadoLibre.getOrders().size(); i++) {
                msj += "\n" + (i + 1) + ") " + mercadoLibre.getOrders().get(i).toString();
            }
            return msj;
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
        String msj = "";
        for (int i = 0; i < categories.length; i++) {
            msj += "\n " + i + ") " + categories[i];
        }
        return msj;
    }

    /**
     * @return MercadoLibre return the mercadoLibre
     */
    public MercadoLibre getMercadoLibre() {
        return mercadoLibre;
    }

}