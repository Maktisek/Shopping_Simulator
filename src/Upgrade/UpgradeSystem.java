package Upgrade;

import java.util.HashMap;

public class UpgradeSystem {

    private final HashMap<UpgradeNames, Upgrade> upgrades;

    public UpgradeSystem() {
        this.upgrades = new HashMap<>();
        loadUpgrades();
    }

    private void loadUpgrades(){
        this.upgrades.put(UpgradeNames.BUY, new MaxBoughtUpgrade());
        this.upgrades.put(UpgradeNames.SELL, new MaxSoldUpgrade());
    }


}
