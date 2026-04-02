package Player;

import Items.ItemNames;
import Items.ItemPlayer;
import Items.WrongEvidenceException;
import Items.WrongItemException;

import java.util.ArrayList;

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
            throw new InvalidPlayerActionException(name + " could not be bought - currentBalance too small");
        }
        ItemPlayer foundItem = findItem(name);
        if (foundItem == null) {
            throw new InvalidPlayerActionException(name + "could not be bought - " + name + " could not be found");
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
            this.currentBalance += foundItem.sellItem(amount, npcPrice);
        } catch (WrongItemException e) {
            throw new InvalidPlayerActionException(e.getMessage());
        }
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
