package model;

import java.util.ArrayList;

public class MercadoLibre {
    
    private ArrayList<Order> orders;
    private ArrayList<Product> products;
    
    public MercadoLibre() {
        orders = new ArrayList<>();
        products = new ArrayList<>();
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

}
