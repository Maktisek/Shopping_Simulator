package Shops;

import Items.WrongItemException;
import Player.Player;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class ShopManagement {

    private ArrayList<Shop> shops;
    private Shop currentShop;
    private Stack<Shop> leftShops;
    private Stack<Shop> rightShops;

    public void loadStacks() {
        this.leftShops = new Stack<>();
        this.rightShops = new Stack<>();
        this.currentShop = shops.get(0);
        for (int i = shops.size() - 1; i >= 1; i--) {
            rightShops.push(shops.get(i));
        }
    }

    public void switchLeft() {
        rightShops.push(currentShop);
        currentShop = leftShops.pop();
    }

    public boolean isSwitchLeft() {
        try {
            leftShops.peek();
        } catch (EmptyStackException e) {
            return false;
        }
        return true;
    }

    public boolean boughtLeft() {
        Shop temp = leftShops.peek();
        return temp.getShopKey().isUnlocked();
    }

    public void switchRight() {
        leftShops.push(currentShop);
        currentShop = rightShops.pop();
    }

    public boolean isSwitchRight() {
        try {
            rightShops.peek();
        } catch (EmptyStackException e) {
            return false;
        }
        return true;
    }

    public boolean boughtRight() {
        Shop temp = rightShops.peek();
        return temp.getShopKey().isUnlocked();
    }

    public Shop peekLeft() {
        return leftShops.peek();
    }

    public Shop peekRight() {
        return rightShops.peek();
    }

    public void setNewDays(Player player) {
        for (Shop shop : shops) {
            if (shop.getShopKey().isUnlocked()) {
                try {
                    shop.newDay(player);
                } catch (WrongItemException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadAllNpc(Player player) throws WrongItemException {
        for (Shop shop : shops) {
            shop.initializeNPC(player, shop);
        }
    }

    public ArrayList<Shop> getShops() {
        return shops;
    }

    public void setShops(ArrayList<Shop> shops) {
        this.shops = shops;
    }

    public Shop getCurrentShop() {
        return currentShop;
    }

    public void setCurrentShop(Shop currentShop) {
        this.currentShop = currentShop;
    }

    public Stack<Shop> getLeftShops() {
        return leftShops;
    }

    public void setLeftShops(Stack<Shop> leftShops) {
        this.leftShops = leftShops;
    }

    public Stack<Shop> getRightShops() {
        return rightShops;
    }

    public void setRightShops(Stack<Shop> rightShops) {
        this.rightShops = rightShops;
    }

    @Override
    public String toString() {
        return "ShopManagement{" +
                "shops=" + shops +
                ", currentShop=" + currentShop +
                ", leftShops=" + leftShops +
                ", rightShops=" + rightShops +
                '}';
    }
}
