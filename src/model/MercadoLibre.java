package model;

import java.util.ArrayList;

public class MercadoLibre {
    
    private ArrayList<Order> orders;
    private ArrayList<Product> products;
    private static MercadoLibre instance;
    
    private MercadoLibre() {
        orders = new ArrayList<>();
        products = new ArrayList<>();
    }

     public static MercadoLibre getInstance() {
        if(instance == null) {
            instance = new MercadoLibre();
        }
        return instance;
    }



    /**
     * @return ArrayList<Order> return the orders
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    /**
     * @return ArrayList<Product> return the products
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * Description: Add a new Product
     * 
     * @param product The new product
     * @return The result of the operation
     */
    public String addProduct(Product product){
        if (products.add(product)) {
            return "The process was correct";
        } else {
            return "The process was incorrect";
        }
    }
    
    /**
     * Description: Add a new Order
     * 
     * @param order The new order
     * @return The result of the operation
     */
    public String addOrder(Order order){
        if (orders.add(order)) {
            return "The process was correct";
        } else {
            return "The process was incorrect";
        }
    }

    
}
