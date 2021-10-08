package View;



import BO.Status;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderInfo {
    private final int id;
    private final Status status;
    private final ArrayList<ItemInfo> items;
    private final HashMap<Integer, Integer> counts;

    public OrderInfo(int id, Status status, ArrayList<ItemInfo> items, HashMap<Integer, Integer> counts) {
        this.id = id;
        this.status = status;
        this.items = items;
        this.counts = counts;
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

    public HashMap<Integer, Integer> getCounts() {
        return counts;
    }
}
