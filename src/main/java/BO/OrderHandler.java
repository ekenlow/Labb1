package BO;

import View.ItemInfo;
import View.OrderInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class OrderHandler {

    public static OrderInfo getById(String id) throws SQLException {
        Order order = Order.getById(id);
        ArrayList<ItemInfo> items = new ArrayList<>();
        for (Item item :
                order.getItems()) {
            items.add(new ItemInfo(item.getId(), item.getType(),item.getName(),item.getStock(), item.getPrice()));
        }
        return new OrderInfo(order.getId(),order.getStatus(),items, order.getCounts());
    }

    public static boolean checkOut(HashMap<Integer, Integer> cart, HashMap<Integer, ItemInfo> cartInfo) throws SQLException {
        Order.createOrder(cart);
        return false;
    }

    public static Collection<OrderInfo> getAll() throws SQLException {
        ArrayList<Order> orders = (ArrayList<Order>) Order.getAll();
        ArrayList<OrderInfo> orderInfos = new ArrayList<>();
        for (Order order : orders) {
            ArrayList<ItemInfo> infos = new ArrayList<>();
            for (Item item : order.getItems()) {
                infos.add(new ItemInfo(item.getId(), item.getType(), item.getName(), item.getStock(), item.getPrice()));
            }
            orderInfos.add(new OrderInfo(order.getId(), order.getStatus(), infos, order.getCounts()));
        }
        return orderInfos;
    }

    public static boolean setStatus(int id, Status newStatus) throws SQLException {
        return Order.setStatus(id, newStatus);
    }
}
