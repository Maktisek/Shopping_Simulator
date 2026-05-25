package Shops;

import Commands.ShopCommands.ShopDirection;
import Items.Exceptions.WrongItemException;
import Player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class ShopManagement implements Serializable {

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

    public void switchFromStack(ShopDirection direction){
        switch (direction){
            case LEFT -> switchLeft();
            case RIGHT -> switchRight();
        }
    }

    public boolean isSwitch(ShopDirection direction){
        switch (direction){
            case LEFT -> {
                return !isSwitchLeft();
            }
            case RIGHT -> {
                return !isSwitchRight();
            }
        }
        return true;
    }

    public boolean isBought(ShopDirection direction){
        switch (direction){
            case RIGHT -> {
                return boughtRight();
            }
            case LEFT -> {
                return boughtLeft();
            }
        }
        return false;
    }

    public Shop peek(ShopDirection direction){
        switch (direction){
            case LEFT -> {
                return peekLeft();
            }
            case RIGHT -> {
                return peekRight();
            }
        }
        return null;
    }

    private void switchLeft() {
        rightShops.push(currentShop);
        currentShop = leftShops.pop();
    }

    private boolean isSwitchLeft() {
        try {
            leftShops.peek();
        } catch (EmptyStackException e) {
            return false;
        }
        return true;
    }

    private boolean boughtLeft() {
        Shop temp = leftShops.peek();
        return temp.getShopKey().isUnlocked();
    }

    private void switchRight() {
        leftShops.push(currentShop);
        currentShop = rightShops.pop();
    }

    private boolean isSwitchRight() {
        try {
            rightShops.peek();
        } catch (EmptyStackException e) {
            return false;
        }
        return true;
    }

    private boolean boughtRight() {
        Shop temp = rightShops.peek();
        return temp.getShopKey().isUnlocked();
    }

    private Shop peekLeft() {
        return leftShops.peek();
    }

    private Shop peekRight() {
        return rightShops.peek();
    }

    public void setNewDays(Player player, double rebirthCoefficient) {
        for (Shop shop : shops) {
            if (shop.getShopKey().isUnlocked()) {
                try {
                    shop.newDay(player, rebirthCoefficient);
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
