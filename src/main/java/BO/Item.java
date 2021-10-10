package BO;

import DB.DBItem;
import View.ItemInfo;

import java.sql.SQLException;
import java.util.Collection;


public class Item {
    private int id;
    private String type;
    private String name;
    private int stock;
    private float price;

    protected Item(int id, String type, String name, int stock, float price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public static Collection getByName(String search){
        return DBItem.getByName(search);
    }
    public static Item getById (String search) {
        return DBItem.getById(search);
    }
    public static Collection getByType(String search) {
        return DBItem.getByType(search);
    }
    public static Collection getAll() throws SQLException {
        return DBItem.getAll();
    }

    public static void createItem(String name, String type, int stock, float price) throws SQLException{
        DBItem.createItem(name,type,stock,price);
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

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public static void updateItem(int id, String name, String type, int stock, float price) throws SQLException {
        DBItem.updateItem(id, name, type, stock, price);
    }

    public static void deleteItem(int id) throws SQLException{
        DBItem.deleteItem(id);
    }
}
