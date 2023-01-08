package model;

public class Customer {

    public String firstName;
    public String lastName;
    public String email;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        if (Customer.checkEmail(email)) {this.email = email;}
    }

    public String getFirstName() {
        return firstName.toUpperCase();
    }

    public String getLastName() {
        return lastName.toUpperCase();
    }

    public String getName() {
        return firstName.toUpperCase() + " " + lastName.toUpperCase();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (Customer.checkEmail(email)) {this.email = email;}
    }

    public static boolean checkEmail(String email) {
        if (email.matches("[\\w._]+@[\\w]+\\.com")) {return true;}
        else {
            throw new IllegalArgumentException(
                    "Not a valid email! Please use name@domain.com format. Only '.' and '_' are allowed in name.");
        }
    }
    @Override
    public String toString() {
        return "=====================================================\n" +
                "Customer Info:\n" +
                "=====================================================\n" +
                "Full Name : " + firstName.toUpperCase() + " " + lastName.toUpperCase() +
                "\nEmail     : " + email +
                "\n=====================================================";
    }
}
