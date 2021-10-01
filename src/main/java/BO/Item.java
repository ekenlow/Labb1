package BO;

import DB.DBItem;

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
}
