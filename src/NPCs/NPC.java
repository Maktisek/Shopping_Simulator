package NPCs;

import Items.*;
import Items.Exceptions.WrongItemException;
import Shops.Shop;
import Player.Player;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 * This class represents a NPC (in game called "buyer")
 * <p>
 * There are some mentionable features, which may not be understandable from first sight. Let me clear them out:
 * </p>
 * <p>
 * Every NPC has its {@link #quantityWeight} and {@link #convenienceWeight} that determines its decisions
 *     <ul>
 *         <li>{@link #quantityWeight} the larger the more NPC want to buy massively bought products</li>
 *         <li>{@link #convenienceWeight} the larger the more NPC want to buy products on which the player will have the best profit</li>
 *     </ul>
 *     Also mentionable are {@link #items} and {@link #demand}.
 *     <ul>
 *         <li>{@link #items} store all items</li>
 *         <li>{@link #demand} store all currently available items. Those item cycle based on their coefficient of popularity (which is calculated in
 *         {@link #loadDemand(Player, Shop)})</li>
 *     </ul>
 * </p>
 *
 * @author Matěj Pospíšil
 */
public class NPC implements Serializable {

    private int quantityWeight;
    private int convenienceWeight;
    private ItemNPC[] items;
    private ItemNPC[] demand;

    /**
     * This complex method calculates, which items should be put into {@link #demand}.
     * <p>
     * Every single item in {@link #items} gets its own coefficient of popularity (in code called {@code s}).
     * Two items with the highest coefficient are then put into {@link #demand}.
     * </p>
     * <p>
     *     The coefficient is not calculated when player own 0 pieces of the product. So it may happen that
     *     there will be no possible item to be added into {@link #demand}. To prevent this a method {@link #checkLoadDemand()} is executed.
     * </p>
     * @param player needs to be inserted, because provides needed data for coefficient calculation
     * @param shop needs to be inserted, because provides needed data for coefficient calculation
     */
    public void loadDemand(Player player, Shop shop) {
        resetDemand();
        double first = 0;
        double second = 0;
        for (ItemNPC item : this.items) {
            ItemPlayer playersItem = player.findItem(item.getItemBase().getName());
            ItemShop shopsItem = shop.findItem(item.getItemBase().getName());
            if (playersItem != null && shopsItem != null) {
                double playerAverage = playersItem.getAverageBuyPrice();
                if (playerAverage != 0) {
                    double s = (playersItem.getAmount() * quantityWeight) +
                            ((shopsItem.getItemBase().getCurrentPrice() / playerAverage) * convenienceWeight);
                    if (s > first && item.getAmountManager().getCurrent() != 0) {
                        demand[1] = demand[0];
                        second = first;
                        first = s;
                        demand[0] = item;
                    } else if (s > second && item.getAmountManager().getCurrent() != 0) {
                        second = s;
                        demand[1] = item;
                    }
                }
            }
        }
        checkLoadDemand();
    }

    /**
     * Determines whether {@link #demand} should be filled randomly or not.
     * <p>
     *     It is executed always after {@link #loadDemand(Player, Shop)}
     * </p>
     */
    private void checkLoadDemand() {
        if (demand[0] == null && demand[1] == null) {
            fillWholeDemandRandomly();
        } else if (demand[1] == null) {
            Random rd = new Random();
            do {
                demand[1] = items[rd.nextInt(items.length)];
            } while (demand[0] == demand[1]);
        }
    }

    /**
     * This method is being run from {@link #checkLoadDemand()} and it only does if the whole {@link #demand} is empty.
     * <p>
     *     It simply loads {@link #demand} with random products - those products will never be same because of while cycle.
     * </p>
     */
    private void fillWholeDemandRandomly() {
        Random rd = new Random();
        int indexOne = rd.nextInt(items.length);
        int indexTwo;
        do {
            indexTwo = rd.nextInt(items.length);
        } while (indexOne == indexTwo);

        demand[0] = items[indexOne];
        demand[1] = items[indexTwo];
    }

    private void resetDemand() {
        Arrays.fill(demand, null);
    }

    public void loadItems(ItemShop[] temp) throws WrongItemException {
        for (int i = 0; i < temp.length; i++) {
            this.items[i].setItem(temp[i].getItemBase().copy());
        }
    }

    /**
     * This method calculates prices of items located in {@link #demand}
     * <p>
     *     Again same as in {@link #loadDemand(Player, Shop)} the price is not calculated when player own 0 pieces of the product.
     *     The price then stays how it was.
     * </p>
     * @param player needs to be inserted, because provides needed data for price calculation
     * @param shop needs to be inserted, because provides needed data for price calculation
     * @throws WrongItemException when the price goes under 1 (impossible)
     */
    public void setNewPrices(Player player, Shop shop) throws WrongItemException {
        Random rd = new Random();
        for (ItemNPC item : demand) {
            if (item == null) {
                continue;
            }

            ItemPlayer playersItem = player.findItem(item.getItemBase().getName());
            if (playersItem == null) {
                continue;
            }
            double playerAverage = playersItem.getAverageBuyPrice();
            double playerWhole = playersItem.getWholeBoughtPrice();
            if (playerAverage == 0 || playerWhole == 0) {
                continue;
            }

            ItemShop itemShop = shop.findItem(item.getItemBase().getName());
            if (itemShop == null) {
                continue;
            }
            double shopPrice = itemShop.getItemBase().getCurrentPrice();
            if (shopPrice == 0) {
                continue;
            }

            double bonus = calculateB(playerAverage, playerWhole);

            double percentUpdate = rd.nextInt(-8, 6) + bonus + calculateL(playerAverage, shopPrice);
            item.getItemBase().setCurrentPrice((int) Math.round(item.getItemBase().getBasePrice() + (((double) item.getItemBase().getCurrentPrice() / 100) * percentUpdate)));
        }
    }

    /**
     * This method calculates a special coefficient, which is later used in {@link #setNewPrices(Player, Shop)}
     * @param playerAverage stands for average buy price of the item, and it is needed for usage in {@link #calculateK(double)}
     * @param playerWhole stands for how much have player spent on that item (not overall, but without sells)
     * @return the calculated bonus
     */
    private double calculateB(double playerAverage, double playerWhole) {
        double k = calculateK(playerAverage);
        double bonus = Math.sqrt(k) / Math.sqrt(playerWhole);
        if (bonus > 5) {
            bonus = 5;
        }
        return bonus;
    }
    
    /**
     * This method calculates a special coefficient, which is later used in {@link #calculateB(double, double)}
     * @return the calculated coefficient
     */
    private double calculateK(double averagePrice) {
        String parser = String.valueOf((int) averagePrice);
        return 5000.00 * Math.pow(10, parser.length() - 2);
    }

    /**
     * This method calculates a special coefficient, which is later used in {@link #setNewPrices(Player, Shop)}
     * @return the calculated coefficient
     */
    private double calculateL(double averagePrice, double shopPrice) {
        double ratio = averagePrice / shopPrice;
        if (ratio > 1) {
            return -ratio;
        }
        if (ratio < 1) {
            return shopPrice / averagePrice;
        }
        return 0;
    }

    public int getQuantityWeight() {
        return quantityWeight;
    }

    public void setQuantityWeight(int quantityWeight) {
        this.quantityWeight = quantityWeight;
    }

    public ItemNPC[] getDemand() {
        return demand;
    }

    public void setDemand(ItemNPC[] demand) {
        this.demand = demand;
    }

    public ItemNPC[] getItems() {
        return items;
    }

    public void setItems(ItemNPC[] items) {
        this.items = items;
    }

    public int getConvenienceWeight() {
        return convenienceWeight;
    }

    public void setConvenienceWeight(int convenienceWeight) {
        this.convenienceWeight = convenienceWeight;
    }

}