package utils;

import model.*;
import service.CustomerService;
import service.ReservationService;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestData {


    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();


    public void createTestData() {
        // Create three customers

        Customer testCustomer1 = new Customer("Test1", "Customer", "test1@customer.com");
        Customer testCustomer2 = new Customer("Test2", "Customer", "test2@customer.com");
        Customer testCustomer3 = new Customer("Test3", "Customer", "test3@customer.com");
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(testCustomer1);
        customers.add(testCustomer2);
        customers.add(testCustomer3);

        for(Customer customer : customers) {
            customerService.addCustomer(customer.firstName, customer.lastName, customer.email);
        }

        // Create three rooms

        IRoom testRoom1 = new Room("Test1", 999.99, RoomType.SINGLE);
        IRoom testRoom2 = new Room("Test2", 999.99, RoomType.SINGLE);
        IRoom testRoom3 = new Room("Test3", 999.99, RoomType.SINGLE);
        ArrayList<IRoom> rooms = new ArrayList<IRoom>();
        rooms.add(testRoom1);
        rooms.add(testRoom2);
        rooms.add(testRoom3);

        for(IRoom room : rooms) {
            reservationService.addRoom(room);
        }

        // create three reservations

        reservationService.reserveARoom(
                testCustomer1, testRoom1,
                LocalDate.of(2023, 10, 15),
                LocalDate.of(2023, 10, 18)
                );

        reservationService.reserveARoom(
                testCustomer1, testRoom3,
                LocalDate.of(2023, 12, 10),
                LocalDate.of(2023, 12, 18)
                );

        reservationService.reserveARoom(
                testCustomer2, testRoom2,
                LocalDate.of(2023, 6, 6),
                LocalDate.of(2023, 6, 9)
                );

    }
}
