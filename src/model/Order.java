package model;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class Order {
    
    private String nameBuyer;
    private ArrayList<CoupleOrderAmount> products;
    private int price;
    private Date date;
    
    public Order(String nameBuyer) {
        this.nameBuyer = nameBuyer;
        this.products = new ArrayList<>();
        this.date = Calendar.getInstance().getTime();
    }

    public void calculateOrderPrice(){
        for (CoupleOrderAmount couple : products) {
            this.price += couple.getProduct().getPrice() * couple.getAmount();
        }
    }

    public void addCouple(CoupleOrderAmount couple){
        products.add(couple);
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
     * @return ArrayList<CoupleOrderAmount<Integer, Product>> return the products
     */
    public ArrayList<CoupleOrderAmount> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(ArrayList<CoupleOrderAmount> products) {
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

    public String showProducts() {
        String msj = "\nPRODUCTS OF THE LIST";
;        if (!products.isEmpty()) {
            for (int i = 0; i < products.size(); i++) {
                msj += "\n" + (i + 1) + ") " + products.get(i).toString();
            }
            return msj;
        } else {
            return "The order list is Empty";

                   }
    }

    @Override
    public String toString() {
        return "Name buyer: " + nameBuyer +  "\nPrice: " + price +  "\nDate: " + date  + showProducts();    }


}