package Player;

import Items.*;
import Shops.Shop;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {


    private int currentBalance;
    private int allTimeBalance;
    private final ArrayList<ItemPlayer> stockItems;
    private ArrayList<ItemDelivery> undeliveredItems;
    private ArrayList<ItemDelivery> deliveredItems;

    public Player() {
        this.stockItems = new ArrayList<>();
        this.undeliveredItems = new ArrayList<>();
        this.deliveredItems = new ArrayList<>();
    }

    public ItemPlayer findItem(ItemNames name) {
        for (ItemPlayer item : stockItems) {
            if (item.getName() == name) {
                return item;
            }
        }
        return null;
    }

    public void buyItemNew(ItemDelivery delivery) throws InvalidPlayerActionException{
        if (delivery.getBoughtPrice() > this.currentBalance) {
            throw new InvalidPlayerActionException("Not enough money for " + delivery.getName());
        }
        this.currentBalance -= delivery.getAmount() * delivery.getBoughtPrice();
        this.undeliveredItems.add(delivery);
    }

    public void updateUndelivered() throws InvalidPlayerActionException {
        deliveredItems.clear();
        for (ItemDelivery delivery : undeliveredItems){
            delivery.setDaysToBeDelivered(delivery.getDaysToBeDelivered() - 1);
            if(delivery.getDaysToBeDelivered() == 0){
                deliveredItems.add(delivery);
            }
        }
        undeliveredItems.removeAll(deliveredItems);
        transferDeliveredItems();
    }

    private void transferDeliveredItems() throws InvalidPlayerActionException {
        for (ItemDelivery delivery : deliveredItems){
            transferItem(delivery.getName(), delivery.getBoughtPrice(), delivery.getAmount());
        }
    }


    public void transferItem(ItemNames name, int shopPrice, int amount) throws InvalidPlayerActionException {
        ItemPlayer foundItem = findItem(name);
        if (foundItem == null) {
            throw new InvalidPlayerActionException(name + "could not be bought " + name + " could not be found");
        }
        try {
            foundItem.buyItem(amount, shopPrice);
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
        if(!undeliveredItems.isEmpty()){
            return true;
        }
        for (ItemPlayer itemPlayer : stockItems){
            if(itemPlayer.getAmount() > 0){
                return true;
            }
        }
        return false;
    }

    public void loadItems(ArrayList<Shop> shops) throws WrongItemException{
        for (Shop shop : shops){
            for (int i = 0; i < shop.getItems().length; i++) {
                this.stockItems.add(new ItemPlayer(shop.getItems()[i].getItem().getName()));
            }
        }
    }

    public int calculateStocks(){
        int stocks = 0;
        for (ItemPlayer itemPlayer : stockItems){
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

    public ArrayList<ItemPlayer> getStockItems() {
        return stockItems;
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
                ", items=" + stockItems +
                '}';
    }

    public Player(int allTimeBalance, int currentBalance, ItemPlayer[] arr) {
        this.allTimeBalance = allTimeBalance;
        this.currentBalance = currentBalance;
        this.stockItems = new ArrayList<>();
        stockItems.addAll(Arrays.asList(arr));
    }
}
