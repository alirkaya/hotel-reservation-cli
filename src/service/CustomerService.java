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
        Customer newCustomer = new Customer(firstName, lastName, email);
        if (customers.containsKey(key)) {
            System.out.println("The account already exists. Please! Sign in!!!");
            return;
        }
        customers.put(key, newCustomer);
        System.out.println("Customer account has been successfully created.");
    }

    public Customer getCustomer(String customerEmail) {
        String key = customerEmail.toLowerCase();
        return customers.getOrDefault(key, null);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    public static CustomerService getInstance() {
        return INSTANCE;
    }
}