package Upgrade;

import Upgrade.Utilities.UpgradeNames;

/**
 * This interface represents an upgrade.
 * <p>
 *     Every upgrade in the game has to implement this interface in order to be stored and manipulated correctly.
 * </p>
 * <p>
 *     Although, it is recommended creating an abstract class and implementing this interface there.
 *     This is completely optional.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public interface Upgrade {

    /**
     * This method levels up the upgrade.
     */
    void levelUp();

    /**
     * This method changes price of the upgrade.
     */
    void changePrice();

    /**
     * Getter for inner data.
     * @return the data
     */
    int dataInfo();

    /**
     * Getter for price.
     * @return the price
     */
    int priceInfo();

    /**
     * Getter for the level
     * @return the level
     */
    int levelInfo();

    /**
     * Getter for the upgrade type
     * @return the upgrade type
     */
    UpgradeNames nameInfo();
}
