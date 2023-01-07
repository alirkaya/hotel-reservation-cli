import model.*;
import service.ReservationService;
import utils.ManageConsole;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.awt.*;

public class HotelReservation {



    public static void main(String[] args) {

        ManageConsole consoleManager = new ManageConsole();

        String mainMenu = """
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
                """;

        String adminMenu = """
                =====================================================
                Admin Menu:
                1. See all customers
                2. See all Rooms
                3. See all Reservations
                4. Add a room
                5. Back to Main Menu
                =====================================================
                """;

        System.out.println(mainMenu);
        System.out.println(adminMenu);
    }
}
