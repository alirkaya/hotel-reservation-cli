package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.List;
import java.util.Collection;


public class AdminResource {

    private final CustomerService customerService = CustomerService.getInstance();

    private final ReservationService reservationService = ReservationService.getInstance();


    private AdminResource() {}

    private static final AdminResource INSTANCE = new AdminResource();

    public AdminResource getInstance() {return INSTANCE;}


    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }

}
