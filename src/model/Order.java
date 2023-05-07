package model;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Order {
    
    private String nameBuyer;
    private ArrayList<CoupleOrderAmount> products;
    private double price;
    private String date;
    private Date dateFormatDate;
    
    public Order(String nameBuyer) throws ParseException {
        this.nameBuyer = nameBuyer;
        this.products = new ArrayList<>();
        //Creating instance of calendar
        Calendar dateWithHour = Calendar.getInstance();
        //Eliminating the atributes of a hour of the date
        dateWithHour.clear(Calendar.HOUR_OF_DAY);
        dateWithHour.clear(Calendar.MINUTE);
        dateWithHour.clear(Calendar.SECOND);
        dateWithHour.clear(Calendar.MILLISECOND);

        SimpleDateFormat formatedDate = new SimpleDateFormat("dd/MM/yyyy"); // Definiton of the format for date
        this.date = formatedDate.format(dateFormatDate.getTime()); // Format the date
        this.dateFormatDate = formatedDate.parse(date);
    }

    /**
     * This function calculates the total price of an order by multiplying the price of each product by
     * its corresponding amount and summing them up.
     */
    public void calculateOrderPrice(){
        for (CoupleOrderAmount couple : products) {
            this.price += couple.getProduct().getPrice() * couple.getAmount();
        }
    }

    /**
     * The function adds a couple order amount to a list of products.
     * 
     * @param couple The parameter "couple" is of type "CoupleOrderAmount", which is likely a custom
     * class that represents a couple of values related to an order, such as the product and the amount
     * ordered. The method "addCouple" adds this couple object to a list of products.
     */
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
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return Date return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * The function displays a list of products in an order or returns a message if the order list is
     * empty.
     * 
     * @return The method `showProducts()` returns a string message that shows the products of the
     * order. If the list of products is not empty, it shows each product with its corresponding index.
     * If the list is empty, it returns a message indicating that the order list is empty.
     */
    public String showProducts() {
        String msj = "\nPRODUCTS OF THE ORDER";
;        if (!products.isEmpty()) {
            for (int i = 0; i < products.size(); i++) {
                msj += "\n" + (i + 1) + ") " + products.get(i).toString();
            }
            return msj;
        } else {
            return "\nThe order list is Empty";

                   }
    }

    @Override
    // The `toString()` method is overriding the default implementation of the method inherited from
    // the `Object` class. It returns a string representation of the `Order` object, which includes the
    // name of the buyer, the total price of the order, the date of the order, and a list of the
    // products ordered. This method is often used for debugging and logging purposes, as well as for
    // displaying the object's information in a user interface.
    public String toString() {
        return "Name buyer: " + nameBuyer +  "\nPrice: " + price +  "\nDate: " + date  + showProducts();    }



    /**
     * @return Date return the dateFormatDate
     */
    public Date getDateFormatDate() {
        return dateFormatDate;
    }

    /**
     * @param dateFormatDate the dateFormatDate to set
     */
    public void setDateFormatDate(Date dateFormatDate) {
        this.dateFormatDate = dateFormatDate;
    }

}