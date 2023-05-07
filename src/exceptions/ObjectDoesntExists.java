package exceptions;

/**
 * The class ObjectDoesntExists is an exception that is thrown when an object does not exist.
 */
public class ObjectDoesntExists extends Exception {

    private String msj;

    public ObjectDoesntExists(String msj){
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
