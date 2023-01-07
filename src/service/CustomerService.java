package service;
import model.Customer;
import java.util.Collection;
import java.util.HashMap;

public class CustomerService {

    private final HashMap<String, Customer> customers = new HashMap<String, Customer>();

    private static final CustomerService INSTANCE = new CustomerService();

    private CustomerService() {}

    public void addCustomer(String firstName, String lastName, String email) {
        String key = email.toLowerCase();
        if (customers.containsKey(key)) {
            System.out.println("The account already exists. Please! Sign in!!!");
        }
        else {
            Customer value = new Customer(firstName, lastName, email);
            customers.put(key, value);
            System.out.println("Customer account has been successfully created.");
        }
    }

    public Customer getCustomer(String customerEmail) {
        String key = customerEmail.toLowerCase();
        if (customers.containsKey(key)) {
            return customers.get(key);
        }
        else {
            System.out.println("No such account exists. Please! Create an account.");
            return null;
        }
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    public static CustomerService getInstance() {
        return INSTANCE;
    }
}