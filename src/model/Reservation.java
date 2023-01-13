package model;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final long durationOfStay;

    public Reservation(Customer customer, IRoom room, LocalDate checkInDate, LocalDate checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.durationOfStay = this.calculateDurationOfStay(checkInDate, checkOutDate);
        if (this.durationOfStay <= 0) {
            throw new IllegalArgumentException(
                    "Check-out date has to be later than the check-in date.");
        }
    }

    public Customer getCustomer() {return customer;}

    public IRoom getRoom() {return room;}

    public LocalDate getCheckInDate() {return checkInDate;}

    public LocalDate getCheckOutDate() {return checkOutDate;}

    public long getDurationOfStay() {return durationOfStay;}
    private long calculateDurationOfStay(LocalDate checkInDate, LocalDate checkOutDate) {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
    @Override
    public String toString() {
        String string_price = "US$" + String.format("%.2f", room.getRoomPrice());
        return "=====================================================\n" +
                "Reservation Info:\n" +
                "=====================================================\n" +
                "Customer       : " + customer.getName() +
                "\nRoom Number    : " + room.getRoomNumber() + " (" + room.getRoomType() + " - " +
                    string_price + "/night)" +
                "\nCheck-In Date  : " + checkInDate +
                "\nCheck-Out Date : " + checkOutDate +
                "\nTotal Cost     : " + "US$" + String.format("%.2f", durationOfStay * room.getRoomPrice()) +
                " (" + string_price + " x " + durationOfStay + " nights)" +
                "\n=====================================================";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Reservation reservation)) return false;

        return Objects.equals(customer, reservation.customer) &&
                Objects.equals(room, reservation.room) &&
                Objects.equals(checkInDate, reservation.checkInDate) &&
                Objects.equals(checkOutDate, reservation.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }
}
