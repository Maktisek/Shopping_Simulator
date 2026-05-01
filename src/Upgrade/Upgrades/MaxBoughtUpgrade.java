package Upgrade.Upgrades;

import Upgrade.UpgradeBasicType;
import Upgrade.Utilities.UpgradeNames;

public class MaxBoughtUpgrade extends UpgradeBasicType {

    public MaxBoughtUpgrade() {
        setData(10);
        setPrice(50);
    }

    @Override
    public UpgradeNames nameInfo() {
        return UpgradeNames.BUY;
    }
}
