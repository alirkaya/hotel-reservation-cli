package model;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber, 0.0, enumeration);
    }

    @Override
    public String toString() {
        return "\n===================================\n" +
                "Room Info:\n" +
                "===================================\n" +
                "Room No    : " + roomNumber +
                "\nRoom Price : " + "US$" + String.format("%.2f", roomPrice) + " (Free Room)" +
                "\nRoom Type  : " + roomType +
                "\n===================================";
    }
}
