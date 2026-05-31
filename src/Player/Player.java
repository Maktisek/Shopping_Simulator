package Player;

import Items.*;
import Items.Exceptions.WrongEvidenceException;
import Items.Exceptions.WrongItemException;
import Player.Exceptions.InvalidPlayerActionException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a player.
 * <p>
 *     Players possessions are being stored here, so this class is more like a warehouse than a player.
 * </p>
 * <p>
 *     Products are being stored in three collections:
 *     <ul>
 *         <li>{@link #stockItems} stands for the classic collection, in which are stored all achievable products. </li>
 *         <li>{@link #undeliveredItems} unlike {@link #stockItems}, this collection is filled by instances of {@link ItemDelivery}.
 *         It is because this collection stores and holds all products before they arrive.</li>
 *         <li>{@link #deliveredItems} this collection is not that important like the other ones, but it is made to store products that have been already
 *         delivered. This is so that the game can display, which products were delivered on the start of a new day.</li>
 *     </ul>
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class Player implements Serializable {


    private long currentBalance;
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

    /**
     * This method searches {@link #undeliveredItems} and calculates how many pieces of one product are there.
     * @param name is the name of the product to be counted
     * @return the final amount
     */
    public int findNumberOfUndelivered(String name) {
        int result = 0;
        for (ItemDelivery itemDelivery : undeliveredItems) {
            if (itemDelivery.getName().equalsIgnoreCase(name)) {
                result += itemDelivery.getAmount();
            }
        }
        return result;
    }

    /**
     * This method represents a system of buying a product.
     * @param delivery the item to be bought
     * @throws InvalidPlayerActionException if player has not enough money
     */
    public void buyItemNew(ItemDelivery delivery) throws InvalidPlayerActionException {
        if ((long) delivery.getBoughtPrice() * delivery.getAmount() > this.currentBalance) {
            throw new InvalidPlayerActionException("Not enough money for " + delivery.getName());
        }
        this.currentBalance -= (long) delivery.getAmount() * delivery.getBoughtPrice();
        this.undeliveredItems.add(delivery);
    }

    /**
     * This method updates state of all items stored in {@link #undeliveredItems} by decrementing their
     * {@code daysToBeDelivered} by one and then checking if the item has been delivered.
     * @throws InvalidPlayerActionException when an issue occurs in {@link #transferDeliveredItems()}
     */
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

    /**
     * This method transfers data from an instance of {@link ItemDelivery} into an instance of {@link ItemPlayer}.
     * @throws InvalidPlayerActionException when an issue occurs in {@link #transferItem(String, long, int)}
     */
    private void transferDeliveredItems() throws InvalidPlayerActionException {
        for (ItemDelivery delivery : deliveredItems) {
            transferItem(delivery.getName(), delivery.getBoughtPrice(), delivery.getAmount());
        }
    }

    /**
     * This method, based on input data, "buys" a product.
     * <p>
     *     In reality the player has already bought that product. The product stored in {@link #stockItems} does not know
     *     about it. So now, when the product was delivered, the game can simulate buying process in the real product.
     * </p>
     * @param name the product that came
     * @param shopPrice for how much it was bought (one piece)
     * @param amount how many of them were bought
     * @throws InvalidPlayerActionException if there is any issue allocated with manipulating with {@link ItemPlayer} data.
     */
    private void transferItem(String name, long shopPrice, int amount) throws InvalidPlayerActionException {
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

    /**
     * This method represents a system of selling an item.
     * <p>
     *     This system is much simpler than the system in {@link #transferItem(String, long, int)}
     *     Because the game can only manipulate with delivered items, no transfer is needed.
     * </p>
     * <p>
     *     All important math is happening inside an instance of {@link ItemPlayer}, which is found through {@link #findItem(String)}.
     * </p>
     * @param name is the name of the item to be sold
     * @param amount represents how many of them will be sold
     * @param npcPrice stands for how much does the NPC sell this product
     * @throws InvalidPlayerActionException if the product cloud not be found (should never occur) or if there is a problem
     * in further process.
     */
    public void sellItem(String name, int amount, long npcPrice) throws InvalidPlayerActionException {
        ItemPlayer foundItem = findItem(name);
        if (foundItem == null) {
            throw new InvalidPlayerActionException(name + "could not be sold - " + name + " could not be found");
        }
        try {
            long profit = foundItem.sellItem(amount, npcPrice);
            this.currentBalance += profit;
        } catch (WrongItemException e) {
            throw new InvalidPlayerActionException(e.getMessage());
        }
    }

    public boolean bankrupt() {
        return this.currentBalance < 15 && !hasSomething();
    }

    /**
     * This method checks whether the player has something to sell now or in the future.
     * <p>
     *     It is to prevent the player playing while he has no chance to continue.
     * </p>
     * @return true if he has something to sell, false if not
     */
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

    /**
     * This method loads {@link #stockItems} by transferring whole array of {@link ItemShop} into individual instances of {@link ItemPlayer}.
     * <p>
     *     Usually this method is executed when player acquires a new shop
     * </p>
     * @param itemShop the array filled by {@link ItemShop} instances
     * @throws WrongItemException if there is any problem in transforming {@link ItemShop} into {@link ItemPlayer}
     */
    public void loadItems(ItemShop[] itemShop) throws WrongItemException {
        for (ItemShop item : itemShop) {
            this.stockItems.add(new ItemPlayer(item.getItemBase().getName()));
        }
    }


    public long calculateStocks() {
        long stocks = 0;
        for (ItemPlayer itemPlayer : stockItems) {
            stocks += itemPlayer.getAmount();
        }
        return stocks;
    }

    public long calculateAllStocks() {
        long stocks = calculateStocks();
        for (ItemDelivery itemDelivery : undeliveredItems) {
            stocks += itemDelivery.getAmount();
        }
        return stocks;
    }

    /**
     * This method finds player's favorite product.
     * @return player's favorite product
     */
    public String findFavorite() {
        long max = 0;
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

    public boolean canBuy(long price) {
        return price <= this.currentBalance;
    }

    public long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(long currentBalance) {
        this.currentBalance = currentBalance;
    }

    public ArrayList<ItemPlayer> getStockItems() {
        return stockItems;
    }

    /**
     * This method is useful when it comes to displaying information about all delivered products.
     * <p>
     *     It creates a HashMap whose
     *     <ul>
     *         <li>key is name of the product</li>
     *         <li>value is the amount</li>
     *     </ul>
     * </p>
     * <p>
     *     If the product is not in the map then it is just put there with its amount.
     *     But if the product is already in the map then the amount is only updated.
     * </p>
     * @return the map with all delivered products and their amounts
     */
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

    /**
     * This method processes {@link #loadMap()} further.
     * <p>
     *     It rewrites the content of the map into pre-defined syntax.
     * </p>
     * The syntax: {@code Name of the product: amount}
     * @return the rewritten content of the map or null if there is no content in the map
     */
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
