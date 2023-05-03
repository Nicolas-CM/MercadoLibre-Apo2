package model;

public class MLController {
    
    private MercadoLibre mercadoLibre;
    private ManagerPersistence managerPersistence;

    public MLController(){

        mercadoLibre = new MercadoLibre();
        managerPersistence = new ManagerPersistence();
        loadOrders();
        loadProducts();
    }

    public void loadOrders(){
        mercadoLibre.setOrders(managerPersistence.loadOrders());
    }

    public void loadProducts(){
        mercadoLibre.setProducts(managerPersistence.loadProducts());
    }

    /**
     * Description: Save the list of Orders and Products
     */
    public void closeProgram(){
        managerPersistence.saveOrders(mercadoLibre.getOrders());
        managerPersistence.saveProducts(mercadoLibre.getProducts());
    }
}
