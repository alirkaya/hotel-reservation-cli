package model;

public class Driver {

    public static void main(String[] args) {

        String firstName = "Bob";
        String lastName  = "Marley";
        String email     = "bob@marley.com";

        Customer validCustomer = new Customer(firstName, lastName, email);
        System.out.println(validCustomer);

        Customer badCustomer = new Customer(firstName, lastName, "email");
        System.out.println(badCustomer);

    }
}
