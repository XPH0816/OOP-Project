import enums.RoomStatus;
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
    static InvoiceList invoicelist = new InvoiceList();

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
                        switch (userChoice) {
                            case 1:
                                user.order(orderlist);
                                break;

                            case 2:
                                user.selectRoom(roomlist);
                                break;
                        }
                    } while (userChoice != 0);
                    Invoice invoice = new Invoice(user, roomlist, orderlist);
                    invoicelist.add(invoice);
                    break;

                case 2:
                    Staff staff = new Staff(name, age);
                    int staffChoice;
                    do {
                        staffChoice = Menu.StaffMenu(staff);
                        switch (staffChoice) {
                            case 1:
                                staff.assignRoom(roomlist);
                                break;

                            case 2:
                                staff.checkout(roomlist);
                                break;

                            case 3:
                                try {
                                    Invoice userInvoice = staff.selectUserInvoice(invoicelist);
                                    if (userInvoice != null)
                                        userInvoice.pay();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;

                            case 4:
                                System.out.println(Room.getRoomsByStatus(RoomStatus.Empty, roomlist));
                                break;
                        }
                    } while (staffChoice != 0);
                    break;
            }
        } while (choice != 0);
    }
}
