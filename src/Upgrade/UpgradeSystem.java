package Upgrade;

import java.util.HashMap;

public class UpgradeSystem {

    private final HashMap<UpgradeNames, Upgrade> upgrades;
    private double rebirthMultiplier;

    public UpgradeSystem() {
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

    public void newRebirth(){
        this.rebirthMultiplier += 0.1;
    }
}
