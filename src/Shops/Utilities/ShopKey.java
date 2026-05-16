package Shops.Utilities;

import java.io.Serializable;

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
