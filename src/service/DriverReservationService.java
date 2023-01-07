package service;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class DriverReservationService {


    public static void main(String[] args) {

        ReservationService office = ReservationService.getInstance();

        IRoom room101 = new Room("101", 159.99, RoomType.SINGLE);
        IRoom room102 = new Room("102", 159.99, RoomType.SINGLE);
        IRoom room201 = new Room("201", 219.99, RoomType.DOUBLE);
        IRoom room202 = new Room("202", 299.99, RoomType.DOUBLE);


        office.addRoom(room101);
//        office.addRoom(room102);
//        office.addRoom(room201);
//        office.addRoom(room202);

        // doesn't accept arguments since check-in date is less than current date
        office.findRooms(
                LocalDate.of(2023, 1, 1), LocalDate.of (2023, 1, 5)
        );

        // check-out cannot be less than check-in
        office.findRooms(
                LocalDate.of(2023, 1, 10), LocalDate.of(2023, 1, 9)
        );

        // no reservation beyond 45 days
        office.findRooms(
                LocalDate.of(2023, 1, 10), LocalDate.of(2023, 5, 1)
        );

        Collection<IRoom> availableRooms = office.findRooms(
                LocalDate.of(2023, 1, 10), LocalDate.of(2023, 1, 15)
        );


//        System.out.println(office.getARoom("101"));
//        IRoom room2 = office.getARoom("301");


        Reservation record1 = office.reserveARoom(
                new Customer("Brad", "Pitt", "brad@pitt.com"),
                room101,
                LocalDate.of(2023, 1, 15),
                LocalDate.of(2023, 1, 17)
        );

        Reservation record2 = office.reserveARoom(
                new Customer("Brad", "Pitt", "brad@pitt.com"),
                room101,
                LocalDate.of(2023, 1, 25),
                LocalDate.of(2023, 1, 31)
        );

//        Reservation record3 = office.reserveARoom(
//                new Customer("Brad", "Pitt", "brad@pitt.com"),
//                room202,
//                LocalDate.of(2023, 1, 15),
//                LocalDate.of(2023, 1, 17)
//        );


        //        System.out.println(record1);

//        System.out.println(
//                office.findRooms(
//                LocalDate.of(2023, 1, 15), LocalDate.of(2023, 2, 1)
//                ));

        office.printAllReservation();
    }
}
