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
        this.date = formatedDate.format(dateWithHour.getTime()); // Format the date
        this.dateFormatDate = formatedDate.parse(date);
    }

    
    /**
     * This function calculates the total price of a couple's order based on the product price and the
     * amount ordered.
     * 
     * @param couple The parameter "couple" is an object of type "CoupleOrderAmount", which contains
     * information about a product and the amount ordered. The method "calculateOrderPrice" takes this
     * object as input and calculates the total price of the order by multiplying the price of the
     * product with the amount ordered and
     */
    public void calculateOrderPrice(CoupleOrderAmount couple){
        this.price += couple.getProduct().getPrice() * couple.getAmount();
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
     * This Java function returns a string containing a list of products ordered by a buyer, or a
     * message indicating that the list is empty.
     * 
     * @return The method `showProducts` is returning a string message that shows the list of products
     * ordered by the buyer. If the list is not empty, it will show the details of each product in the
     * list. If the list is empty, it will show a message indicating that the list is empty.
     */
    public String showProducts() {
        String msj = "\nORDER PRODUCTS OF " + nameBuyer.toUpperCase();
;        if (!products.isEmpty()) {
            for (int i = 0; i < products.size(); i++) {
                msj += "\n"+ products.get(i).toString() + "\n";
            }
            return msj;
        } else {
            return "\nThe order list is Empty\n";

        }
    }

    /**
     * This is a Java function that returns a string representation of an object, including the name of
     * the buyer, price, date, and a list of products.
     * 
     * @return A string representation of an object that includes the name of the buyer, the price, the
     * date, and the products purchased.
     */
    @Override
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