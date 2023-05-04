package exceptions;

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