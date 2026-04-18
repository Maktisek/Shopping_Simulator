package Player;

import Items.ItemNames;
import Items.ItemPlayer;
import Items.WrongEvidenceException;
import Items.WrongItemException;
import Shops.Shop;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {


    private int currentBalance;
    private int allTimeBalance;
    private final ArrayList<ItemPlayer> items;

    public Player() {
        this.items = new ArrayList<>();
    }

    public ItemPlayer findItem(ItemNames name) {
        for (ItemPlayer item : items) {
            if (item.getName() == name) {
                return item;
            }
        }
        return null;
    }

    public void buyItem(ItemNames name, int amount, int shopPrice) throws InvalidPlayerActionException {
        if (amount * shopPrice > this.currentBalance) {
            throw new InvalidPlayerActionException(name + " could not be bought currentBalance too small");
        }
        ItemPlayer foundItem = findItem(name);
        if (foundItem == null) {
            throw new InvalidPlayerActionException(name + "could not be bought " + name + " could not be found");
        }
        try {
            this.currentBalance -= foundItem.buyItem(amount, shopPrice);
        } catch (WrongItemException | WrongEvidenceException e) {
            throw new InvalidPlayerActionException(e.getMessage());
        }
    }

    public void sellItem(ItemNames name, int amount, int npcPrice) throws InvalidPlayerActionException {
        ItemPlayer foundItem = findItem(name);
        if (foundItem == null) {
            throw new InvalidPlayerActionException(name + "could not be sold - " + name + " could not be found");
        }
        try {
            int profit = foundItem.sellItem(amount, npcPrice);
            this.currentBalance += profit;
            this.allTimeBalance += profit;
        } catch (WrongItemException e) {
            throw new InvalidPlayerActionException(e.getMessage());
        }
    }

    public boolean bankrupt(){
        return this.currentBalance < 15 && !hasSomething();
    }

    private boolean hasSomething(){
        for (ItemPlayer itemPlayer : items){
            if(itemPlayer.getAmount() > 0){
                return true;
            }
        }
        return false;
    }

    public void loadItems(ArrayList<Shop> shops) throws WrongItemException{
        for (Shop shop : shops){
            for (int i = 0; i < shop.getItems().length; i++) {
                this.items.add(new ItemPlayer(shop.getItems()[i].getItem().getName()));
            }
        }
    }

    public int calculateStocks(){
        int stocks = 0;
        for (ItemPlayer itemPlayer : items){
            stocks += itemPlayer.getAmount();
        }
        return stocks;
    }

    public boolean canBuy(int price){
        return price <= this.currentBalance;
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

    @Override
    public String toString() {
        return "Player{" +
                "currentBalance=" + currentBalance +
                ", allTimeBalance=" + allTimeBalance +
                ", items=" + items +
                '}';
    }

    public Player(int allTimeBalance, int currentBalance, ItemPlayer[] arr) {
        this.allTimeBalance = allTimeBalance;
        this.currentBalance = currentBalance;
        this.items = new ArrayList<>();
        items.addAll(Arrays.asList(arr));
    }
}
