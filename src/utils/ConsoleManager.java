package utils;

import model.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleManager {

    Scanner scanner = new Scanner(System.in);

    public int getValidMenuInput() {
        int user_response = 0;
        while (true) {
            try {
                user_response = scanner.nextInt();
            }
            catch (InputMismatchException exception) {
                System.out.println(
                        "ERROR: Invalid option. Please! Enter a numeric value between 1-5: " + scanner.next());
            }

            if (user_response >= 1 && user_response <= 5) {
                return user_response;
            }
        }
    }

    public LocalDate readCheckInDate() {
        while (true) {
            try {
                System.out.print(">>> Please! Enter check-in date (MM/DD/YYYY): ");
                String date_string = scanner.next();
                return LocalDate.parse(date_string, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            } catch (Exception e) {
                System.out.println("ERROR: Please! Enter a date (MM/DD/YYYY): ");
            }
        }
    }

    public LocalDate readCheckOutDate() {
        while (true) {
            try {
                System.out.print(">>> Please! Enter check-out date (MM/DD/YYYY): ");
                String date_string = scanner.next();
                return LocalDate.parse(date_string, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            }
            catch (Exception e) {
                System.out.println("ERROR: Please! Enter a date (MM/DD/YYYY): ");
            }
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
        while (true) {
            if (customerEmail.matches("[\\w._]+@[\\w]+\\.com")) {
                return customerEmail;
            }
            System.out.println(
                    "Not a valid email! Please use name@domain.com format. Only '.' and '_' are allowed in name.");
            customerEmail = this.readStringInput();
        }
    }

    public Double readDoubleInput() {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (Exception e) {
                System.out.println("ERROR: Please! Enter room price (e.g. 150.99) : " + scanner.next());
            }
        }
    }

    public String getValidRoomType() {
        String roomType = "";
        while (true) {
            roomType = scanner.next();

            if (roomType.equals("1") || roomType.equals("2")) {
                return roomType;
            }

            System.out.println("ERROR: Invalid input. Please! Enter 1 or 2: ");
        }
    }

    public String getValidRoomNumber() {
        while (true) {
            try {
                return String.valueOf(scanner.nextInt());
            } catch (InputMismatchException exception) {
                System.out.println(
                        "ERROR: Invalid option. Please! Enter a stupid number: " + scanner.next());
            }
        }
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
}
