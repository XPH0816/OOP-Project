package model;

import enums.RoomStatus;
import error.NotAvaliable;
import helper.Validator;
import io.github.cdimascio.dotenv.Dotenv;
import util.InvoiceList;
import util.RoomList;

public class Staff extends Person {
    private String username;
    private String password;

    public Staff(String name, int age) {
        super(name, age);
        this.username = Dotenv.load().get("STAFF_USERNAME", "staff");
        this.password = Dotenv.load().get("STAFF_PASSWORD", "123456");
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void assignRoom(Room room, User user) {
        room.setID(user.getID());
        room.setRoomStatus(RoomStatus.Full);
    }

    public Invoice selectUserInvoice(InvoiceList invoiceList) throws NotAvaliable {

        if (Invoice.getAllCheckedOutInvoices(invoiceList).size() == 0)
            throw new NotAvaliable("No user has checked out");

        System.out.println(invoiceList);
        int userID = Validator.getInt("Enter your UserID: ", 1, Integer.MAX_VALUE);
        InvoiceList userInvoiceList = invoiceList.searchByUserID(userID);
        System.out.println(userInvoiceList);
        int choice = helper.Validator.getInt("Enter your InvoiceID: ", 1, Integer.MAX_VALUE);
        return userInvoiceList.searchByID(choice);
    }

    public void assignRoom(RoomList rooms) throws NotAvaliable {
        RoomList bookedRooms = Room.getRoomsByStatus(RoomStatus.Booked, rooms);
        System.out.println(bookedRooms);

        if (bookedRooms.size() == 0)
            throw new NotAvaliable("No room is booked");

        int choice = Validator.getInt("Enter your choice :", 1, bookedRooms.size());
        Room selectedRoom = bookedRooms.get(choice - 1);
        int userID = selectedRoom.getID();
        char confirm = Validator.getChar("Do you want to assign this room to user " + userID + "? (Y/N) :",
                new char[] { 'Y', 'N' });

        if (confirm == 'N') {
            RoomList emptyRooms = Room.getRoomsByBoth(RoomStatus.Empty, selectedRoom.getRoomType(), rooms);
            System.out.println(emptyRooms);

            if (emptyRooms.size() == 0)
                throw new NotAvaliable("No room available");

            choice = Validator.getInt("Enter your choice :", 1, emptyRooms.size());
            selectedRoom.setRoomStatus(RoomStatus.Empty);
            selectedRoom = emptyRooms.get(choice - 1);
            selectedRoom.setID(userID);
        }

        selectedRoom.setRoomStatus(RoomStatus.Full);
    }

    public void checkout(RoomList rooms) throws NotAvaliable {
        RoomList fullRooms = Room.getRoomsByStatus(RoomStatus.Full, rooms);
        System.out.println(fullRooms);

        if (fullRooms.size() == 0)
            throw new NotAvaliable("No room is occupied");

        int choice = Validator.getInt("Enter your choice :", 1, fullRooms.size());
        Room selectedRoom = fullRooms.get(choice - 1);
        selectedRoom.setRoomStatus(RoomStatus.Empty);
        selectedRoom.setID(-1);
    }
}
