package utils;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MainMenu {

    private final HotelResource hotelResource = HotelResource.getInstance();
    private final ConsoleManager consoleManager = new ConsoleManager();
    private final AdminResource adminResource = AdminResource.getInstance();
    private final AdminMenu adminMenu = AdminMenu.getInstance();

    private static final MainMenu INSTANCE = new MainMenu();

    private MainMenu() {}

    public static MainMenu getInstance() {return INSTANCE;}


    public void printMenuOptions () {
        System.out.println(
                """
                        =====================================================
                        Welcome to Hotel Reservation Application
                        =====================================================
                        Main Menu:
                        1. Find and reserve a room
                        2. See my reservations
                        3. Create an account
                        4. Admin
                        5. Exit
                        =====================================================
                        """
        );
    }

    private boolean isValidCheckInOutDays(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) { return false; }

        LocalDate currentDate = LocalDate.now();
        if (checkInDate.isBefore(currentDate)) {
            System.out.println("ERROR: Check-In date cannot be less than the current date (" + currentDate + ").");
            return false;
        }

        if (checkOutDate.isBefore(checkInDate) || checkInDate.equals(checkOutDate)) {
            System.out.println("ERROR: Check-Out date has to be greater than Check-In date.");
            return false;
        }

        if (ChronoUnit.DAYS.between(checkInDate, checkOutDate) > 45) {
            System.out.println("ERROR: Reservations more than 45 days are not allowed.");
            return false;
        }

        if (ChronoUnit.DAYS.between(currentDate, checkInDate) > 365) {
            System.out.println("Sorry!!! We are only accepting reservations for the next 365 days.");
            return false;
        }

        return true;
    }

    private Collection<IRoom> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        while (availableRooms.size() == 0) {
            System.out.println("No available rooms between " + checkInDate + " - " + checkOutDate + "\n");
            System.out.println(">>> Would you like to check alternative dates (y/n)? ");
            String user_response = consoleManager.getValidInputYesNo();
            if (user_response.equals("n")) {
                return null;
            }

            System.out.println(">>> How many days out would you like to check (e.g. 7)? ");
            int daysOut = consoleManager.getValidIntegerInput();
            checkInDate = checkInDate.plusDays(daysOut);
            checkOutDate = checkOutDate.plusDays(daysOut);
            availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        }

        return availableRooms;
    }

    public Reservation findAndReserveARoom() {
        System.out.println("""
                =====================================================
                Welcome to Reservation Page
                =====================================================
                """);

        String customerEmail = this.getUserAccountDetails();
        if (customerEmail == null) { return null; }

        LocalDate checkInDate = consoleManager.readCheckInDate();
        LocalDate checkOutDate = consoleManager.readCheckOutDate();
        boolean isValidDates = this.isValidCheckInOutDays(checkInDate, checkOutDate);
        if (!isValidDates) { return null; }

        Collection<IRoom> availableRooms = this.getAvailableRooms(checkInDate, checkOutDate);
        if (availableRooms == null) { return null; }

        HashMap<String, IRoom> roomMap = new HashMap<>();
        for(IRoom room : availableRooms) {
            System.out.println(room);
            roomMap.put(room.getRoomNumber(), room);
        }

        System.out.println("\n>>> Please enter the room number: "); //TODO validate room number input
        consoleManager.readStringInput();
        String roomNumber = consoleManager.readStringInput();
        while (true) {
            if (roomMap.containsKey(roomNumber)) {
                break;
            }
            else {
                System.out.println("ERROR: Invalid room number (" + roomNumber + ").");
                System.out.println(">>> Would you like to cancel the transaction (y/n): ");
                String user_response = consoleManager.getValidInputYesNo();
                if (user_response.equals("y")) {
                    System.out.println("Returning to the Main Menu!");
                    return null;
                }
                System.out.println(">>> Please! Enter a valid room number: ");
                roomNumber = consoleManager.readStringInput(); //TODO validate room number as integer
            }
        }

        IRoom room = roomMap.get(roomNumber);
        return hotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
    }

    private String getUserAccountDetails() {
        System.out.println(">>> Do you hava an account (y/n)? ");
        String user_response = consoleManager.getValidInputYesNo();

        if (user_response.equals("y")) {
            System.out.println(">>> Please! Enter your email address: ");
            String customerEmail = consoleManager.getValidCustomerEmail();

            Customer userAccount = adminResource.getCustomer(customerEmail);
            if (userAccount == null) {
                System.out.println("No such account exists. Please! Create an account.");
                return null;
            }
            else {return userAccount.getEmail();}
        }

        System.out.println(">>> Would you like to create one (y/n)? ");
        String createAccount = consoleManager.getValidInputYesNo();
        if (createAccount.equals("n")) {
            return null;
        }

        return this.createAnAccount();
    }

    public void seeMyReservations() {
        System.out.println(">>> Please! Enter your email address: ");
        String customerEmail = consoleManager.getValidCustomerEmail();

        Collection<Reservation> customerReservations = hotelResource.getCustomerReservations(customerEmail);
        if (customerReservations == null) {
            System.out.println("No reservation history found.");
        }
        else {
            for (Reservation record : customerReservations) {
                System.out.println(record);
            }
        }
    }

    public String createAnAccount() {
        System.out.println(">>> Please! Enter your email address: ");
        String customerEmail = consoleManager.getValidCustomerEmail();

        System.out.println(">>> Please! Enter your first name: ");
        String firstName = consoleManager.getValidCustomerName("first");

        System.out.println(">>> Please! Enter your last name: ");
        String lastName = consoleManager.getValidCustomerName("last");

        hotelResource.createACustomer(firstName, lastName, customerEmail);

        System.out.println("Thank you! Your account has been successfully created.\n");
        return customerEmail;
    }

    public void manageMenuOptions() {
        this.printMenuOptions();
        int minValue = 1;
        int maxValue = 5;
        int user_response = consoleManager.getValidMenuInput(minValue, maxValue);

        switch (user_response) {
            case 1 :
                Reservation record = this.findAndReserveARoom();
                if (record != null) {
                    System.out.println(record);
                }
                System.out.println("\nRedirecting to the Main Menu ...\n");
                break;
            case 2 :
                this.seeMyReservations();
                System.out.println("\nRedirecting to the Main Menu ...\n");
                break;
            case 3 :
                this.createAnAccount();
                System.out.println("\nRedirecting to the Main Menu ...\n");
                break;
            case 4 :
                adminMenu.manageMenuOptions();
                break;
            case 5 :
                System.exit(0);
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
