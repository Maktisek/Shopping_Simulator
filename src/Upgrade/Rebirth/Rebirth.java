package Upgrade.Rebirth;

import java.io.Serializable;

/**
 * This class represents a rebirth.
 *  <p>
 *      A rebirth is a game mechanic, which allows player to play the game again, but with some advantages.
 *      In this case the advantages are:
 *  </p>
 *  <ul>
 *      <li>{@link #upgradeMultiplier} highers bounds of all instances of {@link Upgrade.Upgrade}</li>
 *      <li>{@link #penalizationMultiplier} make penalization growth on {@link Items.ItemShop} slower</li>
 *  </ul>
 *  The higher level is acquired the better the multipliers are.
 *  <p>
 *      Also every rebirth has its own:
 *      <ul>
 *          <li>{@link #price}</li>
 *          <li>{@link #level}</li>
 *          <li>{@link #capital}</li>
 *      </ul>
 *      {@link #capital} is mentionable, because it can be little misleading. After every rebirth the game resets to its starting state,
 *      but few things remains still untouched. Players money is reset after new rebirth so he has to get some capital. There is the
 *      place where {@link #capital} comes in handy.
 *  </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class Rebirth implements Serializable {

    private double upgradeMultiplier;
    private double penalizationMultiplier;
    private long price;
    private int level;
    private long capital;

    public Rebirth(int price) {
        this.upgradeMultiplier = 1;
        this.price = price;
        this.level = 1;
    }

    /**
     * This method starts a chain of methods, which are responsible for setting new rebirth
     */
    public void updateRebirth(){
        updateUpgradeMultiplier();
        updatePenalizationMultiplier();
        updateCapital();
        updatePrice();
        this.level++;
    }

    /**
     * This method updates {@link #upgradeMultiplier} and it is used everytime after the player purchases new level.
     * <p>
     *     The higher the level is, the slower {@link #upgradeMultiplier} increases.
     * </p>
     * Maybe it is too drastic on the start, so maybe in future updates this method will be changed.
     */
    private void updateUpgradeMultiplier(){
        this.upgradeMultiplier = this.upgradeMultiplier * (1 + ((double) 1 / level));
    }

    /**
     * This method updates {@link #penalizationMultiplier} and it is used everytime after the player purchases new level.
     * <p>
     *     This method is actually pretty cool, because of the hyperbolic progress.
     *     When the level is powered by so little number like {@code 0.005} the progress is extremely slow and goes under 1, but above 0.
     * </p>
     * It is needed for {@link #penalizationMultiplier} to grow slowly, because it manipulates with extremely reactive values.
     */
    private void updatePenalizationMultiplier(){
        this.penalizationMultiplier = (1 / Math.pow(level, 0.005));
    }

    /**
     * This method updates {@link #price} and it is used everytime after the player purchases new level.
     */
    private void updatePrice(){
        this.price = (long) (1000 * Math.pow(4, level));
    }

    /**
     * This method updates {@link #capital} and it is used everytime after the player purchases new level.
     * <p>
     *     {@link #capital} is updated to 25% of the {@link #price}.
     * </p>
     */
    private void updateCapital(){
        this.capital = (this.price / 100) * 25;
    }

    public double getUpgradeMultiplier() {
        return upgradeMultiplier;
    }

    public double getPenalizationMultiplier() {
        return penalizationMultiplier;
    }


    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getLevel() {
        return level;
    }

    public long getCapital() {
        return capital;
    }

}
