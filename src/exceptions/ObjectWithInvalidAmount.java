package exceptions;


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