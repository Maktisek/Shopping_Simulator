package Items;

import Items.Exceptions.WrongItemException;
import UI.MainUI.ShopUI.Items.ItemSpecification;
import Utilities.Important;

import java.io.Serializable;

public class ItemBase implements Serializable {


    private String name;
    private int basePrice;
    private int currentPrice;
    private int wholePrice;
    private int numberOfPrices;

    public ItemBase() {
    }

    public ItemBase(String name, int currentPrice, int basePrice) throws WrongItemException {
        this.name = name;
        setCurrentPrice(currentPrice);
        this.basePrice = basePrice;
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

    public ItemBase copy() throws WrongItemException {
        return new ItemBase(this.name, this.currentPrice, this.basePrice);
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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



    public String information(ItemSpecification spec){
        switch (spec){
            case SHOP -> {
                return "Base price:" + Important.parseMoney(basePrice) + " FR" + "\n" + "Buy price:" + Important.parseMoney(currentPrice) + " FR"+ "\n" + "Average price:" + Important.parseMoney(calculateAveragePrice()) + " FR";
            }
            case NPC -> {
                return "Base price:" + Important.parseMoney(basePrice) + " FR" + "\n" + "Sell price:" + Important.parseMoney(currentPrice)+ " FR"+ "\n" + "Average price:" + Important.parseMoney(calculateAveragePrice()) + " FR";
            }
        }
        return "Issue";
    }

}
