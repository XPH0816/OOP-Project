import enums.RoomStatus;
import error.NotAvaliable;
import helper.Menu;
import helper.Validator;
import model.Invoice;
import model.Room;
import model.Staff;
import model.User;
import util.InvoiceList;
import util.OrderList;
import util.RoomList;

public class App {

    static RoomList roomlist = Room.all();
    static OrderList orderlist = new OrderList();
    static InvoiceList invoicelist = Invoice.all();

    public static void main(String[] args) {
        int choice;
        do {
            choice = Menu.MainMenu();
            if (choice == 0)
                break;
            String name = Validator.getString("Enter your name: ");
            int age = Validator.getInt("Enter your age: ", 1, 100);
            switch (choice) {
                case 1:
                    User user = new User(name, age);
                    int userChoice;
                    do {

                        userChoice = Menu.UserMenu(user);
                        try {

                            switch (userChoice) {
                                case 1:
                                    if (roomlist.searchByUserID(user.getID()).size() <= 0)
                                        throw new NotAvaliable("Please select room first");
                                    user.order(orderlist);
                                    break;

                                case 2:
                                    user.selectRoom(roomlist);
                                    break;
                            }

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            Validator.pause();
                        }

                    } while (userChoice != 0);
                    if (roomlist.searchByUserID(user.getID()).size() > 0) {
                        Invoice invoice = new Invoice(user, roomlist, orderlist);
                        invoicelist.add(invoice);
                    }
                    break;

                case 2:
                    Staff staff = new Staff(name, age);
                    int staffChoice;
                    do {
                        staffChoice = Menu.StaffMenu(staff);
                        try {
                            switch (staffChoice) {
                                case 1:
                                    staff.assignRoom(roomlist);
                                    break;

                                case 2:
                                    staff.checkout(roomlist);
                                    break;

                                case 3:
                                    Invoice userInvoice = staff.selectUserInvoice(invoicelist);
                                    if (userInvoice != null)
                                        userInvoice.pay();
                                    break;

                                case 4:
                                    System.out.println(invoicelist);
                                    Validator.pause();
                                    break;

                                case 5:
                                    System.out.println(Room.getRoomsByStatus(RoomStatus.Empty, roomlist));
                                    Validator.pause();
                                    break;
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            Validator.pause();
                        }
                    } while (staffChoice != 0);
                    break;
            }

        } while (choice != 0);
        Invoice.save(invoicelist);
    }
}
