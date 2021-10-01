package View;



import BO.Status;

import java.util.ArrayList;

public class OrderInfo {
    private final int id;
    private final Status status;
    private final ArrayList<ItemInfo> items;

    public OrderInfo(int id, Status status, ArrayList<ItemInfo> items) {
        this.id = id;
        this.status = status;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public ArrayList<ItemInfo> getItems() {
        return items;
    }
}
