package BO;

import View.ItemInfo;
import View.OrderInfo;

import java.util.ArrayList;
import java.util.Collection;

public class OrderHandler {

    public static Collection<OrderInfo> getById(int id){
        ArrayList<Order> orders = Order.getById(id);
        ArrayList<OrderInfo> result = new ArrayList<>();

        for (Order order : orders) {
            ArrayList<ItemInfo> items = new ArrayList<>();
            for (Item item : order.getItems()) {
                items.add(new ItemInfo(item.getType(),item.getName(),item.getStock(),item.getPrice()));
            }
            result.add(new OrderInfo(order.getId(), order.getStatus(), items));
        }

        return result;
    }
}
