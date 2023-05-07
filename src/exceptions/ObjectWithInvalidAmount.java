package exceptions;


/**
 * The class ObjectWithInvalidAmount is a custom exception that represents an object with an invalid
 * amount.
 */
public class ObjectWithInvalidAmount extends Exception{
    private String msj;

    public ObjectWithInvalidAmount(String msj){
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