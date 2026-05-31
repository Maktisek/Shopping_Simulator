package Shops;

import Items.Exceptions.WrongItemException;
import Player.Player;
import Upgrade.Rebirth.Rebirth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This class represents a management of shops.
 * <p>
 *     There are more than one shop in the game, so it is needed to have a place where all the shops are being stored.
 * </p>
 * <p>
 *     Shops are being stored in three collections:
 *     <ul>
 *         <li>{@link #shops} stands for the basic collection. All shops are being stored in here, and they are firstly loaded into here
 *         from json in {@link Game.Initialization}</li>
 *         <li>{@link #leftShops} represents a stack of shops, which are on the left. Imagine it as a catalogue - some shops are on left
 *         some on right pages. The reader can cycle between them as he flips the pages on the sides as he wants</li>
 *         <li>{@link #rightShops} is like {@link #leftShops}, but on right</li>
 *     </ul>
 * </p>
 * Also, there is {@link #currentShop} which holds the currently selected shop. It is a must-have feature, because of easier
 * access and also because of the implementation of the stacks. The current shop must be pushed into the opposite stack as from is the new one
 * taken.
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class ShopManagement implements Serializable {

    private Shop currentShop;
    private ArrayList<Shop> shops;
    private Stack<Shop> leftShops;
    private Stack<Shop> rightShops;

    /**
     * Initializes and loads both stacks.
     * <p>
     *     Initially, no shop has to be added into {@link #leftShops} since all shop are on the right from the start.
     *     But the first shop, located on index 0, has to be set as a {@link #currentShop} and all the other shops have to
     *     be pushed to the {@link #rightShops}. Because it is a stack, the shops have to be pushed in the opposite order (LIFO).
     * </p>
     */
    public void loadStacks() {
        this.leftShops = new Stack<>();
        this.rightShops = new Stack<>();
        this.currentShop = shops.get(0);
        for (int i = shops.size() - 1; i >= 1; i--) {
            rightShops.push(shops.get(i));
        }
    }

    /**
     * Calls either {@link #switchLeft()} or {@link #switchRight()} based on the input.
     * @param direction the direction, in which the shop will be changed
     */
    public void switchFromStack(ShopDirection direction){
        switch (direction){
            case LEFT -> switchLeft();
            case RIGHT -> switchRight();
        }
    }

    /**
     * Calls either {@link #isSwitchLeft()} or {@link #isSwitchRight()} based on the input.
     * @param direction the direction, in which the action will be checked
     */
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

    /**
     * Calls either {@link #isBoughtRight()} or {@link #isBoughtLeft()} based on the input.
     * @param direction the direction, in which the shops accessibility will be checked
     */
    public boolean isBought(ShopDirection direction){
        switch (direction){
            case RIGHT -> {
                return isBoughtRight();
            }
            case LEFT -> {
                return isBoughtLeft();
            }
        }
        return false;
    }

    /**
     * Calls either {@link #peekLeft()} or {@link #peekRight()} based on the input.
     * @param direction the direction, in which the stock will be peeked
     */
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

    /**
     * This method puts {@link #currentShop} into {@link #rightShops} and sets {@link #currentShop} to the shop on the top of {@link #leftShops}
     */
    private void switchLeft() {
        rightShops.push(currentShop);
        currentShop = leftShops.pop();
    }

    /**
     * This method checks if there is any available shop in the {@link #leftShops} stack
     * @return true if yes, false if not
     */
    private boolean isSwitchLeft() {
        try {
            leftShops.peek();
        } catch (EmptyStackException e) {
            return false;
        }
        return true;
    }

    /**
     * This method checks whether the shop on the top of {@link #leftShops} stack is unlocked.
     * @return true if yes, false if not
     */
    private boolean isBoughtLeft() {
        Shop temp = leftShops.peek();
        return temp.getShopKey().isUnlocked();
    }

    /**
     * This method puts {@link #currentShop} into {@link #leftShops} and sets {@link #currentShop} to the shop on the top of {@link #rightShops}
     */
    private void switchRight() {
        leftShops.push(currentShop);
        currentShop = rightShops.pop();
    }

    /**
     * This method checks if there is any available shop in the {@link #rightShops} stack
     * @return true if yes, false if not
     */
    private boolean isSwitchRight() {
        try {
            rightShops.peek();
        } catch (EmptyStackException e) {
            return false;
        }
        return true;
    }

    /**
     * This method checks whether the shop on the top of {@link #rightShops} stack is unlocked.
     * @return true if yes, false if not
     */
    private boolean isBoughtRight() {
        Shop temp = rightShops.peek();
        return temp.getShopKey().isUnlocked();
    }

    private Shop peekLeft() {
        return leftShops.peek();
    }

    private Shop peekRight() {
        return rightShops.peek();
    }

    /**
     * This method iterates through all shops in {@link #shops}.
     * If the shop is unlocked then a new day is set on that shop through {@link Shop#newDay(Player, double)}
     * @param player is needed further in the process
     * @param rebirthCoefficient represents a coefficient that is useful in further process (the rebirth coefficient comes from {@link Rebirth#getPenalizationMultiplier()})
     */
    public void setNewDays(Player player, double rebirthCoefficient) {
        for (Shop shop : shops) {
            if (shop.getShopKey().isUnlocked()) {
                try {
                    shop.newDay(player, rebirthCoefficient);
                } catch (WrongItemException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    /**
     * This method iterates through all shops in {@link #shops} and initializes their NPCs.
     * @param player is needed further in the process
     * @throws WrongItemException when there is problem with initializing the NPC
     */
    public void loadAllNpc(Player player) throws WrongItemException {
        for (Shop shop : shops) {
            shop.initializeNPC(player, shop);
        }
    }

    /**
     * This method opens all previously opened shops.
     * <p>
     *     This method has to be executed after buying new rebirth.
     * </p>
     * @param shopManagement the previous instance of {@link ShopManagement} (the one before rebirth)
     */
    public void openAfterRebirth(ShopManagement shopManagement){
        for (int i = 0; i < this.shops.size(); i++) {
            if(shopManagement.getShops().get(i).getShopKey().isUnlocked()){
                this.shops.get(i).getShopKey().setUnlocked(true);
            }
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
}
