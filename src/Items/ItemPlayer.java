package Items;


import Items.Exceptions.WrongEvidenceException;
import Items.Exceptions.WrongItemException;
import Items.Utilities.Evidence;
import Utilities.Important;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class ItemPlayer {

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
            throw new WrongItemException("WholeBoughtPrice must be over -1");
        } else {
            this.wholeBoughtPrice = afterMove;
            this.wholePrice += (shopPrice * amount);
        }
    }

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

    public int getAmount() {
        return amount;
    }

    public Queue<Evidence> getEvidences() {
        return evidences;
    }

    public double getAverageBuyPrice() {
        return averageBuyPrice;
    }

    public void setAverageBuyPrice(double averageBuyPrice) {
        this.averageBuyPrice = averageBuyPrice;
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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setWholeBoughtPrice(int wholeBoughtPrice) {
        this.wholeBoughtPrice = wholeBoughtPrice;
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

    @Override
    public String toString() {
        return "Amount:" + Important.parseMoney(this.amount) + "X" + "\n" + "Avg. Buy Price:" + Important.parseMoney((int) averageBuyPrice) + " FR" + "\n" + "Total earnings:" + Important.parseMoney(wholeEarnings) + " FR"+  "\n" + "Total Spent:" + Important.parseMoney(wholePrice) + " FR" +"\n"+ "Total Avg. buy price:" + Important.parseMoney((int) calculateWholeTimeAveragePrice()) + " FR" + "\n" + "Total Avg. sell price:" + Important.parseMoney((int) calculateAverageSellPrice()) + " FR";
    }
}
