package model;

public class CoupleOrderAmount {

    private int amount;
    private Product product;

    public CoupleOrderAmount(int amount, Product product) {
        this.amount = amount;
        this.product = product;
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
     * @return T return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    // The `toString()` method is overriding the default implementation of the `toString()` method
    // provided by the `Object` class. It returns a string representation of the `CoupleOrderAmount`
    // object, which includes the `Product` object's string representation and the amount of the order.
    // This method is often used for debugging and logging purposes.
    public String toString() {
        // TODO Auto-generated method stub
        return product.toString() + "\nAmount: " + amount;
    }

}
