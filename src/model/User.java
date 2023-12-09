package model;

import enums.RoomStatus;
import enums.RoomType;
import error.NotAvaliable;
import helper.Validator;
import util.FoodList;
import util.OrderList;
import util.RoomList;

public class User extends Person {
    static int count = 0;
    private int ID;

    public User(String name, int age) {
        super(name, age);
        this.ID = ++count;
    }

    public int getID() {
        return this.ID;
    }

    public void order(OrderList orders) {
        FoodList foodList = new FoodList();
        Order order;
        int choice;
        char continueChoice, payChoice;

        do {
            choice = helper.Menu.FoodMenu() - 1;
            if (choice == -1)
                return;
            foodList.add(Food.all().get(choice));
            continueChoice = Validator.getChar("Do you want to continue? (Y/N) :", new char[] { 'Y', 'N' });
        } while (continueChoice == 'Y');

        order = new Order(foodList, this.ID);
        payChoice = Validator.getChar("Do you want to pay now? (Y/N) :", new char[] { 'Y', 'N' });
        if (payChoice == 'Y') {
            Validator.clearScreen();
            System.out.println(order);
            double paid = Validator.getDouble("Enter amount paid: ", order.getTotalPrice(), Double.MAX_VALUE);
            System.out.println(String.format("Change : RM %.2f", paid - order.getTotalPrice()));
            order.pay();
            Validator.pause();
        }
        orders.add(order);
    }

    public void selectRoom(RoomList rooms) throws NotAvaliable {
        RoomList emptyRooms = null;
        switch (helper.Menu.RoomMenu()) {
            case 1:
                emptyRooms = Room.getRoomsByBoth(RoomStatus.Empty, RoomType.Single, rooms);
                System.out.println(emptyRooms);
                break;

            case 2:
                emptyRooms = Room.getRoomsByBoth(RoomStatus.Empty, RoomType.Double, rooms);
                System.out.println(emptyRooms);
                break;

            case 3:
                emptyRooms = Room.getRoomsByBoth(RoomStatus.Empty, RoomType.Deluxe, rooms);
                System.out.println(emptyRooms);
                break;
        }
        if (emptyRooms == null || emptyRooms.size() == 0)
            throw new NotAvaliable("No room available");

        int choice = Validator.getInt("Enter your choice :", 1, emptyRooms.size());
        Room selectedRoom = emptyRooms.get(choice - 1);
        selectedRoom.setID(this.ID);
        selectedRoom.setRoomStatus(RoomStatus.Booked);
    }
}
