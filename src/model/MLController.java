package model;

import java.util.ArrayList;
import java.util.Calendar;

public class MLController {

    private MercadoLibre mercadoLibre;
    private ManagerPersistence managerPersistence;

    public MLController() {

        mercadoLibre = new MercadoLibre();
        managerPersistence = new ManagerPersistence();
        openProgram();
    }

    /**
     * Description: Load the list of Orders and Products of data
     */
    public void openProgram() {
        mercadoLibre.setOrders(managerPersistence.loadOrders());
        mercadoLibre.setProducts(managerPersistence.loadProducts());
    }

    /**
     * Description: Save the list of Orders and Products
     */
    public void closeProgram() {
        managerPersistence.saveOrders(mercadoLibre.getOrders());
        managerPersistence.saveProducts(mercadoLibre.getProducts());
    }

    public String addProduct(String name, String description, double price, int amount, int category) {
        return mercadoLibre.addProduct(new Product(name, description, price, amount, getCategory(category)));
    }

    public String addOrder(String nameBuyer, int price) {
        return mercadoLibre.addOrder(new Order(nameBuyer, price));
    }

    public String addCoupleOrder( int amount, int product){
        Couple couple = new Couple(amount, mercadoLibre.getProducts().get(product));
        
    }

    /* 
    public int binarySearchProduct(mercadoLibre.getProducts(), Product goal, int option){
		int left = 0; // obtenemos una referencia al puntero inicial  
		int right = arr.size() - 1; // obtenemos una referencia al puntero final 

        switch(){
            case 0:
                while(left <= right){ 

                    int mid = (right + left)/2; 

                    Product mid

                    if(goal.getName() < arr.get(mid)){
                        right = mid - 1;  
                    }
                    else if(goal > arr.get(mid)){
                        left = mid + 1; 
                    }
                    // si lo encontramos retornamos el elemento 
                    else {
                        return mid; 
                    }
                }
            break;

            case 1:
                break;

            case 2:
                break;

            case 3:
                break;

            default :
             break;
        }
	}
    */
    public Category getCategory(int typeCategory) {
        return Category.values()[typeCategory];
    }

    public String toStringCategory() {
        Category[] categories = Category.values();
        String msj = "";
        for (int i = 0; i < categories.length; i++) {
            msj += "\n " + i + ") " + categories[i];
        }
        return msj;
    }
}
