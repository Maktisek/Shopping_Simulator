package Items;

import java.util.ArrayList;

public class Shop {

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
}
