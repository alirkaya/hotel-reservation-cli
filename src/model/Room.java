package model;

import java.util.Objects;

public class Room implements IRoom {

    public final String roomNumber;
    public final Double roomPrice;
    public final RoomType roomType;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.roomPrice = price;
        this.roomType = enumeration;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public boolean isFree() {
        return roomPrice == 0.0;
    }

    @Override
    public String toString() {
        return "\n===================================\n" +
                "Room Info:\n" +
                "===================================\n" +
                "Room No    : " + roomNumber +
                "\nRoom Price : " + "US$" + String.format("%.2f", roomPrice) +
                "\nRoom Type  : " + roomType +
                "\n===================================";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Room room)) return false;

        return Objects.equals(roomNumber, room.roomNumber) &&
                Objects.equals(roomPrice, room.roomPrice) &&
                roomType == room.roomType;
    }

    @Override
    public int hashCode() { return Objects.hash(roomNumber, roomPrice, roomType); }

}
