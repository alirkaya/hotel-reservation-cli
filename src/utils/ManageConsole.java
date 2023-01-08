package utils;

import model.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ManageConsole {

    Scanner scanner = new Scanner(System.in);

    public int readIntegerInput() {
        return scanner.nextInt();
    }

    public LocalDate readCheckInDate() {
        System.out.print(">>> Please! Enter check-in date (MM/DD/YYYY): ");
        String date_string = scanner.next();
        return this.parseDateInput(date_string);
    }

    public LocalDate readCheckOutDate() {
        System.out.print(">>> Please! Enter check-in date (MM/DD/YYYY): ");
        String date_string = scanner.next();
        return this.parseDateInput(date_string);
    }

    private LocalDate parseDateInput(String date_string) {
        try {
            return LocalDate.parse(date_string, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        }
        catch (Exception e) {
            System.out.println("ERROR: Invalid date.");
            return null;
        }
    }

    public String readStringInput() {
        return scanner.nextLine();
    }

    private boolean isValidInputYesNo(String user_response) {
        return user_response.equalsIgnoreCase("y") || user_response.equalsIgnoreCase("n");
    }

    public String getValidInputYesNo() {
        String user_response = this.readStringInput();
        while (!this.isValidInputYesNo(user_response)) {
            System.out.println("ERROR: Invalid input. Please! Enter 'y' or 'n': ");
            user_response = this.readStringInput();
        }
        return user_response;
    }

    public String getValidCustomerName() {
        String name = this.readStringInput();
        while (name.isEmpty() || name.isBlank()) {
            System.out.println("ERROR: Invalid first name.");
            System.out.println(">>> Please! Enter a valid first name: ");
            name = this.readStringInput();
        }
        return name;
    }

    public String getValidCustomerEmail() {
        String customerEmail = this.readStringInput();
        if (Customer.checkEmail(customerEmail)) {
            return customerEmail;
        }
        return null;
    }

    public Double readDoubleInput() {
        return scanner.nextDouble();
    }

    public int getValidRoomType() {
        int roomType = this.readIntegerInput();
        while (!(roomType == 1 || roomType == 2)) {
            System.out.println("ERROR: Invalid input. Please! Enter 1 or 2.");
            roomType = this.readIntegerInput();
        }
        return roomType;
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
}
