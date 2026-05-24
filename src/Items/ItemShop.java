package Items;

import Items.Exceptions.WrongItemException;
import Items.Utilities.AmountManager;
import UI.MainUI.ShopUI.Items.ItemSpecification;
import Utilities.Important;

import java.io.Serializable;

public class ItemShop implements Item, Serializable {

    private ItemBase item;
    private double penalization;
    private int currentDayAmount;
    private int priceSensitivity;
    private int daysToBeDelivered;
    private AmountManager amountManager;

    public ItemShop() {
    }

    public void updatePrice() throws WrongItemException {
        this.item.setCurrentPrice((int) Math.round((this.item.getBasePrice() * penalization * (1 + calculateBonusPenalization()))));
    }

    private double calculateBonusPenalization() {
        double result = ((double) currentDayAmount / this.priceSensitivity);
        if (item.getName().equalsIgnoreCase("APPLE")){
            System.out.println("For item " + item.getName());
            System.out.println("Calculated: " + result);
            System.out.println("Returning: "+ Math.min(result, 0.125));
            System.out.println("Penalization: " + penalization);
            System.out.println("Final penalization: " + penalization * (1 + Math.min(result, 0.125)));
        }
        return Math.min(result, 0.125);
    }

    public void updatePenalization(double change, double rebirthCoefficient) {
        double afterChange = (this.penalization + (change * (5 / (penalization) / 2))) * rebirthCoefficient;
        if (afterChange < 0.9) {
            this.penalization = 0.9;
        } else if (afterChange > 1.2) {
            this.penalization = 1.2;
        } else {
            this.penalization = afterChange;
        }
    }

    public void newDayPenalization(double change, double rebirthCoefficient) {
        double afterChange = (this.penalization + change) * rebirthCoefficient;
        this.penalization = Math.max(afterChange, 0.9);
    }

    public void updateCurrentDayAmount(int change) {
        this.currentDayAmount += change;
    }

    public void resetCurrentDayAmount() {
        this.currentDayAmount = 0;
    }

    public ItemBase getItem() {
        return item;
    }

    @Override
    public String specification() {
        return this.item.information(ItemSpecification.SHOP) + "\n" + "Delivery time:" + daysToBeDelivered + " days" + "\n" +  "Supply:" + Important.parseMoney(amountManager.getCurrent());
    }

    public void setItem(ItemBase item) {
        this.item = item;
    }

    public double getPenalization() {
        return penalization;
    }

    public void setPenalization(double penalization) {
        this.penalization = penalization;
    }

    public int getCurrentDayAmount() {
        return currentDayAmount;
    }

    public void setCurrentDayAmount(int currentDayAmount) {
        this.currentDayAmount = currentDayAmount;
    }

    public int getPriceSensitivity() {
        return priceSensitivity;
    }

    public void setPriceSensitivity(int priceSensitivity) {
        this.priceSensitivity = priceSensitivity;
    }

    public int getDaysToBeDelivered() {
        return daysToBeDelivered;
    }

    public void setDaysToBeDelivered(int daysToBeDelivered) {
        this.daysToBeDelivered = daysToBeDelivered;
    }

    public AmountManager getAmountManager() {
        return amountManager;
    }

    public void setAmountManager(AmountManager amountManager) {
        this.amountManager = amountManager;
    }


}
