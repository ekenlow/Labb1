package BO;

import DB.DBItem;
import View.ItemInfo;

import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;


public class Item {
    private final int id;
    private String type;
    private String name;
    private final int stock;
    private final float price;

    protected Item(int id, String type, String name, int stock, float price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public static List<Item> getByName(String search) {

    }

    public static Item getById(String search) throws SQLException {
        return DBItem.getById(search);
    }

    public static List<Item> getByType(String search) throws SQLException {
        ArrayList<Item> list = new ArrayList<>();
        for (DBItem item : DBItem.getByType(search)) {
            list.add((Item) item);
        }
        return list;
    }

    public static List<Item> getAll() throws SQLException {
        return DBItem.getAll();
    }

    public static void createItem(String name, String type, int stock, float price) throws SQLException {
        DBItem.createItem(name, type, stock, price);
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public float getPrice() {
        return price;
    }

    public static void updateItem(int id, String name, String type, int stock, float price) throws SQLException {
        DBItem.updateItem(id, name, type, stock, price);
    }

    public static void deleteItem(int id) throws SQLException {
        DBItem.deleteItem(id);
    }
}
