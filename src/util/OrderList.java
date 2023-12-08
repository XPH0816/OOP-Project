package util;

import java.util.ArrayList;

import interfaces.Searchable;
import model.Order;

public class OrderList extends ArrayList<Order> implements Searchable<OrderList> {
    @Override
    public OrderList searchByUserID(int id) {
        OrderList result = new OrderList();
        for (Order order : this) {
            if (order.getUserID() == id) {
                result.add(order);
            }
        }
        return result;
    }

    public Order searchByID(int id) {
        for (Order order : this) {
            if (order.getID() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        if(this.size() == 0) return String.format("No order");
        String result = "";
        result += String.format("====================================") + "\n";
        result += String.format(" OrderID %12s %13s", "OrderStatus", "Total Price") + "\n";
        result += String.format("====================================") + "\n";
        for (Order order : this) {
            result += String.format(" %3d %16s %1s RM %8.2f", order.getID(), order.getOrderStatus(), "", order.getTotalPrice()) + "\n";
        }
        result += String.format("====================================") + "\n";
        return result;
    }
}
