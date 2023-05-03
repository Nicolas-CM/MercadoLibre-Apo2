package model;

import exceptions.*;

public class MLController {
    private Searcher<Product> productSearcher;
    private Searcher<Order> orderSearcher;
    private MercadoLibre mercadoLibre;
    private ManagerPersistence managerPersistence;

    public MLController() {
        productSearcher= new Searcher<>();
        orderSearcher= new Searcher<>();
        mercadoLibre = new MercadoLibre();
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

    public String addProduct(String name, String description, double price, int amount, int category) throws ObjectWithSameName {
        Product product = new Product(name, description, price, amount, getCategory(category));
        if(searchProduct(product)!=-1){
            mercadoLibre.addProduct(product);
            return "Product Added Correctly ";
        }else{
            throw new ObjectWithSameName("There is another product with the same name");
        }
    }

    public String addOrder(String nameBuyer,int amount ,int product)  {
        Couple couple = new Couple(amount, mercadoLibre.getProducts().get(product));
        mercadoLibre.addOrder(new Order(nameBuyer));
        mercadoLibre.getOrders().get(mercadoLibre.getOrders().size()-1).addCouple(couple);
        return "Order created correctly";
        
    }

    public int searchOrder(Order order){
        return orderSearcher.binarySearchProduct(mercadoLibre.getOrders(), order);
    }

    public int searchProduct(Product product){
        return productSearcher.binarySearchProduct(mercadoLibre.getProducts(), product);
    }

    public Category getCategory(int typeCategory) {
        return Category.values()[typeCategory];
    }

    public String toStringCategory() {
        Category[] categories = Category.values();
        String msj = "";
        for (int i = 0; i < categories.length; i++) {
            msj += "\n " + i + ") " + categories[i];
        }
        return msj;
    }
}
