package BO;

import View.ItemInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ItemHandler {
    public static Collection<ItemInfo> getByName(String name) {
        ArrayList<Item> items = (ArrayList<Item>) Item.getByName(name);
        return items.stream().map(item -> new ItemInfo(item.getId(), item.getType(), item.getName(), item.getStock(), item.getPrice())).collect(Collectors.toCollection(ArrayList::new));
    }
    public static ItemInfo getById(String searchTerm){
        Item item = Item.getById(searchTerm);
        return new ItemInfo(item.getId(),item.getType(), item.getName(), item.getStock(), item.getPrice());
    }
    public static Collection<ItemInfo> getByType(String searchTerm){
        ArrayList<Item> items = (ArrayList<Item>) Item.getByType(searchTerm);
        ArrayList<ItemInfo> result = new ArrayList<>();

        for (Item item : items) {
            result.add(new ItemInfo(item.getId(), item.getType(),item.getName(), item.getStock(), item.getPrice()));
        }

        return  result;
    }

    public static Collection<ItemInfo> getAll() throws SQLException {
        ArrayList<Item> items = (ArrayList<Item>) Item.getAll();
        return items.stream().map(item -> new ItemInfo(item.getId(), item.getType(), item.getName(), item.getStock(), item.getPrice())).collect(Collectors.toCollection(ArrayList::new));
    }

    public static void updateItem(int id, String name, String type, int stock, float price) throws SQLException  {
        Item.updateItem(id, name, type, stock, price);
    }

    public static void createItem(String name, String type, int stock, float price) throws SQLException {
        Item.createItem(name,type,stock,price);
    }

    public static void deleteItem(String id) throws SQLException {
        Item.deleteItem(Integer.parseInt(id));
    }
}
