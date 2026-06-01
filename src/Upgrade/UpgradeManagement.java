package Upgrade;

import Upgrade.Rebirth.Rebirth;
import Upgrade.Utilities.UpgradeType;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class represents a management of upgrades.
 * <p>
 *     Upgrades are stored in HashMap {@link #upgrades} where:
 *     <ul>
 *         <li>Key is {@link UpgradeType}</li>
 *         <li>Value is {@link Upgrade}</li>
 *     </ul>
 * </p>
 * Also, {@link Rebirth} is stored here.
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class UpgradeManagement implements Serializable {

    private final HashMap<UpgradeType, Upgrade> upgrades;
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
//        this.upgrades.put(UpgradeType.BUY, new MaxBoughtUpgrade());
//        this.upgrades.put(UpgradeType.SELL, new MaxSoldUpgrade());
//        this.upgrades.put(UpgradeType.STOCK, new MaxStockUpgrade());
    }

    public void addUpgrade(UpgradeType key, Upgrade value){
        this.upgrades.put(key, value);
    }

    public void levelUpUpgrade(UpgradeType name){
        this.upgrades.get(name).levelUp();
    }

    public int getUpgradeData(UpgradeType name){
        return (int) (this.upgrades.get(name).dataInfo() * rebirth.getUpgradeMultiplier());
    }

    public long getUpgradePrice(UpgradeType name){
        return this.upgrades.get(name).priceInfo();
    }

    public long getUpgradeLevel(UpgradeType name){
        return this.upgrades.get(name).levelInfo();
    }

    public void setNewRebirth(){
        this.rebirth.updateRebirth();
    }

    public HashMap<UpgradeType, Upgrade> getUpgrades() {
        return upgrades;
    }

    public Rebirth getRebirth() {
        return rebirth;
    }

    public void setRebirth(Rebirth rebirth) {
        this.rebirth = rebirth;
    }

}
