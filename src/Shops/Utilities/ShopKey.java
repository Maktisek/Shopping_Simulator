package Shops.Utilities;

import java.io.Serializable;

/**
 * This class represents a system for shop unlocking and it is just POJO.
 * <p>
 *     It manages whether the player can reach that shop or not.
 * </p>
 * <p>
 *     <ul>
 *         <li>{@link #price} stands for how much does the shop costs</li>
 *         <li>{@link #unlocked} represents a boolean value, which determines if the shop has been already unlocked</li>
 *         <li>{@link #rebirthLevel} tells how big level player has to be in order to unlock or reach this shop. This value has the biggest
 *         preference when it comes to checking if the player can change the shop</li>
 *     </ul>
 * </p>
 * @author Matěj Pospíšil
 */
public class ShopKey implements Serializable {

    private int price;
    private boolean unlocked;
    private int rebirthLevel;

    public ShopKey() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public int getRebirthLevel() {
        return rebirthLevel;
    }

    public void setRebirthLevel(int rebirthLevel) {
        this.rebirthLevel = rebirthLevel;
    }
}
