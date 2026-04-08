package Upgrade;

import java.util.HashMap;

public class UpgradeManagement {

    private final HashMap<UpgradeNames, Upgrade> upgrades;
    private double rebirthMultiplier;

    public UpgradeManagement() {
        this.rebirthMultiplier = 1;
        this.upgrades = new HashMap<>();
        loadUpgrades();
    }

    private void loadUpgrades(){
        this.upgrades.put(UpgradeNames.BUY, new MaxBoughtUpgrade());
        this.upgrades.put(UpgradeNames.SELL, new MaxSoldUpgrade());
        this.upgrades.put(UpgradeNames.STOCK, new MaxStockUpgrade());
    }

    public void levelUpUpgrade(UpgradeNames name){
        this.upgrades.get(name).levelUp();
    }

    public int getUpgradeData(UpgradeNames name){
        return (int) (this.upgrades.get(name).dataInfo() * rebirthMultiplier);
    }

    public int getUpgradePrice(UpgradeNames name){
        return this.upgrades.get(name).priceInfo();
    }

    public void newRebirth(){
        this.rebirthMultiplier += 0.1;
    }

    public HashMap<UpgradeNames, Upgrade> getUpgrades() {
        return upgrades;
    }

    public double getRebirthMultiplier() {
        return rebirthMultiplier;
    }

    public void setRebirthMultiplier(double rebirthMultiplier) {
        this.rebirthMultiplier = rebirthMultiplier;
    }
}
