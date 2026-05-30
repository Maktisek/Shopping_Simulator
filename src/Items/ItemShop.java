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
 * @since   1.0 - (pre-release version)
 */
public class ItemShop implements Item, Serializable {

    private ItemBase item;
    private double penalization;
    @SuppressWarnings("unused")
    private double addPenalization;
    @SuppressWarnings("unused")
    private double coolDown;
    private int currentDayAmount;
    private int moveAbleDayAmount;
    @SuppressWarnings("unused")
    private int priceSensitivity;
    @SuppressWarnings("unused")
    private int daysToBeDelivered;
    @SuppressWarnings("unused")
    private AmountManager amountManager;

    public ItemShop() {
    }

    public void updatePrice() throws WrongItemException {
        this.item.setCurrentPrice(Math.round((this.item.getBasePrice() * penalization * (1 + calculateBonusPenalization()))));
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
    public void updatePenalization(int amount, double rebirthCoefficient) {
        double change = addPenalization * amount;
        if(change < 0){
            change = -1 * change;
        }
        double afterChange = (this.penalization + (change * rebirthCoefficient));
        if(afterChange > 1.2){
            this.penalization = 1.2;
            calculateUnpenalizedProducts(change, afterChange);
        }else {
            this.penalization = afterChange;
        }
    }

    private void calculateUnpenalizedProducts(double change, double afterChange){
        while (afterChange > 1.2){
            afterChange -= change;
            this.moveAbleDayAmount--;
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
     * @param rebirthCoefficient comes from {@link Rebirth#getPenalizationMultiplier()}, which tenderly softens the changes
     */
    public void newDayPenalization(int amount, double rebirthCoefficient) {
        double afterChange = (this.penalization - (coolDown * amount) * rebirthCoefficient);
        this.penalization = Math.max(afterChange, 0.9);
    }

    public void updateCurrentDayAmount(int change) {
        this.currentDayAmount += change;
        this.moveAbleDayAmount += change;
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

    public int getDaysToBeDelivered() {
        return daysToBeDelivered;
    }

    public AmountManager getAmountManager() {
        return amountManager;
    }

    public int getMoveAbleDayAmount() {
        return moveAbleDayAmount;
    }
}
