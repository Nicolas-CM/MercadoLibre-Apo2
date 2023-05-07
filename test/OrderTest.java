package test;


import model.*;
import exceptions.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.ParseException;

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

   
    
    @Test(expected = ObjectWithInvalidAmount.class)
    public void testEditAmountProductNegativeAmount() throws ObjectWithInvalidAmount {
        // Prueba que se lance la excepción ObjectWithInvalidAmount cuando se intenta editar la cantidad a un número negativo
        controller.editAmountProduct(1, -1);
    }

    @Test
    public void testEditAmountProductValidAmount() throws ObjectWithInvalidAmount {
        // Prueba que la cantidad de un producto se cambie correctamente cuando se proporciona un valor válido
        String result = controller.editAmountProduct(3, 2);
        assertEquals("The quantity of the product has been changed successfully", result);
        int pos = controller.searchProductPosByName("Macbook Pro 2018");
        assertEquals(2, container.getProducts().get(pos).getAmount());
    }

    @Test(expected = ObjectWithInvalidAmount.class)
    public void testEditAmountProductNotFound() throws ObjectWithInvalidAmount {
        // Prueba que el método devuelve "Product not found" cuando se intenta editar la cantidad de un producto que no existe
        controller.editAmountProduct(2, -20);
    }

    @Test(expected = ObjectOutOfStock.class)
    public void testAddProductOutStock() throws ObjectOutOfStock, ParseException, ObjectWithInvalidAmount, ObjectDoesntExists{
        controller.addOrder("Nicolas");
        controller.addProductToOrder(11, 3);

    }

     @Test(expected = ObjectDoesntExists.class)
    public void testAddProductDoesntExist() throws ObjectOutOfStock, ParseException, ObjectWithInvalidAmount, ObjectDoesntExists{
        controller.addOrder("Nicolas");
        controller.addProductToOrder(11, 4);

    }

    @Test
    public void testAddOrder() throws ParseException{
        controller.addOrder("Flamini");
        
        assertEquals("flamini", container.getOrders().get(0).getNameBuyer());
        
    }

    







}
