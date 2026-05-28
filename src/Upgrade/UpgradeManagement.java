package Upgrade;

import Upgrade.Rebirth.Rebirth;
import Upgrade.Upgrades.MaxBoughtUpgrade;
import Upgrade.Upgrades.MaxSoldUpgrade;
import Upgrade.Upgrades.MaxStockUpgrade;
import Upgrade.Utilities.UpgradeNames;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class represents a management of upgrades.
 * <p>
 *     Upgrades are stored in HashMap {@link #upgrades} where:
 *     <ul>
 *         <li>Key is {@link UpgradeNames}</li>
 *         <li>Value is {@link Upgrade}</li>
 *     </ul>
 * </p>
 * Also, {@link Rebirth} is stored here.
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class UpgradeManagement implements Serializable {

    private final HashMap<UpgradeNames, Upgrade> upgrades;
    private Rebirth rebirth;

    public UpgradeManagement() {
        this.rebirth = new Rebirth(1000);
        this.upgrades = new HashMap<>();
        loadUpgrades();
    }

    /**
     * This method loads {@link #upgrades} with all needed upgrades.
     */
    private void loadUpgrades(){
        this.upgrades.put(UpgradeNames.BUY, new MaxBoughtUpgrade());
        this.upgrades.put(UpgradeNames.SELL, new MaxSoldUpgrade());
        this.upgrades.put(UpgradeNames.STOCK, new MaxStockUpgrade());
    }

    public void levelUpUpgrade(UpgradeNames name){
        this.upgrades.get(name).levelUp();
    }

    public int getUpgradeData(UpgradeNames name){
        return (int) (this.upgrades.get(name).dataInfo() * rebirth.getUpgradeMultiplier());
    }

    public int getUpgradePrice(UpgradeNames name){
        return this.upgrades.get(name).priceInfo();
    }

    public int getUpgradeLevel(UpgradeNames name){
        return this.upgrades.get(name).levelInfo();
    }

    public void setNewRebirth(){
        this.rebirth.updateRebirth();
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

}
