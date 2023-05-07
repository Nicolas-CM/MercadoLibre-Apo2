package exceptions;

/**
 * The ObjectOutOfStock class is an exception class that represents an object being out of stock and
 * contains a message describing the exception.
 */
public class ObjectOutOfStock extends Exception{
    private String msj;

    public ObjectOutOfStock(String msj){
        super(msj);
        this.msj= msj;
    }

    /**
     * @return String return the msj
     */
    public String getMsj() {
        return msj;
    }
}