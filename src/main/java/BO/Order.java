package BO;

import DB.DBOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Order {
    private int id;
    private Status status;
    private ArrayList<Item> items;
    private final HashMap<Integer, Integer> counts;

    public Order(int id, String status, ArrayList<Item> items, HashMap<Integer, Integer> counts) {
        this.id = id;
        this.status = Status.valueOf(status);
        this.items = items;
        this.counts = counts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> temp = new ArrayList<>();
        for (Item item : items) {
            temp.add(new Item(item.getId(), item.getType(),item.getName(), item.getStock(), item.getPrice()));
        }
        return temp;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public HashMap<Integer, Integer> getCounts() {
        HashMap<Integer, Integer> temp = new HashMap<>();
        for (int key : counts.keySet()) {
            temp.put(key, counts.get(key));
        }
        return temp;
    }

    public static Order getById(String id) throws SQLException {
        return DBOrder.getById(id);
    }

    public static boolean createOrder(HashMap<Integer, Integer> cart) throws SQLException {
        return DBOrder.createOrder(cart);
    }

    public static Collection<Order> getAll() throws SQLException {
        return DBOrder.getAll();
    }
    
    public static boolean setStatus(int id, Status newStatus) throws SQLException {
        return DBOrder.setStatus(id, newStatus);
    }
}
