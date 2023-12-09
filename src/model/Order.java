package model;

import enums.OrderStatus;
import util.FoodList;
import util.OrderList;

import java.util.stream.Collectors;

public class Order implements java.io.Serializable{
    static int count = 0;
    private int ID;
    private FoodList foodList;
    private OrderStatus orderStatus;
    private int user_id;

    public Order(FoodList foodList, int uid) {
        this.user_id = uid;
        this.ID = ++count;
        this.orderStatus = OrderStatus.Ordered;
        this.foodList = foodList;
    }

    public void pay() {
        this.orderStatus = OrderStatus.Paid;
    }

    public int getID() {
        return this.ID;
    }

    public int getUserID() {
        return this.user_id;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public double getTotalPrice() {
        return this.foodList.stream().mapToDouble((food) -> food.getPrice()).sum();
    }

    public static OrderList getAllUnPaidOrders(OrderList orders) {
        return orders.stream().filter(
                (order) -> order.getOrderStatus() == OrderStatus.Ordered)
                .collect(Collectors.toCollection(OrderList::new));
    }

    @Override
    public String toString() {
        return String.format("====================================") + "\n" +
                String.format(" OrderID: %d      Status: %s", this.ID, this.orderStatus) + "\n" +
                String.format("====================================") + "\n" +
                String.format(" No %-15s %-15s", "Food", "Price") + "\n" +
                String.format("====================================") + "\n" +
                foodList +
                String.format("====================================") + "\n" +
                String.format("    %-15s: RM %8.2f", "Total", this.getTotalPrice()) + "\n" +
                String.format("====================================");
    }
}
