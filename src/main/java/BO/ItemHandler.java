package BO;

import View.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;

public class ItemHandler {

    public static Collection<ItemInfo> getByName(String name) {
        ArrayList<Item> items = (ArrayList<Item>) Item.getByName(name);
        ArrayList<ItemInfo> result = new ArrayList<>();

        for (Item item : items) {
            result.add(new ItemInfo(item.getType(),item.getName(), item.getStock(), item.getPrice()));
        }

        return result;
    }

}
