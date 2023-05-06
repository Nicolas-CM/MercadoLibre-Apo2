package model;

import java.util.Collections;
import java.util.Comparator;

import exceptions.*;

public class MLController {
    private Searcher<String> stringSearcher;
    private Searcher<Double> doubleSearcher;

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

    public String addProduct(String name, String description, double price, int amount, int category)
            throws ObjectWithSameName, ObjectWithInvalidAmount {
        Product product = new Product(name.toLowerCase(), description, price, amount, category);
        if (amount < 0) {
            throw new ObjectWithInvalidAmount("Please write a valid amount (amount must be grater than or equal to 0)");
        }
        if (searchProductByName(name.toLowerCase()) == -1) {
            mercadoLibre.addProduct(product);
            return "Product Added Correctly ";
        } else {
            throw new ObjectWithSameName(
                    "There is another product with the same name, please write another name for the product");
        }
    }

    public void addOrder(String nameBuyer) {
        mercadoLibre.getOrders().add(new Order(nameBuyer));
    }

    public String editAmountProduct(String name, int newAmount) throws ObjectWithInvalidAmount {
        int pos = searchProductByName(name);
        if (newAmount < 0) {
            throw new ObjectWithInvalidAmount("Please write a valid amount (amount must be grater than or equal to 0)");
        }
        if (searchProductByName(name) == -1) {
            return "Product not found";
        } else {
            mercadoLibre.getProducts().get(pos).setAmount(newAmount);
            return "The quantity of the product has been changed successfully";

        }

    }

    public String addProductToOrder(int amount, String product) throws ObjectOutOfStock {
        int pos = searchProductByName(product.toLowerCase());
        if (pos != -1) {
            if (mercadoLibre.getProducts().get(pos).getAmount() >= amount) {
                mercadoLibre.getProducts().get(pos)
                        .setAmount(mercadoLibre.getProducts().get(pos).getAmount() - amount);
                mercadoLibre.getOrders().get(pos)
                        .addCouple(new CoupleOrderAmount(amount, mercadoLibre.getProducts().get(pos)));
                return "Product Added to the Order correctly";
            } else {
                throw new ObjectOutOfStock("The product is Out of Stock, select another one");
            }
        }
        return "";
    }

    public int searchProductByName(String product) {
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
        return stringSearcher.binarySearch(arr, product);
    }

    public String showProducts() {
        String msj = "PRODUCTS LIST";
        if (!mercadoLibre.getProducts().isEmpty()) {
            for (int i = 0; i < mercadoLibre.getProducts().size(); i++) {
                msj += "\n" + (i + 1) + ") " + mercadoLibre.getProducts().get(i).toString();
            }
            return msj;
        } else {
            return "The products list is Empty";
        }
    }

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

    public int getProductsSize() {
        return mercadoLibre.getProducts().size();
    }

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
