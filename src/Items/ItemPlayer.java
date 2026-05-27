package Items;


import Game.GameData;
import Items.Exceptions.WrongEvidenceException;
import Items.Exceptions.WrongItemException;
import Items.Utilities.Evidence;
import Utilities.Important;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents an item, which is in players possession.
 * <p>
 *     This class is the example that an instance of {@link ItemBase} do not have to be everywhere.
 * </p>
 * <p>
 *     It is identified by its name - works like an ID
 * </p>
 * <p>
 *     It needed to mention {@link #evidences}, which is a queue that holds all evidences of purchases.
 *     It is in the queue so it can be easily backtracked. The oldest purchase is taken first.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class ItemPlayer implements Serializable {

    private String name;
    private int amount;
    private int sellAmount;
    private int wholeBoughtPrice;
    private int wholePrice;
    private int wholeEarnings;
    private double averageBuyPrice;
    private final Queue<Evidence> evidences;
    private final ArrayList<Double> averagePrices;


    public ItemPlayer(String name) {
        this.name = name;
        this.evidences = new LinkedList<>();
        this.averagePrices = new ArrayList<>();
        this.amount = 0;
        this.wholeBoughtPrice = 0;
        this.averageBuyPrice = 0;
    }

    /**
     * This method stands for an action of buying an item.
     * <p>
     *     Only thing it does it that it creates a chain of reactions.
     * </p>
     * @param amount how many of that product want the player to buy
     * @param shopPrice for how much does the shop sell this product
     * @throws WrongEvidenceException if there is a problem with loading the evidence (this should never occur)
     * @throws WrongItemException if there is a problem with moving with data (also this should never occur)
     */
    public void buyItem(int amount, int shopPrice) throws WrongEvidenceException, WrongItemException {
        moveWithAmount(amount);
        moveWithWholeBoughtPrice(amount, shopPrice);
        this.evidences.add(new Evidence(amount, shopPrice));
        updateAveragePrice();
        saveAveragePrice();
    }

    private void updateAveragePrice() {
        if (this.amount > 0) {
            this.averageBuyPrice = (double) this.wholeBoughtPrice / this.amount;
        } else {
            this.averageBuyPrice = 0;
        }
    }

    private void saveAveragePrice(){
        this.averagePrices.add(this.averageBuyPrice);
    }

    private void moveWithAmount(int move) throws WrongItemException {
        int afterMove = this.amount + move;
        if (afterMove < 0) {
            throw new WrongItemException("There is less than " + -move + " pieces of " + name);
        } else {
            this.amount = afterMove;
        }
    }

    private void moveWithWholeBoughtPrice(int amount, int shopPrice) throws WrongItemException {
        int afterMove = this.wholeBoughtPrice + (shopPrice * amount);
        if (afterMove < 0) {
            throw new WrongItemException("WholeBoughtPrice must be larger that -1");
        } else {
            this.wholeBoughtPrice = afterMove;
            this.wholePrice += (shopPrice * amount);
        }
    }

    /**
     * This method stands for an action of selling an item.
     * <p>
     *     The amount has to be changed and also the evidences has to be backtracked (more about backtracking evidences is available in {@link Evidence}
     *     documentation)
     * </p>
     * <p>
     *     The backtracking process from here stands on just one while cycle. The param {@code amount} is being decremented every single round
     *     and when it reaches 0, the backtracking process ends. {@link #amount} should not be larger than sum of all amounts in {@link #evidences}
     * </p>
     * @param amount how many of that product will be sold
     * @param NPCPrice for how much will this product be sold
     * @return how much the player made
     * @throws WrongItemException if the {@code amount} is larger than {@link #amount}
     */
    public int sellItem(int amount, int NPCPrice) throws WrongItemException {
        moveWithAmount(-amount);
        this.sellAmount += amount;
        int result = 0;
        while (amount != 0 && this.evidences.peek() != null) {
            int[] arr = this.evidences.peek().register(amount);
            amount = arr[0];
            result = result + (arr[3] * NPCPrice);
            if (arr[1] == 0) {
                this.evidences.poll();
            }
            this.wholeBoughtPrice = this.wholeBoughtPrice - arr[2];
        }
        updateAveragePrice();
        this.wholeEarnings += result;
        return result;
    }

    private double calculateWholeTimeAveragePrice(){
        double average = 0;
        for (double d : averagePrices){
            average += d;
        }
        return average / averagePrices.size();
    }

    private double calculateAverageSellPrice(){
        if(this.sellAmount != 0){
            return (double) this.wholeEarnings / this.sellAmount;
        }
        return 0;
    }

    public String description(GameData gameData) {
        return "Amount:" + Important.parseMoney(this.amount) + "X" + "\n" + "To be delivered:" + Important.parseMoney(gameData.getPlayer().findNumberOfUndelivered(this.name)) + "X" +"\n" + "Avg. Buy Price:" + Important.parseMoney((int) averageBuyPrice) + " FR" + "\n" + "Total earnings:" + Important.parseMoney(wholeEarnings) + " FR"+  "\n" + "Total Spent:" + Important.parseMoney(wholePrice) + " FR" +"\n"+ "Total Avg. buy price:" + Important.parseMoney((int) calculateWholeTimeAveragePrice()) + " FR" + "\n" + "Total Avg. sell price:" + Important.parseMoney((int) calculateAverageSellPrice()) + " FR";
    }
    public int getAmount() {
        return amount;
    }

    public double getAverageBuyPrice() {
        return averageBuyPrice;
    }

    public int getWholeBoughtPrice() {
        return wholeBoughtPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellAmount() {
        return sellAmount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
