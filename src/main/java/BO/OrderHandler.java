package BO;

import View.ItemInfo;
import View.OrderInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class OrderHandler {

    public static OrderInfo getById(String id) throws SQLException {
        Order order = Order.getById(id);
        ArrayList<ItemInfo> items = new ArrayList<>();
        for (Item item :
                order.getItems()) {
            items.add(new ItemInfo(item.getId(), item.getType(),item.getName(),item.getStock(), item.getPrice()));
        }
        return new OrderInfo(order.getId(),order.getStatus(),items);
    }
}
