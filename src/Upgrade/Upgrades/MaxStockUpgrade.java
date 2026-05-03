package Upgrade.Upgrades;

import Upgrade.UpgradeBasicType;
import Upgrade.Utilities.UpgradeNames;

public class MaxStockUpgrade extends UpgradeBasicType {

    public MaxStockUpgrade() {
        setData(100);
        setPrice(150);
    }


    @Override
    public UpgradeNames nameInfo() {
        return UpgradeNames.STOCK;
    }
}
