package test;


import model.*;
import exceptions.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class OrderTest {

    private MercadoLibre container;
    private MLController controller;

    @Before
    public void setUp() throws ObjectWithSameName, ObjectWithInvalidAmount {
        controller = new MLController();
        container = MercadoLibre.getInstance();
        // Agregue algunos productos de prueba
        controller.addProduct("Macbook Pro 2016", "Apple Laptop", 1299.99, 10, 1);
        controller.addProduct("Macbook Pro 2017", "Apple Laptop", 1299.99, 10, 1);
        controller.addProduct("Macbook Pro 2018", "Apple Laptop", 1299.99, 10, 1);
    }

    //editProduct
    
    @Test(expected = ObjectWithInvalidAmount.class)
    public void testEditAmountProductNegativeAmount() throws ObjectWithInvalidAmount {
        // Prueba que se lance la excepción ObjectWithInvalidAmount cuando se intenta editar la cantidad a un número negativo
        controller.editAmountProduct("Macbook Pro 2016", -1);
    }

    @Test
    public void testEditAmountProductValidAmount() throws ObjectWithInvalidAmount {
        // Prueba que la cantidad de un producto se cambie correctamente cuando se proporciona un valor válido
        String result = controller.editAmountProduct("Macbook Pro 2018", 2);
        assertEquals("The quantity of the product has been changed successfully", result);
        int pos = controller.searchProductByName("Macbook Pro 2018");
        assertEquals(2, container.getProducts().get(pos).getAmount());
    }

    @Test
    public void testEditAmountProductNotFound() throws ObjectWithInvalidAmount {
        // Prueba que el método devuelve "Product not found" cuando se intenta editar la cantidad de un producto que no existe
        String result = controller.editAmountProduct("Flamini", 10);
        assertEquals("Product not found", result);
    }

}
