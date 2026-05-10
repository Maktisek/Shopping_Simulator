package Shops.Utilities;

import java.io.Serializable;

public class ShopKey implements Serializable {

    private int price;
    private boolean unlocked;

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

}
