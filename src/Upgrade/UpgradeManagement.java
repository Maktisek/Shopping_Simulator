package Upgrade;

import java.util.HashMap;

public class UpgradeManagement {

    private final HashMap<UpgradeNames, Upgrade> upgrades;
    private Rebirth rebirth;

    public UpgradeManagement() {
        this.rebirth = new Rebirth(10000000);
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
        return (int) (this.upgrades.get(name).dataInfo() * rebirth.getMultiplier());
    }

    public int getUpgradePrice(UpgradeNames name){
        return this.upgrades.get(name).priceInfo();
    }

    public int getUpgradeLevel(UpgradeNames name){
        return this.upgrades.get(name).levelInfo();
    }

    public void setNewRebirth(){
        this.rebirth.newRebirth();
    }


    public HashMap<UpgradeNames, Upgrade> getUpgrades() {
        return upgrades;
    }

    public Rebirth getRebirth() {
        return rebirth;
    }

    public void setRebirth(Rebirth rebirth) {
        this.rebirth = rebirth;
    }

    @Override
    public String toString() {
        return "UpgradeManagement{" +
                "upgrades=" + upgrades +
                ", rebirthMultiplier=" + rebirth +
                '}';
    }
}
