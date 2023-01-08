package utils;

import api.AdminResource;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class AdminMenu {

    private final AdminResource adminResource = AdminResource.getInstance();
    private final ManageConsole manager = new ManageConsole();


    private static final AdminMenu INSTANCE = new AdminMenu();
    private AdminMenu() {}
    public static AdminMenu getInstance() {return INSTANCE;}


    public void printMenuOptions() {
        System.out.println(
                """
                =====================================================
                Admin Menu:
                1. See all customers
                2. See all Rooms
                3. See all Reservations
                4. Add a room
                5. Back to Main Menu
                =====================================================
                """
        );
    }

    public void seeAllCustomers() {
        Collection<Customer> allCustomers = adminResource.getAllCustomers();
        if (allCustomers.isEmpty()) {
            System.out.println("Currently, there are no customers in the system!");
        }
        for(Customer customer : allCustomers) {
            System.out.println(customer);
        }
    }

    public void seeAllRooms() {
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        for(IRoom room : allRooms) {
            System.out.println(room);
        }
    }

    public void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    public void addARoom() {
        ArrayList<IRoom> rooms = new ArrayList<>();
        String userResponse = "y";
        manager.readStringInput();

        while (userResponse.equalsIgnoreCase("y")) {
            System.out.println(">>> Please! Enter room number: ");
            String roomNumber = manager.readStringInput();

            System.out.println(">>> Please! Enter the type of the room (1. Single 2. Double): ");
            int roomType = manager.getValidRoomType();
            RoomType enumeration = (roomType == 1) ? RoomType.SINGLE : RoomType.DOUBLE;
            manager.readStringInput();

            System.out.println(">>> Is this a free room (y/n)? ");
            String isFreeRoom = manager.getValidInputYesNo();
            if (isFreeRoom.equalsIgnoreCase("y")) {
                rooms.add(new FreeRoom(roomNumber, enumeration));
            }
            else {
                System.out.println(">>>Please! Enter the room price: ");
                Double roomPrice = manager.readDoubleInput();
                rooms.add(new Room(roomNumber, roomPrice, enumeration));
                manager.readStringInput();
            }

            System.out.println(">>> Would you like to add another room (y/n)?");
            userResponse = manager.getValidInputYesNo();
        }

        adminResource.addRoom(rooms);
    }

    private int getValidUserInput() {
        int user_response = manager.readIntegerInput();
        while (true) {
            if (user_response >= 1 && user_response <= 5) {
                break;
            }
            else {
                System.out.println("ERROR: Invalid option. Please! Enter a valid option (1-5): ");
                user_response = manager.readIntegerInput();
            }
        }
        return user_response;
    }

    public void manageMenuOptions() {
        this.printMenuOptions();
        System.out.println(">>> Please! Enter your choice: ");
        int user_response = this.getValidUserInput();

        switch (user_response) {
            case 1:
                this.seeAllCustomers();
                System.out.println("\nRedirecting to the Main Menu ...\n");
                break;

            case 2:
                this.seeAllRooms();
                System.out.println("\nRedirecting to the Main Menu ...\n");
                break;

            case 3:
                this.seeAllReservations();
                System.out.println("\nRedirecting to the Main Menu ...\n");
                break;

            case 4:
                this.addARoom();
                System.out.println("\nRedirecting to the Main Menu ...\n");
                break;

            case 5:
                System.out.println("\nRedirecting to the Main Menu ...\n");
                break;
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
