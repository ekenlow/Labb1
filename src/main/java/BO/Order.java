package BO;

import DB.DBOrder;

import java.util.ArrayList;

public class Order {
    private int id;
    private Status status;
    private ArrayList<Item> items;

    public Order(int id, Status status, ArrayList<Item> items) {
        this.id = id;
        this.status = status;
        this.items = items;
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
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public static ArrayList<Order> getById(int id) {
        return DBOrder.getById(id);
    }
}
