package test;
import model.*;
import exceptions.*;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;


public class AddProdcutTest {

    private MercadoLibre container;
    private MLController controller;


    @Before
    public void setup(){
        controller = new MLController();
    }



    

    

    @Test
    public void testAddProductSuccess() throws ObjectWithSameName {
        Product product = new Product("Macbook Pro", "Apple Laptop", 1299.99, 10, 1);
        String result = container.addProduct(product);
        assertEquals("The process was correct", result);
        assertEquals(1, container.getProducts().size());
    }

    @Test(expected = ObjectWithSameName.class)
    public void testAddProductWithSameName() throws ObjectWithSameName {
        MLController controller = new MLController();
        controller.addProduct("Macbook Pro", "Apple Laptop", 1299.99, 10, 1);
        controller.addProduct("Macbook Pro", "Apple Laptop", 300000, 10, 1);
    }

    @Test
    public void testAddedProductHasCorrectValues() throws ObjectWithSameName {
      
    
        controller.addProduct("Macbook Pro", "Apple Laptop", 1299.99, 10, 1);
        Product product = controller.getMercadoLibre().getProducts().get(0); 
        assertEquals("Macbook Pro", product.getName());
        assertEquals("Apple Laptop", product.getDescription());
        assertEquals(1299.99, product.getPrice(), 0.01);
        assertEquals(10, product.getAmount());
        assertEquals("ELECTRONIC", product.getCategory().toString());
    }

   
}
