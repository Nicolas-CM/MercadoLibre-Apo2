package exceptions;


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