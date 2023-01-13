package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleManager {

    Scanner scanner = new Scanner(System.in);

    public int getValidIntegerInput() {
        String errorMessage = "ERROR: Invalid input. Please! Enter a numeric value: ";
        while (true) {
            try { return Integer.parseInt(scanner.nextLine()); }
            catch (NumberFormatException exception) { System.out.println(errorMessage); }
        }
    }

    public int getValidMenuInput(int minValue, int maxValue) {
        System.out.println(">>> Please! Enter your choice (1-5): ");
        while (true) {
            int user_response = this.getValidIntegerInput();
            if (user_response >= minValue && user_response <= maxValue) {
                return user_response;
            } else {
                System.out.println("ERROR: Invalid option. Please! Select a menu option between 1-5: ");
            }
        }
    }

    public LocalDate readDate() {
        while (true) {
            try { return LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("MM/dd/yyyy")); }
            catch (DateTimeParseException exception) {
                System.out.println("ERROR: Please! Enter a date (MM/DD/YYYY): ");
            }
        }
    }

    public LocalDate readCheckInDate() {
        System.out.println(">>> Please! Enter the check-in date (MM/DD/YYYY): ");
        return this.readDate();
    }

    public LocalDate readCheckOutDate() {
        System.out.println(">>> Please! Enter the check-out date (MM/DD/YYYY): ");
        return this.readDate();
    }

    public String readStringInput() {
        return scanner.nextLine();
    }

    private boolean isValidInputYesNo(String user_response) {
        return user_response.equalsIgnoreCase("y") || user_response.equalsIgnoreCase("n");
    }

    public String getValidInputYesNo() {
        String user_response = this.readStringInput().trim();
        while (!this.isValidInputYesNo(user_response)) {
            System.out.println("ERROR: Invalid input. Please! Enter 'y' or 'n': ");
            user_response = this.readStringInput();
        }
        return user_response;
    }

    public String getValidCustomerName(String kind) {
        String name = this.readStringInput().trim();
        while (name.isEmpty() || name.isBlank()) {
            System.out.println("ERROR: Invalid " + kind + " name.");
            System.out.println(">>> Please! Enter a valid " + kind + " name: ");
            name = this.readStringInput();
        }
        return name.toUpperCase();
    }

    public String getValidCustomerEmail() {
        String customerEmail = this.readStringInput().trim();
        while (true) {
            if (customerEmail.matches("[\\w._]+@[\\w]+\\.com")) {
                return customerEmail.toLowerCase();
            }
            System.out.println(
                    "Not a valid email! Please use name@domain.com format. Only '.' and '_' are allowed in name.");
            customerEmail = this.readStringInput();
        }
    }

    public Double readDoubleInput() {
        while (true) {
            try { return scanner.nextDouble(); }
            catch (Exception e) {
                System.out.println("ERROR: Please! Enter room price (e.g. 150.99) : " + scanner.next());
            }
        }
    }

    public String getValidRoomType() {
        String roomType = "";
        while (true) {
            roomType = scanner.nextLine().trim();
            if (roomType.equals("1") || roomType.equals("2")) { return roomType; }
            System.out.println("ERROR: Invalid input. Please! Enter 1 or 2: ");
        }
    }

    public String getValidRoomNumber() {
        while (true) {
            try { return String.valueOf(scanner.nextInt()); }
            catch (InputMismatchException exception) {
                System.out.println("ERROR: Invalid option. Please! Enter a number: " + scanner.next());
            }
        }
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
}
