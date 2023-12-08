package helper;

import model.Food;
import model.Staff;
import model.User;

public class Menu {
    public static int MainMenu() {
        Validator.clearScreen();
        System.out.println("====================================");
        System.out.println("Welcome to the Hotel Management System");
        System.out.println("====================================");
        System.out.println("1. User Booking");
        System.out.println("2. Staff Login");
        System.out.println("0. Exit");
        System.out.println("====================================");
        return Validator.getInt("Enter your choice: ", 0, 2);
    }

    public static int RoomMenu() {
        Validator.clearScreen();
        System.out.println("====================================");
        System.out.println("Please select the room type");
        System.out.println("====================================");
        System.out.println("1. Single Room");
        System.out.println("2. Double Room");
        System.out.println("3. Deluxe Room");
        System.out.println("0. Back");
        System.out.println("====================================");
        return Validator.getInt("Enter your choice: ", 0, 3);
    }

    public static int FoodMenu() {
        Validator.clearScreen();
        System.out.println("====================================");
        System.out.println("Please select the food or drink");
        System.out.println("====================================");
        System.out.print(Food.all());
        System.out.println("  0 Back");
        System.out.println("====================================");
        return Validator.getInt("Enter your choice: ", 0, Food.all().size());
    }

    public static int StaffMenu(Staff staff) {
        Validator.clearScreen();
        if (staff == null)
            return 0;
        System.out.println("====================================");
        System.out.println("Please login to continue");
        System.out.println("====================================");
        String username = Validator.getString("Enter your username: ");
        String password = Validator.getString("Enter your password: ");
        if (!staff.login(username, password)){
            System.out.println("Invalid username or password");
            Validator.pause();
            return 0;
        }
        Validator.clearScreen();
        System.out.println("====================================");
        System.out.println(
                "Hello! " + staff.getName() + " (" + staff.getAge() + ")");
        System.out.println("Please select the staff function");
        System.out.println("====================================");
        System.out.println("1. Assign Room");
        System.out.println("2. Check Out");
        System.out.println("3. Check Invoice");
        System.err.println("4. Check Room Availability");
        System.out.println("0. Back");
        System.out.println("====================================");
        return Validator.getInt("Enter your choice: ", 0, 4);
    }

    public static int UserMenu(User user) {
        Validator.clearScreen();
        System.out.println("====================================");
        System.out.println("Hello! " + user.getName() + " (" + user.getAge() + ")");
        System.out.println("Please select the user function");
        System.out.println("====================================");
        System.out.println("1. Order Food");
        System.out.println("2. Select Room");
        System.out.println("0. Back");
        System.out.println("====================================");
        return Validator.getInt("Enter your choice: ", 0, 2);
    }
}
