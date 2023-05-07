package exceptions;

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
