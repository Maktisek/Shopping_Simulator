package Upgrade.Rebirth;

import java.io.Serializable;

public class Rebirth implements Serializable {

    private double upgradeMultiplier;
    private int price;
    private int level;
    private int capital;

    public Rebirth(int price) {
        this.upgradeMultiplier = 1;
        this.price = price;
        this.level = 1;
    }

    public void updateRebirth(){
        updateUpgradeMultiplier();
        updateCapital();
        updatePrice();
        this.level++;
    }

    private void updateUpgradeMultiplier(){
        this.upgradeMultiplier = this.upgradeMultiplier * (1 + (0.3/ level));
    }

    private void updatePrice(){
        this.price = (int) (this.price * (1 + (level * 0.04)));
    }

    private void updateCapital(){
        this.capital = (this.price / 100) * 5;
    }

    public double getUpgradeMultiplier() {
        return upgradeMultiplier;
    }

    public void setUpgradeMultiplier(double upgradeMultiplier) {
        this.upgradeMultiplier = upgradeMultiplier;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }
}
