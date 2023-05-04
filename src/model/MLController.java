package model;

import java.util.ArrayList;

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
            throws ObjectWithSameName {
        Product product = new Product(name, description, price, amount, category);
        if (searchProductByName(product) == -1) {
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

    public String addProductToOrder(int amount, int product) throws ObjectOutOfStock {
        int pos = mercadoLibre.getOrders().size() - 1;
        mercadoLibre.getOrders().get(pos);
        if (mercadoLibre.getProducts().get(product - 1).getAmount() >= amount) {
            mercadoLibre.getProducts().get(product - 1)
                    .setAmount(mercadoLibre.getProducts().get(product - 1).getAmount() - amount);
            mercadoLibre.getOrders().get(pos)
                    .addCouple(new CoupleOrderAmount(amount, mercadoLibre.getProducts().get(product - 1)));
            return "Product Added to the Order correctly";
        } else {
            throw new ObjectOutOfStock("The product is Out of Stock, select another one");
        }
    }

    public int searchProductByName(Product product) {
        String[] arr = new String[mercadoLibre.getProducts().size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mercadoLibre.getProducts().get(i).getName();
        }
        return stringSearcher.binarySearchProduct(arr, product.getName());
    }

    public String showProducts() {
        String msj = "ORDER LIST";
        if (!mercadoLibre.getProducts().isEmpty()) {
            for (int i = 0; i < mercadoLibre.getProducts().size(); i++) {
                msj += "\n" + (i + 1) + ") " + mercadoLibre.getProducts().get(i).toString();
            }
            return msj;
        } else {
            return "The order list is Empty";
        }
    }

    public String showOrders() {
        String msj = "PRODUCT LIST";
        if (!mercadoLibre.getOrders().isEmpty()) {
            for (int i = 0; i < mercadoLibre.getOrders().size(); i++) {
                msj += "\n" + (i + 1) + ") " + mercadoLibre.getOrders().get(i).toString();
            }
            return msj;
        } else {
            return "The product list is Empty";
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
