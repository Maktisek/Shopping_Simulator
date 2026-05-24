package Player;

import Items.*;
import Items.Exceptions.WrongEvidenceException;
import Items.Exceptions.WrongItemException;
import Player.Exceptions.InvalidPlayerActionException;
import Shops.Shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Player implements Serializable {


    private int currentBalance;
    private final ArrayList<ItemPlayer> stockItems;
    private final ArrayList<ItemDelivery> undeliveredItems;
    private final ArrayList<ItemDelivery> deliveredItems;

    public Player() {
        this.stockItems = new ArrayList<>();
        this.undeliveredItems = new ArrayList<>();
        this.deliveredItems = new ArrayList<>();
    }

    public ItemPlayer findItem(String name) {
        for (ItemPlayer item : stockItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public int findNumberOfUndelivered(String name) {
        int result = 0;
        for (ItemDelivery itemDelivery : undeliveredItems) {
            if (itemDelivery.getName().equalsIgnoreCase(name)) {
                result++;
            }
        }
        return result;
    }

    public void buyItemNew(ItemDelivery delivery) throws InvalidPlayerActionException {
        if (delivery.getBoughtPrice() * delivery.getAmount() > this.currentBalance) {
            throw new InvalidPlayerActionException("Not enough money for " + delivery.getName());
        }
        this.currentBalance -= delivery.getAmount() * delivery.getBoughtPrice();
        this.undeliveredItems.add(delivery);
    }

    public void updateUndelivered() throws InvalidPlayerActionException {
        deliveredItems.clear();
        for (ItemDelivery delivery : undeliveredItems) {
            delivery.setDaysToBeDelivered(delivery.getDaysToBeDelivered() - 1);
            if (delivery.getDaysToBeDelivered() == 0) {
                deliveredItems.add(delivery);
            }
        }
        undeliveredItems.removeAll(deliveredItems);
        transferDeliveredItems();
    }

    private void transferDeliveredItems() throws InvalidPlayerActionException {
        for (ItemDelivery delivery : deliveredItems) {
            transferItem(delivery.getName(), delivery.getBoughtPrice(), delivery.getAmount());
        }
    }


    public void transferItem(String name, int shopPrice, int amount) throws InvalidPlayerActionException {
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

    public void sellItem(String name, int amount, int npcPrice) throws InvalidPlayerActionException {
        ItemPlayer foundItem = findItem(name);
        if (foundItem == null) {
            throw new InvalidPlayerActionException(name + "could not be sold - " + name + " could not be found");
        }
        try {
            int profit = foundItem.sellItem(amount, npcPrice);
            this.currentBalance += profit;
        } catch (WrongItemException e) {
            throw new InvalidPlayerActionException(e.getMessage());
        }
    }

    public boolean bankrupt() {
        return this.currentBalance < 15 && !hasSomething();
    }

    private boolean hasSomething() {
        if (!undeliveredItems.isEmpty()) {
            return true;
        }
        for (ItemPlayer itemPlayer : stockItems) {
            if (itemPlayer.getAmount() > 0) {
                return true;
            }
        }
        return false;
    }

//    public void loadItems(ArrayList<Shop> shops) throws WrongItemException {
//        for (Shop shop : shops) {
//            for (int i = 0; i < shop.getItems().length; i++) {
//                this.stockItems.add(new ItemPlayer(shop.getItems()[i].getItem().getName()));
//            }
//        }
//    }

    public void loadItems(ItemShop[] itemShop) throws WrongItemException {
        for (ItemShop item : itemShop) {
            this.stockItems.add(new ItemPlayer(item.getItem().getName()));
        }
    }

    public int calculateStocks() {
        int stocks = 0;
        for (ItemPlayer itemPlayer : stockItems) {
            stocks += itemPlayer.getAmount();
        }
        return stocks;
    }

    public int calculateAllStocks() {
        int stocks = calculateStocks();
        for (ItemDelivery itemDelivery : undeliveredItems) {
            stocks += itemDelivery.getAmount();
        }
        return stocks;
    }

    public String findFavorite() {
        int max = 0;
        String result = "";
        for (ItemPlayer itemPlayer : stockItems) {
            if (itemPlayer.getSellAmount() > max) {
                max = itemPlayer.getSellAmount();
                result = itemPlayer.getName();
            }
        }
        if (result.equalsIgnoreCase("")) {
            return "Nothing";
        }

        return result;
    }

    public boolean canBuy(int price) {
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

    private HashMap<String, Integer> loadMap() {
        HashMap<String, Integer> map = new HashMap<>();
        for (ItemDelivery itemDelivery : deliveredItems) {
            if (map.containsKey(itemDelivery.getName())) {
                int current = map.get(itemDelivery.getName());
                current += itemDelivery.getAmount();
                map.put(itemDelivery.getName(), current);
            } else {
                map.put(itemDelivery.getName(), itemDelivery.getAmount());
            }
        }
        return map;
    }

    public String information() {
        HashMap<String, Integer> map = loadMap();
        StringBuilder sb = new StringBuilder();
        for (String name : map.keySet()) {
            sb.append(name).append(":").append(map.get(name)).append("\n");
        }
        if (sb.isEmpty()) {
            return null;
        }
        return sb.toString();
    }
}
