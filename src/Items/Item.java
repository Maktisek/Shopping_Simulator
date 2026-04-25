package Items;

import UI.MainUI.ShopUI.ItemSpecification;
import Utilities.Important;

public class Item {


    private ItemNames name;
    private int basePrice;
    private int currentPrice;
    private int wholePrice;
    private int numberOfPrices;
    private int daysToBeDelivered;

    public Item() {
    }

    public Item(ItemNames name, int currentPrice, int basePrice, int daysToBeDelivered) throws WrongItemException{
        this.name = name;
        setCurrentPrice(currentPrice);
        this.basePrice = basePrice;
        this.daysToBeDelivered = daysToBeDelivered;
    }

    public void setCurrentPrice(int currentPrice) throws WrongItemException {
        if (currentPrice > 0) {
            this.wholePrice += currentPrice;
            this.numberOfPrices++;
            this.currentPrice = currentPrice;
        } else {
            throw new WrongItemException("The price of " + name + " is under 0");
        }
    }

    private int calculateAveragePrice(){
        if(numberOfPrices == 0){
            return currentPrice;
        }
        return wholePrice / numberOfPrices;
    }

    public Item copy() throws WrongItemException {
        return new Item(this.name, this.currentPrice, this.basePrice, this.daysToBeDelivered);
    }


    public void setName(ItemNames name) {
        this.name = name;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public ItemNames getName() {
        return name;
    }


    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public int getWholePrice() {
        return wholePrice;
    }

    public void setWholePrice(int wholePrice) {
        this.wholePrice = wholePrice;
    }

    public int getNumberOfPrices() {
        return numberOfPrices;
    }

    public void setNumberOfPrices(int numberOfPrices) {
        this.numberOfPrices = numberOfPrices;
    }

    public int getDaysToBeDelivered() {
        return daysToBeDelivered;
    }

    public void setDaysToBeDelivered(int daysToBeDelivered) {
        this.daysToBeDelivered = daysToBeDelivered;
    }

    public String information(ItemSpecification spec){
        switch (spec){
            case SHOP -> {
                return "Base price:" + Important.parseMoney(basePrice) + " FR" + "\n" + "Buy price:" + Important.parseMoney(currentPrice) + " FR"+ "\n" + "Average price:" + Important.parseMoney(calculateAveragePrice()) + " FR"+ "\n" + "Delivery time:" + daysToBeDelivered + " days";
            }
            case NPC -> {
                return "Base price:" + Important.parseMoney(basePrice) + " FR" + "\n" + "Sell price:" + Important.parseMoney(currentPrice)+ " FR"+ "\n" + "Average price:" + Important.parseMoney(calculateAveragePrice()) + " FR"+ "\n" + "Delivery time:" + daysToBeDelivered + " days";
            }
        }
        return "Issue";
    }

}
