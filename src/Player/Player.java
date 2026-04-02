package Player;

import Items.ItemNames;
import Items.ItemPlayer;

import java.util.ArrayList;

public class Player {


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

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }

    public ArrayList<ItemPlayer> getItems() {
        return items;
    }

    public int getAllTimeBalance() {
        return allTimeBalance;
    }

    public void setAllTimeBalance(int allTimeBalance) {
        this.allTimeBalance = allTimeBalance;
    }
}
