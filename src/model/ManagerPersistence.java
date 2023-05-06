package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class ManagerPersistence {

    private final File PROJECT_DIR = new File(System.getProperty("user.dir"));
    private Gson gson;

    public ManagerPersistence() {
        gson = new Gson();
    }

    /**
     * Guarda un arraylist de orders a Json
     * 
     * @param orders El arraylist que se guardara
     */
    public void saveOrders(ArrayList<Order> orders) {

        File location = new File(PROJECT_DIR + "/data/orders.json");

        try {
            String json = gson.toJson(orders);
            FileOutputStream fos = new FileOutputStream(location);
            fos.write(json.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado");
        } catch (IOException e) {
            System.out.println("Error: No se puede escribir en el archivo");
        }
    }

    /**
     * Guarda un arraylist de products a Json
     * 
     * @param products El arraylist que se guardara
     */
    public void saveProducts(ArrayList<Product> products) {

        File location = new File(PROJECT_DIR + "/data/products.json");

        try {
            String json = gson.toJson(products);
            FileOutputStream fos = new FileOutputStream(location);
            fos.write(json.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado");
        } catch (IOException e) {
            System.out.println("Error: No se puede escribir en el archivo");
        }
    }

    /**
     * This function loads orders from a JSON file and returns them as an ArrayList.
     * 
     * @return An ArrayList of Order objects is being returned.
     */
    public ArrayList<Order> loadOrders() {
        File location = new File(PROJECT_DIR + "/data/orders.json");
        ArrayList<Order> orders = new ArrayList<>();
        try {
            if (location.exists() && location.length() > 0) {
                List<?> list = gson.fromJson(
                        new String(Files.readAllBytes(location.toPath()), StandardCharsets.UTF_8),
                        List.class);
                for (Object obj : list) {
                    Order order = gson.fromJson(gson.toJson(obj), Order.class);
                    orders.add(order);
                }
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Error: Sintaxis invalida de JSON.");
        } catch (JsonIOException e) {
            System.out.println("Error: No se puede leer el archivo.");
        } catch (IOException e) {
            System.out.println("Error: No se puede leer el archivo.");
        }
        return orders;
    }

    /**
     * This function loads product data from a JSON file and returns an ArrayList of Product objects.
     * 
     * @return An ArrayList of Product objects is being returned.
     */
    public ArrayList<Product> loadProducts() {
        File location = new File(PROJECT_DIR + "/data/products.json");
        ArrayList<Product> products = new ArrayList<>();
        try {
            if (location.exists() && location.length() > 0) {
                List<?> list = gson.fromJson(
                        new String(Files.readAllBytes(location.toPath()), StandardCharsets.UTF_8),
                        List.class);
                for (Object obj : list) {
                    Product product = gson.fromJson(gson.toJson(obj), Product.class);
                    products.add(product);
                }
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Error: Sintaxis invalida de JSON.");
        } catch (JsonIOException e) {
            System.out.println("Error: No se puede leer el archivo.");
        } catch (IOException e) {
            System.out.println("Error: No se puede leer el archivo.");
        }
        return products;
    }
}
