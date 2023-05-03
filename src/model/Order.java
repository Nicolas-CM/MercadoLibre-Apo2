package model;

import java.util.ArrayList;
import java.util.Calendar;

public class Order {
    
    private String nameBuyer;
    private ArrayList<Product> products;
    private int price;
    private Calendar date;
    
    public Order(String nameBuyer, ArrayList<Product> products, int price, Calendar date) {
        this.nameBuyer = nameBuyer;
        this.products = products;
        this.price = price;
        this.date = date;
    }

    /**
     * @return String return the nameBuyer
     */
    public String getNameBuyer() {
        return nameBuyer;
    }

    /**
     * @param nameBuyer the nameBuyer to set
     */
    public void setNameBuyer(String nameBuyer) {
        this.nameBuyer = nameBuyer;
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
     * @return int return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return Calendar return the date
     */
    public Calendar getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Calendar date) {
        this.date = date;
    }

}
