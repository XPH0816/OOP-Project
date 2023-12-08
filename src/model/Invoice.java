package model;

import enums.RoomStatus;
import helper.Validator;
import util.InvoiceList;
import util.OrderList;
import util.RoomList;

public class Invoice {
    private static int count = 0;
    private int ID;
    private RoomList roomList;
    private OrderList orderList;
    private User user;

    public Invoice(User user, RoomList roomList, OrderList orderList) {
        this.ID = ++count;
        this.roomList = roomList.searchByUserID(user.getID());
        this.orderList = orderList.searchByUserID(user.getID());
        this.user = user;
    }

    public int getID() {
        return this.ID;
    }

    public User getUser() {
        return this.user;
    }

    public double getSubtotalPrice() {
        return this.roomList.stream().mapToDouble((room) -> room.getPrice()).sum() +
                this.orderList.stream().mapToDouble((order) -> order.getTotalPrice()).sum();
    }

    public double getTotalPrice() {
        return this.roomList.stream().mapToDouble((room) -> room.getPrice()).sum()
                + Order.getAllUnPaidOrders(this.orderList).stream().mapToDouble((order) -> order.getTotalPrice()).sum();
    }

    public void pay() {
        System.out.print(this);
        double paid = Validator.getDouble("Enter amount paid: ", this.getTotalPrice(), Double.MAX_VALUE);
        System.out.println(String.format("Change : RM %.2f", paid - this.getTotalPrice()));
        Order.getAllUnPaidOrders(this.orderList).forEach((order) -> order.pay());
        roomList.forEach((room) -> room.setRoomStatus(RoomStatus.Empty));
        Validator.pause();
    }

    public static InvoiceList getAllCheckedOutInvoices(InvoiceList invoices) {
        return invoices.stream().filter(
                (invoice) -> invoice.roomList.stream().allMatch((room) -> room.getRoomStatus() == RoomStatus.Empty))
                .collect(InvoiceList::new, InvoiceList::add, InvoiceList::addAll);
    }

    @Override
    public String toString() {
        String result = "";
        result += String.format("============================================") + "\n";
        result += String.format(" InvoiceID : %d", this.ID) + "\n\n" +
                this.user;
        result += String.format("============================================") + "\n";
        result += String.format(" RoomType %15s %15s", "", "Total Price") + "\n";
        result += String.format("============================================") + "\n";
        for (Room room : this.roomList) {
            result += String.format(" %s %21s RM %8.2f", room.getRoomType(), "", room.getPrice()) + "\n";
        }
        result += String.format("============================================") + "\n";
        result += String.format(" OrderID %16s %15s", "OrderStatus", "Total Price") + "\n";
        result += String.format("============================================") + "\n";
        for (Order order : this.orderList) {
            result += String.format(" %s %22s %3s RM %8.2f", order.getID(), order.getOrderStatus(), "",
                    order.getTotalPrice()) + "\n";
        }
        result += String.format("============================================") + "\n";
        result += String.format(" %-11s: %15s RM %8.2f", "SubTotal", "", this.getSubtotalPrice()) + "\n";
        result += String.format(" %-11s: %15s RM %8.2f", "Paid", "", this.getSubtotalPrice() - this.getTotalPrice()) + "\n";
        result += String.format(" %-11s: %15s RM %8.2f", "Total", "", this.getTotalPrice()) + "\n";
        result += String.format("============================================") + "\n";
        return result;
    }
}
