package utils;

import api.AdminResource;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class AdminMenu {

    private final AdminResource adminResource = AdminResource.getInstance();
    private final ConsoleManager consoleManager = new ConsoleManager();
    private final TestData testData = new TestData();


    private static final AdminMenu INSTANCE = new AdminMenu();
    private AdminMenu() {}
    public static AdminMenu getInstance() {return INSTANCE;}


    public void printMenuOptions() {
        System.out.println(
                """
                =====================================================
                Admin Menu:
                1. See all Customers
                2. See all Rooms
                3. See all Reservations
                4. Add a Room
                5. Add Test Data
                6. Back to Main Menu
                =====================================================
                """
        );
    }

    public void seeAllCustomers() {
        Collection<Customer> allCustomers = adminResource.getAllCustomers();
        if (allCustomers.isEmpty()) { System.out.println("Currently, there are no customers in the system!"); }
        for(Customer customer : allCustomers) {
            System.out.println(customer);
        }
    }

    public void seeAllRooms() {
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        if (allRooms.size() == 0 ) {
            System.out.println("No rooms to show.");
            return;
        }
        for(IRoom room : allRooms) {
            System.out.println(room);
        }
    }

    public void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    public void addARoom() {
        ArrayList<IRoom> rooms = new ArrayList<>();
        ArrayList<String> roomNumbers = new ArrayList<>();

        String userResponse = "y";
        while (userResponse.equalsIgnoreCase("y")) {
            String roomNumber = consoleManager.getValidRoomNumber();

            if (roomNumbers.contains(roomNumber)) {
                System.out.println("Room " + roomNumber + " already exists.");
                break;
            }
            roomNumbers.add(roomNumber);

            RoomType enumeration = consoleManager.getValidRoomType();
            if (consoleManager.wantToGenerateFreeRoom()) { rooms.add(new FreeRoom(roomNumber, enumeration)); }
            else {
                Double roomPrice = consoleManager.getValidRoomPrice();
                rooms.add(new Room(roomNumber, roomPrice, enumeration));
            }

            System.out.println(">>> Would you like to add another room (y/n)?");
            userResponse = consoleManager.getValidInputYesNo();
        }

        adminResource.addRoom(rooms);
    }

    public void manageMenuOptions() {
        this.printMenuOptions();
        int minValue = 1;
        int maxValue = 6;

        int user_response = consoleManager.getValidMenuInput(minValue, maxValue);

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
                testData.createTestData();
                System.out.println("\nRedirecting to the Main Menu ...\n");
                break;

            case 6:
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
