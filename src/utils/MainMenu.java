package utils;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MainMenu {

    private final HotelResource hotelResource = HotelResource.getInstance();
    private final AdminMenu adminMenu = AdminMenu.getInstance();
    private final ConsoleManager consoleManager = new ConsoleManager();


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

    public Reservation findAndReserveARoom() {
        System.out.println("""
                =====================================================
                Welcome to Reservation Page
                =====================================================
                """);
        LocalDate checkInDate = consoleManager.readCheckInDate();
        if (checkInDate == null) {return null;}
        LocalDate checkOutDate = consoleManager.readCheckOutDate();
        if (checkOutDate == null) {return null;}

        LocalDate today = LocalDate.now();
        if (ChronoUnit.DAYS.between(today, checkInDate) > 365) {
            System.out.println("Sorry!!! We are only accepting reservations for the next 365 days.");
            return null;
        }

        Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        if (availableRooms == null) {
            if (ChronoUnit.DAYS.between(checkInDate, checkOutDate) > 45) {
                return null;
            }
            System.out.println(
                    "We are sorry! No available rooms between " + checkInDate + " - " + checkOutDate + ".\n");
            return null;
        }
        HashMap<String, IRoom> roomMap = new HashMap<>();
        for(IRoom room : availableRooms) {
            System.out.println(room);
            roomMap.put(room.getRoomNumber(), room);
        }

        System.out.println("\n>>> Please enter the room number: ");
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
                if (user_response.equalsIgnoreCase("y")) {
                    System.out.println("Returning to the Main Menu!");
                    return null;
                }
                System.out.println(">>> Please! Enter a valid room number: ");
                roomNumber = consoleManager.readStringInput();
            }
        }

        String customerEmail = this.getUserAccountDetails();
        IRoom room = roomMap.get(roomNumber);
        return hotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
    }

    private String getUserAccountDetails() {
        System.out.println(">>> Do you hava an account (y/n)? ");
        String user_response = consoleManager.getValidInputYesNo();

        if (user_response.equalsIgnoreCase("n")) {
            System.out.println(">>> Would you like to create one (y/n)? ");
            String createAccount = consoleManager.getValidInputYesNo();
            if (createAccount.equalsIgnoreCase("n")) {
                return null;
            }
            this.createAnAccount();
            System.out.println("Returning to booking page!");
        }

        System.out.println(">>> Please! Enter your email address: ");
        return consoleManager.getValidCustomerEmail();
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

    public void createAnAccount() {
        System.out.println(">>> Please! Enter your email address: ");
        String customerEmail = consoleManager.getValidCustomerEmail();

        System.out.println(">>> Please! Enter your first name: ");
        String firstName = consoleManager.getValidCustomerName();

        System.out.println(">>> Please! Enter your last name: ");
        String lastName = consoleManager.getValidCustomerName();

        hotelResource.createACustomer(firstName, lastName, customerEmail);

        System.out.println("Thank you! Your account has been successfully created.");
    }

    public void manageMenuOptions() {
        this.printMenuOptions();
        System.out.println(">>> Please! Enter your choice: ");
        int user_response = consoleManager.getValidMenuInput();
        consoleManager.readStringInput();

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
