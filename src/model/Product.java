package model;

public class Product  {

    private String name;
    private String description;
    private double price;
    private int amount;
    private Category category;
    private int numberPurchases;

    public Product(String name, String description, double price, int amount, int category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.category = Category.values()[category];
        this.numberPurchases = 0;
    }


    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return double return the price
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
     * @return int return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return Category return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return int return the numberPurchases
     */
    public int getNumberPurchases() {
        return numberPurchases;
    }

    /**
     * @param numberPurchases the numberPurchases to set
     */
    public void setNumberPurchases(int numberPurchases) {
        this.numberPurchases = numberPurchases;
    }

    @Override
    // The `toString()` method is overriding the default implementation of the `toString()` method
    // provided by the `Object` class. It returns a string representation of the `Product` object,
    // including its name, description, price, actual stock, category, and number of times it has been
    // purchased. This method is often used for debugging and logging purposes.
    public String toString() {
        return "Name: " + name + "\nDescription: " + description + "\nPrice: " + price + "\nActual stock: "+ amount + "\nCategory: " + category + "\nTimes purchased: " + numberPurchases;
    }

}
