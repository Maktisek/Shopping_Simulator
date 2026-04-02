package Shops;

import Items.WrongItemException;
import Player.Player;

import java.util.ArrayList;

public class ShopManagement {

    private ArrayList<Shop> shops;

    public ShopManagement() {
        this.shops = new ArrayList<>();
    }

    public Shop findShop(ShopNames name){
        for (Shop shop : shops){
            if(shop.getName() == name){
                return shop;
            }
        }
        return null;
    }

    public void setNewDays(Player player) {
        for (Shop shop : shops){
            try {
                shop.newDay(player);
            }catch (WrongItemException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Shop> getShops() {
        return shops;
    }

    public void setShops(ArrayList<Shop> shops) {
        this.shops = shops;
    }
}
