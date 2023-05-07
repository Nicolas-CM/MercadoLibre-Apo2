package exceptions;


/**
 * The class "ObjectWithSameName" extends the Exception class and has a constructor that takes a
 * message as a parameter.
 */
public class ObjectWithSameName extends Exception{
    private String msj;

    public ObjectWithSameName(String msj){
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