package model;

public class Couple {

    private int amount;
    private Product product;

    public Couple(int amount, Product product) {
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

}
