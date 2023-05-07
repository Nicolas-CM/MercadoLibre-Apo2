package test;

import model.*;
import exceptions.*;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

public class ProductTest {

    private MercadoLibre container;
    private MLController controller;

    @Before
    public void setup() {
        controller = new MLController();
        container = MercadoLibre.getInstance();
    }

    @Test
    public void testAddProductSuccess() throws ObjectWithSameName {
        Product product = new Product("Macbook Pro", "Apple Laptop", 1299.99, 10, 1);
        String result = container.addProduct(product);
        assertEquals("The process was correct", result);
        assertEquals(1, container.getProducts().size());
    }

    @Test(expected = ObjectWithSameName.class)
    public void testAddProductWithSameName() throws ObjectWithSameName, ObjectWithInvalidAmount {
        MLController controller = new MLController();
        controller.addProduct("Macbook Pro", "Apple Laptop", 1299.99, 10, 1);
        controller.addProduct("Macbook Pro", "Apple Laptop", 300000, 10, 1);
    }

    @Test
    public void testAddedProductHasCorrectValues() throws ObjectWithSameName, ObjectWithInvalidAmount {

        controller.addProduct("Macbook Pro", "Apple Laptop", 1299.99, 10, 3);
        Product product = controller.getMercadoLibre().getProducts().get(0);
        assertEquals("macbook pro", product.getName());
        assertEquals("Apple Laptop", product.getDescription());
        assertEquals(1299.99, product.getPrice(), 0.01);
        assertEquals(10, product.getAmount());
        assertEquals("ELECTRONIC", product.getCategory().toString());
    }

    @Test
    public void testSearchProductPosByName() throws ObjectWithSameName, ObjectWithInvalidAmount {
        controller.addProduct("Macbook Pro", "Apple Laptop", 1299.99, 10, 3);
        Product product = controller.getMercadoLibre().getProducts().get(0);
        assertEquals("macbook pro",
                container.getProducts().get(controller.searchProductPosByName(product.getName())).getName());
    }

    @Test
    public void testSearchProductByPrice() throws ObjectWithSameName, ObjectWithInvalidAmount {
        controller.addProduct("Macbook Pro 10", "Apple Laptop", 1999, 10, 3);
        controller.addProduct("Macbook Pro 2", "Apple Laptop", 2000, 10, 3);
        controller.addProduct("Macbook Pro 3", "Apple Laptop", 3000, 10, 3);
        controller.addProduct("Macbook Pro 4", "Apple Laptop", 7000, 10, 3);
        String result = controller.searchProductsByPrice(2000.0, 3000.0, true);
        assertEquals("\n2)  Name: macbook pro 2\n Description: Apple Laptop\n Price: 2000.0\n Actual stock: 10\n Category: ELECTRONIC\n Times purchased: 0\n3)  Name: macbook pro 3\n Description: Apple Laptop\n Price: 3000.0\n Actual stock: 10\n Category: ELECTRONIC\n Times purchased: 0" , result);
    }

    @Test
    public void testSearchProductByCategoryWithCategoryNotFound() throws ObjectWithSameName, ObjectWithInvalidAmount {
        controller.addProduct("Macbook Pro 10", "Apple Laptop", 1999, 10, 3);
        controller.addProduct("Macbook Pro 2", "Apple Laptop", 2000, 10, 3);
        String result = controller.searchProductByCategory(1, 1, true);
        assertEquals("\nThere are not elements on this range" , result);
    }


}
