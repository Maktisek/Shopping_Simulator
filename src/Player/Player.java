package Player;

import Items.ItemNames;
import Items.ItemPlayer;

import java.util.ArrayList;

public class Player {


    private String name;
    private int currentBalance;
    private int allTimeBalance;
    private final ArrayList<ItemPlayer> items;

    public Player() {
        this.items = new ArrayList<>();
    }

    public ItemPlayer findItem(ItemNames name){
        for (ItemPlayer item : items){
            if(item.getName() == name){
                return item;
            }
        }
        return null;
    }

    public ArrayList<ItemPlayer> getItems() {
        return items;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public String getName() {
        return name;
    }

    public int getAllTimeBalance() {
        return allTimeBalance;
    }

}
