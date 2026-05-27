package Items;

import Items.Exceptions.WrongItemException;
import Items.Utilities.AmountManager;
import UI.MainUI.ShopUI.Items.ItemSpecification;
import Upgrade.Rebirth.Rebirth;
import Utilities.Important;

import java.io.Serializable;

/**
 * This class represents an item stored in {@link Shops.Shop}
 * <p>
 *     It implements {@link Item} so {@link #item} is needed.
 * </p>
 * This class also features plenty of interesting price calculating mechanics, such as:
 * <p>
 *     <ul>
 *         <li>{@link #penalization} represents a coefficient of price change.
 *         The interval is <0.9;1.2> and the price is multiplied by that.
 *         Every single purchase increments it and every single new day decrements it.</li>
 *         <li>{@link #priceSensitivity} represents how aggressively will the price spike after buying a product.
 *         Larger it is, less it spikes. The spike is created only the day after.</li>
 *     </ul>
 * </p>
 * @author Matěj Pospíšil
 */
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
        return Math.min(result, 0.125);
    }

    /**
     * This method updates {@link #penalization} by its input.
     * <p>
     *     The higher the current penalization is the slower it rises.
     * </p>
     * <p>
     *     The penalization belongs to interval <0.9;1.2>
     * </p>
     * <p>
     *     Watch out, {@link #penalization} is always incremented, never decremented.
     * </p>
     * @param change stands for how much should be {@link #penalization} incremented.
     * @param rebirthCoefficient comes from {@link Rebirth#getPenalizationMultiplier()}, which tenderly softens the changes
     */
    public void updatePenalization(double change, double rebirthCoefficient) {
        if(change < 0){
            change = -1 * change;
        }
        double afterChange = (this.penalization + ((change * (5 / (penalization) / 2))) * rebirthCoefficient);
//        if (afterChange < 0.9) {
//            this.penalization = 0.9;
//        } else

        if (afterChange > 1.2) {
            this.penalization = 1.2;
        } else {
            this.penalization = afterChange;
        }
    }

    /**
     * This method updates {@link #penalization} by its input.
     * <p>
     *     The update is calculated normally - penalization is only incremented by {@code change} and reduced by {@code rebirthCoefficient}.
     * </p>
     * <p>
     *     Watch out, {@link #penalization} is always decremented, never incremented.
     * </p>
     * Use this method only when setting a new day, because it does not check upper bound.
     * @param change stands for how much should be {@link #penalization} decremented.
     * @param rebirthCoefficient comes from {@link Rebirth#getPenalizationMultiplier()}, which tenderly softens the changes
     */
    public void newDayPenalization(double change, double rebirthCoefficient) {
        if(change > 0){
            change = -1 * change;
        }
        double afterChange = (this.penalization + (change) * rebirthCoefficient);
        this.penalization = Math.max(afterChange, 0.9);
    }

    public void updateCurrentDayAmount(int change) {
        this.currentDayAmount += change;
    }

    public void resetCurrentDayAmount() {
        this.currentDayAmount = 0;
    }

    public ItemBase getItemBase() {
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
