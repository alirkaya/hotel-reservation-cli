package utils;

import model.RoomType;

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

    public double getValidRoomPrice() {
        System.out.println(">>>Please! Enter the room price (e.g. 79.99): ");
        while (true) {
            try { return Double.parseDouble(scanner.nextLine()); }
            catch (NumberFormatException e) {
                System.out.println("ERROR: Please! Enter room price (e.g. 79.99) : ");
            }
        }
    }

    public RoomType getValidRoomType() {
        System.out.println(">>> Please! Enter the type of the room (1. Single 2. Double): ");
        String roomType = "";
        while (true) {
            roomType = scanner.nextLine().trim();
            if (roomType.equals("1") || roomType.equals("2")) {
                return (roomType.equals("1")) ? RoomType.SINGLE : RoomType.DOUBLE;
            }
            System.out.println("ERROR: Invalid input. Please! Enter 1 or 2: ");
        }
    }

    public boolean wantToGenerateFreeRoom() {
        System.out.println(">>> Is this a free room (y/n)? ");
        String isFreeRoom = this.getValidInputYesNo();
        return isFreeRoom.equalsIgnoreCase("y");
    }

    public String getValidRoomNumber() {
        System.out.println(">>> Please! Enter a room number (e.g. 101): ");
        int roomNumber = this.getValidIntegerInput();
        while (roomNumber < 0) {
            System.out.println("ERROR: Room number cannot be negative.");
            roomNumber = this.getValidIntegerInput();
        }
        return Integer.toString(roomNumber);
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
}
