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
     * This function adds a new product to a marketplace and checks for duplicate
     * names and invalid
     * amounts.
     * 
     * @param name        The name of the product being added (as a String).
     * @param description A String that describes the product being added.
     * @param price       A double value representing the price of the product.
     * @param amount      The amount parameter represents the quantity of the
     *                    product being added to the
     *                    inventory.
     * @param category    The category parameter is an integer that represents the
     *                    category of the product
     *                    being added. The specific categories and their
     *                    corresponding integer values are defined
     *                    elsewhere in the code.
     * @return The method is returning a String message. If the product is added
     *         correctly, it returns
     *         "Product Added Correctly". If there is another product with the same
     *         name, it throws an
     *         exception with the message "There is another product with the same
     *         name, please write another
     *         name for the product". If the amount is invalid, it throws an
     *         exception with the message "Please
     *         write a valid amount (amount must
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
        mercadoLibre.getOrders().add(new Order(nameBuyer.toLowerCase()));
    }

    /**
     * This function removes the last order from a list if it has no products.
     */
    public void verifyOrder() {
        if (mercadoLibre.getOrders().get(mercadoLibre.getOrders().size() - 1).getProducts().isEmpty()) {
            mercadoLibre.getOrders().remove(mercadoLibre.getOrders().size() - 1);
        }
    }

    /**
     * This Java function edits the amount of a product in a marketplace and throws
     * an exception if the
     * new amount is invalid.
     * 
     * @param position  The position of the product in the list of products
     *                  (starting from 1).
     * @param newAmount An integer representing the new amount of a product.
     * @return The method is returning a String message indicating that the quantity
     *         of the product has
     *         been changed successfully.
     */
    public String editAmountProduct(int position, int newAmount) throws ObjectWithInvalidAmount {

        if (newAmount < 0) {
            throw new ObjectWithInvalidAmount("Please write a valid amount (amount must be grater than or equal to 0)");
        }

        mercadoLibre.getProducts().get(position - 1).setAmount(newAmount);
        return "The quantity of the product has been changed successfully";

    }

    /**
     * This Java function adds a product to an order, updating the product's amount
     * and number of
     * purchases, and throws exceptions if the product doesn't exist, is out of
     * stock, or has an
     * invalid amount.
     * 
     * @param amount  The amount of the product that the user wants to add to the
     *                order.
     * @param product The ID of the product being added to the order.
     * @return The method returns a String message indicating whether the product
     *         was added to the
     *         order correctly or if there was an error (ObjectOutOfStock,
     *         ObjectWithInvalidAmount,
     *         ObjectDoesntExists).
     */
    public String addProductToOrder(int amount, int product)
            throws ObjectOutOfStock, ObjectWithInvalidAmount, ObjectDoesntExists {

        if (product > productsLimit() || product < 1) {
            throw new ObjectDoesntExists("The product selected doesn't exists, select another one");
        } else if (mercadoLibre.getProducts().get(product - 1) != null) {

            if (mercadoLibre.getProducts().get(product - 1).getAmount() == 0) {
                throw new ObjectOutOfStock("The product is Out of Stock, select another one");
            } else if (mercadoLibre.getProducts().get(product - 1).getAmount() >= amount) {
                mercadoLibre.getProducts().get(product - 1)
                        .setAmount(mercadoLibre.getProducts().get(product - 1).getAmount() - amount);
                mercadoLibre.getProducts().get(product - 1)
                        .setNumberPurchases(mercadoLibre.getProducts().get(product - 1).getNumberPurchases() + amount);
                Product po = mercadoLibre.getProducts().get(product - 1);
                try {
                    Product pc = (Product) po.clone();
                    CoupleOrderAmount couple = new CoupleOrderAmount(amount, pc);
                    mercadoLibre.getOrders().get(mercadoLibre.getOrders().size() - 1)
                            .addCouple(couple);
                    mercadoLibre.getOrders().get(mercadoLibre.getOrders().size() - 1)
                            .calculateOrderPrice(couple);
                    return "Product Added to the Order correctly";
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                return "";
            } else {
                throw new ObjectWithInvalidAmount(
                        "The product has less units than written please enter other quantity");
            }
        }
        return "";

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

    /**
     * The function searches for products with names within a specified range and
     * returns a formatted
     * string of the results.
     * 
     * @param min      The minimum string value to search for in the product names.
     * @param max      The maximum value to search for in the range. It is used in
     *                 the binary search
     *                 algorithm to find the upper bound of the range.
     * @param minToMax A boolean value indicating whether the search should be
     *                 performed from the
     *                 minimum value to the maximum value (true) or from the maximum
     *                 value to the minimum value
     *                 (false).
     * @return The method is returning a string that contains the products whose
     *         names fall within the
     *         specified range, sorted either from minimum to maximum or from
     *         maximum to minimum, depending on
     *         the value of the boolean parameter `minToMax`.
     */
    public String searchExactProductByName(String min, String max, boolean minToMax) {
        mercadoLibre.getProducts().sort(Comparator.comparing(Product::getName));
        String[] arr = new String[mercadoLibre.getProducts().size()];
        for (int i = 0; i < arr.length; i++) {
            if (mercadoLibre.getProducts().get(i).getName().length() >= min.length()) {
                arr[i] = mercadoLibre.getProducts().get(i).getName().substring(0, min.length());
            } else {
                arr[i] = "";
            }
        }
        ArrayList<Integer> position = stringSearcher.binarySearchStringRange(arr, arr, min, max);
        return printInRange(position, minToMax, true);
    }

    /**
     * This function searches for products within a given range of names, either by
     * prefix or suffix,
     * and returns the results in a specified order.
     * 
     * @param min      A string representing the minimum value to search for in the
     *                 product names.
     * @param max      The maximum string value to search for in the product names.
     * @param minToMax A boolean value indicating whether the search should be
     *                 performed from the
     *                 minimum value to the maximum value (true) or from the maximum
     *                 value to the minimum value
     *                 (false).
     * @param preffix  A boolean value indicating whether the search should be based
     *                 on the prefix of
     *                 the product name or the suffix. If true, the search will be
     *                 based on the prefix; if false, it
     *                 will be based on the suffix.
     * @return The method is returning a String that represents the result of a
     *         search for products
     *         within a certain range of names, based on the parameters passed to
     *         the method. The returned
     *         String contains information about the products found within the
     *         range, including their names and
     *         prices. If there is an exception thrown due to a
     *         StringIndexOutOfBoundsException, the method
     *         returns a message indicating that there may be products with a length
     *         shorter than the
     */
    public String searchProductByName(String min, String max, boolean minToMax, boolean preffix)
            throws StringIndexOutOfBoundsException {
        try {
            if (preffix) {
                mercadoLibre.getProducts().sort(Comparator.comparing(Product::getName));
                String[] arr = new String[mercadoLibre.getProducts().size()];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = mercadoLibre.getProducts().get(i).getName().substring(0, min.length()).toLowerCase();
                }
                String[] arr2 = new String[mercadoLibre.getProducts().size()];
                for (int i = 0; i < arr2.length; i++) {
                    arr2[i] = mercadoLibre.getProducts().get(i).getName().substring(0, max.length()).toLowerCase();
                }
                ArrayList<Integer> position = stringSearcher.binarySearchStringRange(arr, arr2, min, max);
                return printInRange(position, minToMax, true);
            } else {
                final int lastIndex = max.length();
                Collections.sort(mercadoLibre.getProducts(), new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        String sufijo1 = p1.getName().substring(p1.getName().length() - lastIndex).toLowerCase();
                        String sufijo2 = p2.getName().substring(p2.getName().length() - lastIndex).toLowerCase();
                        return sufijo1.compareTo(sufijo2);
                    }
                });

                String[] arr = new String[mercadoLibre.getProducts().size()];
                for (int i = 0; i < arr.length; i++) {
                    String temp = mercadoLibre.getProducts().get(i).getName().toLowerCase();
                    arr[i] = temp.substring(temp.length() - min.length());
                }
                String[] arr2 = new String[mercadoLibre.getProducts().size()];
                for (int i = 0; i < arr2.length; i++) {
                    String temp = mercadoLibre.getProducts().get(i).getName().toLowerCase();
                    arr2[i] = temp.substring(temp.length() - max.length());
                }
                ArrayList<Integer> position = stringSearcher.binarySearchStringRange(arr, arr2, min, max);
                return printInRange(position, minToMax, true);
            }
        } catch (StringIndexOutOfBoundsException e) {
            return "\nThere are some products that might have a lenght shother than the sufix or prefix\n";

        }
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
     * The function searches for orders within a given date range and returns them
     * in either ascending
     * or descending order.
     * 
     * @param min      The minimum date to search for orders.
     * @param max      The maximum date to search for orders.
     * @param minToMax A boolean value that determines whether the search results
     *                 should be sorted in
     *                 ascending order (true) or descending order (false) based on
     *                 the order date.
     * @return The method is returning a string that contains the orders within the
     *         specified date
     *         range, sorted either from earliest to latest or from latest to
     *         earliest, depending on the value
     *         of the boolean parameter `minToMax`.
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
     * The function searches for orders in a list by the buyer's name within a
     * specified range and
     * returns the results in a specific order.
     * 
     * @param min      The minimum value to search for in the buyer name.
     * @param max      The maximum value to search for in the range of buyer names.
     * @param minToMax A boolean value that determines whether the search results
     *                 should be sorted in
     *                 ascending order (true) or descending order (false) based on
     *                 the buyer name.
     * @return The method is returning a string that contains the result of
     *         searching for orders with
     *         buyer names that fall within a certain range, specified by the "min"
     *         and "max" parameters. The
     *         search is performed on a list of orders stored in the "mercadoLibre"
     *         object. The search results
     *         are sorted based on the buyer name, and the method returns a
     *         formatted string that contains the
     *         order information for
     */
    public String searchExactOrderByBuyerName(String min, String max, boolean minToMax) {
        mercadoLibre.getOrders().sort(Comparator.comparing(Order::getNameBuyer));
        String[] arr = new String[mercadoLibre.getOrders().size()];
        for (int i = 0; i < arr.length; i++) {
            if (mercadoLibre.getOrders().get(i).getNameBuyer().length() >= min.length()) {
                arr[i] = mercadoLibre.getOrders().get(i).getNameBuyer().substring(0, min.length()).toLowerCase();
            } else {
                arr[i] = "";
            }
        }
        ArrayList<Integer> position = stringSearcher.binarySearchByRange(arr, min, max);
        return printInRange(position, minToMax, false);
    }

    /**
     * This function searches for orders by buyer name within a given range, either
     * by prefix or
     * suffix, and returns the results in a specified order.
     * 
     * @param min      A string representing the minimum value to search for in the
     *                 buyer name.
     * @param max      The maximum string value to search for in the buyer name.
     * @param minToMax A boolean value indicating whether the search results should
     *                 be sorted from
     *                 minimum to maximum (true) or from maximum to minimum (false).
     * @param preffix  A boolean value indicating whether the search should be based
     *                 on the prefix
     *                 (true) or suffix (false) of the buyer name.
     * @return The method is returning a String that represents the result of a
     *         search for orders in a
     *         given range of buyer names, based on certain criteria such as prefix
     *         or suffix, and sorted in
     *         ascending or descending order. The returned String contains
     *         information about the orders found,
     *         including their buyer names, order IDs, and purchase dates. If there
     *         is an exception due to a
     *         StringIndexOutOfBoundsException, the method returns a message
     */
    public String searchOrderByBuyerName(String min, String max, boolean minToMax, boolean preffix)
            throws StringIndexOutOfBoundsException {
        try {
            if (preffix) {
                mercadoLibre.getOrders().sort(Comparator.comparing(Order::getNameBuyer));
                String[] arr = new String[mercadoLibre.getOrders().size()];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = mercadoLibre.getOrders().get(i).getNameBuyer().substring(0, min.length()).toLowerCase();
                }
                String[] arr2 = new String[mercadoLibre.getOrders().size()];
                for (int i = 0; i < arr2.length; i++) {
                    arr2[i] = mercadoLibre.getOrders().get(i).getNameBuyer().substring(0, max.length()).toLowerCase();
                }
                ArrayList<Integer> position = stringSearcher.binarySearchStringRange(arr, arr2, min, max);
                return printInRange(position, minToMax, false);
            } else {
                final int lastIndex = max.length();
                Collections.sort(mercadoLibre.getOrders(), new Comparator<Order>() {
                    @Override
                    public int compare(Order p1, Order p2) {
                        String sufijo1 = p1.getNameBuyer().substring(p1.getNameBuyer().length() - lastIndex);
                        String sufijo2 = p2.getNameBuyer().substring(p2.getNameBuyer().length() - lastIndex);
                        return sufijo1.compareTo(sufijo2);
                    }
                });

                String[] arr = new String[mercadoLibre.getOrders().size()];
                for (int i = 0; i < arr.length; i++) {
                    String temp = mercadoLibre.getOrders().get(i).getNameBuyer().toLowerCase();
                    arr[i] = temp.substring(temp.length() - min.length());
                }
                String[] arr2 = new String[mercadoLibre.getOrders().size()];
                for (int i = 0; i < arr2.length; i++) {
                    String temp = mercadoLibre.getOrders().get(i).getNameBuyer().toLowerCase();
                    arr2[i] = temp.substring(temp.length() - max.length());
                }
                ArrayList<Integer> position = stringSearcher.binarySearchStringRange(arr, arr2, min, max);
                return printInRange(position, minToMax, false);
            }
        } catch (StringIndexOutOfBoundsException e) {
            return "\nThere are some orders that might have a lenght shother than the sufix or prefix\n";

        }
    }

    /**
     * This function takes in an ArrayList of integers representing positions, a
     * boolean indicating
     * whether to print in ascending or descending order, and a boolean indicating
     * whether to print
     * products or orders, and returns a string containing the corresponding
     * products or orders in the
     * specified order.
     * 
     * @param position  An ArrayList of integers representing the positions of
     *                  elements to be printed.
     * @param minToMax  A boolean value indicating whether the elements should be
     *                  printed in ascending
     *                  order (true) or descending order (false).
     * @param isProduct A boolean value indicating whether the elements in the
     *                  ArrayList are products
     *                  or orders. If true, the method will print information about
     *                  products. If false, it will print
     *                  information about orders.
     * @return The method is returning a String that contains a message with the
     *         elements within a
     *         given range of an ArrayList, either in ascending or descending order,
     *         and either as products or
     *         orders, depending on the parameters passed to the method.
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
     * @return The method `showProducts` returns a String that represents either the
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
     * The function returns a string representation of the orders list, or a message
     * indicating that
     * the list is empty.
     * 
     * @return The method `showOrders` returns a String that represents either the
     *         list of orders or a
     *         message indicating that the list is empty.
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

    /**
     * This function returns the number of products in a MercadoLibre object.
     * 
     * @return The method `productsLimit()` is returning the size of the list of
     *         products in the
     *         `mercadoLibre` object.
     */
    public int productsLimit() {
        return mercadoLibre.getProducts().size();
    }

}