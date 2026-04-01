package Shops;

import Items.Item;
import Items.ItemNames;

import java.util.ArrayList;

public class Shop {

    private ShopNames name;
    private ArrayList<Item> items;


    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Item findItem(ItemNames name){
        for (Item item : items){
            if(item.getName() == name){
                return item;
            }
        }
        return null;
    }

    public ShopNames getName() {
        return name;
    }

    public void setName(ShopNames name) {
        this.name = name;
    }
}
