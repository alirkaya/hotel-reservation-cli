package service;

import model.IRoom;
import model.Reservation;
import model.Customer;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.LocalDate;

public class ReservationService {

    private final HashMap<String, IRoom> rooms = new HashMap<String, IRoom>();
    private final HashMap<String, ArrayList<Reservation>> reservations = new HashMap<String, ArrayList<Reservation>>();


    private static final ReservationService INSTANCE = new ReservationService();

    private ReservationService() {}

    public static ReservationService getInstance() {return INSTANCE;}


    public void addRoom(IRoom room) {
        String roomNumber = room.getRoomNumber();
        if (rooms.containsKey(roomNumber)) {
            System.out.println("Room " + roomNumber + " already exists.");
        }
        else {
            rooms.put(roomNumber, room);
            System.out.println("Room " + roomNumber + " has been successfully added.");
        }
    };

    public IRoom getARoom(String roomId) {
        if (rooms.containsKey(roomId)) {
            return rooms.get(roomId);
        }
        else {
            System.out.println("ERROR: Room " + roomId + " doesn't exists. Please! double-check the room number.");
            return null;
        }
    }

    // https://stackoverflow.com/questions/19064109/java-how-to-sort-custom-type-arraylist
    public Reservation reserveARoom(Customer customer, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate.isBefore(LocalDate.now())) {
            System.out.println("Check-In date cannot be less than today.");
            return null;
        }

        if (checkOutDate.isBefore(checkInDate)) {
            System.out.println("Check-Out date has to be greater than Check-In date.");
            return null;
        }

        if (!rooms.containsKey(room.getRoomNumber())) {
            System.out.println(
                    "ERROR: Room " + room.getRoomNumber() + " doesn't exist. Please! double-check the room number");
            return null;
        }

        Collection<IRoom> availableRooms = this.findRooms(checkInDate, checkOutDate);
        if (availableRooms == null) {
            return null;
        }
        if (!availableRooms.contains(room)) {
            System.out.println("ERROR: Room " + room.getRoomNumber() + " is not available for reservation.");
            return null;
        }

        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        String customerEmail = customer.getEmail();

        if (!reservations.containsKey(customerEmail)) {
            reservations.put(customerEmail, new ArrayList<>());
        }

        reservations.get(customerEmail).add(newReservation);
        // sort the array list to keep the most recent reservations at the top of the list
        ArrayList<Reservation> records = reservations.get(customerEmail);
        if (records.size() > 1) {
            records.sort(new Comparator<Reservation>() {
                @Override
                public int compare(Reservation recordOne, Reservation recordTwo) {
                    int flag1 = recordTwo.getCheckInDate().compareTo(recordOne.getCheckInDate());
                    int flag2 = recordTwo.getCheckOutDate().compareTo(recordOne.getCheckOutDate());
                    int flag3 = recordTwo.getRoom().getRoomPrice().compareTo(
                            recordOne.getRoom().getRoomPrice());

                    if (flag1 == 0) {
                        if (flag2 == 0) {
                            return flag3;
                        }
                        else {
                            return flag2;
                        }
                    }
                    return flag1;
                }
            });
        }
        return newReservation;
    }

    public Collection<IRoom> findRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate.isBefore(LocalDate.now())) {
            System.out.println("ERROR: Check-In date cannot be less than today.");
            return null;
        }

        if (checkOutDate.isBefore(checkInDate)) {
            System.out.println("ERROR: Check-Out date has to be greater than Check-In date.");
            return null;
        }

        if (ChronoUnit.DAYS.between(checkInDate, checkOutDate) > 45) {
            System.out.println("ERROR: Reservations more than 45 days are not allowed.");
            return null;
        }

        if (rooms.isEmpty()) {
            System.out.println("No available rooms between " + checkInDate + " - " + checkOutDate + ".");
            return null;
        }

        if (reservations.isEmpty()) {
            return rooms.values();
        }

        ArrayList<String> bookedRooms = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Reservation>> reservationEntry: reservations.entrySet()) {
            ArrayList<Reservation> customerReservation = reservationEntry.getValue();

            for (Reservation record: customerReservation) {
                if (!record.getCheckOutDate().isBefore(checkInDate)) {
                    bookedRooms.add(record.getRoom().getRoomNumber());
                }
            }
        }

        ArrayList<IRoom> availableRooms = new ArrayList<>();
        for (Map.Entry<String, IRoom> roomEntry: rooms.entrySet()) {
            if (!bookedRooms.contains(roomEntry.getKey())) {
                availableRooms.add(roomEntry.getValue());
            }
        }

        if (availableRooms.size() == 0) {
            System.out.println("No available rooms between " + checkInDate + " - " + checkOutDate + ".");
            return null;
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        String customerEmail = customer.getEmail();
        return reservations.getOrDefault(customerEmail, null);
    }

    public void printAllReservation() {
        ArrayList<Reservation> activeReservations = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Reservation>> reservationEntry: reservations.entrySet()) {
            ArrayList<Reservation> customerReservation = reservationEntry.getValue();

            for (Reservation record: customerReservation) {
                if (!record.getCheckOutDate().isBefore(LocalDate.now())) {
                    activeReservations.add(record);
                }
            }
        }

        int counter = 1;
        for (Reservation record : activeReservations) {
            System.out.println("Reservation No: " + counter);
            System.out.println(record + "\n");
            counter ++;
        }
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }
}
