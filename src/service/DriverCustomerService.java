package service;
import model.Customer;
import java.util.Collection;


public class DriverCustomerService {

    public static void main(String[] args) {

        CustomerService frontDesk = CustomerService.getInstance();

        frontDesk.addCustomer("Brad", "Pitt", "brad@pitt.com");
        frontDesk.addCustomer("Johnny", "Depp", "johnny@depp.com");
        frontDesk.addCustomer("Fake", "Customer", "brad@pitt.com");
        frontDesk.getCustomer("brad@pitt.com");
        frontDesk.getCustomer("fake@customer.com");
        Collection<Customer> allCustomers = frontDesk.getAllCustomers();

        int counter = 1;
        for (Customer customer : allCustomers) {
            System.out.println("Customer No: " + counter);
            System.out.println(customer + "\n");
            counter++;
        }



    }
}
