package model;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class Order implements Comparable <Order> {
    
    private String nameBuyer;
    private ArrayList<Couple> products;
    private int price;
    private Date date;
    
    public Order(String nameBuyer) {
        this.nameBuyer = nameBuyer;
        this.products = new ArrayList<>();
        this.date = Calendar.getInstance().getTime();
    }

    public void calculateOrderPrice(){
        for (Couple couple : products) {
            this.price += couple.getProduct().getPrice() * couple.getAmount();
        }
    }

    public void addCouple(Couple couple){
        products.add(couple);
    }

    @Override
    public int compareTo(Order order2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
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
     * @return ArrayList<Couple<Integer, Product>> return the products
     */
    public ArrayList<Couple> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(ArrayList<Couple> products) {
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
     * @return Date return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

}
