import utils.ConsoleManager;
import utils.MainMenu;

import java.util.Scanner;


public class HotelReservation {

    public static void main(String[] args) {
        MainMenu mainMenu = MainMenu.getInstance();

        while (true) {
            mainMenu.manageMenuOptions();
        }

    }
}
